package com.qwash.user;

/**
 * Created by Amay on 12/28/2016.
 */
public class Sample {

    public static final String BASE_URL = "http://maps.googleapis.com/maps/api/";
    public static final String BASE_URL_QLAP = "https://api.myjson.com/bins/";
    public static final String BASE_URL_INPROGRESS = "https://api.myjson.com/bins/";
    public static final String BASE_URL_QWASH = "http://apis.aanaliudin.com/index.php/api/";
    public static final String BASE_URL_IMAGE = " http://apis.aanaliudin.com/sources/images/profile/";

    public static final String BASE_URL_MAP = "http://maps.googleapis.com/maps/api/";
    public static final String BASE_URL_MY_JON = "https://api.myjson.com/bins/";

    public static final String PREPARE_ORDER_OBJECT = "prepare_order_object";
    public static final String WASHER_ACCEPTED_OBJECT = "washer_accepted_object";
    public static final String VEHICLE_OBJECT = "vehicle_object";
    public static final String HISTORY_OBJECT = "history_object";
    public static final String HISTORY_ID = "historyId";

//FCM
    public static final String SERVER_KEY_FIREBASE = "AAAA6dPgYVk:APA91bHI2HxHsoiiqS6_8pdO84jNMU-Rq_Rhg9nWAmwETLsiyn5Do8zB_MW-__aGu1keJOIS3_moL-csuAsvUYeOBcdBCZp93GJGk1JKm3VMfxQ_0AWlxrpGjqNJNojtbmjM_3UItK90";
    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";

    public static final String TOOLBAR_TITLE = "toolbar_title";
    public static final String IS_FINISH_LOADING_AWAL_DATA = "is_loading";
    public static final String IS_LOADING_MORE_DATA = "is_locked";
    public static final String KEYWORD = "keyword";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String FIREBASE_ID = "firebase_id";
    public static final String CITY = "city";
    public static final String RATE = "rate";
    public static final String CENTER_LAT = "center_lat";
    public static final String CENTER_LONG = "center_long";
    public static String PAGE = "page";
    public static String DATA = "data";

    public static String isSuccess = "isSuccess";
    public static String message = "message";

    public static String history = "history";
    public static String id_history = "id_history";
    public static String history_time = "history_time";
    public static String address = "address";
    public static String vehicle_model = "models";

    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String TOKEN = "token";
    public static final String NAME = "name";
    public static final String AUTH_LEVEL = "auth_level";
    public static final String MESSAGE = "message";
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    public static final String PHONE = "phone";
    public static final String PHOTO = "photo";

    public static final String CUSTOMER_ORDER = "customer_order";

    public static final String ORDERS_REF = "orders_ref";

    // customer order
    public static final String ORDER_USERID="userId";
    public static final String ORDER_USERNAME="username";
    public static final String ORDER_EMAIL="email";
    public static final String ORDER_NAME="name";
    public static final String ORDER_PHONE="phone";
    public static final String ORDER_PHOTO="photo";
    public static final String ORDER_AUTHLEVEL="authLevel";
    public static final String ORDER_FIREBASE_ID="firebase_id";

    // address order
    public static final String ORDER_USERSDETAILSID="usersDetailsId";
    public static final String ORDER_USERIDFK="userIdFk";
    public static final String ORDER_NAMEADDRESS="nameAddress";
    public static final String ORDER_ADDRESS="address";
    public static final String ORDER_LATLONG="latlong";
    public static final String ORDER_TYPE="type";

    //vehicle order
    public static final String ORDER_VCUSTOMERSID="vCustomersId";
    public static final String ORDER_VNAME="vName";
    public static final String ORDER_VBRAND="vBrand";
    public static final String ORDER_MODELS="models";
    public static final String ORDER_VTRANSMISION="vTransmision";
    public static final String ORDER_YEARS="years";
    public static final String ORDER_VID="vId";
    public static final String ORDER_VBRANDID="vBrandId";
    public static final String ORDER_VMODELID="vModelId";
    public static final String ORDER_VTRANSID="vTransId";
    public static final String ORDER_VYEARSID="vYearsId";

    //order detail
    public static final String ORDER_PRICE="price";
    public static final String ORDER_PERFUMED="perfumed_price";
    public static final String ORDER_PERFUMED_STATUS="perfumed_price";
    public static final String ORDER_INTERIOR_VACCUM="interior_vaccum_price";
    public static final String ORDER_INTERIOR_VACCUM_STATUS="perfumed_price";
    public static final String ORDER_ESTIMATED_PRICE="estimated_price";
    public static final String ORDER_PICK_DATE="pick_date";
    public static final String ORDER_PICK_TIME="pick_time";


    public static final String ACTION="action";
    public static final String ORDER = "order";
    public static final String WASHER = "washer";
    public static final String REGISTRATION_IDS = "registration_ids";


    //Washer Accept
    public static final String WASHER_PHONE = "phone";
    public static final String WASHER_PHOTO = "photo";
    public static final String WASHER_NAME = "name";
    public static final String WASHER_EMAIL = "email";
    public static final String WASHER_USER_ID = "userId";
    public static final String WASHER_FIREBASE_ID = "firebase_id";
    public static final String WASHER_RATING = "rating";

    //
    public static final int CODE_DEACLINE= 2;
    public static final int CODE_ACCEPT = 3;
    public static final int CODE_START = 4;
    public static final int CODE_FINISH_WORKING = 5;

    public static String USER_ID_FK="user_id_fk";
    public static String V_CUSTOMERS_ID_FK="v_customers_id_fk";
    public static String PICK_DATE="pick_date";
    public static String PICK_TIME="pick_time";
    public static String LAT="lat";
    public static String LONG="long";
    public static String PRICE="price";
    public static String PERFUMED="perfurmed";
    public static String VACUMMED="vacummed";
    public static String NAME_ADDRESS="name_address";
    public static String ADDRESS= "address";
    public static String COMMENTS="comments";


    public static final int ACTION_ORDER = 1;
    public static final int ACTION_CANCEL_ORDER = 2;
    public static final int ACTION_OPEN_FEED_ORDER = 3;

}
