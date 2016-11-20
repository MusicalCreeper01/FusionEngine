package com.fusionengine.gui.elements;

import com.fusionengine.gui.GUIElement;
import com.fusionengine.gui.util.Fonts;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;

import static org.lwjgl.opengl.GL11.*;

public class GUIText extends GUIElement {

    public String text = "";
    public UnicodeFont font;

    public GUIText (String t, int x, int y){
        text = t;
        font = Fonts.getFont("default");
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(int width, int height){
        glEnable(GL_TEXTURE_2D);
        font.drawString(this.x, this.y, text);
        glDisable(GL_TEXTURE_2D); // MUST be disabled or objects will not properly render because text texture is still bound

    }
}
