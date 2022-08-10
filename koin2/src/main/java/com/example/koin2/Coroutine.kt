package com.example.koin2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.koin2.databinding.ActivityCoroutineBinding
import kotlinx.coroutines.*


/*..
GlobalScope - continues executing even after activity/fragment is destroyed
CoroutineScope - have to be cancelled to prevent memory leaks
..*/
class Coroutine : AppCompatActivity() {

    val a = 10
    val b = 20
    ///////
    private var scope = CoroutineScope(CoroutineName("MyScope"))
    //CoroutineScope(Job() + Dispatchers.Default + CoroutineName("WithAllParams"))

    val coroutineScopeVar = CoroutineScope(Job()+Dispatchers.IO)
    lateinit var coroutineBinding : ActivityCoroutineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineBinding = DataBindingUtil.setContentView(this,R.layout.activity_coroutine)


        //////
        val scopeJob = scope.launch {
            val job1 = launch {
                while (isActive) {
                    //ensureActive()
                    yield() // to cancel the code flow in this launch job/sccope
                   //(or) delay(500L)
                    Log.d("revathi", "job1 is running"+this.coroutineContext.toString())
                }
            }
            delay(1000L)
            job1.cancel()
            job1.join() //(or) job1.cancelAndJoin() // to cancel the job
            Log.d("revathi","job 1 is cancelled")
        }
        //if a scope launch is cancelled all the jobs in the scope are cancelled
        //not preferred but used in main functions and tests
        runBlocking {//not a suspend fn. so we cant use in scope
            delay(1000L)
            Log.d("revathi","scopeJob is cancelled")
            scopeJob.cancelAndJoin()
        }
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("revathi","on scope launch : "+this.coroutineContext.toString())
            withContext(Dispatchers.Main) {//suspend function
                Log.d("revathi","withcontext : "+this.coroutineContext.toString()) //main dispatcher
            }
        }


        coroutineScopeVar.launch {
            //For temporary change of context for coroutine
            withContext(Dispatchers.Default){
                // Do CPU intensive task
            }
        }
        //jobJoin()

/*        coroutineBinding.textView2.text = "Gone to calculate sum of a & b"

        GlobalScope.launch {
            val result = async {
                calculateSum()
            }
            coroutineBinding.textView2.append("\nSum of a & b is: ${result.await()}")
        }
        coroutineBinding.textView2.append("\n\nCarry on with some other task while the coroutine is waiting for a result...")
        runBlocking {
            delay(3000L) // keeping jvm alive till calculateSum is finished
        }*/


        //globalScope()
        //async to return
/*        runBlocking {
            val job1 = this.async {
                delay(2000)
                500
            }

            val job2 = this.async {
                delay(2000)
                700
            }
            coroutineBinding.textView2.text =job1.await().toString() +"\n" + job2.await().toString()
            asyncToAwait()

        }*/


        //   global scope with variable  ==> o/p - step1step2step2....
/*        runBlocking {
            val job: Job = this.launch {
                delay(2000)
                coroutineBinding.textView2.append("step2")
            }
            coroutineBinding.textView2.text = "step1"
            //delay(5000)
            job.join()
            coroutineBinding.textView2.append("step2....")
            job.cancel()
        }*/
    }

    //Will launch activity with 3secs delay
    private fun globalScope() {
        GlobalScope.launch {
            delay(2000)
            Log.d("revathi","after")
            coroutineBinding.textView2.append("after delayed")
        }
        coroutineBinding.textView2.setText("message : ")
        Log.d("revathi","before")
        Toast.makeText(this,"before",Toast.LENGTH_SHORT).show()
        runBlocking { delay(3000) }
    }



    // suspend function will call inside coroutine/suspend function
    suspend fun asyncToAwait() {
        val job1 = GlobalScope.async {
            delay(2000)
            900
        }

        val job2 = GlobalScope.async {
            delay(2000)
            5000
        }
        coroutineBinding.textView2.text = job1.await().toString() +"\n" + job2.await().toString()
    }


    suspend fun calculateSum(): Int {
        delay(2000L) // simulate long running task
        return a + b
    }


    // Concurrently executes both sections
    suspend fun doWorld() = coroutineScope { // this: CoroutineScope
        launch {
            delay(2000L)
            println("World 2")
        }
        launch {
            delay(1000L)
            println("World 1")
        }
        println("Hello")
    }


private fun jobJoin() {
    runBlocking {

        val job1: Job = launch { // launch a new coroutine and keep a reference to its Job
            delay(1000L)
            println("World!")
        }
        println("Hello")
        job1.join() // wait until child coroutine completes
        println("Done")
        job1.cancel()
        // Sequentially executes doWorld followed by "..."

        println("call other method")
        doWorld()
        println("method completed")
    }
}
}