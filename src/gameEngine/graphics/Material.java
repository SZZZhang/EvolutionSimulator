package gameEngine.graphics;


import gameEngine.math.Vector3f;

public class Material {

    private static final Vector3f DEFAULT_COLOUR = new Vector3f(1.0f, 1.0f, 1.0f);

    private Vector3f ambientColour;

    private Vector3f diffuseColour;

    private Vector3f specularColour;

    private float reflectance;

    private Texture texture;

    public Material() {
        this.ambientColour = DEFAULT_COLOUR;
        this.diffuseColour = DEFAULT_COLOUR;
        this.specularColour = DEFAULT_COLOUR;
        this.texture = null;
        this.reflectance = 0;
    }

    public Material(Vector3f colour, float reflectance) {
        this(colour, colour, colour, null, reflectance);
    }

    public Material(Texture texture) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, 0);
    }

    public Material(Texture texture, float reflectance) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, reflectance);
    }

    public Material(Vector3f ambientColour, Vector3f diffuseColour, Vector3f specularColour, Texture texture, float reflectance) {
        this.ambientColour = ambientColour;
        this.diffuseColour = diffuseColour;
        this.specularColour = specularColour;
        this.texture = texture;
        this.reflectance = reflectance;
    }

    public Vector3f getAmbientColour() {
        return ambientColour;
    }

    public void setAmbientColour(Vector3f ambientColour) {
        this.ambientColour = ambientColour;
    }

    public Vector3f getDiffuseColour() {
        return diffuseColour;
    }

    public void setDiffuseColour(Vector3f diffuseColour) {
        this.diffuseColour = diffuseColour;
    }

    public Vector3f getSpecularColour() {
        return specularColour;
    }

    public void setSpecularColour(Vector3f specularColour) {
        this.specularColour = specularColour;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public boolean isTextured() {
        return this.texture != null;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

}