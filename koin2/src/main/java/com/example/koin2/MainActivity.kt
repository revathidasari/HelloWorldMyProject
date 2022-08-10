package com.example.koin2

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

class MainActivity : AppCompatActivity() {
    //Binding------ private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val textKoinData = this.findViewById<TextView>(R.id.textViewkoin)
        val b = findViewById<Button>(R.id.button)
Log.d("revathi","koin activity")
        //Binding------ binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //startKoin(this, listOf(appcheck, access))
        //   val student : Student by inject()

        /* without koin */
         val course = Course()
         val friend = Friend()
         val student = Student(course, friend)

        textKoinData?.text = student.doWork()
        //Binding------  binding.textViewkoin.text = student.doWork()

//        val student1 : Student_1 by inject()
//        textKoinData.append("\n"+student1.passion())

        b.setOnClickListener{
            //startActivity(Intent(this,TimePickerSpinner::class.java))
            //startActivity(Intent(this,SecondActivity::class.java))
            startActivity(Intent(this,SecondActivity::class.java))
        }
    }

    /* to uninstall an package */
    fun uninstallPackage(){
        val intent = Intent(Intent.ACTION_UNINSTALL_PACKAGE)
        intent.data = Uri.parse("package:com.example.koin2")
        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true)
        startActivity(intent)
    }

    /* Hide icon on Launcher screen or App tray by COMPONENT_ENABLED_STATE_DISABLED */
    fun hideIcon() {
        // To hide icon use this:  & un hide : COMPONENT_ENABLED_STATE_ENABLED
      val p = packageManager
        val componentName = ComponentName(this, MainActivity::class.java)
        p.setComponentEnabledSetting(componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
    }

    /* Un-Hide/Show icon on Launcher screen or App tray by COMPONENT_ENABLED_STATE_ENABLED */
    fun unHideIcon() {
      val p = packageManager
        val componentName = ComponentName(this, MainActivity::class.java)
        p.setComponentEnabledSetting(componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
    }
}