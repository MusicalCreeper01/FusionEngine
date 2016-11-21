package com.fusionengine.gui.elements;

import com.fusionengine.gui.GUIElement;
import com.fusionengine.gui.util.Fonts;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;

import static org.lwjgl.opengl.GL11.*;

public class GUIText extends GUIElement {

    public enum Alignment {
        Left,
        Middle,
        Right
    }

    public enum Vertical {
        Top,
        Middle,
        Bottom
    }

    public String text = "";
    public UnicodeFont font;

    public Alignment alignment = Alignment.Left;
    public Vertical vertical = Vertical.Top;

    public GUIText (String t, int x, int y){
        text = t;
        font = Fonts.getFont("default");
        this.x = x;
        this.y = y;
    }

    public GUIText (String t, int x, int y, int width, int height){
        text = t;
        font = Fonts.getFont("default");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(int width, int height){
        glEnable(GL_TEXTURE_2D);

        int drawX = 0;
        int drawY = 0;

        if(alignment == Alignment.Middle){
            drawX = (this.width/2)-(font.getWidth(text)/2);
        }else if (alignment == Alignment.Right){
            drawX = this.width-font.getWidth(text);
        }

        if(vertical == Vertical.Middle){
            drawY = (this.height/2)-(font.getHeight(text)/2);
        }else if (vertical == Vertical.Bottom){
            drawY = this.height-font.getHeight(text);
        }

        font.drawString(this.x+drawX, this.y+drawY, text);
        glDisable(GL_TEXTURE_2D); // MUST be disabled or objects will not properly render because text texture is still bound

    }
}
