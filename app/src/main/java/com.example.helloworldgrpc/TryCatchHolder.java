package com.example.helloworldgrpc;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//https://www.section.io/engineering-education/making-api-requests-using-retrofit-android/



//https://www.freecodecamp.org/news/how-to-install-and-setup-flutter-on-ubuntu/
//https://itnext.io/flutter-for-web-c75011a41956
//https://docs.flutter.dev/get-started/install/linux
//https://www.developerlibs.com/2020/03/flutter-web-app-setup-vscode-android-studio.html
//https://www.developerlibs.com/2018/05/flutter-introduction-and-setup.html

//https://stackoverflow.com/questions/56332913/is-it-possible-to-use-android-studio-to-build-a-website-in-flutter


public class TryCatchHolder extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_content_provider);

        Toast.makeText(this, "size "+getWidthScreen(this)+" : "
                +getHeightScreen(this), Toast.LENGTH_SHORT).show();
    }

    public static int getWidthScreen(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getHeightScreen(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public void tryBlock(){
        try {

        } catch (Exception e) {

        } finally {

        }

        try {
            //Runtime.getRuntime().exec("su sh /sdcard/adbshellscript.sh");
/*            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd);*/

            //  Runtime.getRuntime().exec("su input keyevent KEYCODE_POWER");//"sh /sdcard/adbshellscript.sh");
            /*BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String listOfFiles = "";
            String line;
            while ((line = in.readLine()) != null) {
                listOfFiles += line;
            }
            Toast.makeText(this, "list "+listOfFiles, Toast.LENGTH_SHORT).show();*/
        } catch (Exception e) {
            Toast.makeText(this, "exception", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

}
/*Zombie Apocalypse
A patient with first stage zombie virus has escaped from the facility in a city of population N.
The zombie virus is very dangerous as it passer on to other people as they come in contact with the patient making them a patient as well.
Zombie virus has K life stages to develop, each of which takes 1 unit of time.
Only the last stage is contagious. A person with zombie virus attacks only healthy people and can only affect one person in 1 unit of time.
if the patient escaped on Day 1. Find out the number of days it will take to wipe out the entire city's healthy population and turn them into a last stage zombie.
Note: 1 unit of time is equal to 1 day.

Input Specification.
Input1:Total healthy population of the city.
input2:Number of stages of zombie virus.
Output Specification : your function must return the numbers of days.
Example 1: input1:10 , input2:1 , output:5 [daysToAttackAllPeople(10,1)] ==> 5 days(16 people will be affected)
Example 2: input1:10 , input2:2 , output:6 [daysToAttackAllPeople(10,2)] ==> 6 days(18 people will be affected)
Program:

public void daysToAttackAllPeople(int totalPopulation, int zombieStages){
        int k=zombieStages;
        int ans = 0;
        while (totalPopulation > zombieStages) {
             zombieStages *= (k+1);
             ans=ans+k;
        }
        System.out.println("k"+k+" zombieStages "+zombieStages+" output is ans : "+(ans+k));
}
*/