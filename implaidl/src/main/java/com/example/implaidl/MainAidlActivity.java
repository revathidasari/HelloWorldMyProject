package com.example.implaidl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.KeyEventDispatcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainAidlActivity extends AppCompatActivity {

    IMyAidlInterface iMyAidlInterface;
    MyAidlInterfaceServiceConnection myAidlInterfaceService;

    /**
     * This class represents the actual service connection. It casts the bound
     * stub implementation of the service to the AIDL interface.
     */
    class MyAidlInterfaceServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            Log.d("Aidl-Activity","service is connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            iMyAidlInterface = null;
            Log.d("Aidl-Activity","service is disconnected");
        }
    }

    /** Binds this activity to the service. */
    private void initService() {
        myAidlInterfaceService = new MyAidlInterfaceServiceConnection();
        Log.d("d","f");
        Intent intent = new Intent();
        intent.setClassName("com.example.implaidl",com.example.implaidl.MyAidlInterfaceService.class.getName());
        /*..Converting implicit intent to explicit intent as service intent has to be explicit..*/
        /* intent.setAction("com.example.implaidl.MyService");
        List<ResolveInfo> resolveInfoList = this.getPackageManager().queryIntentServices(intent,0);
        if (resolveInfoList == null || resolveInfoList.size() != 1) {
            return;
        }
        ResolveInfo resolveInfo = resolveInfoList.get(0);
        ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
        Intent explicitIntent = new Intent(intent);
        explicitIntent.setComponent(componentName);

        boolean retVal = bindService(explicitIntent, myAidlInterfaceService, Context.BIND_AUTO_CREATE);
        */

        boolean retVal = bindService(intent, myAidlInterfaceService, Context.BIND_AUTO_CREATE);
        Log.d("Aidl-Activity","init service bind status is "+retVal);
    }

    /** Unbinds this activity from the service. */
    private void closeService(){
        unbindService(myAidlInterfaceService);
        myAidlInterfaceService = null;
        Log.d("Aidl-Activity", "closed the service connection");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aidl);

        initService();

        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.result);
        EditText edit1 = findViewById(R.id.value1);
        EditText edit2 = findViewById(R.id.value2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value1 = 0;
                int value2 = 0;
                value1 = Integer.parseInt(TextUtils.isEmpty(edit1.getText().toString()) ? String.valueOf(0) : edit1.getText().toString());
                value2 = Integer.parseInt(TextUtils.isEmpty(edit2.getText().toString()) ? String.valueOf(0) :  edit2.getText().toString());
                int result = -1;

                try {
                    result = iMyAidlInterface.add(value1, value2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                textView.append(Integer.toString(result));
            }
        });

    }

    @Override
    protected void onDestroy() {
        closeService();
        super.onDestroy();
    }
}