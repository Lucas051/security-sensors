package com.mycompany.JTreeSensors.dominio;

import java.util.ArrayList;

public class EventoAlarmaService {

    private ArrayList<EventoAlarma> eventos;
    private ArrayList<IEventoAlarmaServiceObserver> observadores;
    private static EventoAlarmaService instance = null;

    public static EventoAlarmaService getInstance() {
        if (instance == null) {
            instance = new EventoAlarmaService();
        }
        return instance;
    }

    private EventoAlarmaService() {
        eventos = new ArrayList<>();
        observadores = new ArrayList<>();
    }

    public void agregarEvento(EventoAlarma nuevoEvento) {
        eventos.add(nuevoEvento);
        notificarObservadores();
    }

    public ArrayList<EventoAlarma> obtenerEventos() {
        return eventos;
    }

    public void agregarObservador(IEventoAlarmaServiceObserver observer) {
        if (!observadores.contains(observer)) {
            observadores.add(observer);
        }
    }

    public void eliminarObservador(IEventoAlarmaServiceObserver observer) {
        observadores.remove(observer);
    }

    public void notificarObservadores() {
        for (IEventoAlarmaServiceObserver observer : observadores) {
            observer.update(this);
        }
    }
}
