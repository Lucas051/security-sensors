package com.mycompany.JTreeSensors.dominio;

import java.util.ArrayList;

public abstract class Dispositivo {

    private int id;
    public String nombre;

    public Dispositivo(String n, int id) {
        nombre = n;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        nombre = n;
    }

    abstract boolean seActivoAlarma();

    public abstract Dispositivo getHijo(int posicion);

    public abstract int getCantidadHijos();

    public abstract boolean esHoja();

    public abstract Dispositivo buscarPadre(Dispositivo hijo);

    public abstract int getIndiceHijo(Dispositivo hijo);

    public abstract void armarPath(Dispositivo ultimo, ArrayList path);

    public abstract boolean agregar(Dispositivo hijo);

    public abstract void eliminar(Dispositivo hijo);

    public abstract boolean isEnAlarma();

    public abstract void agregarAlarma(Alarma alarma);

}
