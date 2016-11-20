package com.fusionengine.gui;

import com.fusionengine.gui.util.Fonts;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIManager {

    private List<GUIScreen> screens = new ArrayList<>();

    public GUIManager(){
        //Fonts.registerSystemFont("default", "Times New Roman", Font.PLAIN, 24);
        Fonts.registerFont("default", "assets/fonts/Roboto-Thin.ttf", 24);
    }

    public void addScreen (GUIScreen screen) {
        screens.add(screen);
    }

    public void render(int width, int height){
        for(GUIScreen screen : screens) {
            if(screen.show)
                screen.render(width, height);
        }
    }

}
