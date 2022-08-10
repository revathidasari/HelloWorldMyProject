package com.example.helloworldgrpc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.splashscreen.SplashScreen;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.TestManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.libgeneration.MainLibActivity;
import com.example.nativelibc.NativeLib;
import com.google.common.collect.Lists;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class MainActivity extends AppCompatActivity {

    private static final Object NETWORK_SETTINGS = "android.permission.NETWORK_SETTINGS";
    String[] availableActivities = {"BottomNavigationActivity","FileWriteRead","MyContentProvider","MyBackgroundService",
    "TabbedActivity","MainActivity2","ReadQRCode", "Sorting"};
    String inputValue_wifi;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    ImageView qrImage;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    Button btnBkgSrv, btnDb, btnFile, btnBottomNav;
    TextView tv, scrollText;

    private MyBackgroundService myBackgroundService;
    private com.example.koin2.MainActivity koinMainActivity;
    Intent serviceIntent ;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {

        //splash screen for Android 12
        //SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //To display the text scrolling horizontally
        /*(or) mTextViewT.setHorizontallyScrolling(true);
mTextViewT.setSingleLine(true);
mTextViewT.setInputType(InputType.TYPE_CLASS_NUMBER);*/
        scrollText = findViewById(R.id.textScroll);
        scrollText.setSelected(true);

        tv = findViewById(R.id.text);
        btnBkgSrv = (Button) findViewById(R.id.button);
        btnDb = (Button) findViewById(R.id.buttonDB);
        btnFile = (Button) findViewById(R.id.buttonFile);
        btnBottomNav = (Button) findViewById(R.id.buttonBottomNav);
        qrImage = (ImageView) findViewById(R.id.QR_Image);

        tv.setText(Arrays.toString(availableActivities));
        inputValue_wifi = "wifi-connect";//turnOnHotspot();


        //meta-data decoding/checking
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(
                    this.getPackageName(), PackageManager.GET_META_DATA);
            ActivityInfo activityInfo = getPackageManager().getActivityInfo(this.getComponentName(),
                    PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            String apiKey = bundle.getString("api_key");
            Bundle activityBundle = activityInfo.metaData;
            String activityKey = activityBundle.getString("activity_api_key");
            Log.d("revathi","api key is "+apiKey+" activity key was "+activityKey);
        } catch (Exception e) {
            e.printStackTrace();//NameNotFound or NullPointer
        }

        //manifest declared provider tag usage
        File file = null;
        Intent providerIntent = null;
        //try { file = createImageFile(); } catch(IOException e){e.getMessage();}
       /* Uri uri = FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID+".provider",file);
        providerIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        providerIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivity(providerIntent);
*/
//        isDeviceScreenLocked();

        ActionReceiver actionReceiver = new ActionReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(actionReceiver,intentFilter);

        SortingExample sortingExample = new SortingExample();
        sortingExample.sortAndGetPreviousIndex();

        /* Kotlin class call */
        koinMainActivity = new com.example.koin2.MainActivity();
        //startActivity(new Intent(this,koinMainActivity.getClass()));

        /* Native library call */
/*        NativeLib nativeLib = new NativeLib();
        nativeLib.inputStringToJni("random");
        String nativeText = nativeLib.stringFromJNI();
        Toast.makeText(this, "Native text "+nativeText+"\n"+nativeLib.getText(), Toast.LENGTH_SHORT).show();*/

        /* Another module library call */
/*        MainLibActivity mainLibActivity = new MainLibActivity();
        mainLibActivity.libAccess();*/

        /* Service call */
        serviceIntent = new Intent(MainActivity.this,MyBackgroundService.class);
        /* Another way for a service call */
/*        serviceIntent = new Intent();
        serviceIntent.setAction("com.example.helloworldgrpc.MY_BKG_SERVICE");*/


        /* Animation of QR image */
/*        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.property_animator);
        set.setTarget(R.id.imgQR);
        set.start();
        Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
        qrImage.startAnimation(hyperspaceJump);*/


        btnBottomNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BottomNavigationActivity.class));
            }
        });

        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FileWriteRead.class));
            }
        });

        btnDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MyContentProvider.class));
            }
        });

        btnBkgSrv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (!isServiceRunning())
                     Toast.makeText(MainActivity.this,"Service is not running : started now...", Toast.LENGTH_SHORT).show();
                     //startService(new Intent(MainActivity.this,MyBackgroundService.class));

                 /* Service Intent must be explicit when assigned with action before call */
/*                 List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentServices(serviceIntent,0);
                 if (resolveInfoList == null || resolveInfoList.size() != 1) {
                     return;
                 }
                 ResolveInfo resolveInfo = resolveInfoList.get(0);
                 ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
                 Intent explicitIntent = new Intent(serviceIntent);
                 explicitIntent.setComponent(componentName);
                 startService(explicitIntent);*/
                 startService(serviceIntent);
             }
         });


        /* Class to save compilation issue */
        TestManager testManager = new TestManager();
        testManager.getProductName();

        /* QR display */
        WindowManager wmanager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wmanager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        qrgEncoder = new QRGEncoder(inputValue_wifi, null, QRGContents.Type.TEXT, smallerDimension);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch ( WriterException e) {
            Log.v("revathi", e.toString());
        }

        /* display details in pixels and dps  */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width_px = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height_px =Resources.getSystem().getDisplayMetrics().heightPixels;
        int pixeldpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        int width_dp = (width_px/pixeldpi)*160;
        int height_dp = (height_px/pixeldpi)*160;

        /* convert dp to pixel and pixel to dp
        * dpToPixel = dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT)
        * pixelToDp = px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT)
        *  */
    }


    private boolean isServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)){
            //if (runningServiceInfo.service.getClassName().equals(myBackgroundService.getClass().getName())) {
            /*...With intent action..*/
            if (runningServiceInfo.service.getClassName().equals("com.example.helloworldgrpc.MY_BKG_SERVICE")) {
                return true;
            }
        }
        return false;
    }

    public boolean onKeyDown(int key, KeyEvent event) {
        if (key == KeyEvent.KEYCODE_HOME) {
            stopService(serviceIntent);
        }
        if (key == KeyEvent.KEYCODE_8 || key == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MainActivity.this, MyContentProvider.class);
            startActivity(intent);
            Toast.makeText(this, "setaction called", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(key, event);
    }

    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        super.onDestroy();
    }

    private void checkFromJavaToKotlin() {
        int a =19;
        String s = "sai";
        Log.d("revathi","log check translate");

    }

    private class NETWORK_SETTINGS {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isDeviceScreenLocked() {
        //int lockPatternEnable = Settings.Secure.getInt(getApplicationContext().getContentResolver(), Settings.Secure.LOCK_PATTERN_ENABLED);
        KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE); //api 16+
        Toast.makeText(this, "pin/password "+keyguardManager.isKeyguardSecure()+" device "+keyguardManager.isDeviceSecure(), Toast.LENGTH_SHORT).show();
        return keyguardManager.isKeyguardSecure();
    }
}