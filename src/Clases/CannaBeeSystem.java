package Clases;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CannaBeeSystem {
    //HashMapGen <String,Cepa> registroCepas = new HashMapGen<>();
    HashMapGen<Integer, HashMapGen> cepasUser = new HashMapGen<>();

    public void agregarCepaUser(Integer id, Cepa c) {
        if (cepasUser.containsKey(id)) {
            cepasUser.elementByKey(id).añadir(c.getNombre(), c);
        } else {
            cepasUser.añadir(id, new HashMapGen<String, Cepa>());
            cepasUser.elementByKey(id).añadir(c.getNombre(), c);
        }
    }

    public boolean cepasUserIsEmpty(int id) {
        if (cepasUser.hSize() > 0)
            return cepasUser.elementByKey(id).hSize() == 0;
        else return false;
    }

    public Iterator getCepasUserIterator(int id) {
        return cepasUser.elementByKey(id).getIterator();
    }

    public void cepasToFile() {
        try {
            File fl = new File("Data/GensUser.bin");
            createFolder(fl);
            FileOutputStream fo = new FileOutputStream(fl);
            ObjectOutputStream oO = new ObjectOutputStream(fo);
            int cont = 1;
            while (cont <= cepasUser.hSize()) {
                oO.writeObject(cepasUser.elementByKey(cont));
                cont++;
            }
            oO.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cepasReadFile() {
        try {
            File fl = new File("Data/GensUser.bin");
            if (fl.length() != 0) {
                FileInputStream fI = new FileInputStream(fl);
                ObjectInputStream oI = new ObjectInputStream(fI);
                int lectura = 1;
                while (lectura == 1) {
                    HashMapGen<Integer, HashMapGen> aux = (HashMapGen<Integer, HashMapGen>) oI.readObject();
                    System.out.println("Archivo siendo leido");
                    if (aux != null){
                        System.out.println(aux.mostrar());
                        cepasUser.añadir(aux.getKey(), aux);
                        int auxK = aux.getKey();
                        Iterator it = getCepasUserIterator(auxK);
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            Cepa c = (Cepa) entry.getValue();
                            System.out.println("Valor " + c.toString());
                            agregarCepaUser(auxK, (Cepa) entry.getValue());
                        }
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
