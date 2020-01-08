package gameEngine.graphics;

import gameEngine.FileUtil;
import gameEngine.Window;
import org.lwjgl.system.MemoryUtil;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;


public class Renderer {
    private ShaderProgram shader;



    private int vaoId;
    private int vboId;

    public void render(Window window, ArrayList<MeshObject> meshes) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.bind();

        //draw objects
        for (MeshObject mesh : meshes) {
            glBindVertexArray(mesh.getVaoId());
            //glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
            glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount());
        }

        //TODO program doesnt display anything

        //restore state
        glBindVertexArray(0);

        shader.unbind();
    }

    public void init() {

        try {
            shader = new ShaderProgram();
            shader.createVertexShader(FileUtil.loadString("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/gameEngine/graphics/vertex.vs"));
            shader.createFragmentShader(FileUtil.loadString("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/gameEngine/graphics/fragment.fs"));
            shader.link();
        } catch (Exception e) {
            System.out.println("Shader not initiallized properly: " + e);
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
