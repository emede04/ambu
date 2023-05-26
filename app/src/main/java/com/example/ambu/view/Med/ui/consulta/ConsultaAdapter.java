package com.example.ambu.view.Med.ui.consulta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConsultaAdapter extends ViewModel {

    private final MutableLiveData<String> mText;

    public ConsultaAdapter() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}