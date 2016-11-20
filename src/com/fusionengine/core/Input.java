package com.fusionengine.core;

import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldd20 on 11/19/2016.
 */
public class Input {

    public static Map<Integer, Boolean> keys = new HashMap<>();

    public static void poll(){
        keys.clear();
        while (Keyboard.next()) {
            keys.put(Keyboard.getEventKey(), Keyboard.getEventKeyState());
        }
    }

    public static boolean getKeyDown(int key){
        return keys.containsKey(key) ? keys.get(key) == true : false;
    }
    public static boolean getKeyUp(int key){
        if(keys.containsKey(key))
            return  keys.get(key) == false ? true : false;
        return false;
    }


}
