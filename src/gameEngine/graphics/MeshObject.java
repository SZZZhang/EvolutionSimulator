package gameEngine.graphics;

import gameEngine.math.Point3D;
import gameEngine.math.Vector3D;
import org.lwjgl.system.MemoryUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class MeshObject {
    private int vaoId, vboId, colourVboId, vertexCount;

    private ArrayList<Point3D> vertex;
    private ArrayList<Float[]> texCoords;
    private ArrayList<Vector3D> normals;

    private ArrayList<Face> faces;        // indices into the vertices array list

    private Texture texture;

    public MeshObject() {
        vertex = new ArrayList();
        texCoords = new ArrayList();
        normals = new ArrayList();
        faces = new ArrayList();
    }

    public MeshObject(float[] coor, float[] colours) {


        FloatBuffer verticesBuffer = null;
        FloatBuffer colourBuffer = null;

        try {
            verticesBuffer = MemoryUtil.memAllocFloat(coor.length);
            verticesBuffer.put(coor).flip();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            vboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(0);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            //glBindBuffer(GL_ARRAY_BUFFER, 0);

            // Colour VBO
           /* colourVboId = glGenBuffers();
            colourBuffer = MemoryUtil.memAllocFloat(colours.length);
            colourBuffer.put(colours).flip();
            glBindBuffer(GL_ARRAY_BUFFER, colourVboId);
            glBufferData(GL_ARRAY_BUFFER, colourBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(1);
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);*/

            glBindVertexArray(0);
        } finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
        }
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        //Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);

        //Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public void draw() {
        if (texture != null) {
            glEnable(GL_TEXTURE_2D);    // enable texture mapping
            texture.bind();
        } else
            glDisable(GL_TEXTURE_2D);    // disable texture mapping
        for (int i = 0; i < faces.size(); i++) {
            glBegin(GL_POLYGON);
            for (int j = 0; j < faces.get(i).getSize(); j++) {
                Float[] tex = texCoords.get(faces.get(i).getTextureIndex(j) - 1);
                glTexCoord2f(tex[0], tex[1]);
                Point3D p = vertex.get(faces.get(i).getVertexIndex(j) - 1);
                glVertex3f(p.getX(), p.getY(), p.getZ());
                Vector3D v = normals.get(faces.get(i).getNormalIndex(j) - 1);
                glNormal3f(v.getX(), v.getY(), v.getZ());
            }
            glEnd();
        }
    }

    public MeshObject(String filename) {
        this();
        try {
            Scanner scan = new Scanner(new File(filename));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] lineArray = line.split(" ");
                if (lineArray[0].equals("v")) {
                    Point3D point = new Point3D(Float.parseFloat(lineArray[1]),
                            Float.parseFloat(lineArray[2]),
                            Float.parseFloat(lineArray[3]));
                    vertex.add(point);
                } else if (lineArray[0].equals("vt")) {
                    Float[] f = new Float[2];
                    f[0] = Float.parseFloat(lineArray[1]);
                    f[1] = Float.parseFloat(lineArray[2]);
                    texCoords.add(f);
                } else if (lineArray[0].equals("vn")) {
                    Vector3D vector = new Vector3D(Float.parseFloat(lineArray[1]),
                            Float.parseFloat(lineArray[2]),
                            Float.parseFloat(lineArray[3]));
                    normals.add(vector);
                } else if (lineArray[0].equals("f")) {
                    Face f = new Face(lineArray.length - 1);

                    for (int i = 1; i < lineArray.length; i++) {
                        String[] attribString = lineArray[i].split("/");
                        int[] attrib = new int[3];
                        attrib[0] = Integer.parseInt(attribString[0]);
                        attrib[1] = Integer.parseInt(attribString[1]);
                        attrib[2] = Integer.parseInt(attribString[2]);

                        f.setVertex(i - 1, attrib);
                    }
                    faces.add(f);
                } else if (lineArray[0].equals("mtllib")) {
                    Scanner mtlibScanner = new Scanner(new File("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/" + lineArray[1]));
                    while (mtlibScanner.hasNextLine()) {

                        while (mtlibScanner.hasNextLine()) {
                            String mtlib_line = mtlibScanner.nextLine();
                            String[] mtlib_lineArray = mtlib_line.split(" ");
                            if (mtlib_lineArray[0].equals("map_Kd")) {
                                texture = new Texture(mtlib_lineArray[1]);
                            }
                        }
                    }


                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}