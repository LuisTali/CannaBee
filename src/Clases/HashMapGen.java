package Clases;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapGen <K,V> implements Serializable {
    private K key;
    private V value;
    private HashMap <K,V> hashMap;

    public HashMapGen() {
        this.hashMap = new HashMap<>();
    }

    public void añadir(K key, V value){
        if (!hashMap.containsKey(key)) hashMap.put(key,value);
    }

    public void eliminar(K key){
        if (!hashMap.containsKey(key)) hashMap.remove(key);
    }

    public Iterator getIterator(){
        return hashMap.entrySet().iterator();
    }

    public int hSize(){
        return hashMap.size();
    }

    public void clearHashMap(){
        hashMap.clear();
    }

    public boolean containsKey(K key){
        if (hashMap.containsKey(key)) return true;
        else return false;
    }

    public V elementByKey(K key){
        if (hashMap.containsKey(key))
            return hashMap.get(key);
        else return null;
    }

    public K getKey(){return key;}

    public String mostrar(){
        StringBuilder buffer = new StringBuilder();
        for (Map.Entry<K,V> obj : hashMap.entrySet()) {
            buffer.append(obj.getKey().toString());
            buffer.append("||");
            buffer.append(obj.getValue().toString());
            buffer.append("");
        }
        return buffer.toString();
    }
}

