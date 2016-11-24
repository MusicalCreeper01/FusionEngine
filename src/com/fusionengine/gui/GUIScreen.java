package com.fusionengine.gui;

import java.util.ArrayList;
import java.util.List;

public class GUIScreen {

    public boolean show = true;

    private List<GUIElement> elements = new ArrayList<>();

    public GUIScreen (){

    }

    public void render(int width, int height){
        for(GUIElement element : elements)
            element.render(width, height);
    }

    public void addElement(GUIElement element) {
        elements.add(element);
    }

    public void removeElement(GUIElement element){
        elements.remove(element);
    }

    public GUIElement[] getElements(){
        return elements.toArray(new GUIElement[elements.size()]);
    }

    public void hide(){
        show = false;
    }
    public void show(){
        show = true;
    }

}
