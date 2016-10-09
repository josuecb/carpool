package com.example.josue.carpool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by Josue on 10/8/2016.
 */

public class InternetConnection {
    public static int NOT_CONNECTED = 0;
    public static int WIFI_NETWORK = 1;
    public static int MOBILE_NETWORK = 2;
    public static int VPN_NETWORK = 3;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager IntConnection = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = IntConnection.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return WIFI_NETWORK;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return MOBILE_NETWORK;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_VPN)
                return VPN_NETWORK;
        }
        //Log.e("INTERNET CONNECTION", IntConnection.getActiveNetworkInfo().toString());
        return NOT_CONNECTED;
    }

    public static boolean isConnectionFast(Context context){
        ConnectivityManager IntConnection = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = IntConnection.getActiveNetworkInfo();
//        Log.e("FINDING", "SPEED");
//        Log.e("FINDING", "SPEED TYPE: " + activeNetwork.getType());
        if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
            return true;
        }else if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){
//            Log.e("FINDING", "SPEED SUBTYPE: " + activeNetwork.getType());
            switch(activeNetwork.getSubtype()){
                case TelephonyManager.NETWORK_TYPE_1xRTT:
//                    Log.e("SPEED", "50-100kbps");
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
//                    Log.e("SPEED", "14-64kbps");
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
//                    Log.e("SPEED", "50-100kbps");
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
//                    Log.e("SPEED", "400-1000kbps");
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
//                    Log.e("SPEED", "600-1000kbps");
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
//                    Log.e("SPEED", "-100kbps");
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
//                    Log.e("SPEED", "2-14kbps");
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
//                    Log.e("SPEED", "700-1700kbps");
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
//                    Log.e("SPEED", "1-23kbps");
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
//                    Log.e("SPEED", "400-7000kbps");
                    return true; // ~ 400-7000 kbps
			/*
			 * Above API level 7, make sure to set android:targetSdkVersion
			 * to appropriate level to use these
			 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        }else{
            return false;
        }
    }


}
