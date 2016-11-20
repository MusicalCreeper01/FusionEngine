package com.fusionengine.gui.util;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class Fonts {

    static Map<String, Font> awtfonts = new HashMap<>();
    static Map<String, UnicodeFont> fonts = new HashMap<>();

    /*public static void registerSystemFont(String name, int style, int size){
        registerSystemFont(name, name, style, size);
    }

    public static void registerSystemFont(String name, String fontname, int style, int size){
        if(fonts.containsKey(name))
            return;
        Font awtFont = new Font(fontname, style, size);//Font("Times New Roman", Font.BOLD, 24);
        awtfonts.put(name, awtFont);
        TrueTypeFont font = new TrueTypeFont(awtFont, true);
        fonts.put(name, font);
    }*/

    public static UnicodeFont registerFont(String name, String path, int size ){
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream(path);

            Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont = awtFont.deriveFont(size); // set font size
            UnicodeFont font = new UnicodeFont(awtFont, size, false, false);//, true);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect(Color.white));
            try {
                font.loadGlyphs();
                awtfonts.put(name, awtFont);
                fonts.put(name, font);
                return font;
            } catch (SlickException e) {
                e.printStackTrace();
                return null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UnicodeFont getFont(String name){
        return fonts.containsKey(name) ? fonts.get(name) : null;
    }

    public static void destroyFont(String name){
        if(fonts.containsKey(name))
            fonts.remove(name);
    }
}
