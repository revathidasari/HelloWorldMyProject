#include <jni.h>
#include <string>
#include <iostream>
#include <android/log.h>
#define APPNAME "NativeLib"
using namespace std;


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_nativelibc_NativeLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    //cout << "Hello" << endl;
    return env->NewStringUTF(hello.c_str());
}
//customized
jstring str;
extern "C"
JNIEXPORT void JNICALL
Java_com_example_nativelibc_NativeLib_inputStringToJni(JNIEnv *env, jobject thiz, jstring s) {
    const char *v = env->GetStringUTFChars(s,NULL);
    str = s;
    __android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "The value of s is %s", v);
    env->ReleaseStringUTFChars(s,v);
    cout << s;
    cout.flush();
    const char *c_str;
    c_str = env->GetStringUTFChars(s, NULL);
    if (c_str == NULL)
        return;
    std::cout << "Entered string : " << c_str << std::endl;
    std::string str(c_str);
    cout<<"srt "<<s;
    env->ReleaseStringUTFChars(s, c_str);}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_nativelibc_NativeLib_outputJniString(JNIEnv *env, jobject thiz) {
    std::string check = "checking";
    return env->NewStringUTF(check.c_str()); //env->NewStringUTF(str);
}