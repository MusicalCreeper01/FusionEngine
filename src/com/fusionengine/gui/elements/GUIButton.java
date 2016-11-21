package com.fusionengine.gui.elements;

import com.fusionengine.core.Input;
import com.fusionengine.gui.GUIElement;
import org.lwjgl.util.Color;

public class GUIButton extends GUIElement{

    public GUIElement background;
    public GUIElement text;

    public GUIButton(String s, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        GUISolid background = new GUISolid((Color)Color.CYAN, x, y, width, height);
        this.background = background;

        GUIText text = new GUIText(s, x, y, width, height);
        text.alignment = GUIText.Alignment.Middle;
        text.vertical = GUIText.Vertical.Middle;
        this.text = text;
    }

    public boolean pressed = false;

    public void render(int w, int h){
        pressed = false;
        background.render(w, h);
        text.render(w,h);

        /*System.out.println(">> Mouse: " + Input.getMousePosition().x  + "/" + Input.getMousePosition().y);
        System.out.println(">> Pos:   " + this.x + "/" + this.y);
        System.out.println(">> End:   " + (this.x+this.width) + "/" + (this.y+this.height));
        System.out.println("\n");*/

        if(Input.getMouseDown(0)
                && Input.getMousePosition().x > x
                && Input.getMousePosition().y > y
                && Input.getMousePosition().x < x+width
                && Input.getMousePosition().y < y+height) {

            System.out.println("Button pressed!");
            pressed = true;
        }
    }

    public boolean isPressed (){
        return pressed;
    }


}
