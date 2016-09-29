package NewTetris;

import java.util.Random;
import java.lang.Math;

public class Shape1 {

	enum Tetrinomes {
		NoShape, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape
	};

	private Tetrinomes pieceshape;
	// private int[][] coords;
	// private int[][][] coordsTable;
	private int coords[][];
	private int[][][] coordsTable;

	public Shape1() {
		setShape(Tetrinomes.NoShape);
		coords = new int[4][2];
	}

	public Tetrinomes getShape1() {
		return pieceshape;
	}

	public void setShape(Tetrinomes shape) {
		coordsTable = new int[][][] { { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
				{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }, { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
				{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
				{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } },
				{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } } };

		if (shape == Tetrinomes.NoShape) {
			System.out.println("let me know- no shape");
			coords = new int [][] { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
			pieceshape = shape;
			return;
		}
		
		if(shape == Tetrinomes.SquareShape){
			System.out.println("let me know- squareshape");
			coords = new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } };
			pieceshape = shape;
			return;
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.println(coordsTable[shape.ordinal()][i][j]);
				coords[i][j] = coordsTable[shape.ordinal()][i][j];
			}
		}

		pieceshape = shape;
	}

	public int x(int index) {
		return coords[index][0];
	}

	public int y(int index) {
		return coords[index][1];
	}

	public void setRandomShape() {
		setShape(Tetrinomes.SquareShape);
	}

}
