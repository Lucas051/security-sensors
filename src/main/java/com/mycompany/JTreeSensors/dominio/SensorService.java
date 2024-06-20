package com.mycompany.JTreeSensors.dominio;

import java.util.ArrayList;

public class SensorService {

    private ArrayList<Sensor> sensores;

    private static SensorService instancia = null;

    public static SensorService getInstance() {
        if (instancia == null) {
            instancia = new SensorService();
        }
        return instancia;
    }

    private SensorService() {
        sensores = new ArrayList<>();
    }

    public void agregarSensor() {

    }

    public ArrayList<Sensor> obtenerSensores() {
        return sensores;
    }

}
