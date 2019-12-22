package math;

public class Vector3D {
    private int LENGTH = 4;
    private float[] V = new float[LENGTH];

    public Vector3D() {
    }

    ;

    public Vector3D(float x, float y, float z) {
        set(x, y, z);
    }

    ;

    public float getMagnitude() {
        return (float) Math.sqrt(getX() + getY() + getZ());
    }

    //adds vector and point
    public Point3D add(Point3D P) {
        return new Point3D(this.getX() + P.getX(), this.getY()
                + P.getY(), this.getZ() + P.getZ());
    }

    //adds vector and vector
    public Vector3D add(Vector3D V) {
        return new Vector3D(this.getX() + V.getX(), this.getY() +
                V.getY(), this.getZ() + V.getZ());
    }

    //scales vector by a scale factor
    public Vector3D scale(int scaleFactor) {
        return new Vector3D(scaleFactor * this.getX(), scaleFactor * this.getY(),
                scaleFactor * this.getZ());

    }

    //returns the dot product of two vectors
    public float dotProduct(Vector3D V2) {
        return this.getX() * V2.getX() + this.getY() * V2.getY() + this.getZ() + V2.getZ();
    }

    //returns the normal of the vector
    public Vector3D normal() {
        return new Vector3D(
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

    public void set(float x, float y, float z) {
        V[0] = x;
        V[1] = y;
        V[2] = z;
    }
}
