package com.mycompany.JTreeSensors.dominio;

import java.util.ArrayList;

public class Red extends Dispositivo {

    private ArrayList<Dispositivo> dispositivos;

    private ArrayList hijos = new ArrayList();

    public Red(String n, int id) {
        super(n, id);
        dispositivos = new ArrayList<>();
    }

    boolean seActivoAlarma() {
        for (Dispositivo dispositivo : dispositivos) {
            if (dispositivo.seActivoAlarma()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Dispositivo getHijo(int posicion) {
        return (Dispositivo) hijos.get(posicion);
    }

    public ArrayList<Dispositivo> getHijos() {
        return new ArrayList<Dispositivo>(hijos);
    }

    @Override
    public int getCantidadHijos() {
        return hijos.size();
    }

    @Override
    public boolean esHoja() {
        return false;
    }

    @Override
    public Dispositivo buscarPadre(Dispositivo hijo) {
        if (hijos.contains(hijo)) {
            return this;
        }
        Dispositivo padre = null;
        for (int x = 0; x < hijos.size() && padre == null; x++) {
            Dispositivo n = (Dispositivo) hijos.get(x);
            padre = n.buscarPadre(hijo);
        }
        return padre;
    }

    @Override
    public int getIndiceHijo(Dispositivo hijo) {
        return hijos.indexOf(hijo);

    }

    @Override
    public void armarPath(Dispositivo ultimo, ArrayList path) {
        for (int x = 0; x < hijos.size(); x++) {
            Dispositivo hijo = ((Dispositivo) hijos.get(x));
            if (hijo.equals(ultimo)) {
                path.add(this);
                path.add(ultimo);
                return;
            }
            hijo.armarPath(ultimo, path);
            if (path.contains(ultimo)) {
                path.add(0, this);
                return;
            }
        }
    }

    @Override
    public boolean agregar(Dispositivo hijo) {
        if (hijo.getNombre().trim().equals("")) {
            return false;
        }
        if (hijos.contains(hijo)) {
            return false;
        }
        return hijos.add(hijo);
    }

    @Override
    public String toString() {
        return "Red " + getNombre();
    }

    @Override
    public void eliminar(Dispositivo hijo) {
        hijos.remove(hijo);
    }

    @Override
    public boolean isEnAlarma() {
        for (Dispositivo d : dispositivos) {
            if (d.isEnAlarma()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void agregarAlarma(Alarma alarma) {
        for (Dispositivo disp : dispositivos) {
            disp.agregarAlarma(alarma);
        }
    }
}
