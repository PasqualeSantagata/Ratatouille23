package com.example.springclient.contract;

import retrofit2.Response;

public interface CallbackResponse<T> {
    void onFailure(Throwable t);
    void onSuccess(Response<T> retData);
}
