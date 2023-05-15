package com.example.ambu.utils;

import com.example.ambu.utils.Interfaces.LoginService;
import com.example.ambu.utils.Interfaces.apiMedicService;

public class Apis {
    //clase que tiene las apis
    String user;
    String pass;
    public final String ApiMedicUser = "mariadolorespersonal@gmail.com";
    public final String ApiMedicPass = "e5XNa9f6P2LgAj4r7";
    public final String ApiMedicUrl = "https://sandbox-healthservice.priaid.ch";
    public static final String URL_003 = "https://sandbox-healthservice.priaid.ch";
    public static final String URL_001 = "http://10.0.2.2:8080";

    public static final String URL_002 = "https://sandbox-authservice.priaid.ch";
    public static apiMedicService apiMedicServiceData() {
        return RetrofitController.buildRetrofit(URL_003).create(apiMedicService.class);
    }

    public static LoginService loginUserService() {
        return RetrofitController.buildRetrofit(URL_001).create(LoginService.class);
    }
    public static apiMedicService apiMedicServiceLogin(){
        return RetrofitController.buildRetrofit(URL_002).create(apiMedicService.class);
    }


}

