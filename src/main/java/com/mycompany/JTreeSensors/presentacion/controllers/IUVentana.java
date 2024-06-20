package com.mycompany.JTreeSensors.presentacion.controllers;

import com.mycompany.JTreeSensors.dominio.Alarma;
import com.mycompany.JTreeSensors.dominio.Dispositivo;
import com.mycompany.JTreeSensors.dominio.EventoAlarma;
import com.mycompany.JTreeSensors.dominio.ModeloArbol;
import java.util.ArrayList;
import javax.swing.JTree;

public interface IUVentana {

    void actualizarListadoDispositivos(Dispositivo raiz);

    String obtenerNombreRed();

    void setearNombreRed(String nombreRed);

    void setearNombreSensor(String nombreRed);

    void setearTipoSensor(String tipoSensor);

    void setearLatitud(String latitud);

    void setearLongitud(String longitud);

    void setearValorActual(String valorActual);

    void setearActivo(Boolean activo);

    JTree obtenerArbol();

    ModeloArbol obtenerModeloArbol();

    String obtenerNombreSensor();

    String obtenerTipoSensor();

    double obtenerLatitud();

    double obtenerLongitud();

    double obtenerValorActual();

    boolean obtenerActivo();

    String obtenerTipoSensorAlarma();

    String obtenerNombreAlarma();

    double obtenerUmbral();

    void listarAlarmas(ArrayList<Alarma> alarmas);

    void listarAlarmasPorSensor(ArrayList<Alarma> alarmas);

    void listarEventoAlarma(ArrayList<EventoAlarma> eventos);

}
