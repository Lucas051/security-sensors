package com.mycompany.JTreeSensors;

import com.mycompany.JTreeSensors.presentacion.controllers.VentanaController;
import com.mycompany.JTreeSensors.presentacion.interfaz.VentanaPrincipal;

public class JTreeSensors {

    public static void main(String[] args) {
        VentanaPrincipal ven = new VentanaPrincipal();
        VentanaController controller = new VentanaController(ven);
        ven.setController(controller);
        ven.setVisible(true);
    }
}
