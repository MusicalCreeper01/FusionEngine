package com.fusionengine.main;

import com.fusionengine.core.Input;
import com.fusionengine.gui.GUIElement;
import com.fusionengine.gui.GUIManager;
import com.fusionengine.gui.GUIScreen;
import com.fusionengine.gui.elements.GUIButton;
import com.fusionengine.gui.elements.GUISolid;
import com.fusionengine.gui.elements.GUIText;
import com.fusionengine.gui.util.Fonts;
import com.fusionengine.shaders.Shader;
import com.fusionengine.theme.Theme;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.Color;
import org.newdawn.slick.UnicodeFont;

import javax.management.monitor.Monitor;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;


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

    private boolean resized = false;

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

            Keyboard.create();
            Mouse.create();
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

        GL13.glActiveTexture(GL13.GL_TEXTURE0);

        glViewport(0,0,width,height);
        glMatrixMode(GL_MODELVIEW);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);


        Shader shader = new Shader("assets/shaders/basic.vert", "assets/shaders/basic.frag");
        Shader.addScreenShader(shader);

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

        getGuiManager().addScreen(themeScreen);

        GUIScreen menu = new GUIScreen();

        GUISolid menuBackground = new GUISolid((Color)Color.BLACK, 0, 0, width, height);
        menuBackground.relativePosition = true;
        menu.addElement(menuBackground);
        GUIButton button = new GUIButton("Options", 100, 100, 120, 60);
        menu.addElement(button);

        menu.show = false;

        getGuiManager().addScreen(menu);


        while (!Display.isCloseRequested()) {
            width = Display.getWidth();
            height = Display.getHeight();

            if (Display.wasResized() || resized){
                glViewport(0, 0, width, height);
                glMatrixMode(GL_PROJECTION);
                glLoadIdentity();
                glOrtho(0, width, height, 0, 1, -1);
                glMatrixMode(GL_MODELVIEW);
            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Input.poll();

//            Shader.render();

            guiManager.render(width, height);

            if(mode == Mode.Theme){
                //themeScreen.render(width, height);
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

                    menuBackground.loadTexture(Theme.themes[theme].root + Theme.themes[theme].background.src, !Theme.themes[theme].background.stretch);
                    themeScreen.show = false;
                    menu.show = true;

                }


            }else{
                Display.setResizable(true);



            }



            Display.update();
            Display.sync(60);


//            ARBShaderObjects.glUseProgramObjectARB(0);
            if (Input.getKeyDown(Keyboard.KEY_F11)) {

                    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                    boolean fullscreen = !Display.isFullscreen();
                    if(fullscreen) {
                        setDisplayMode(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight(), fullscreen);
                    }else {
                        setDisplayMode(800, 600, fullscreen);
                        Display.setResizable(true);
                    }
                resized = true;

            }
            if (Display.isCloseRequested() || Input.getKeyDown(Keyboard.KEY_ESCAPE)) {
                Display.destroy();
                System.exit(0);
            }
        }

        Display.destroy();
    }

    public void setDisplayMode(int width, int height, boolean fullscreen) {

        // return if requested DisplayMode is already set
        if ((Display.getDisplayMode().getWidth() == width) &&
                (Display.getDisplayMode().getHeight() == height) &&
                (Display.isFullscreen() == fullscreen)) {
            return;
        }

        try {
            DisplayMode targetDisplayMode = null;

            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                for (int i=0;i<modes.length;i++) {
                    DisplayMode current = modes[i];

                    if ((current.getWidth() == width) && (current.getHeight() == height)) {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        // if we've found a match for bpp and frequence against the
                        // original display mode then it's probably best to go for this one
                        // since it's most likely compatible with the monitor
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
                                (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode(width,height);
            }

            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
                return;
            }

            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);

        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
        }
    }


    public DisplayMode[] getDisplayModes(boolean fullscreen) throws LWJGLException{
        DisplayMode displayMode = null;
        DisplayMode[] modes = Display.getAvailableDisplayModes();

        for (int i = 0; i < modes.length; i++)
        {
            /*if (modes[i].getWidth() == width
                    && modes[i].getHeight() == height
                    && modes[i].isFullscreenCapable())
            {
                displayMode = modes[i];
            }*/
            if(fullscreen){
                if(modes[i].isFullscreenCapable())
                    displayMode = modes[i];
            }else {
                displayMode = modes[i];
            }

        }
        return modes;
    }

    public static void main(String[] argv) {
        new FusionEngine();
    }

}
