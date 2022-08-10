package com.example.koin2

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.AudioRecord

import android.media.MediaRecorder
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class AudioRecordUsage : AppCompatActivity() {

    val AUDIO_SOURCE = MediaRecorder.AudioSource.MIC // for raw audio, use MediaRecorder.AudioSource.UNPROCESSED, see note in MediaRecorder section

    val SAMPLE_RATE = 8000
    val CHANNEL_CONFIG: Int = AudioFormat.CHANNEL_IN_MONO
    val AUDIO_FORMAT: Int = AudioFormat.ENCODING_PCM_16BIT
    val BUFFER_SIZE_RECORDING = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT)
    var audioRecord: AudioRecord? = null
    private var recordingThread: Thread? = null
    private var isRecording = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_record_usage)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        audioRecord = AudioRecord(AUDIO_SOURCE, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE_RECORDING)

        var start = findViewById<Button>(R.id.bstart)
        var stop = findViewById<Button>(R.id.bstop)

        start.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"recording",Toast.LENGTH_SHORT).show()
            startRecording()
        })


        stop.setOnClickListener(){
            stopRecording()
        }
    }

    private fun stopRecording() {
        if (audioRecord!=null) {
            isRecording = false
            audioRecord?.stop()
            Toast.makeText(this,"stopped",Toast.LENGTH_SHORT).show()
            audioRecord?.release()
            audioRecord = null
            recordingThread = null
        }

    }

    private fun startRecording() {
        if (audioRecord!!.state != AudioRecord.STATE_INITIALIZED) { // check for proper initialization
            Log.d("revathi", "error initializing " )
            return
        }
        audioRecord!!.startRecording()
        Toast.makeText(this,"recording",Toast.LENGTH_SHORT).show()
        isRecording = true
        recordingThread = Thread({ writeAudioData() }, "AudioRecorder Thread")
        recordingThread!!.start()

    }

    private fun writeAudioData() { // to be called in a Runnable for a Thread created after call to startRecording()
        val fileName = "/sdcard/abcd.mp3"
        val data =
            ByteArray(BUFFER_SIZE_RECORDING / 2) // assign size so that bytes are read in in chunks inferior to AudioRecord internal buffer size
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(fileName) //fileName is path to a file, where audio data should be written
        } catch (e: FileNotFoundException) {
// handle error
        }
        while (isRecording) { // continueRecording can be toggled by a button press, handled by the main (UI) thread
            val read = audioRecord!!.read(data, 0, data.size)
            try {
                outputStream?.write(data, 0, read)
//                Toast.makeText(this,"written to file",Toast.LENGTH_SHORT).show()

            } catch (e: IOException) {
                Log.d("revathi", "exception while writing to file")
                e.printStackTrace()
            }
        }
        try {
            outputStream?.flush()
            outputStream?.close()
        } catch (e: IOException) {
            Log.d("revathi", "exception while closing output stream " + e.toString())
            e.printStackTrace()
        }
/*        // Clean up
        audioRecord!!.stop()
        audioRecord!!.release()
        audioRecord = null*/
    }
}



/*

    private void startRecording() {
            final int CHANNELCONFIG = AudioFormat.CHANNEL_IN_MONO;
            String filename = getTempFilename();
            OutputStream os = null;

            try {
                os = new FileOutputStream(filename);
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }

            bufferSize =                 AudioRecord.getMinBufferSize(FREQUENCY,CHANNELCONFIG,AUDIO_FORMAT);
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,FREQUENCY,CHANNELCONFIG,AUDIO_FORMAT,bufferSize);

            audioData = new byte[bufferSize];

            audioRecord.startRecording();

            int read = 0;

            while (recording) {
                read = audioRecord.read(audioData,0,bufferSize);
                if(AudioRecord.ERROR_INVALID_OPERATION != read){
                    try {
                        os.write(audioData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                os.close();
            } catch (IOException io) {
                io.printStackTrace();
            }

        }

        private void playRecording() {

            String fileName = getFilename();
            File file = new File(fileName);

            byte[] audioData = null;

            try {
                InputStream inputStream = new FileInputStream(fileName);

                int minBufferSize =         AudioTrack.getMinBufferSize(44100,AudioFormat.CHANNEL_OUT_MONO,         AudioFormat.ENCODING_PCM_16BIT);
                audioData = new byte[minBufferSize];

                AudioTrack audioTrack = new         AudioTrack(AudioManager.STREAM_MUSIC,FREQUENCY,AudioFormat.CHANNEL_OUT_MONO,AUDI        O_FORMAT,minBufferSize,AudioTrack.MODE_STREAM);
                audioTrack.play();
                int i=0;

                while((i = inputStream.read(audioData)) != -1) {
                    audioTrack.write(audioData,0,i);
                }

            } catch(FileNotFoundException fe) {
                Log.e(LOG_TAG,"File not found");
            } catch(IOException io) {
                Log.e(LOG_TAG,"IO Exception");
            }
        }
 */

