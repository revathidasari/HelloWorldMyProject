package com.example.nativelibc;

//JNI example
public class NativeLib {

    // Used to load the 'nativelibc' library on application startup.
    static {
        System.loadLibrary("nativelibc");
    }

    /**
     * A native method that is implemented by the 'nativelibc' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    //not working
    public native void inputStringToJni(String s);

    public native String outputJniString();

    public String getText() {
        return outputJniString();
    }

    // if main() as static we use as new NativeLib().stringFromJNI();
    public void main(String[] args) {
        android.util.Log.d("revathi",stringFromJNI());
        inputStringToJni("empty");
        System.out.println(outputJniString());

    /*    NativeLib nativeLib = new NativeLib();
        nativeLib.outputJniString();*/
    }
}
/*
* JNI is an interfaces allows to interact Java with Native language. Used for for Reusability and performance.
* e.g. - Java command line utility which launches Java code in a Java Virtual machine
* Javah is a JDK tool - adapts Java method signatures to native function prototypes
* jni.h is a C/C++ header file included with the JDK that maps java types to their native counterparts
* Javah automatically includes the file in the application header files
*
* UnsatisfiedLinkError - occurs on incorrect naming of shared library (OS dependent) due to
*                        library not being in search path or wrong library being loaded by Java code
* */