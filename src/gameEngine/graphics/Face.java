package gameEngine.graphics;

public class Face {
    private int[] vertexIndices;
	private int[] textureCoordIndices;
	private int[] normalIndices;

	public Face(int size)
	{
		vertexIndices = new int[size];
		textureCoordIndices = new int[size];
		normalIndices = new int[size];
	}

	public int getSize()
	{
		return vertexIndices.length;
	}

	public void setVertex(int i, int[] attrib)
	{
		vertexIndices[i] = attrib[0];
		textureCoordIndices[i] = attrib[1];
		normalIndices[i] = attrib[2];
	}

	public int getVertexIndex(int i)
	{
		return vertexIndices[i];
	}

	public int getTextureIndex(int i)
	{
		return textureCoordIndices[i];
	}

	public int getNormalIndex(int i)
	{
		return normalIndices[i];
	}
}
