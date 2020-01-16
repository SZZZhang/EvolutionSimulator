package gameEngine.graphics;

import gameEngine.FileUtil;
import gameEngine.Window;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;


public class Renderer {
    private ShaderProgram shader;


    private int vaoId;
    private int vboId;


    public void render(Window window) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.bind();

        //draw objects
        glBindVertexArray(vaoId);
        glDrawArrays(GL_TRIANGLES, 0, 6);

        glBindVertexArray(0);

        shader.unbind();
    }

    public void init(ArrayList<Mesh> meshes) {
        FloatBuffer verticesBuffer = null;

        try {
            shader = new ShaderProgram(); //TODO fix paths for final
            shader.createVertexShader(FileUtil.loadString("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/gameEngine/graphics/vertex.vs"));
            shader.createFragmentShader(FileUtil.loadString("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/gameEngine/graphics/fragment.fs"));
            shader.link();
            for (Mesh mesh : meshes) {

                verticesBuffer = MemoryUtil.memAllocFloat(mesh.getVerticesLength());
                verticesBuffer.put(mesh.getVertices()).flip();

                vaoId = glGenVertexArrays();
                glBindVertexArray(vaoId);

                vboId = glGenBuffers();
                glBindBuffer(GL_ARRAY_BUFFER, vboId);
                glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);


                glEnableVertexAttribArray(0); 
                glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            }

        } catch (Exception e) {
            System.out.println("Shader not initiallized properly: " + e);
        } finally {
            if (verticesBuffer != null) {
                memFree(verticesBuffer);
            }
        }
    }

    public void cleanup() {
        if (shader != null) {
            shader.cleanup();
        }

        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }


}
