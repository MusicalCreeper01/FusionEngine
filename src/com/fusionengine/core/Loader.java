package com.fusionengine.core;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;

public class Loader {
    public static Texture loadTexture(String tex){
        try {
            System.out.println(tex);
            String type = tex.split("\\.")[1].toUpperCase();
            type = type == "JPEG" ? "JPG" : type;

            Texture texture = TextureLoader.getTexture(type, new FileInputStream(tex));//ResourceLoader.getResourceAsStream(tex));

            System.out.println("Texture loaded: "+texture);
            System.out.println(">> Image width: "+texture.getImageWidth());
            System.out.println(">> Image height: "+texture.getImageHeight());
            System.out.println(">> Texture width: "+texture.getTextureWidth());
            System.out.println(">> Texture height: "+texture.getTextureHeight());
            System.out.println(">> Texture ID: "+texture.getTextureID());

            System.out.println(">> Image Ratio: " + ((double)texture.getImageHeight() / texture.getImageWidth()));

            return texture;

        }catch(IOException ex){
            ex.printStackTrace();
            System.out.println("Error loading texture " + tex);
            return null;
        }
    }
}
