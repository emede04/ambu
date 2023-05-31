package com.example.ambu.view.Med.ui.miscelaneaos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ambu.R;
import com.example.ambu.models.Issue;
import com.example.ambu.models.Symptom;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MiscelaneosFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public MiscelaneosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_miscelaneos, container, false);

        //WebView myWebView = (WebView) view.findViewById(R.id.webview);
     //   myWebView.loadUrl("http://www.example.com");

        return view;
    }
}





