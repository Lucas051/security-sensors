package com.mycompany.JTreeSensors.presentacion.controllers;

import com.mycompany.JTreeSensors.dominio.Alarma;
import com.mycompany.JTreeSensors.dominio.AlarmaService;
import com.mycompany.JTreeSensors.dominio.Dispositivo;
import com.mycompany.JTreeSensors.dominio.DispositivosService;
import com.mycompany.JTreeSensors.dominio.EventoAlarma;
import com.mycompany.JTreeSensors.dominio.EventoAlarmaService;
import com.mycompany.JTreeSensors.dominio.IAlarmaObserver;
import com.mycompany.JTreeSensors.dominio.IEventoAlarmaServiceObserver;
import com.mycompany.JTreeSensors.dominio.Red;
import com.mycompany.JTreeSensors.dominio.Sensor;
import java.util.ArrayList;
import javax.swing.tree.TreePath;

public class VentanaController implements IAlarmaObserver, IEventoAlarmaServiceObserver {

    private IUVentana iu;
    private DispositivosService dispositivosService;

    public VentanaController(IUVentana iu) {
        this.iu = iu;
        iu.actualizarListadoDispositivos((Red) DispositivosService.getInstance().getRaiz());

        this.dispositivosService = DispositivosService.getInstance();

        AlarmaService.getInstance().agregarObservador(this);

        iu.listarEventoAlarma(EventoAlarmaService.getInstance().obtenerEventos());
        EventoAlarmaService.getInstance().agregarObservador(this);

    }

    public void agregarSimple() {

        try {
            String tipoSensor = iu.obtenerTipoSensor();
            double latitud = iu.obtenerLatitud();
            double longitud = iu.obtenerLongitud();
            double valorActual = iu.obtenerValorActual();
            boolean activo = iu.obtenerActivo();
            String nombreSensor = iu.obtenerNombreSensor();

            // New objeto Sensor
            Dispositivo nuevo = new Sensor(nombreSensor, dispositivosService.getIdAutoincremental(), tipoSensor, latitud, longitud, valorActual, activo);

            agregarDispositivo(nuevo);

            iu.actualizarListadoDispositivos((Red) DispositivosService.getInstance().getRaiz());

        } catch (NumberFormatException e) {
        }

    }

    public void agregarCompuesto() {
        Dispositivo dispositivo = new Red(iu.obtenerNombreRed(), dispositivosService.getIdAutoincremental());

        agregarDispositivo(dispositivo);

        //esto soluciono el tema de que no se actualizaba el jtree
        // Crear un nuevo modelo con el nuevo dispositivo como raíz
        iu.actualizarListadoDispositivos((Red) DispositivosService.getInstance().getRaiz());

    }

    public void eliminarRed() {
        Dispositivo seleccionado = getDispositivoSeleccionado();
        if (seleccionado.esHoja()) {
            throw new RuntimeException("Debe seleccionar un red, no un sensor");
        }

        if (seleccionado.getCantidadHijos() > 0) {
            throw new RuntimeException("Debe seleccionar un red sin hijos");
        }

        Dispositivo padre = iu.obtenerModeloArbol().buscarPadre(seleccionado);
        iu.obtenerModeloArbol().eliminar(seleccionado);
    }

    public void eliminarSensor() {
        Dispositivo seleccionado = getDispositivoSeleccionado();
        if (!seleccionado.esHoja()) {
            throw new RuntimeException("Debe seleccionar un sensor, no una red");
        }

        Dispositivo padre = iu.obtenerModeloArbol().buscarPadre(seleccionado);
        if (iu.obtenerModeloArbol().eliminar(seleccionado)) {
            iu.obtenerArbol().expandPath(new TreePath(iu.obtenerModeloArbol().getPath(padre)));
        }
    }

    public void editarNombreDispositivo(String nuevoNombre) {
        Dispositivo seleccionado = getDispositivoSeleccionado();
        seleccionado.setNombre(nuevoNombre);
        iu.actualizarListadoDispositivos((Red) DispositivosService.getInstance().getRaiz());
    }

    //PARA MODIFICAR UN SENSOR
    public void editarSensor() {
        String tipoSensor = iu.obtenerTipoSensor();
        double latitud = iu.obtenerLatitud();
        double longitud = iu.obtenerLongitud();
        double valorActual = iu.obtenerValorActual();
        boolean activo = iu.obtenerActivo();

        Sensor sensorSeleccionado = getSensorSeleccionado();

        sensorSeleccionado.setActivo(activo);
        sensorSeleccionado.setLatitud(latitud);
        sensorSeleccionado.setLongitud(longitud);
        sensorSeleccionado.setTipoSensor(tipoSensor);
        sensorSeleccionado.setValorActual(valorActual);

        iu.actualizarListadoDispositivos((Red) DispositivosService.getInstance().getRaiz());
    }

