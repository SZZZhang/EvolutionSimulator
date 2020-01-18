package gameEngine.graphics;

import gameEngine.FileUtil;
import gameEngine.GameEngine;
import gameEngine.GameObject;
import gameEngine.Window;
import gameEngine.math.Matrix4f;
import gameEngine.math.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

public class Renderer {
    private ShaderProgram shader;
    private static final float FOV = (float) Math.toRadians(70.0f);
    private static final float Z_NEAR = 0.1f;
    private static final float Z_FAR = 1000.f;


    public void render(Window window, ArrayList<GameObject> gameObjects, SceneLight sceneLight) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.bind();

        float aspectRatio = (float) window.getWidth() / window.getHeight();
        //update projection matrix
        Matrix4f projectionMatrix = Matrix4f.getProjectionMatrix(FOV, aspectRatio, Z_NEAR, Z_FAR);
        shader.setUniform("projectionMatrix", projectionMatrix);

        //update view matrix
        Matrix4f viewMatrix = Matrix4f.getViewMatrix(GameEngine.camera.getPosition(),
                GameEngine.camera.getRotation());

        shader.setUniform("texture_sampler", 0);
        renderLights(viewMatrix, sceneLight);

        //draw objects
        for (GameObject object : gameObjects) {
            Matrix4f modelViewMatrix = Matrix4f.getModelViewMatrix(object, viewMatrix);
            shader.setUniform("modelViewMatrix", modelViewMatrix);

            object.getMesh().render();
        }

        shader.unbind();
    }

    private void renderLights(Matrix4f viewMatrix, SceneLight sceneLight) {

        // Get a copy of the directional light object and transform its position to view coordinates
        DirectionalLight currDirLight = new DirectionalLight(sceneLight.getDirectionalLight());
        Vector3f dir = new Vector3f(currDirLight.getDirection().getX(),
                currDirLight.getDirection().getY(),
                currDirLight.getDirection().getZ());

        dir = Matrix4f.multiply(dir, viewMatrix);

        currDirLight.setDirection(new Vector3f(dir.getX(), dir.getY(), dir.getZ()));
        shader.setUniform("directionalLight", currDirLight);
    }

    public void init(Window window) {
        FloatBuffer verticesBuffer = null;

        try {
            //set up shader
            shader = new ShaderProgram(); //TODO fix paths for final
            shader.createVertexShader(FileUtil.loadString("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/gameEngine/graphics/vertex.vs"));
            shader.createFragmentShader(FileUtil.loadString("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/gameEngine/graphics/fragment.fs"));
            shader.link();

            //create uniforms
            shader.createUniform("projectionMatrix");
            shader.createUniform("modelViewMatrix");

            shader.createUniform("texture_sampler");
            shader.createDirectionalLightUniform("directionalLight");

            window.setBackgroundColor(0.0f, 0.0f, 0.0f);

        } catch (Exception e) {
            System.out.println("Shader not initialized properly: " + e);
        } finally {
            if (verticesBuffer != null) {
                memFree(verticesBuffer);
            }
        }
    }


    public void cleanup(ArrayList<GameObject> gameObjects) {
        if (shader != null) {
            shader.cleanup();
        }
        for (GameObject object : gameObjects) {
            object.getMesh().cleanUp();
        }
    }


}
