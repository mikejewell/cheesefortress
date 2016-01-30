package cheese.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
 
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
 
public class WedgeGFX {
    
    // Quad variables
    private int[] vArr;
    private int indicesCount = 0;
    // Shader variables
    private int vsId = 0;
    private int fsId = 0;
    private int pId = 0;
     
    public WedgeGFX() {
    	vArr = getVertexArray();      
        this.setupQuad();
        this.initShaders();
    }
     
    public void setupQuad() {
        // Vertices, the order is not important. XYZW instead of XYZ
        float[] vertices = {
                -0.5f, 0.5f, 0f, 1f, 1f, 0f, 0f, 1f,
                -0.5f, -0.5f, 0f, 1f, 0f, 1f, 0f, 1f,
                0.5f, -0.5f, 0f, 1f, 0f, 0f, 1f, 1f,
                0.5f, 0.5f, 0f, 1f, 1f, 1f, 1f, 1f
        };
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();
         
        // OpenGL expects to draw vertices in counter clockwise order by default
        byte[] indices = {
                0, 1, 2,
                2, 3, 0
        };
        indicesCount = indices.length;
        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
         
        // Create a new Vertex Array Object in memory and select it (bind)
        GL30.glBindVertexArray(vArr[0]);
         
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vArr[1]);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
         
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vArr[2]);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }
     
    private void setupShaders() {
        int errorCheckValue = GL11.glGetError();
         
        // Load the vertex shader
        vsId = this.loadShader("assets/shaders/basic.vs", GL20.GL_VERTEX_SHADER);
        // Load the fragment shader
        fsId = this.loadShader("assets/shaders/basic.fs", GL20.GL_FRAGMENT_SHADER);
         
        // Create a new shader program that links both shaders
        pId = GL20.glCreateProgram();
        GL20.glAttachShader(pId, vsId);
        GL20.glAttachShader(pId, fsId);
 
        // Position information will be attribute 0
        GL20.glBindAttribLocation(pId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(pId, 1, "in_Color");
         
        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);
         
        errorCheckValue = GL11.glGetError();
        if (errorCheckValue != GL11.GL_NO_ERROR) {
            System.out.println("ERROR - Could not create the shaders:" + GLU.gluErrorString(errorCheckValue));
            System.exit(-1);
        }
    }
     
    public void loopCycle() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
         
        GL20.glUseProgram(pId);
         
        // Bind to the VAO that has all the information about the vertices
        GL30.glBindVertexArray(vArr[0]);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
         
        // Bind to the index VBO that has all the information about the order of the vertices
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vArr[2]);
         
        // Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);
         
        // Put everything back to default (deselect)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);
    }
    
    private int[] getVertexArray(){
    	int[] r = new int[3];
    	
    	// Create a new Vertex Array Object in memory and select it (bind)
        r[0] = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(r[0]);
         
        // Create a new Vertex Buffer Object in memory and select it (bind) - VERTICES
        r[1] = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, r[1]);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 32, 0);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 32, 16);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
         
        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);
         
        // Create a new VBO for the indices and select it (bind) - INDICES
        r[2] = GL15.glGenBuffers();
        return r;
    }
    
    private void initShaders() {
        int errorCheckValue = GL11.glGetError();
         
        // Load the vertex shader
        vsId = this.loadShader("assets/shaders/basic.vs", GL20.GL_VERTEX_SHADER);
        // Load the fragment shader
        fsId = this.loadShader("assets/shaders/basic.fs", GL20.GL_FRAGMENT_SHADER);
         
        // Create a new shader program that links both shaders
        pId = GL20.glCreateProgram();
        GL20.glAttachShader(pId, vsId);
        GL20.glAttachShader(pId, fsId);
 
        // Position information will be attribute 0
        GL20.glBindAttribLocation(pId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(pId, 1, "in_Color");
         
        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);
         
        errorCheckValue = GL11.glGetError();
        if (errorCheckValue != GL11.GL_NO_ERROR) {
            System.out.println("ERROR - Could not create the shaders:" + GLU.gluErrorString(errorCheckValue));
            System.exit(-1);
        }
    }
     
    public void destroyOpenGL() {
        
    }
     
    public int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;
         
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }
         
        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
         
        return shaderID;
    }
}
