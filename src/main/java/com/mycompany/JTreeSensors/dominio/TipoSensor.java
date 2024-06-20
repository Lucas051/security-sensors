package com.mycompany.JTreeSensors.dominio;

public enum TipoSensor {

    Movimiento("Movimiento"),
    Gas("Gas"),
    Sismisco("Sismico"),
    Calor("Calor");

    private String sensor;

    private TipoSensor(String sen) {
        this.sensor = sen;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

}
