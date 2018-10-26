package com.example.robin.sukarela.utility;

import com.example.robin.sukarela.model.ItemEvent;

import java.util.HashMap;

public class EventHelper {

    public static final HashMap<String, ItemEvent> MAP = new HashMap<>();

    public static void add(String key, ItemEvent event) {
        synchronized (MAP){
            MAP.put(key, event);
        }
    }

    public static void remove(String key) {
        synchronized (MAP){
            MAP.remove(key);
        }
    }

    public static ItemEvent get(String key) {
        synchronized (MAP){
            return MAP.get(key);
        }
    }

    public static int size(){
        synchronized (MAP){
            return EventHelper.MAP.keySet().size();
        }
    }

    public static Object[] list(){
        synchronized (MAP){
            return EventHelper.MAP.keySet().toArray();
        }
    }
}
