package Clases;

import java.io.*;
import java.security.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CannaBeeSystem {
    HashMapGen<Integer, HashMapGen<String, Cepa>> cepasUser = new HashMapGen<>();
    HashMapGen<String, HashMapGen<String, Cepa>> cepasBancos = new HashMapGen<>();
    Connect con = new Connect();

    public void agregarCepaUser(Integer id, Cepa c) {
        if (cepasUser.containsKey(id)) {
            cepasUser.elementByKey(id).añadir(c.getNombre(), c);
            System.out.println("ID ya creado: " + id);
        } else {
            HashMapGen<String, Cepa> registroCepas = new HashMapGen<>();
            registroCepas.añadir(c.getNombre(), c);
            System.out.println("ID Creado: " + id);
            cepasUser.añadir(id, registroCepas);
        }
    }

    public void agregarCepaBanco(String key, Cepa c) {
        if (cepasBancos.containsKey(key)) {
            cepasBancos.elementByKey(key).añadir(c.getNombre(), c);
            System.out.println("Banco ya creado: " + key);
        } else {
            HashMapGen<String, Cepa> registroCepas = new HashMapGen<>();
            registroCepas.añadir(c.getNombre(), c);
            System.out.println("Banco Creado: " + key);
            cepasBancos.añadir(key, registroCepas);
        }
    }

    public void eliminarCepa(int id, String nombre){
        if (cepasUser.containsKey(id)){
             cepasUser.elementByKey(id).eliminar(nombre);
        }
    }

    public void editCepa(int id, String nombre, double thc, String raza, String comments) {
        Cepa aux = null;
        if (comments != null) aux = new Cepa(nombre, raza, thc, comments);
        else aux = new Cepa(nombre, raza, thc);
        HashMapGen<String, Cepa> auxH = cepasUser.elementByKey(id);
        if (auxH.containsKey(aux.getNombre()))
            cepasUser.elementByKey(id).elementByKey(nombre); //Finiquitar.
    }

    public boolean cepasListIsEmpty() {
        if (cepasUser.hSize() > 0) return false;
        else return true;
    }

    public boolean cepasUserIsEmpty(int id) {
        if (cepasUser.hSize() > 0)
            if (cepasUser.elementByKey(id).hSize() > 0) return false;
            else return true;
        else return false;
    }

    public Iterator getUserCepasListIterator() {
        return cepasUser.getIterator();
    }

    public Iterator getCepasUserIterator(int id) {
        return cepasUser.elementByKey(id).getIterator();
    }

    public Iterator getCepasBancosIterator(){return cepasBancos.getIterator();}

    public Iterator getCepasPorBancoIterator(String key){return cepasBancos.elementByKey(key).getIterator();}

    public void cepasToFile() {
        if (cepasUser.hSize() > 0) {
            try {
                File fl = new File("Data/GensUser.bin");
                createFolder(fl);
                FileOutputStream fo = new FileOutputStream(fl);
                ObjectOutputStream oO = new ObjectOutputStream(fo);
                System.out.println("Cargando archivo");
                System.out.println(cepasUser.mostrar());
                oO.writeObject(cepasUser);
                System.out.println("Mapa cargado");
                oO.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else System.out.println("Mapa Cepas Vacio"); //Borrar luego los sout del System.

    }

    public void cepasReadFile() {
        try {
            File fl = new File("Data/GensUser.bin");
            if (fl.length() != 0) {
                FileInputStream fI = new FileInputStream(fl);
                ObjectInputStream oI = new ObjectInputStream(fI);
                int lectura = 1;
                while (lectura == 1) {
                    HashMapGen<Integer, HashMapGen<String, Cepa>> aux = (HashMapGen<Integer, HashMapGen<String, Cepa>>) oI.readObject();
                    System.out.println("Archivo siendo leido");
                    if (aux != null) {
                        //System.out.println(aux.mostrar()); Borrar linea luego.
                        cepasUser = aux;
                    }
                }
                oI.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createFolder(File file) {
        File folder = file.getParentFile();
        if (!folder.exists()) folder.mkdir();
    }
}
