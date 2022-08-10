package com.example.koin2

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import android.speech.SpeechRecognizer
import android.content.Intent
import android.graphics.Color
import android.media.*
import android.os.Handler
//import kotlinx.android.synthetic.main.activity_main.*

class SecondActivity : AppCompatActivity() {

    var output: String? = null
    var mediaRecorder: MediaRecorder? = null
    var mediaPlayer: MediaPlayer? = null
    var state: Boolean = false
    var ar: AudioRecord? = null
    var mRecognizer: SpeechRecognizer? = null
    var mRecognizerIntent: Intent? = null
    var mHandler: Handler? = null

    var visualizerView: VisualizerView? = null
    private var handler: Handler? = null
    val REPEAT_INTERVAL = 40

    lateinit var myVisualView: MyVisualView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var button_start = findViewById<Button>(R.id.button_start)
        var button_stop = findViewById<Button>(R.id.button_stop)
        var button_play = findViewById<Button>(R.id.button_play)
        var record_text = findViewById<TextView>(R.id.textView)
        myVisualView= findViewById<View>(R.id.siriView) as MyVisualView
       // visualizerView=findViewById<VisualizerView>(R.id.visualizer)

        button_stop.isEnabled = false
        mediaRecorder = MediaRecorder()
        mediaPlayer = MediaPlayer()
        record_text.visibility = View.GONE

       // mHandler =  Handler(getMainLooper());
        //mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this, permissions, 0)
            button_start.isEnabled = true
        }

        output = this.getExternalFilesDir(Environment.DIRECTORY_DCIM).toString() + "/record.mp3"
//          output = Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3"
/*      mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setOutputFile(output)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)*/

        // create the Handler for visualizer update
        handler = Handler()



        button_start.setOnClickListener {
            button_stop.isEnabled = true
            button_start.isEnabled = false
            button_play.isEnabled = false
            record_text.visibility = View.VISIBLE
            startRecording()

            myVisualView.apply {
                updateSpeaking(true)
                updateViewColor(Color.BLACK)
                updateAmplitude(0.5f)
                updateSpeed(-0.1f)
            }
        }
        button_stop.setOnClickListener {
            button_start.isEnabled = true
            button_stop.isEnabled = false
            button_play.isEnabled = true
            record_text.visibility = View.INVISIBLE
            stopRecording()
            myVisualView.apply {
                updateSpeaking(false)
            }
        }
        button_play.setOnClickListener {
            button_play.isEnabled = false
            playRecording()
        }
        mediaPlayer!!.setOnCompletionListener {
            button_play.isEnabled = true
        }

    }

    private fun isMicAvailable(audioRecord: AudioRecord): Boolean {
        audioRecord.startRecording()
        val isAvailable = audioRecord.recordingState == AudioRecord.RECORDSTATE_RECORDING
        audioRecord.stop()
        audioRecord.release()
        return isAvailable
    }

    private fun playRecording() {
        try {
            mediaPlayer?.setDataSource(output)
            mediaPlayer?.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Toast.makeText(this, "playing......", Toast.LENGTH_SHORT).show()
        mediaPlayer?.start()
    }

    private fun stopRecording() {
        if (state) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
            mediaRecorder = null

       //     handler?.removeCallbacks(updateVisualizer);
       //     visualizerView?.clear();
            Toast.makeText(this, "Recording stopped!! path : " + output, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startRecording() {
        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFile(output)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        try {
            mediaRecorder?.prepare()
            state = true
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaRecorder?.start()
        mediaRecorder?.setAudioSamplingRate(50)

       // handler?.post(updateVisualizer);
/*        Log.d("revathi"," recognizing  "+mediaRecorder?.maxAmplitude)
        mHandler!!.post {
            if (mRecognizer != null) {
                val recognizerIntent =
                    Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                recognizerIntent.putExtra(
                    RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)
                mRecognizer!!.startListening(recognizerIntent)
               Log.d("revathi","started recognizing")*/
/*                while () {
                    Log.d("revathi"," recognizing ..... "+mediaRecorder?.maxAmplitude)
                    Toast.makeText(this, "listening", Toast.LENGTH_LONG).show()
                }*/
            //}
        //}
        //  textView.append("detect")
    }

    private fun stopPlaying() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        if (mRecognizer != null) {
            mRecognizer!!.destroy();
            mRecognizer = null;
        }
        super.onDestroy()
    }

    // updates the visualizer every 50 milliseconds
    var updateVisualizer: Runnable = object : Runnable {
        override fun run() {
            if (state) // if we are already recording
            {
                // get the current amplitude
                val x: Int = mediaRecorder!!.getMaxAmplitude()
                visualizerView!!.addAmplitude(x) // update the VisualizeView
                visualizerView!!.invalidate() // refresh the VisualizerView

                // update in 40 milliseconds
                handler!!.postDelayed(this, REPEAT_INTERVAL.toLong())
            }
        }
    }
/*    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        handler!!.post(updater)
    }*/
    val updater: Runnable = object : Runnable {
        override fun run() {
            handler!!.postDelayed(this, 1)
            val maxAmplitude: Int? = mediaRecorder?.getMaxAmplitude()
            if (maxAmplitude != 0) {
                visualizerView!!.addAmplitude(maxAmplitude!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }
}


