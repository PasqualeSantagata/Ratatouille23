package com.example.springclient.analytics;

public class AnalyticsData {
    private String cuoco;
    private Integer ordiniEvasi;

    public AnalyticsData(String cuoco, Integer ordiniEvasi){
        this.cuoco = cuoco;
        this.ordiniEvasi = ordiniEvasi;
    }

    public String getCuoco() {
        return cuoco;
    }

    public void setCuoco(String cuoco) {
        this.cuoco = cuoco;
    }

    public Integer getOrdiniEvasi() {
        return ordiniEvasi;
    }

    public void setOrdiniEvasi(Integer ordiniEvasi) {
        this.ordiniEvasi = ordiniEvasi;
    }
}
