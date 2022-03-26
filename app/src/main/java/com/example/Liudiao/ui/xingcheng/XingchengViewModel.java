package com.example.Liudiao.ui.xingcheng;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class XingchengViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public XingchengViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
