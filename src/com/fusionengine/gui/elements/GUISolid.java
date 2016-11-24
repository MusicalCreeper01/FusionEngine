package com.fusionengine.gui.elements;

import com.fusionengine.core.Loader;
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
        loadTexture(tex, this.preserveRatio);
    }

    public void loadTexture(String tex, boolean preserveRatio){
        this.preserveRatio = preserveRatio;
        texture = Loader.loadTexture(tex);
    }

    @Override
    public void render(int w, int h){

        float uvw = 1;
        float uvh = 1;

        if(texture != null) {
            //System.out.println("texture: " + texture.getTextureID());
            glEnable(GL_TEXTURE_2D);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            texture.bind();

            uvw = ((texture.getImageWidth() * 1.0f) / texture.getTextureWidth());
            uvh = ((texture.getImageHeight() * 1.0f) / texture.getTextureHeight());
        }else{
            glDisable(GL_TEXTURE_2D);
            glColor4f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f);
        }
        glPushMatrix();

        glBegin(GL_QUADS);

//        if(!this.relativePosition) {
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
                glTexCoord2f(uvw, 0);
                glVertex2f(x + width, y);
                glTexCoord2f(uvw, uvh);
                glVertex2f(x + width, y + height);
                glTexCoord2f(0, uvh);
                glVertex2f(x, y + height);
            }
//        }else {
//            if(preserveRatio) {
//                int relWidth = (w-this.right)-this.left;
//                int relHeight= (h-this.bottom)-this.top;
//
//                double ratio1 = (double)texture.getImageHeight() / texture.getImageWidth();
//                double ratio2 = (double)texture.getImageWidth() / texture.getImageHeight();
//
//                int tw = 0;
//                int th = 0;
//
//                th = relHeight;
//                tw = (int)(th*ratio2);
//
//                if(tw < relWidth){
//                    tw = relWidth;
//                    th = (int)(tw*ratio1);
//                }
//
//                glTexCoord2f(0, 0);
//                glVertex2f(0, 0);
//                glTexCoord2f(uvw, 0);
//                glVertex2f(tw, 0);
//                glTexCoord2f(uvw, uvh);
//                glVertex2f(tw, th);
//                glTexCoord2f(0, uvh);
//                glVertex2f(0, th);
//            }else {
//                glTexCoord2f(0, 0);
//                glVertex2f(this.left, this.top);
//                glTexCoord2f(uvw, 0);
//                glVertex2f(w - this.right, this.top);
//                glTexCoord2f(uvw, uvh);
//                glVertex2f(w - this.right, h - this.bottom);
//                glTexCoord2f(0, uvh);
//                glVertex2f(this.left, h - this.bottom);
//            }
//        }


        glEnd();
        glPopMatrix();

        if(texture != null) {
            glDisable(GL_TEXTURE_2D);
        }
    }


}
