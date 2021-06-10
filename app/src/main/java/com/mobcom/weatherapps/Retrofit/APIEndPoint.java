package com.mobcom.weatherapps.Retrofit;

import com.mobcom.weatherapps.Model.History;
import com.mobcom.weatherapps.Model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIEndPoint {
    @GET("current.json?key=5daa820d551d4c9083d164721212905&q=Jakarta&aqi=yes")
    Call<WeatherModel> getCurrentData();
    @GET("forecast.json?key=5daa820d551d4c9083d164721212905&q=Jakarta&days=3&aqi=no&alerts=no")
    Call<WeatherModel> getData();
    @GET Call<History> getHistoryData(@Url String url);
}
