package gameEngine.math;

public class Matrix3D {

    public static int SIZE = 4;
    private float[][] M;


    public Matrix3D() {
        M = new float[SIZE][SIZE];
    }

    public Matrix3D(float[][] matrix) {
        M = matrix;
    }

    public float get(int r, int c) {
        return M[r][c];
    }

    public void set(int r, int c, float value) {
        M[r][c] = value;
    }

    public float[][] getAll() {
        return M;
    }

    public void setAll(float[][] M2) {

    }

    public Matrix3D identity() {
        float[][] M2 = new float[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++)
            M2[i][i] = 1;

        return new Matrix3D(M2);
    }

    public Matrix3D translate(Vector3D translate) {
        Matrix3D result = this.identity();

        result.set(3, 0, translate.getX());
        result.set(3, 1, translate.getY());
        result.set(3, 2, translate.getZ());

        return result;
    }

    ///copied!! not my code
    public Matrix3D rotate(float angle, Vector3D axis) {
        Matrix3D result = this.identity();

        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        float C = 1 - cos;

        result.set(0, 0, cos + axis.getX() * axis.getX() * C);
        result.set(0, 1, axis.getX() * axis.getY() * C - axis.getZ() * sin);
        result.set(0, 2, axis.getX() * axis.getZ() * C + axis.getY() * sin);
        result.set(1, 0, axis.getY() * axis.getX() * C + axis.getZ() * sin);
        result.set(1, 1, cos + axis.getY() * axis.getY() * C);
        result.set(1, 2, axis.getY() * axis.getZ() * C - axis.getX() * sin);
        result.set(2, 0, axis.getZ() * axis.getX() * C - axis.getY() * sin);
        result.set(2, 1, axis.getZ() * axis.getY() * C + axis.getX() * sin);
        result.set(2, 2, cos + axis.getZ() * axis.getZ() * C);

        return result;
    }

    public Matrix3D scale(Vector3D scalar) {
        Matrix3D result = this.identity();

        result.set(0, 0, scalar.getX());
        result.set(1, 1, scalar.getY());
        result.set(2, 2, scalar.getZ());

        return result;
    }

    public Matrix3D transform(Vector3D position, Vector3D rotation, Vector3D scale) {
        Matrix3D result = this.identity();

        Matrix3D translationMatrix = translate(position);
        Matrix3D rotXMatrix = rotate(rotation.getX(), new Vector3D(1, 0, 0));
        Matrix3D rotYMatrix = rotate(rotation.getY(), new Vector3D(0, 1, 0));
        Matrix3D rotZMatrix = rotate(rotation.getZ(), new Vector3D(0, 0, 1));
        Matrix3D scaleMatrix = scale(scale);

        Matrix3D rotationMatrix = rotXMatrix.multiply(rotYMatrix.multiply(rotZMatrix));

        result = translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix));

        return result;
    }

    public Matrix3D projection(float fov, float aspect, float near, float far) {
        Matrix3D result = this.identity();

        float tanFOV = (float) Math.tan(Math.toRadians(fov / 2));
        float range = far - near;

        result.set(0, 0, 1.0f / (aspect * tanFOV));
        result.set(1, 1, 1.0f / tanFOV);
        result.set(2, 2, -((far + near) / range));
        result.set(2, 3, -1.0f);
        result.set(3, 2, -((2 * far * near) / range));
        result.set(3, 3, 0.0f);

        return result;
    }

    public Matrix3D view(Vector3D position, Vector3D rotation) {

        Vector3D negative = new Vector3D(-position.getX(), -position.getY(), -position.getZ());
        Matrix3D translationMatrix = translate(negative);
        Matrix3D rotXMatrix = rotate(rotation.getX(), new Vector3D(1, 0, 0));
        Matrix3D rotYMatrix = rotate(rotation.getY(), new Vector3D(0, 1, 0));
        Matrix3D rotZMatrix = rotate(rotation.getZ(), new Vector3D(0, 0, 1));

        Matrix3D rotationMatrix = rotZMatrix.multiply(rotYMatrix.multiply(rotXMatrix));

        Matrix3D result = translationMatrix.multiply(rotationMatrix);

        return result;
    }

    public Matrix3D multiply(Matrix3D other) {
        Matrix3D result = this.identity();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result.set(i, j, this.get(i, 0) * other.get(0, j) +
                        this.get(i, 1) * other.get(1, j) +
                        this.get(i, 2) * other.get(2, j) +
                        this.get(i, 3) * other.get(3, j));
            }
        }

        return result;
    }

}
