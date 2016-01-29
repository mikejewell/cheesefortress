package cheese.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class WedgeGFX {
	
	//Sprite batch data
	private float[] batch;
	
	//Triuangle indices
	private int[] ind;
	
	//Maximum renderable sprites
	private int maxSprites;
	
	//Index in data array
	private int i = 0;
	
	//Number of sprites in current batch
	private int numSprites = 0;
	
	//Vertex objects
	private int vaoId, vboId, vboiId;
	
	//Shaders
	private int vsId, fsId, pId;
	
	private Texture curTex;
	
	public WedgeGFX(int maxSprites){
		this.maxSprites = maxSprites;
		batch = new float[maxSprites*36];
		ind = new int[maxSprites*6];
		
		float[] verts = {
				-0.5f, 0.5f, 1, 1, 1, 0, 0,
				-0.5f, -0.5f, 1, 1, 1, 0, 1,
				0.5f, -0.5f, 1, 1, 1, 1, 1,
				0.5f, 0.5f, 1, 1, 1, 1, 0};
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(verts.length);
		verticesBuffer.put(verts);
        verticesBuffer.flip();
        
        byte[] indices = {
                0, 1, 2,
                2, 3, 0
        };
        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
        
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
         
        // Create a new Vertex Buffer Object in memory and select it (bind)
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
         
        // Put the position coordinates in attribute list 0
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, 
                false, 28, 0);
        // Put the color components in attribute list 1
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, 
                false, 28, 8);
        // Put the texture coordinates in attribute list 2
        GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, 
                false, 28, 20);
         
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
         
        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);
         
        // Create a new VBO for the indices and select it (bind) - INDICES
        vboiId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        
        curTex = new Texture("assets/images/monster/penguin.png");
	}
	
	public void testImage(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        GL20.glUseProgram(pId);
         
        // Bind the texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        curTex.bind();
         
        // Bind to the VAO that has all the information about the vertices
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
         
        // Bind to the index VBO that has all the information about the order of the vertices
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
         
        // Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0);
         
        // Put everything back to default (deselect)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
         
        GL20.glUseProgram(0);
	}
	
	public void drawImage(){
		
	}
	
	private void initShaders(){
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
        // Textute information will be attribute 2
        GL20.glBindAttribLocation(pId, 2, "in_TextureCoord");
         
        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);
	}
	
	private int loadShader(String filename, int type) {
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
         
        if (GL20.glGetProgrami(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader.");
            System.exit(-1);
        }

         
        return shaderID;
    }
	
}
