package com.mycompany.JTreeSensors.dominio;

import java.util.ArrayList;

public class AlarmaService {

    private ArrayList<Alarma> alarmas;
    private static AlarmaService instancia = null;

    ArrayList<IAlarmaObserver> observadores = new ArrayList<>();

    public static AlarmaService getInstance() {
        if (instancia == null) {
            instancia = new AlarmaService();
        }
        return instancia;
    }

    public AlarmaService() {
        observadores = new ArrayList<>();
        alarmas = new ArrayList<>();
    }

    public void agregarAlarma(Alarma alarma) {
        alarmas.add(alarma);
    }

    public void eliminarAlarma(String nombreAlarma) {
        for (int i = 0; i < alarmas.size(); i++) {
            if (alarmas.get(i).getNombre().equals(nombreAlarma)) {
                alarmas.remove(i);
            }
        }
    }

    public ArrayList<Alarma> obtenerAlarmas() {
        return alarmas;
    }

    //observadores
    public void agregarObservador(IAlarmaObserver observer) {
        if (!observadores.contains(observer)) {
            observadores.add(observer);
        }
    }

    public void eliminarObservador(IAlarmaObserver observer) {
        observadores.remove(observer);
    }

    public void notificarAlarmaActivada(EventoAlarma nuevoEvento) {

        for (IAlarmaObserver observer : observadores) {
            //secuencia 7
            observer.actualizar(nuevoEvento);

        }
    }

}
