package com.fusionengine.main;

import com.fusionengine.core.Input;
import com.fusionengine.gui.GUIElement;
import com.fusionengine.gui.GUIManager;
import com.fusionengine.gui.GUIScreen;
import com.fusionengine.gui.elements.GUISolid;
import com.fusionengine.gui.elements.GUIText;
import com.fusionengine.gui.util.Fonts;
import com.fusionengine.theme.Theme;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;


public class FusionEngine {

    public enum Mode {
        Theme ,
        Menu,
        Game
    }

    private Mode mode = Mode.Theme;
    private int theme = 0;

    private FusionEngine INSTANCE;

    private GUIManager guiManager;

    public FusionEngine(){
        INSTANCE = this;
        start();
    }

    public FusionEngine getEngine(){
        return INSTANCE;
    }

    public GUIManager getGuiManager(){
        return guiManager;
    }

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800,600));
            Display.setTitle("Fusion Engine");
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        int width = 800;
        int height = 600;

        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glViewport(0,0,width,height);
        glMatrixMode(GL_MODELVIEW);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);




        Theme.LoadThemes();

        guiManager = new GUIManager();

        GUIScreen themeScreen = new GUIScreen();
        UnicodeFont themeScreenFont = Fonts.registerFont("roboto", "assets/fonts/Roboto-Thin.ttf", 24);
        int pad = 4;
        {
            int fontHeight = 48;
            int h = (Display.getHeight() - (fontHeight * Theme.themes.length)) / 2;
            int i = 0;
            for (Theme theme : Theme.themes) {
                String name = theme.name;
                if(i == 0)
                    name = ">> " + theme.name + " <<";
                GUIText text = new GUIText(name, (Display.getWidth() / 2) - (themeScreenFont.getWidth(name) / 2), (h + ((fontHeight+pad) * i)) - ((fontHeight+pad) / 2));
                text.font = themeScreenFont;
                text.meta = i;
                themeScreen.addElement(text);
                ++i;
            }
        }



        GUIScreen menu = new GUIScreen();


        while (!Display.isCloseRequested()) {
            width = Display.getWidth();
            height = Display.getHeight();

            if (Display.wasResized()){
                glViewport(0, 0, width, height);
                glMatrixMode(GL_PROJECTION);
                glLoadIdentity();
                glOrtho(0, width, height, 0, 1, -1);
                glMatrixMode(GL_MODELVIEW);
            };

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Input.poll();


            if(mode == Mode.Theme){
                themeScreen.render(width, height);
                boolean themechange = false;

                boolean change = false;
                int last = -1;

                if ((Input.getKeyDown(Keyboard.KEY_W) || Input.getKeyDown(Keyboard.KEY_UP))) {
                    last = theme;
                    --theme;
                    if(theme < 0)
                        theme = 0;
                    change = true;
                }

                if ((Input.getKeyDown(Keyboard.KEY_S) || Input.getKeyDown(Keyboard.KEY_DOWN))) {
                    last = theme;
                    ++theme;
                    if(theme >= Theme.themes.length)
                        theme = Theme.themes.length-1;
                    change = true;
                }

                if(change && last != -1){
                    GUIElement[] elements = themeScreen.getElements();
                    ((GUIText)elements[last]).text = Theme.themes[(int)elements[last].meta].name;
                    ((GUIText)elements[last]).x = (Display.getWidth() / 2) - (themeScreenFont.getWidth(((GUIText)elements[last]).text) / 2);
                    ((GUIText)elements[theme]).text = ">> " + Theme.themes[(int)elements[theme].meta].name + " <<";
                    ((GUIText)elements[theme]).x = (Display.getWidth() / 2) - (themeScreenFont.getWidth(((GUIText)elements[theme]).text) / 2);
                    System.out.println("Changing theme selection... Last: " + last + " New: " + theme);
                }

                if(Input.getKeyDown(Keyboard.KEY_RETURN)){
                    mode = Mode.Menu;
                    GUISolid background = new GUISolid(Theme.themes[theme].root + Theme.themes[theme].background.src, 0, 0, width, height, true);
                    background.relativePosition = true;
                    menu.addElement(background);
                }

                Display.update();
                Display.sync(60);
            }else{
                Display.setResizable(true);
                menu.render(width, height);

//                glColor3f(1.0f,0.5f,0.5f);
//                glBegin(GL_QUADS);
//                glVertex2f(0,0);
//                glVertex2f(width,0);
//                glVertex2f(width,height);
//                glVertex2f(0,height);
//                glEnd();

                guiManager.render(width, height);

                Display.update();
                Display.sync(60);
            }



            if (Display.isCloseRequested()) {
                Display.destroy();
                System.exit(0);
            }
        }

        Display.destroy();
    }

    public static void main(String[] argv) {
        new FusionEngine();
    }

}
