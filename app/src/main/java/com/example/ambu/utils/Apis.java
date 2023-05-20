package com.example.ambu.utils;

import com.example.ambu.utils.Interfaces.LoginService;
import com.example.ambu.utils.Interfaces.ApiMedicService;

public class Apis {
    //clase que tiene las apis
    String user;
    String pass;

    public static final String URL_003 = "https://sandbox-healthservice.priaid.ch";
    public static final String URL_001 = "http://10.0.2.2:8080";

    public static final String URL_002 = "https://sandbox-authservice.priaid.ch";
    public static ApiMedicService apiMedicServiceData() {
        return RetrofitController.buildRetrofit(URL_003).create(ApiMedicService.class);
    }

    public static LoginService loginUserService() {
        return RetrofitController.buildRetrofit(URL_001).create(LoginService.class);
    }
    public static ApiMedicService apiMedicServiceLogin(){
        return RetrofitController.buildRetrofit(URL_002).create(ApiMedicService.class);
    }


}

