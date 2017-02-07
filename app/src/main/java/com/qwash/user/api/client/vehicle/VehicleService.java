package com.qwash.user.api.client.vehicle;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.api.model.login.Login;
import com.qwash.user.api.model.vehicle.RegisterVehicle;
import com.qwash.user.api.model.vehicle.VehicleBrandFromService;
import com.qwash.user.api.model.vehicle.VehicleModelFromService;
import com.qwash.user.api.model.vehicle.VehicleTransmissionFromService;
import com.qwash.user.api.model.vehicle.VehicleYearFromService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VehicleService {

    @GET("vehicles/getVehiclesBrand/{selected_vehicle}")
    Call<VehicleBrandFromService> getBrandVehicleLink(@Path("selected_vehicle") int selected_vehicle);

    @GET("vehicles/getVehiclesModel/{selected_brand_vehicle}")
    Call<VehicleModelFromService> getModelVehicleLink(@Path("selected_brand_vehicle") String selected_brand_vehicle);

    @GET("vehicles/getVehiclesTransmission/{selected_model_vehicle}")
    Call<VehicleTransmissionFromService> getTransmissionVehicleLink(@Path("selected_model_vehicle") String selected_model_vehicle);

    @GET("vehicles/getVehiclesYear/{selected_transmission_vehicle}")
    Call<VehicleYearFromService> getYearVehicleLink(@Path("selected_transmission_vehicle") String selected_transmission_vehicle);


    @FormUrlEncoded
    @POST("vehicles/add")
    Call<RegisterVehicle> getRegisterVehicleLink(@FieldMap Map<String, String> params);
}