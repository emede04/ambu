package com.example.ambu.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.ambu.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 *
 */
public class internetoFragment extends Fragment {

    String url;


    public internetoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_interneto, container, false);

        WebView myWebView = (WebView) view.findViewById(R.id.webview);

        this.url = getArguments().getString("url");
        myWebView.loadUrl(url);
        return view;
    }

}