package com.example.implaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyAidlInterfaceService extends Service {
    public MyAidlInterfaceService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IMyAidlInterface.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            }

            @Override
            public int add(int a, int b) throws RemoteException {
                Log.d("Aidl-service","add value1 : "+a+" value2 : "+b);
                return a+b;
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}