    public void manejarDispositivoSeleccionado() {
        Dispositivo seleccionado = getDispositivoSeleccionado();
        if (seleccionado.esHoja()) {
            Sensor sensor = getSensorSeleccionado();

            iu.setearNombreSensor(sensor.getNombre());
            iu.setearTipoSensor(sensor.getTipoSensor());
            iu.setearLatitud(String.valueOf(sensor.getLatitud()));
            iu.setearLongitud(String.valueOf(sensor.getLongitud()));
            iu.setearValorActual(String.valueOf(sensor.getValorActual()));

            iu.setearActivo(sensor.isActivo());

        } else {
            iu.setearNombreRed(seleccionado.getNombre());
        }
    }

    public void agregarAlarma() {
        AlarmaService alarmaService = AlarmaService.getInstance();

        String tipoSensor = iu.obtenerTipoSensorAlarma();
        String nombre = iu.obtenerNombreAlarma();
        double umbral = iu.obtenerUmbral();
        Alarma alarma = new Alarma(nombre, tipoSensor, umbral);

        Dispositivo dispositivoSeleccionado = getDispositivoSeleccionado();
        if (dispositivoSeleccionado.esHoja()) {
            Sensor sensorSeleccionado = (Sensor) dispositivoSeleccionado;

            if (!sensorSeleccionado.getTipoSensor().equals(tipoSensor)) {
                throw new RuntimeException("El tipo del sensor seleccionado debe ser igual al tipo de la alarma que se le quiere asignar");
            }

            sensorSeleccionado.agregarAlarma(alarma);
            if (sensorSeleccionado.getValorActual() >= alarma.getUmbral()) {
                sensorSeleccionado.setEnAlarma(true);
            }
        } else { //Caso red
            ArrayList<Sensor> sensoresDeRed = dispositivosService.getSensoresDeRed((Red) dispositivoSeleccionado, tipoSensor);

            for (Sensor sensor : sensoresDeRed) {
                if (sensor.getTipoSensor().equals(tipoSensor)) {
                    sensor.agregarAlarma(alarma);

                    if (sensor.getValorActual() >= alarma.getUmbral()) {
                        sensor.setEnAlarma(true);
                    }
                }
            }
        }

        alarmaService.agregarAlarma(alarma);

        actualizarListaDeAlarmas();

        iu.actualizarListadoDispositivos(DispositivosService.getInstance().getRaiz());

    }

    public void eliminarAlarma() {
        String nombreAlarma = iu.obtenerNombreAlarma();
        AlarmaService alarmaService = AlarmaService.getInstance();

        Red dispositivoSeleccionado = (Red) DispositivosService.getInstance().getRaiz();

        ArrayList<Sensor> sensoresDeRed = dispositivosService.getSensoresDeRed(dispositivoSeleccionado);
        for (Sensor sensor : sensoresDeRed) {
            sensor.eliminarAlarma(nombreAlarma);
        }

        alarmaService.eliminarAlarma(nombreAlarma);
        actualizarListaDeAlarmas();
        iu.actualizarListadoDispositivos(DispositivosService.getInstance().getRaiz());
    }

    private void agregarDispositivo(Dispositivo hijo) {
        Dispositivo seleccionado = getDispositivoSeleccionado();
        if (iu.obtenerModeloArbol().agregar(seleccionado, hijo)) {
            iu.obtenerArbol().expandPath(new TreePath(iu.obtenerModeloArbol().getPath(seleccionado)));
        }
    }

    public Dispositivo getDispositivoSeleccionado() {
        Dispositivo seleccionado = (Dispositivo) iu.obtenerArbol().getLastSelectedPathComponent();
        if (seleccionado == null) {
            seleccionado = DispositivosService.getInstance().getRaiz();
        }
        return seleccionado;
    }

    public Sensor getSensorSeleccionado() throws RuntimeException {
        Sensor seleccionado = (Sensor) iu.obtenerArbol().getLastSelectedPathComponent();
        if (seleccionado == null) {
            throw new RuntimeException("Debe seleccionar un sensor");
        }
        return seleccionado;
    }

    @Override
    public void actualizar(EventoAlarma nuevoEvento) {
        iu.actualizarListadoDispositivos(DispositivosService.getInstance().getRaiz());

    }

    public void actualizarListaDeAlarmas() {
        iu.listarAlarmas(AlarmaService.getInstance().obtenerAlarmas());
    }

    public void actualizarListaAlarmasPorSensor() {
        Dispositivo seleccionado = getDispositivoSeleccionado();
        if (!seleccionado.esHoja()) {
            throw new ClassCastException("Debe seleccionar un sensor, no una red");
        }
        Sensor sensorSeleccionado = (Sensor) seleccionado;
        ArrayList<Alarma> alarmasDelSensor = sensorSeleccionado.getAlarmas();

        iu.listarAlarmasPorSensor(alarmasDelSensor);
    }

    @Override
    public void update(EventoAlarmaService eventoAlarma) {
        iu.listarEventoAlarma(EventoAlarmaService.getInstance().obtenerEventos());
    }

}
