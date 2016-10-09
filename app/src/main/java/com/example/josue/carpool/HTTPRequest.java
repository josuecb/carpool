package com.example.josue.carpool;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Josue on 10/8/2016.
 */

public class HTTPRequest extends AsyncTask<String, String, String> {
    static final String DATA_TYPE_HTTPS = "dataType";
    static final String DATA_CONTENT_HTTPS = "dataContent";
    HashMap<String, String> data = new HashMap<>();
    List<Pair<String, String>> parameters = new ArrayList<>();
    int option = -1;
    String phone;
    Context context = null;

    //data.put("dataType", dataContent);
    //data.put("dataContent", dataContent);
    public HTTPRequest(Context context, HashMap<String, String> inputDatas) {
        this.context = context;
        data = inputDatas;
        parameters = parsingDataInputs(this.data);
    }


    private List<Pair<String, String>> parsingDataInputs(HashMap<String, String> data) {
        if (data != null) {
            int dataInputSie = data.size() / 2;
            for (int i = 0; i < dataInputSie; i++) {
                String dataType;
                String dataContent;

                if (i != 0) {
                    dataType = data.get(DATA_TYPE_HTTPS + Integer.toString(i));
                    dataContent = data.get(DATA_CONTENT_HTTPS + Integer.toString(i));
                } else {
                    dataType = data.get(DATA_TYPE_HTTPS);
                    dataContent = data.get(DATA_CONTENT_HTTPS);
                }
                parameters.add(new Pair<>(dataType, dataContent));
            }
        }
        return parameters;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... url) {
        String response = null;
        if (InternetConnection.getConnectivityStatus(context) != InternetConnection.NOT_CONNECTED) {
            HttpURLConnection connection = null;
            URL requestToServer;
            InputStream inputStream = null;
            try {
                requestToServer = new URL(url[0]); // will only accept 1 url
                connection = (HttpURLConnection) requestToServer.openConnection();
                connection.setReadTimeout(10000); // 10 seconds for reading it
                connection.setConnectTimeout(15000); // connection timeout will be for 15 seconds just in case

                connection.setRequestMethod("GET");

                connection.setDoOutput(true);
                connection.setDoInput(true);
                //--System.out.println("@@@@@@@@@@@@@@@@@@@@@@: " + phone_number);
                /**
                 * in case we want no posting
                 */
                if (parameters != null) {
                    OutputStream os = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    //--System.out.println(getQuery(parameters));
                    //Writes the website url
                    writer.write(getQuery(parameters));
                    writer.flush();
                    writer.close();
                    os.close();
                }
                Log.e("ddddddddddddd", connection.getRequestMethod());
                Log.e("RESPONSE CODE    ", "" + connection.getResponseCode());


                try {
                    inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String str = null;
                    StringBuilder sb = new StringBuilder();
                    while ((str = reader.readLine()) != null) {
                        sb.append(str);
                    }
                    response = sb.toString();
                    //Log.d("String Content", response);
                } catch (IOException e) {
                    e.printStackTrace();
                }


//            inputStream = connection.getInputStream();
//            Log.i("String Content", response);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            while ((response = reader.readLine()) != null){
//                Log.i("String Content", response);
//                Log.i("Server Response", reader.readLine());
//            }
                //--System.out.println("SOMETHING HAPPENED@@@@@@@@@@@@@@@@@@@@@@");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Log.e("VAL", "KEY" + ": " + response);
        return response;
    }

    protected static String getQuery(List<Pair<String, String>> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair<String, String> pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.first, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second, "UTF-8"));
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
        Log.d("RESYL", "" + result);
    }

}