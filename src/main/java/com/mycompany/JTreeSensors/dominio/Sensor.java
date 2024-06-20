package com.mycompany.JTreeSensors.dominio;

import java.util.ArrayList;

public class Sensor extends Dispositivo {

    private ArrayList<IAlarmaObserver> observadores = new ArrayList<>();
    private ArrayList<Alarma> alarmas = new ArrayList<Alarma>();

    //estatico para que mantenga el ultimo cogigo generado
    private String tipoSensor;
    private double latitud;
    private double longitud;
    private double valorActual;
    private boolean activo;
    private boolean enAlarma;

    public Sensor(String n, int id, String tipoSensor, double latitud, double longitud, double valorActual, boolean activo) {
        super(n, id);
        this.tipoSensor = tipoSensor;
        this.latitud = latitud;
        this.longitud = longitud;
        this.valorActual = valorActual;
        this.activo = activo;
    }

    public ArrayList<Alarma> getAlarmas() {
        return alarmas;
    }

    public void setAlarmas(ArrayList<Alarma> alarmas) {
        this.alarmas = alarmas;
    }

    public void eliminarAlarma(String nombreAlarma) {
        for (int i = 0; i < alarmas.size(); i++) {
            if (alarmas.get(i).getNombre().equals(nombreAlarma)) {
                alarmas.remove(i);
            }
        }
    }

    public void setEnAlarma(boolean enAlarma) {
        this.enAlarma = enAlarma;
    }

    @Override
    public Dispositivo getHijo(int posicion) {
        return null;
    }

    @Override
    public int getCantidadHijos() {
        return 0;
    }

    @Override
    public boolean esHoja() {
        return true;
    }

    @Override
    public Dispositivo buscarPadre(Dispositivo hijo) {
        return null;
    }

    @Override
    public int getIndiceHijo(Dispositivo hijo) {
        return -1;
    }

    @Override
    public void armarPath(Dispositivo ultimo, ArrayList path) {
        return;
    }

    @Override
    public boolean agregar(Dispositivo hijo) {
        return false;
    }

    public void addObserver(IAlarmaObserver observador) {
        observadores.add(observador);
    }

    public double getValorActual() {
        return valorActual;
    }

    public void setValorActual(double nuevoValor) {
        this.valorActual = nuevoValor;
        //secuencia 1
        notificarAlarmas();
    }

    //secuencia 2
    private void notificarAlarmas() {
        for (Alarma alarma : alarmas) {
            alarma.actualizar(this);
        }
    }

    public ArrayList<IAlarmaObserver> getObservadores() {
        return observadores;
    }

    public void setObservadores(ArrayList<IAlarmaObserver> observadores) {
        this.observadores = observadores;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    @Override
    public String toString() {
        return "Sensor " + getNombre()
                + " | Id: " + getId()
                + " | Alarma: " + enAlarma
                + " | Tipo de Sensor: " + getTipoSensor()
                + " | Longitud: " + getLongitud()
                + " | Latitud: " + getLatitud()
                + " | Valor Actual: " + getValorActual();
    }

    @Override
    public boolean isEnAlarma() {
        return enAlarma;
    }

    @Override
    public void agregarAlarma(Alarma alarma) {
        if (alarma.getTipoDispositivo().equals(this.tipoSensor)) {
            alarmas.add(alarma);
        }
    }

    @Override
    public void eliminar(Dispositivo hijo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    boolean seActivoAlarma() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
