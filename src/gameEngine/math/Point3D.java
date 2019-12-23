package gameEngine.math;

public class Point3D {
    private int LENGTH = 4;
    private float[] P = new float[LENGTH];

    public Point3D() {
        P[3] = 1;
    }

    public Point3D(float x, float y, float z) {
        set(x, y, z);
        P[3] = 1;
    }

    public Point3D add(Vector3D vector) {
        return new Point3D(vector.getX() + this.getX(),
                vector.getY() + this.getY(), vector.getZ() + this.getY());
    }

    public Vector3D subtract(Point3D point) {
        return new Vector3D(point.getX() - this.getX(),
                point.getY() - this.getY(), point.getZ() - this.getZ());
    }

    public float getX() {
        return P[0];
    }

    public float getY() {
        return P[1];
    }

    public float getZ() {
        return P[2];
    }

    public void setX(float x) {
        P[0] = x;
    }

    public void setY(float y) {
        P[1] = y;
    }

    public void setZ(float z) {
        P[2] = z;
    }

    public void set(float x, float y, float z) {
        P[0] = x;
        P[1] = y;
        P[2] = z;
    }
}
