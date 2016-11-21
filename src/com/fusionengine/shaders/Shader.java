package com.fusionengine.shaders;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    public static List<Shader> screenShaders = new ArrayList<>();

    public static void addScreenShader(Shader shader){
        screenShaders.add(shader);
    }

    public static void render(){
        for(Shader shad : screenShaders){
            ARBShaderObjects.glUseProgramObjectARB(shad.getProgramId());
            //Please note your program must be linked before calling this and I would advise the program be in use also.
            int loc = GL20.glGetUniformLocation(shad.getProgramId(), "texture1");
            //First of all, we retrieve the location of the sampler in memory.
            GL20.glUniform1i(loc, 0);
            //Then we pass the 0 value to the sampler meaning it is to use texture unit 0.
        }
        //   GL20.glUseProgram( shad.getProgramId() );
    }

    // OpenGL handle that will point to the executable shader program
    // that can later be used for rendering
    private int programId;

    public Shader(String v, String f){
        init(v, f);
    }

    public void init(String vertexShaderFilename, String fragmentShaderFilename)
    {
        // create the shader program. If OK, create vertex and fragment shaders
        programId = glCreateProgram();

        // load and compile the two shaders
        int vertShader = loadAndCompileShader(vertexShaderFilename, GL_VERTEX_SHADER);
        int fragShader = loadAndCompileShader(fragmentShaderFilename, GL_FRAGMENT_SHADER);

        // attach the compiled shaders to the program
        glAttachShader(programId, vertShader);
        glAttachShader(programId, fragShader);

        // now link the program
        glLinkProgram(programId);

        // validate linking
        if (glGetProgram(programId, GL_LINK_STATUS) == GL11.GL_FALSE)
        {
            throw new RuntimeException("could not link shader. Reason: " + glGetProgramInfoLog(programId, 1000));
        }

        // perform general validation that the program is usable
        glValidateProgram(programId);

        if (glGetProgram(programId, GL_VALIDATE_STATUS) == GL11.GL_FALSE)
        {
            throw new RuntimeException("could not validate shader. Reason: " + glGetProgramInfoLog(programId, 1000));
        }
    }

    /*
     * With the exception of syntax, setting up vertex and fragment shaders
     * is the same.
     * @param the name and path to the vertex shader
     */
    private int loadAndCompileShader(String filename, int shaderType)
    {
        //vertShader will be non zero if succefully created
        int handle = glCreateShader(shaderType);

        if( handle == 0 )
        {
            throw new RuntimeException("could not created shader of type "+shaderType+" for file "+filename+". "+ glGetProgramInfoLog(programId, 1000));
        }

        // load code from file into String
        String code = loadFile(filename);

        // upload code to OpenGL and associate code with shader
        glShaderSource(handle, code);

        // compile source code into binary
        glCompileShader(handle);

        // acquire compilation status
        int shaderStatus = glGetShader(handle, GL20.GL_COMPILE_STATUS);

        // check whether compilation was successful
        if( shaderStatus == GL11.GL_FALSE)
        {
            throw new IllegalStateException("compilation error for shader ["+filename+"]. Reason: " + glGetShaderInfoLog(handle, 1000));
        }

        return handle;
    }

    /**
     * Load a text file and return it as a String.
     */
    private String loadFile(String filename)
    {
        StringBuilder vertexCode = new StringBuilder();
        String line = null ;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while( (line = reader.readLine()) !=null )
            {
                vertexCode.append(line);
                vertexCode.append('\n');
            }
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("unable to load shader from file ["+filename+"]", e);
        }

        return vertexCode.toString();
    }


    public int getProgramId() {
        return programId;
    }

    /*
    public int program=0;

    public Shader(String vertPath, String fragPath){
        int vertShader = 0, fragShader = 0;

        try {
            if(vertPath != null)
                vertShader = createShader(vertPath,ARBVertexShader.GL_VERTEX_SHADER_ARB);
            if(fragPath != null)
                fragShader = createShader(fragPath,ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        }
        catch(Exception exc) {
            exc.printStackTrace();
            return;
        }
        finally {
            if(vertShader == 0 || fragShader == 0)
                return;
        }

        program = ARBShaderObjects.glCreateProgramObjectARB();

        if(program == 0)
            return;


        if(vertPath != null)
            ARBShaderObjects.glAttachObjectARB(program, vertShader);
        if(fragPath != null)
            ARBShaderObjects.glAttachObjectARB(program, fragShader);

        ARBShaderObjects.glLinkProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(program));
            return;
        }

        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(program));
            return;
        }
    }

    private int createShader(String filename, int shaderType) throws Exception {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

            if(shader == 0)
                return 0;

            ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
            ARBShaderObjects.glCompileShaderARB(shader);

            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
                throw new RuntimeException("Error creating shader: " + getLogInfo(shader));

            return shader;
        }
        catch(Exception exc) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw exc;
        }
    }

    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    private String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();

        FileInputStream in = new FileInputStream(filename);

        Exception exception = null;

        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));

            Exception innerExc= null;
            try {
                String line;
                while((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            }
            catch(Exception exc) {
                exception = exc;
            }
            finally {
                try {
                    reader.close();
                }
                catch(Exception exc) {
                    if(innerExc == null)
                        innerExc = exc;
                    else
                        exc.printStackTrace();
                }
            }

            if(innerExc != null)
                throw innerExc;
        }
        catch(Exception exc) {
            exception = exc;
        }
        finally {
            try {
                in.close();
            }
            catch(Exception exc) {
                if(exception == null)
                    exception = exc;
                else
                    exc.printStackTrace();
            }

            if(exception != null)
                throw exception;
        }

        return source.toString();
    }*/

}
