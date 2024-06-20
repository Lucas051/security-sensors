package com.mycompany.JTreeSensors.presentacion.interfaz;

import com.mycompany.JTreeSensors.dominio.Dispositivo;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class ArbolCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, leaf, expanded, leaf, row, hasFocus);
        if (((Dispositivo) value).isEnAlarma()) {
            this.setForeground(Color.red);
        } else {
            this.setForeground(Color.black);
        }
        return this;
    }

}
