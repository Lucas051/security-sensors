package com.mycompany.JTreeSensors.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

public class EventoAlarma {

    UUID id;
    LocalDateTime fechaHora;
    Sensor sensor;
    Alarma alarma;
    double valorUmbral;
    double valorSensor;

    public EventoAlarma() {
    }

    public EventoAlarma(UUID id, LocalDateTime fechaHora, Sensor sensor, Alarma alarma, double valorUmbral, double valorSensor) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.sensor = sensor;
        this.alarma = alarma;
        this.valorUmbral = valorUmbral;
        this.valorSensor = valorSensor;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Alarma getAlarma() {
        return alarma;
    }

    public void setAlarma(Alarma alarma) {
        this.alarma = alarma;
    }

    public double getValorUmbral() {
        return valorUmbral;
    }

    public void setValorUmbral(double valorUmbral) {
        this.valorUmbral = valorUmbral;
    }

    public double getValorSensor() {
        return valorSensor;
    }

    public void setValorSensor(double valorSensor) {
        this.valorSensor = valorSensor;
    }

    @Override
    public String toString() {
        return "EventoAlarma{" + "Fecha Hora= " + fechaHora + ", Sensor= " + sensor.getNombre() + ", Alarma= " + alarma.getNombre() + ", Valor Umbral= " + valorUmbral + ", Valor Sensor= " + valorSensor + "   - ID= " + id + '}';
    }

}
