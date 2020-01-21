package gameEngine.graphics;

import gameEngine.Utils;
import gameEngine.GameEngine;
import gameEngine.GameObject;
import gameEngine.Window;
import gameEngine.math.Matrix4f;
import gameEngine.math.Vector3f;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

public class Renderer {
    private ShaderProgram shader;
    private static final float FOV =  70.0f;
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;

    private float specularPower = 10f;

    public void render(Window window, ArrayList<GameObject> gameObjects,
                       Vector3f ambientLight, PointLight pointLight, DirectionalLight directionalLight) {

        //clears buffers
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

        shader.bind();

        float aspectRatio = (float) window.getWidth() / window.getHeight();
        //update projection matrix
        Matrix4f projectionMatrix = Matrix4f.getProjectionMatrix(FOV, aspectRatio, Z_NEAR, Z_FAR);
        shader.setUniform("projectionMatrix", projectionMatrix);

        //update view matrix
        Matrix4f viewMatrix = Matrix4f.getViewMatrix(GameEngine.camera.getPosition(),
                GameEngine.camera.getRotation());

        // Update Light Uniforms
        shader.setUniform("ambientLight", ambientLight);
        shader.setUniform("specularPower", specularPower);
        // Get a copy of the light object and transform its position to view coordinates
        PointLight currPointLight = new PointLight(pointLight);
        Vector3f lightPos = currPointLight.getPosition();
        Vector3f aux = new Vector3f(lightPos.getX(), lightPos.getY(), lightPos.getZ(), 1);
        aux = Matrix4f.multiply(aux, viewMatrix);

        lightPos.setX(aux.getX());
        lightPos.setY(aux.getY());
        lightPos.setZ(aux.getZ());

        shader.setUniform("pointLight", currPointLight);

        // Get a copy of the directional light object and transform its position to view coordinates
        DirectionalLight currDirLight = new DirectionalLight(directionalLight);
        Vector3f dir = new Vector3f(currDirLight.getDirection());
        dir = Matrix4f.multiply(dir, viewMatrix);
        currDirLight.setDirection(new Vector3f(dir.getX(), dir.getY(), dir.getZ()));
        shader.setUniform("directionalLight", currDirLight);

        shader.setUniform("texture_sampler", 0);

        //draw objects
        for (GameObject object : gameObjects) {
            Matrix4f modelViewMatrix = Matrix4f.getModelViewMatrix(object, viewMatrix);
            shader.setUniform("modelViewMatrix", modelViewMatrix);

            shader.setUniform("material", object.getMesh().getMaterial());
            // Render the mesh for this game item
            // shader.setUniform("colour", object.getMesh().getColour());
            //shader.setUniform("useColour", object.getMesh().isTextured() ? 0 : 1);


            object.getMesh().render();
        }

        shader.unbind();
    }

    public void init(Window window) {
        FloatBuffer verticesBuffer = null;

        try {
            //set up shader
            shader = new ShaderProgram(); //TODO fix paths for final
            shader.createVertexShader(Utils.loadString("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/gameEngine/graphics/vertex.vs"));
            shader.createFragmentShader(Utils.loadString("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/gameEngine/graphics/fragment.fs"));
            shader.link();

            //create uniforms
            shader.createUniform("projectionMatrix");
            shader.createUniform("modelViewMatrix");
            shader.createUniform("texture_sampler");
            // Create uniform for default colour and the flag that controls it

// Create uniform for material
            shader.createMaterialUniform("material");
            // Create lighting related uniforms
            shader.createUniform("specularPower");
            shader.createUniform("ambientLight");
            shader.createPointLightUniform("pointLight");
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
