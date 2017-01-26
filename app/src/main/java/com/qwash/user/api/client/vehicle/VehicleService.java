package com.qwash.user.api.client.vehicle;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.api.model.vehicle.VehicleBrandFromService;
import com.qwash.user.api.model.vehicle.VehicleModelFromService;
import com.qwash.user.api.model.vehicle.VehicleTransmissionFromService;
import com.qwash.user.api.model.vehicle.VehicleYearFromService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VehicleService {

    @GET("vehicles/getVehiclesBrand/{selected_vehicle}")
    Call<VehicleBrandFromService> getBrandVehicleLink(@Path("selected_vehicle") int selected_vehicle);

    @GET("vehicles/getVehiclesModel/{selected_brand_vehicle}")
    Call<VehicleModelFromService> getModelVehicleLink(@Path("selected_brand_vehicle") int selected_brand_vehicle);

    @GET("vehicles/getVehiclesTransmission/{id}")
    Call<VehicleTransmissionFromService> getTransmissionVehicleLink(@Path("selected_model_vehicle") int selected_model_vehicle);

    @GET("vehicles/getVehiclesTransmission/{id}")
    Call<VehicleYearFromService> getYearVehicleLink(@Path("selected_transmission_vehicle") int selected_transmission_vehicle);
}