package com.example.helloworldgrpc.ui.dashboard;

import android.widget.Spinner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Spinner> mAge;
    private MutableLiveData<Spinner> mWeight;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mAge = new MutableLiveData<>();
        mWeight = new MutableLiveData<>();

        mText.setValue("This is dashboard fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}