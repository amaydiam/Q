package com.qwash.user;

import com.qwash.user.utils.Prefs;

/**
 * Created by Amay on 12/28/2016.
 */
public class Sample {

    public static final String BASE_URL_QWASH_PUBLIC = "http://api.qwash-indonesia.com:1337/public/";
    public static final String BASE_URL_QWASH_API = "http://api.qwash-indonesia.com:1337/api/v1/";

    public static final String BASE_URL_MAP = "https://maps.googleapis.com/maps/api/";

    public static final String PREPARE_ORDER_OBJECT = "prepare_order_object";
    public static final String WASHER_ACCEPTED_OBJECT = "washer_accepted_object";
    public static final String VEHICLE_OBJECT = "vehicle_object";
    public static final String HISTORY_OBJECT = "history_object";
    public static final String HISTORY_ID = "historyId";
    public static final String NOTIFICATION_ID = "notificationId";

    //KEY GOOGLE API
    public static final String KEY_GOOGLE = "AIzaSyB57vfJlr9d4155E8hY4Klf2s9H3E3ouGM";

    //FCM
    public static final String SERVER_KEY_FIREBASE = "AAAA2kHlMFE:APA91bHojkPnnebXqNs8IWgL37K0HCvFJyXkipFXbTbYmigL1bsUPJIjv5P1kJDgBbWzbzR6QkxMGNMgLDzb7f_uC-BFfzhb7cEbwnov5ErnjvHSyRw0gKBtfiCqKhz55pOr0zgyld2z";
    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";

    public static final String IS_FINISH_LOADING_AWAL_DATA = "is_loading";
    public static final String IS_LOADING_MORE_DATA = "is_locked";
    public static final String KEYWORD = "keyword";
    public static final String PASSWORD = "password";
    public static final String CITY = "city";
    public static final String CENTER_LAT = "center_lat";
    public static final String CENTER_LONG = "center_long";
    public static final String PAGE = "page";
    public static final String DATA = "data";

    public static final String isSuccess = "isSuccess";
    public static final String message = "message";

    public static final String history = "history";
    public static final String id_history = "id_history";
    public static final String history_time = "history_time";
    public static final String address = "address";
    public static final String vehicle_model = "models";

    public static final String TOKEN = "token";
    public static final String AUTH_LEVEL = "auth_level";
    public static final String MESSAGE = "message";
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    public static final String PHONE = "username";
    public static final String PHOTO = "photo";


    // customer order
    public static final String ORDER_USERID = "customersId";
    public static final String ORDER_USERNAME = "username";
    public static final String ORDER_EMAIL = "email";
    public static final String ORDER_NAME = "name";
    public static final String ORDER_PHONE = "username";
    public static final String ORDER_PHOTO = "photo";
    public static final String ORDER_AUTHLEVEL = "authLevel";
    public static final String ORDER_FIREBASE_ID = "firebase_id";

    // address order
    public static final String ORDER_USERSDETAILSID = "usersDetailsId";
    public static final String ORDER_USERIDFK = "userIdFk";
    public static final String ORDER_NAMEADDRESS = "nameAddress";
    public static final String ORDER_ADDRESS = "address";
    public static final String ORDER_LAT = "lat";
    public static final String ORDER_LONG = "long";
    public static final String ORDER_TYPE = "type";

    //vehicle order
    public static final String ORDER_VEHICLES_TYPE = "vehicles_type";
    public static final String ORDER_VEHICLES = "vehicles";

    //order detail
    public static final String ORDER_PRICE = "price";

    public static final String ORDER_PERFUM_PRICE = "perfum_price";
    public static final String ORDER_PERFUM_STATUS = "perfum_stattus";

    public static final String ORDER_INTERIOR_VACUUM_PRICE = "interior_vacuum";
    public static final String ORDER_INTERIOR_VACUUM_STATUS = "interior_vacuum_status";

    public static final String ORDER_WATERLESS_PRICE = "waterless_price";
    public static final String ORDER_WATERLESS_STATUS = "waterless_status";

    public static final String ORDER_ESTIMATED_PRICE = "estimated_price";


    public static final String ACTION = "action";
    public static final String ORDER = "order";
    public static final String WASHER = "washer";
    public static final String REGISTRATION_IDS = "registration_ids";


