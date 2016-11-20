package com.fusionengine.gui.elements;

import com.fusionengine.gui.GUIElement;
import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

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
            texture = TextureLoader.getTexture("PNG", new FileInputStream(tex));//ResourceLoader.getResourceAsStream(tex));
            System.out.println("Texture loaded: "+texture);
            System.out.println(">> Image width: "+texture.getImageWidth());
            System.out.println(">> Image height: "+texture.getImageHeight());
            System.out.println(">> Texture width: "+texture.getTextureWidth());
            System.out.println(">> Texture height: "+texture.getTextureHeight());
            System.out.println(">> Texture ID: "+texture.getTextureID());

            /*if(fitRatio){
                float ratio = texture.getTextureWidth() / texture.getTextureHeight();
                int texwidth = texture.getTextureWidth();
                int texheight= texture.getTextureHeight();

                System.out.println("Sepecified width: " + width);
                System.out.println("Sepecified height: " + height);
                System.out.println("Ratio: " + ratio);

                if(texwidth>texheight){
                    height = (int)(ratio * width);
                }else if (texheight>texwidth){
                    width = (int)(ratio*height);
                }

                System.out.println("New width: " + width);
                System.out.println("New height: " + height);
            }*/
        }catch(IOException ex){
            ex.printStackTrace();
            System.out.println("Error loading texture " + tex);
        }
    }

    @Override
    public void render(int w, int h){


//        glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        glEnable(GL_TEXTURE_2D);
        texture.bind();
        glBegin(GL_QUADS);
        /*glTexCoord2f(0,0);
        glVertex2f(x,y);
        glTexCoord2f(1,0);
        glVertex2f(x+width,y);
        glTexCoord2f(1,1);
        glVertex2f(x+width,y+height);
        glTexCoord2f(0,1);
        glVertex2f(x,y+height);*/

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

//                System.out.println(w + "/" + h);

                if(relWidth>relHeight){
                    glTexCoord2f(0, 0);
                    glVertex2f(this.left, this.top);
                    glTexCoord2f(uvw, 0);
                    glVertex2f(relWidth, this.top);
                    glTexCoord2f(uvw, uvh);
                    glVertex2f(relWidth, relWidth);
                    glTexCoord2f(0, uvh);
                    glVertex2f(this.left, relWidth);
//                    System.out.println(relWidth);
                }else if (relHeight>relWidth){
                    glTexCoord2f(0, 0);
                    glVertex2f(this.left, this.top);
                    glTexCoord2f(uvw, 0);
                    glVertex2f(relHeight, this.top);
                    glTexCoord2f(uvw, uvh);
                    glVertex2f(relHeight, relHeight);
                    glTexCoord2f(0, uvh);
                    glVertex2f(this.left, relHeight);
//                    System.out.println(relHeight);
                }
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
