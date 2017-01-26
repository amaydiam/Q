package com.qwash.user.model; /**
 * Created by Amay on 12/29/2016.
 */


public class FindWasher {
    public String user_id_fk, name, latlong, firebaseId;
    public boolean deacline;

    FindWasher() {

    }

    public FindWasher(String user_id_fk, String latlong, String name, String firebaseId, boolean deacline) {
        this.user_id_fk = user_id_fk;
        this.name = name;
        this.latlong = latlong;
        this.name = name;
        this.firebaseId = firebaseId;
        this.deacline = deacline;
    }

}