/*--------------------qr code changes for generating------------------------*/
/*
lateinit var currentConfig: WifiConfiguration
lateinit var hotspotReservation: WifiManager.LocalOnlyHotspotReservation
lateinit var wifiManager: WifiManager
var qrgEncoder: QRGEncoder? = null
var key: String? = null
var ussid: String? = null

val PORT_ADDRESS = 50005

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        wifiManager = this.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if(wifiManager.wifiState == WifiManager.WIFI_STATE_ENABLED) {
            binding.ipPortDetails.visibility = View.VISIBLE
            qrGenerator("ip_port", (wifiManager.connectionInfo.ipAddress).toString()+" &   "+PORT_ADDRESS)
        }

        fun getIPAddress(useIPv4: Boolean): String? {
            try {
                val interfaces: List<NetworkInterface> =
                    Collections.list(NetworkInterface.getNetworkInterfaces())
                for (intf in interfaces) {
                    val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
                    for (addr in addrs) {
                        if (!addr.isLoopbackAddress) {
                            val sAddr = addr.hostAddress
                            Log.d("revathi"," get ip address "+sAddr+"]]"+sAddr.indexOf(':'));
                            val isIPv4 = sAddr.indexOf(':') < 0
                            if (useIPv4) {
                                if (isIPv4) return sAddr
                            } else {
                                if (!isIPv4) {
                                    val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                    return if (delim < 0) sAddr.toUpperCase() else sAddr.substring(
                                        0,
                                        delim
                                    ).toUpperCase()
                                }
                            }
                        }
                    }
                }
            } catch (ignored: Exception) {
            }
            return ""
        }
private fun checkAndRequestPermissions(): Boolean {
    val fineLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

    val listPermissionsNeeded: MutableList<String> = ArrayList()
    if (fineLoc != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    if (!listPermissionsNeeded.isEmpty()) {
        ActivityCompat.requestPermissions(
            this,
            listPermissionsNeeded.toTypedArray(),1)
        return true
    }
    return true
}
@RequiresApi(Build.VERSION_CODES.O)
private fun turnOnHotspot() {

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return
    }
    wifiManager.startLocalOnlyHotspot(object : WifiManager.LocalOnlyHotspotCallback() {
        @RequiresApi(Build.VERSION_CODES.R)
        override fun onStarted(reservation: WifiManager.LocalOnlyHotspotReservation) {
            super.onStarted(reservation)
            hotspotReservation = reservation
            currentConfig = hotspotReservation.wifiConfiguration!!
            if (currentConfig!=null) {
                key = hotspotReservation.getWifiConfiguration()!!.preSharedKey
                ussid = hotspotReservation.getWifiConfiguration()!!.SSID
                Log.d("DiagApp : Hotspot", "STARTED THE HOTSPOT KEY: $key USSID: $ussid")
            }
            qrGenerator("wifi", ussid+ " & "+key)
            qrGenerator("ip_port", getIPAddress(true)+ " & "+PORT_ADDRESS)
        }

        override fun onStopped() {
            super.onStopped()
        }

        override fun onFailed(reason: Int) {
            super.onFailed(reason)
        }
    },  Handler())
}

private fun turnOffHotspot() {
    if (hotspotReservation != null) {
        hotspotReservation.close()
    }
}
private fun qrGenerator(key:String, value:String) {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    var display: Display = windowManager.defaultDisplay
    var point: Point = Point()
    display.getSize(point)
    var width: Int = point.x
    var height: Int = point.y
    var dimen: Int = 0
    if (width < height)
        dimen = width
    else dimen = height
    dimen = dimen * 3 / 4

    when (key) {
        "wifi" -> {
            qrgEncoder = QRGEncoder(value, null, QRGContents.Type.TEXT, dimen)
            binding.wifiConnect.setImageBitmap(qrgEncoder!!.encodeAsBitmap())
        }
        "ip_port" -> {
            qrgEncoder = QRGEncoder(value, null, QRGContents.Type.TEXT, dimen)
            binding.ipPortDetails.setImageBitmap(qrgEncoder!!.encodeAsBitmap())
        }
        else -> {
            return
        }
    }
}*/
