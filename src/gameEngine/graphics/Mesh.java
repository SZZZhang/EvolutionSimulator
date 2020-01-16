package gameEngine.graphics;

public class Mesh {
    float[] vertices;

    public Mesh(float[] vertices) {
        this.vertices = vertices;
    }

    public float[] getVertices() {
        return vertices;
    }
    public int getVerticesLength() {
        return vertices.length;
    }
}
