#define all paths, constants here
PROJECT_DIR='/home/ee211905/AndroidStudioProjects/HelloWorldGrpc/'
OUTPUT_DIR='/home/ee211905/AndroidStudioProjects/HelloWorldGrpc/OUTPUT_DIR/'

#Enter project dir
cd $PROJECT_DIR

#define color for logos
print_blue(){
	printf "\e[1;34m$1\e[0m"
}
print_red(){
	printf "\e[1;31m$1\e[0m"
}
print_green(){
	printf "\e[1;32m$1\e[0m"
}
print_yellow(){
	printf "\e[1;33m$1\e[0m"
}

#start clean process
print_blue "\n\n\nStarting"
print_blue "\n\n\nCleaning...\n"
#./gradlew clean

print_blue "\n\n\ncleanBuildCache...\n"
#./gradlew cleanBuildCache

#build project
print_blue "\n\n\n build...\n"
#./gradlew build

#run test cases
#./gradlew installDebugAndroidTest

#assemble apk
print_blue "\n\n\n assembleDebug...\n"
#./gradlew assembleDebug

#install apk
print_blue "installDebug...\n"
#./gradlew installDebug
print_blue "\n\n\n Done Installing\n"

#to uninstall previous build apk
#./gradlew uninstallDebug

#launch main activity
adb shell am start -n "com.example.helloworldgrpc/com.example.helloworldgrpc.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER

print_blue "\n\n\n Launched Main Activity\n"

#copy APK to output folder
cp "$PROJECT_DIR"app/build/outputs/apk/debug/app-debug.apk $OUTPUT_DIR
print_blue "\n\n\n Copying APK to outputs Done\n"
