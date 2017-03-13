package com.qwash.user.api;


import android.util.Log;

import com.qwash.user.api.client.RetrofitClient;
import com.qwash.user.api.model.GlobalError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {


    public static GlobalError parseErrorGlobal(Response<?> response) {
        Converter<ResponseBody, GlobalError> converter =
                RetrofitClient.retrofit
                        .responseBodyConverter(GlobalError.class, new Annotation[0]);
        GlobalError error;

        try {
            Log.v("error", response.errorBody().toString());
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new GlobalError();
        }

        return error;
    }


}
