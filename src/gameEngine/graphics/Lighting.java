package gameEngine.graphics;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_SPOT_CUTOFF;

public class Lighting {
    public void init() {
        FloatBuffer ambient = BufferUtils.createFloatBuffer(4);
		ambient.put(new float[] { 0.6f, 0.65f, 0.65f, 1f, });
		ambient.flip();
		FloatBuffer specular = BufferUtils.createFloatBuffer(4);
		specular.put(new float[] { 0.8f, 0.8f, 0.8f, 1f, });
		specular.flip();

		FloatBuffer position = BufferUtils.createFloatBuffer(4);
		position.put(new float[] { 0f, 5f, -5f, 1f, });
		position.flip();

		FloatBuffer spot_dir = BufferUtils.createFloatBuffer(4);
		spot_dir.put(new float[] { 0f, -5f, -5f, 0f, });
		spot_dir.flip();

		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		// Intensity of ambient light (RGBA)
		glLightModelfv(GL_LIGHT_MODEL_AMBIENT, ambient);
		// Calculation of specular reflection angles (local or infinite viewer?)
		glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_FALSE);
		glLightfv(GL_LIGHT0, GL_POSITION, position);
		glLightfv(GL_LIGHT0, GL_DIFFUSE, specular);
		glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, spot_dir);
		glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 45.0f);
    }
}
