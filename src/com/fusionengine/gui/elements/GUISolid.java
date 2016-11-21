package com.fusionengine.gui.elements;

import com.fusionengine.gui.GUIElement;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class GUISolid extends GUIElement{

    private Texture texture;

    public Color color;
    public int x = 0;
    public int y = 0;
    public int width = 0;
    public int height = 0;
    public boolean preserveRatio;

    public GUISolid (Color color, int x, int y, int width, int height){
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GUISolid (String tex, int x, int y, int width, int height, boolean preserveRatio){
        this.color = (Color)Color.WHITE;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.preserveRatio = preserveRatio;
        try {
            System.out.println(tex);
            String type = tex.split("\\.")[1].toUpperCase();
            type = type == "JPEG" ? "JPG" : type;

            texture = TextureLoader.getTexture(type, new FileInputStream(tex));//ResourceLoader.getResourceAsStream(tex));

            System.out.println("Texture loaded: "+texture);
            System.out.println(">> Image width: "+texture.getImageWidth());
            System.out.println(">> Image height: "+texture.getImageHeight());
            System.out.println(">> Texture width: "+texture.getTextureWidth());
            System.out.println(">> Texture height: "+texture.getTextureHeight());
            System.out.println(">> Texture ID: "+texture.getTextureID());

            System.out.println(">> Enforcing Ratio: "+preserveRatio);
            System.out.println(">> Image Ratio: " + ((double)texture.getImageHeight() / texture.getImageWidth()));

        }catch(IOException ex){
            ex.printStackTrace();
            System.out.println("Error loading texture " + tex);
        }
    }

    @Override
    public void render(int w, int h){

        glEnable(GL_TEXTURE_2D);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        texture.bind();
        glBegin(GL_QUADS);

        float uvw = ((texture.getImageWidth() * 1.0f) / texture.getTextureWidth());
        float uvh = ((texture.getImageHeight() * 1.0f) / texture.getTextureHeight());

        if(!this.relativePosition) {
            if(preserveRatio) {
                if(w>h){
                    glTexCoord2f(0, 0);
                    glVertex2f(x, y);
                    glTexCoord2f(uvw, 0);
                    glVertex2f(x + width, y);
                    glTexCoord2f(uvw, uvh);
                    glVertex2f(x + width, y + width);
                    glTexCoord2f(0, uvh);
                    glVertex2f(x, y + width);
                }else if (h>w){
                    glTexCoord2f(0, 0);
                    glVertex2f(x, y);
                    glTexCoord2f(uvw, 0);
                    glVertex2f(x + height, y);
                    glTexCoord2f(uvw, uvh);
                    glVertex2f(x + height, y + height);
                    glTexCoord2f(0, uvh);
                    glVertex2f(x, y + height);
                }
            }else {
                glTexCoord2f(0, 0);
                glVertex2f(x, y);
                glTexCoord2f(1, 0);
                glVertex2f(x + width, y);
                glTexCoord2f(1, 1);
                glVertex2f(x + width, y + height);
                glTexCoord2f(0, 1);
                glVertex2f(x, y + height);
            }
        }else {
            if(preserveRatio) {
                int relWidth = (w-this.right)-this.left;
                int relHeight= (h-this.bottom)-this.top;

                double ratio1 = (double)texture.getImageHeight() / texture.getImageWidth();
                double ratio2 = (double)texture.getImageWidth() / texture.getImageHeight();

                int tw = 0;
                int th = 0;

                th = relHeight;
                tw = (int)(th*ratio2);

                if(tw < relWidth){
                    tw = relWidth;
                    th = (int)(tw*ratio1);
                }

                glTexCoord2f(0, 0);
                glVertex2f(0, 0);
                glTexCoord2f(uvw, 0);
                glVertex2f(tw, 0);
                glTexCoord2f(uvw, uvh);
                glVertex2f(tw, th);
                glTexCoord2f(0, uvh);
                glVertex2f(0, th);

                /*
                th = h;
                tw = (int)(th*ratio2);

                if(tw < w){
                    tw = w;
                    th = (int)(tw*ratio1);
                }

                glTexCoord2f(0, 0);
                glVertex2f(0, 0);
                glTexCoord2f(uvw, 0);
                glVertex2f(tw, 0);
                glTexCoord2f(uvw, uvh);
                glVertex2f(tw, th);
                glTexCoord2f(0, uvh);
                glVertex2f(0, th);*/
            }else {
                glTexCoord2f(0, 0);
                glVertex2f(this.left, this.top);
                glTexCoord2f(uvw, 0);
                glVertex2f(w - this.right, this.top);
                glTexCoord2f(uvw, uvh);
                glVertex2f(w - this.right, h - this.bottom);
                glTexCoord2f(0, uvh);
                glVertex2f(this.left, h - this.bottom);
            }
        }

        glEnd();
        glDisable(GL_TEXTURE_2D);
    }


}
