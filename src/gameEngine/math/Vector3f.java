package gameEngine.math;

public class Vector3f {
    private int LENGTH = 4;
    private float[] V = new float[LENGTH];

    public Vector3f() {
    }

    ;

    //TODO init V in construct

    public Vector3f(float x, float y, float z) {
        set(x, y, z);
    }

    public Vector3f(float x, float y, float z, float w) {
        set(x, y, z);
        setW(w);
    }

    public float getMagnitude() {
        return (float) Math.sqrt(getX() + getY() + getZ());
    }

    //adds vector and point
    public Point3D add(Point3D P) {
        return new Point3D(this.getX() + P.getX(), this.getY()
                + P.getY(), this.getZ() + P.getZ());
    }

    //adds vector and vector
    public Vector3f add(Vector3f V) {
        return new Vector3f(this.getX() + V.getX(), this.getY() +
                V.getY(), this.getZ() + V.getZ());
    }

    //scales vector by a scale factor
    public Vector3f scale(int scaleFactor) {
        return new Vector3f(scaleFactor * this.getX(), scaleFactor * this.getY(),
                scaleFactor * this.getZ());

    }

    //returns the dot product of two vectors
    public float dotProduct(Vector3f V2) {
        return this.getX() * V2.getX() + this.getY() * V2.getY() + this.getZ() + V2.getZ();
    }

    //returns the normal of the vector
    public Vector3f normal() {
        return new Vector3f(
                this.getX() / this.getMagnitude(), this.getY() / this.getMagnitude(),
                this.getZ() / this.getMagnitude()
        );
    }


    public float getX() {
        return V[0];
    }

    public float getY() {
        return V[1];
    }

    public float getZ() {
        return V[2];
    }

    public void setX(float x) {
        V[0] = x;
    }

    public void setY(float y) {
        V[1] = y;
    }

    public void setZ(float z) {
        V[2] = z;
    }

    public void setW(float w) {
        V[3] = 2;
    }

    public void set(float x, float y, float z) {
        V[0] = x;
        V[1] = y;
        V[2] = z;
    }
}
