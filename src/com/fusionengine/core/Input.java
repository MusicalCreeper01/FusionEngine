package com.fusionengine.core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldd20 on 11/19/2016.
 */
public class Input {

    public static Map<Integer, Boolean> keys = new HashMap<>();
    public static Map<Integer, Boolean> mouse = new HashMap<>();

    public static Map<Integer, Boolean> keysDown = new HashMap<>();
    public static Map<Integer, Boolean> mouseDown = new HashMap<>();

    public static void poll(){
        keys.clear();
        mouse.clear();
        while (Keyboard.next()) {
            keys.put(Keyboard.getEventKey(), Keyboard.getEventKeyState());
            keysDown.put(Keyboard.getEventKey(), Keyboard.getEventKeyState());
        }
        while(Mouse.next()){
            mouse.put(Mouse.getEventButton(), Mouse.getEventButtonState());
            mouseDown.put(Mouse.getEventButton(), Mouse.getEventButtonState());
        }
    }

    public static boolean getKeyDown(int key){
        return keys.containsKey(key) ? keys.get(key) == true : false;
    }
    public static boolean getKey(int key){
        return keysDown.containsKey(key) ? keysDown.get(key) : false;
    }
    public static boolean getKeyUp(int key){
        if(keys.containsKey(key))
            return  keys.get(key) == false ? true : false;
        return false;
    }

    public static boolean getMouseDown(int mousebutton) {
        return mouse.containsKey(mousebutton) ? mouse.get(mousebutton) : false;
    }
    public static boolean getMouse(int mousebutton){
        return mouseDown.containsKey(mousebutton) ? mouseDown.get(mousebutton) : false;
    }
    public static boolean getMouseUp(int mousebutton){
        if(mouse.containsKey(mousebutton))
            return  mouse.get(mousebutton) == false ? true : false;
        return false;
    }

    public static Vector2f getMousePosition(){
        //return new Vector2f(Mouse.getDX(), Mouse.getDY()); // returns mouse movement since last poll
        return new Vector2f(Mouse.getX(), Mouse.getY());
    }


}
