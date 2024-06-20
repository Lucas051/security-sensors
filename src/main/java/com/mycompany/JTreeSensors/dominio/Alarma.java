package com.mycompany.JTreeSensors.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

public class Alarma {

    private String nombre;
    private String tipoDispositivo;
    private double umbral;

    public Alarma(String nombre, String tipoDispositivo, double umbral) {
        this.nombre = nombre;
        this.tipoDispositivo = tipoDispositivo;
        this.umbral = umbral;
    }

    //secuencia 4
    public void actualizar(Sensor sensor) {
        if (sensor.getValorActual() > umbral) {
            sensor.setEnAlarma(true);
            //secuencia 5
            UUID uuid = UUID.randomUUID();
            LocalDateTime fechaHora = LocalDateTime.now();
            EventoAlarma nuevoEvento = new EventoAlarma(uuid, fechaHora, sensor,
                    this, umbral, sensor.getValorActual());

            EventoAlarmaService.getInstance().agregarEvento(nuevoEvento);

            //secuencia 6
            AlarmaService.getInstance().notificarAlarmaActivada(nuevoEvento);

        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDispositivo() {
        return tipoDispositivo;
    }

    public void setTipoDispositivo(String tipoDispositivo) {
        this.tipoDispositivo = tipoDispositivo;
    }

    public double getUmbral() {
        return umbral;
    }

    public void setUmbral(double umbral) {
        this.umbral = umbral;
    }

    @Override
    public String toString() {
        return "Alarma " + nombre + " | Tipo Dispositivo=" + tipoDispositivo + " | Umbral=" + umbral;
    }

}
