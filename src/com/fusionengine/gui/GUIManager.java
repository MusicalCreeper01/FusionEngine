package com.fusionengine.gui;

import com.fusionengine.gui.util.Fonts;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class GUIManager {

    private List<GUIScreen> screens = new ArrayList<>();

   // private int displayList = 0;

    public GUIManager(){
        //Fonts.registerSystemFont("default", "Times New Roman", Font.PLAIN, 24);
        Fonts.registerFont("default", "assets/fonts/Roboto-Thin.ttf", 24);
       // displayList = glGenLists(1);
    }

    public void addScreen (GUIScreen screen) {
        screens.add(screen);
       // compile(Display.getWidth(), Display.getHeight());
    }

  /*  public void compile(int width, int height){
        glNewList(displayList, GL_COMPILE);
        for(GUIScreen screen : screens) {
            if(screen.show)
                screen.render(width, height);
        }
        glEndList();
    }*/

    public void render(int width, int height){
        glPushMatrix();
       // glCallList(displayList);
        for(GUIScreen screen : screens) {
            if(screen.show)
                screen.render(width, height);
        }
        glPopMatrix();
    }

}
