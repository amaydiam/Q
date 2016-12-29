package com.ad.sample.api;

import com.ad.sample.api.client.RetrofitClient;
import com.ad.sample.api.client.SOService;

/**
 * Created by Amay on 12/29/2016.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://maps.googleapis.com/maps/api/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