    //Washer Accept
    public static final String WASHER_USERNAME = "username";
    public static final String WASHER_PHOTO = "photo";
    public static final String WASHER_NAME = "name";
    public static final String WASHER_EMAIL = "email";
    public static final String WASHERS_ID = "washersId";
    public static final String WASHER_FIREBASE_ID = "firebase_id";
    public static final String WASHER_RATING = "rating";

    //
    // kondisi dimana blum lagi dapet order, berarti order yg masuk bisa tampil
    public static final int CODE_NO_ORDER = 1;

    // kondisi dimana orderan sedang tampil di layar dan belum diambil,
    // berarti kalo ada order yg masuk ga ditampilin
    public static final int CODE_GET_ORDER = 2;

    // kondisi dimana orderan ditolak
    public static final int CODE_DEACLINE_ORDER = 3;

    // kondisi dimana orderan yg  tampil di layar dan telah diterima
    public static final int CODE_ACCEPT_ORDER = 4;

    // kondisi dimana washer sedang dalam perjalanan ke rumah customer yg mengorder
    public static final int CODE_ON_THE_WAY= 5;

    // kondisi dimana washer memulai pencucian pada orderan yg  telah diterima
    public static final int CODE_START_WORKING = 6;


    // kondisi dimana washer selesai pencucian pada orderan yg  telah diterima
    public static final int CODE_FINISH_WORKING = 7;

    public static final String USER_ID_FK = "user_id_fk";
    public static final String VEHICLES = "vehicles";
    public static final String LAT = "lat";
    public static final String LONG = "long";
    public static final String PRICE = "price";
    public static final String PERFUM = "perfurm";
    public static final String VACUUM = "vacuum";
    public static final String WATERLESS = "waterless";
    public static final String NAME_ADDRESS = "name_address";
    public static final String ADDRESS = "address";
    public static final String COMMENTS = "comments";


    public static final int ACTION_ORDER = 1;
    public static final int ACTION_CANCEL_ORDER = 2;
    public static final int ACTION_OPEN_FEED_ORDER = 3;


    public static final int VEHICLE_CAR = 1;
    public static final int VEHICLE_CAR_CITY_CAR = 1;
    public static final int VEHICLE_CAR_MINIVAN = 2;
    public static final int VEHICLE_CAR_SUV = 3;

    public static final int VEHICLE_MOTORCYCLE = 2;
    public static final int VEHICLE_MOTORCYCLE_UNDER_150 = 4;
    public static final int VEHICLE_MOTORCYCLE_150 = 5;
    public static final int VEHICLE_MOTORCYCLE_ABOVE_150 = 6;

    public static final String LIMIT_DATA = "10";
    public static final String ADDRESS_TYPE = "address_type";
    public static final int CODE_ADRESS_HOME = 1;
    public static final int CODE_ADRESS_WORK = 2;


    public static final String CUSTOMER = "customer";
    public static final String VEHICLE = "vehicle";
    public static final String DETAILS = "details";


    public static final String CODE = "code";
    public static final String AUTHORIZATION = "Authorization";
    public static final int NO_INDEX = 0;
    public static final int ACTIVATION_CODE_INDEX = 1;

    public static final String ACTIVITY_INDEX = "lock_after_register";
    public static final String ORDERS_ID = "ordersId";

    public static String USER_ID = "customersId";
    public static String EMAIL = "email";
    public static String USERNAME = "username";
    public static String TYPE = "type";
    public static String FULL_NAME = "fullName";
    public static String SALDO = "saldo";
    public static String FIREBASE_ID = "firebaseId";
    public static String GEOMETRY_LAT = "geometryLat";
    public static String GEOMETRY_LONG = "geometryLong";
    public static String PROFILE_BIRTHDATE = "profileBirthdate";
    public static String PROFILE_GENDER = "profileGender";
    public static String PROFILE_PHOTO = "profilePhoto";
    public static String PROFILE_PROVINCE = "profileProvince";
    public static String PROFILE_CITY = "profileCity";
    public static String PROFILE_NIK = "profileNik";
    public static String ONLINE = "online";
    public static String STATUS = "status";
    public static String CREATED_AT = "createdAt";
    public static String UPDATED_AT = "updatedAt";
}
