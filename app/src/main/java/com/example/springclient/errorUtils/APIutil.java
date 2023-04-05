package com.example.springclient.errorUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Response;

public class APIutil {


    public static String parseError(Response<?> response){
        String statusMessage = null;
        try {
            JSONObject jsonError = new JSONObject(response.errorBody().string());
            statusMessage = jsonError.getString("error");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return statusMessage;

    }


}
