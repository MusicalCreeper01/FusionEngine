package com.fusionengine.rendering.helpers;

import com.fusionengine.core.Loader;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.ARBTextureBorderClamp;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class RenderSkybox {

    public Texture texture;
    public Vector3f position;
    public Quaternion rotation = new Quaternion(0,0,0,0);

    public RenderSkybox(String tex, Vector3f position){
        texture = Loader.loadTexture(tex);
        this.position = position;
    }

    public final float size = 0.5f;
    private double rot = 0;
    private double error = 0.001d;


    public void render (){
        double uvx = (texture.getImageWidth()*1.0f) / texture.getTextureWidth();
        double uvy = (texture.getImageHeight()*1.0f) / texture.getTextureHeight();

        // Store the current matrix
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();

        glEnable(GL_TEXTURE_2D);

        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);
        glDisable(GL_BLEND);

        glRotated(rot,0.0f,1.0f,0.0f);
        rot+=0.2f;

        // Just in case we set all vertices to white.
        glColor4f(1,1,1,1);

        // Render the front quad

        texture.bind();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        glBegin(GL_QUADS);
        glTexCoord2d((uvx/4)*2-error, (uvy/3)*2-error); glVertex3f(  size, -size, -size );
        glTexCoord2d((uvx/4)+error, (uvy/3)*2-error); glVertex3f( -size, -size, -size );
        glTexCoord2d((uvx/4)+error, (uvy/3)+error); glVertex3f( -size,  size, -size );
        glTexCoord2d((uvx/4)*2-error, (uvy/3)+error); glVertex3f(  size,  size, -size );
        glEnd();


        // Render the right quad
        texture.bind();
        glBegin(GL_QUADS);

        glTexCoord2d(((uvx/4)*3)-error, ((uvy/3)*2)-error); glVertex3f(  size, -size,  size );
        glTexCoord2d(((uvx/4)*2)+error, ((uvy/3)*2)-error); glVertex3f(  size, -size, -size );
        glTexCoord2d(((uvx/4)*2)+error, (uvy/3)+error); glVertex3f(  size,  size, -size );
        glTexCoord2d(((uvx/4)*3)-error, (uvy/3)+error); glVertex3f(  size,  size,  size );
        glEnd();

        // Render the back quad
        texture.bind();
        glBegin(GL_QUADS);
        glTexCoord2d(((uvx/4)*4)-error, ((uvy/3)*2)-error); glVertex3f( -size, -size,  size );
        glTexCoord2d(((uvx/4)*3)+error, ((uvy/3)*2)-error); glVertex3f(  size, -size,  size );
        glTexCoord2d(((uvx/4)*3)+error, (uvy/3)+error); glVertex3f(  size,  size,  size );
        glTexCoord2d(((uvx/4)*4)-error, (uvy/3)+error); glVertex3f( -size,  size,  size );

        glEnd();

        // Render the left quad
        texture.bind();
        glBegin(GL_QUADS);
        glTexCoord2d((uvx/4)-error, ((uvy/3)*2)-error); glVertex3f( -size, -size, -size );
        glTexCoord2d(0+error, ((uvy/3)*2)-error); glVertex3f( -size, -size,  size );
        glTexCoord2d(0+error, (uvy/3)+error); glVertex3f( -size,  size,  size );
        glTexCoord2d((uvx/4)-error, (uvy/3)+error); glVertex3f( -size,  size, -size );
        glEnd();

        // Render the top quad
        texture.bind();
        glBegin(GL_QUADS);
        glTexCoord2d(((uvx/4)+error), (uvy/3)-error); glVertex3f( -size,  size, -size );
        glTexCoord2d(((uvx/4)+error), 0+error); glVertex3f( -size,  size,  size );
        glTexCoord2d(((uvx/4)*2)-error, 0+error); glVertex3f(  size,  size,  size );
        glTexCoord2d(((uvx/4)*2)-error, (uvy/3)-error); glVertex3f(  size,  size, -size );
        glEnd();

        // Render the bottom quad
        texture.bind();
        glBegin(GL_QUADS);
        glTexCoord2d((uvx/4)+error, ((uvy/3)*2)+error); glVertex3f( -size, -size, -size );
        glTexCoord2d((uvx/4)+error, ((uvy/3)*3)-error); glVertex3f( -size, -size,  size );
        glTexCoord2d(((uvx/4)*2)-error, ((uvy/3)*3)-error); glVertex3f(  size, -size,  size );
        glTexCoord2d(((uvx/4)*2)-error, ((uvy/3)*2)+error); glVertex3f(  size, -size, -size );
        glEnd();

        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();


    }
}
