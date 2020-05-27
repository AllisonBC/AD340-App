package com.example.ad340app_a1;

public interface OnGetDataListener<T> {
    // this is for callbacks
    void onSuccess(T dataResponse);
    void onFailure();
}
