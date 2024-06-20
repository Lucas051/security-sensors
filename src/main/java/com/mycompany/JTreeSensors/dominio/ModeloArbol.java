package com.mycompany.JTreeSensors.dominio;

import java.util.ArrayList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ModeloArbol implements TreeModel {

    private Dispositivo raiz;
    private ArrayList listeners = new ArrayList();

    public ModeloArbol(Dispositivo r) {
        raiz = r;
        //crear objeto root
    }

    @Override
    public Object getRoot() {
        return raiz;
        //retorna objeto root creado
    }

    @Override
    public Object getChild(Object dispositivo, int posicion) {
        return ((Dispositivo) dispositivo).getHijo(posicion);
    }

    @Override
    public int getChildCount(Object dispositivo) {
        return ((Dispositivo) dispositivo).getCantidadHijos();
    }
//preguntar

    @Override
    public boolean isLeaf(Object dispositivo) {

        if (dispositivo instanceof Sensor) {

            //  return ((Sensor) sensor).esHoja();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getIndexOfChild(Object dispositivo, Object hijo) {
        return ((Dispositivo) dispositivo).getIndiceHijo((Dispositivo) hijo);
    }

    public Object[] getPath(Dispositivo dispositivo) {
        ArrayList path = new ArrayList();
        if (dispositivo == raiz) {
            path.add(dispositivo);
        } else {
            raiz.armarPath(dispositivo, path);
        }
        //System.out.println(path);
        return path.toArray();
    }

    public boolean agregar(Dispositivo padre, Dispositivo hijo) {
        //Un nodo no puede estar dos veces en el mismo modelo.
        if (buscarPadre(hijo) != null) {
            return false;
        }
        if (padre.agregar(hijo)) {

            return true;
        }
        return false;

    }

    public Dispositivo buscarPadre(Dispositivo hijo) {
        return raiz.buscarPadre(hijo);
    }

    public boolean eliminar(Dispositivo hijo) {
        if (hijo == raiz) {
            return false;
        }
        Dispositivo padre = buscarPadre(hijo);
        if (padre != null) {
            padre.eliminar(hijo);
            notificar();
            return true;
        }
        return false;
    }

    private void notificar() {
        for (int x = 0; x < listeners.size(); x++) {
            TreeModelListener l = (TreeModelListener) listeners.get(x);
            l.treeStructureChanged(new TreeModelEvent(this, new TreePath(raiz)));
        }
    }

    ///// para observer
    @Override
    public void removeTreeModelListener(TreeModelListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void addTreeModelListener(TreeModelListener listener) {
        listeners.add(listener);
    }

    //////////////////////////////////////
    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

}
