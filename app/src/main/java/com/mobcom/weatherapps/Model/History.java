package com.mobcom.weatherapps.Model;

public class History {
    private CurrentModel currentModel;
    private LocationModel locationModel;
    private Forecast forecast;

    public CurrentModel getCurrentModel() {
        return currentModel;
    }

    public void setCurrentModel(CurrentModel currentModel) {
        this.currentModel = currentModel;
    }

    public LocationModel getLocationModel() {
        return locationModel;
    }

    public void setLocationModel(LocationModel locationModel) {
        this.locationModel = locationModel;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}
