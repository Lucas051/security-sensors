package com.mycompany.JTreeSensors.dominio;

import java.util.ArrayList;

public class DispositivosService {

    private Red raiz;
    private static DispositivosService instancia = null;
    private int contadorIds = 0;

    public static DispositivosService getInstance() {
        if (instancia == null) {
            instancia = new DispositivosService();
        }
        return instancia;
    }

    public DispositivosService() {
        raiz = new Red("ROOT", getIdAutoincremental());

        Alarma alarma = new Alarma("Seguimiento Termico", "Calor", 65);

        AlarmaService.getInstance().agregarAlarma(alarma);

        Sensor nuevo = new Sensor("Termometro 3B", getIdAutoincremental(), TipoSensor.Calor.toString(), 56, 65, 60, true);

        nuevo.agregarAlarma(alarma);

        raiz.agregar(nuevo);
    }

    public Dispositivo getRaiz() {
        return raiz;
    }

    public void agregarDispositivo(Dispositivo dispositivo, Red contenedora) {
        if (contenedora != null) {
            contenedora.agregar(dispositivo);
        } else {
            raiz.agregar(dispositivo);
        }
    }

    public ArrayList<Sensor> getSensoresDeRed(Red red, String tipoSensor) {
        ArrayList<Sensor> retorno = new ArrayList<Sensor>();

        for (Dispositivo disp : red.getHijos()) {
            if (disp.esHoja()) {
                Sensor s = (Sensor) disp;
                if (s.getTipoSensor().equals(tipoSensor)) {
                    retorno.add((Sensor) disp);
                }
            } else {
                retorno.addAll(getSensoresDeRed((Red) disp, tipoSensor));
            }
        }

        return retorno;
    }

    public ArrayList<Sensor> getSensoresDeRed(Red red) {
        ArrayList<Sensor> retorno = new ArrayList<Sensor>();

        for (Dispositivo disp : red.getHijos()) {
            if (disp.esHoja()) {
                retorno.add((Sensor) disp);
            } else {
                retorno.addAll(getSensoresDeRed((Red) disp));
            }
        }

        return retorno;
    }

    public int getIdAutoincremental() {
        contadorIds++;
        return contadorIds;
    }
}
