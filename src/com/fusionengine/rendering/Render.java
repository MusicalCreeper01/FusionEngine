package com.fusionengine.rendering;


import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

public class Render {

    public static void initGL(){
        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void configure2D(){
        initGL();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        GLU.gluOrtho2D(0.0f, Display.getWidth(), Display.getHeight(), 0.0f);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glDisable(GL_DEPTH_TEST);
    }

    public static void configure3D(){
        initGL();

        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        glMatrixMode(GL_PROJECTION);

        glLoadIdentity();
        GLU.gluPerspective(75, ((float)Display.getWidth()/(float)Display.getHeight()), 0.1f, 5000.0f);


        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glDepthFunc(GL_LEQUAL);
        glEnable(GL_DEPTH_TEST);

    }
}
