import java.util.ArrayList;

public class Cube {

	ArrayList<Line> lines;

	double[][] A = { { -1, 1, -1, 1 } };
	double[][] B = { { 1, 1, -1, 1 } };
	double[][] C = { { 1, -1, -1, 1 } };
	double[][] D = { { -1, -1, -1, 1 } };
	double[][] E = { { -1, 1, 1, 1 } };
	double[][] F = { { 1, 1, 1, 1 } };
	double[][] G = { { 1, -1, 1, 1 } };
	double[][] H = { { -1, -1, 1, 1 } };
	Line AB, BC, CD, DA, EF, FG, GH, HE, AE, BF, CG, DH;

	public void makeCube() {
		lines = new ArrayList<Line>();

		// Compute 2D points
		int[] Ac = get2D(A);
		int[] Bc = get2D(B);
		int[] Cc = get2D(C);
		int[] Dc = get2D(D);
		int[] Ec = get2D(E);
		int[] Fc = get2D(F);
		int[] Gc = get2D(G);
		int[] Hc = get2D(H);

		// Create lines using 2D points
		AB = new Line(Ac, Bc);
		BC = new Line(Bc, Cc);
		CD = new Line(Cc, Dc);
		DA = new Line(Dc, Ac);
		EF = new Line(Ec, Fc);
		FG = new Line(Fc, Gc);
		GH = new Line(Gc, Hc);
		HE = new Line(Hc, Ec);
		AE = new Line(Ac, Ec);
		BF = new Line(Bc, Fc);
		CG = new Line(Cc, Gc);
		DH = new Line(Dc, Hc);

		// Add the lines to the Array List
		lines.add(AB);
		lines.add(BC);
		lines.add(CD);
		lines.add(DA);
		lines.add(EF);
		lines.add(FG);
		lines.add(GH);
		lines.add(HE);
		lines.add(AE);
		lines.add(BF);
		lines.add(CG);
		lines.add(DH);
	}

	// Perform perspective projection to get the 2 dimensional values of the provided point
	private int[] get2D(double[][] p) {
		int[] newP = new int[3];
		double xe = 6;
		double ye = 8;
		double ze = 7.5;
		double size = 10;
		double distance = 60;
		double Vsc = 600 / 2;
		int xs, ys;
		double xc, yc, zc;

		double[][] V = getV(xe, ye, ze);
		double[][] N = getN(distance, size);
		double[][] T = matrixMultiply(V, N);
		double[][] result = matrixMultiply(p, T);

		xc = result[0][0];
		yc = result[0][1];
		zc = result[0][2];

		xs = (int) ((xc / zc) * Vsc + Vsc);
		ys = (int) ((yc / zc) * Vsc + Vsc);

		newP[0] = xs;
		newP[1] = ys;
		newP[2] = 1;

		return newP;
	}

	// Returns the V matrix
	private double[][] getV(double xe, double ye, double ze) {
		double x, y;
		double o = Math.sqrt((Math.pow(xe, 2)) + (Math.pow(ye, 2)));

		// T1
		double[][] t1 = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { -6, -8, -7.5, 1 } };

		// T2
		double[][] t2 = { { 1, 0, 0, 0 }, { 0, 0, -1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 1 } };

		// T3
		x = xe / o;
		y = ye / o;
		double[][] t3 = { { -y, 0, x, 0 }, { 0, 1, 0, 0 }, { -x, 0, -y, 0 }, { 0, 0, 0, 1 } };

		// T4
		double denominator = Math.sqrt(Math.pow(ze, 2) + Math.pow(o, 2));
		x = ze / denominator;
		y = o / denominator;
		double[][] t4 = { { 1, 0, 0, 0 }, { 0, y, x, 0 }, { 0, -x, y, 0 }, { 0, 0, 0, 1 } };

		// T5
		double[][] t5 = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, -1, 0 }, { 0, 0, 0, 1 } };

		// V
		double[][] V;
		V = matrixMultiply(t1, t2);
		V = matrixMultiply(V, t3);
		V = matrixMultiply(V, t4);
		V = matrixMultiply(V, t5);

		return V;
	}

	// Returns the N matrix
	private double[][] getN(double d, double s) {
		double x = d / s;
		double[][] n = { { x, 0, 0, 0 }, { 0, x, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return n;
	}

	// Function to multiply two Matrices
	private double[][] matrixMultiply(double[][] m1, double[][] m2) {
		int m1Rows = m1.length;
		int m1Cols = m1[0].length;
		int m2Cols = m2[0].length;

		double[][] result = new double[m1Rows][m2Cols];

		for (int i = 0; i < m1Rows; i++) {
			for (int j = 0; j < m2Cols; j++) {
				for (int k = 0; k < m1Cols; k++) {
					result[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}

		return result;
	}

	// Performs the translate transformation on the world coordinates of the cube
	public void translate3D(double Tx, double Ty, double Tz) {
		double[][] translate = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { Tx, Ty, Tz, 1 } };

		A = matrixMultiply(A, translate);
		B = matrixMultiply(B, translate);
		C = matrixMultiply(C, translate);
		D = matrixMultiply(D, translate);
		E = matrixMultiply(E, translate);
		F = matrixMultiply(F, translate);
		G = matrixMultiply(G, translate);
		H = matrixMultiply(H, translate);

		makeCube();
	}

	// Performs the scale transformation on the world coordinates of the cube
	public void scale3D(double Sx, double Sy, double Sz) {
		double[][] scale = { { Sx, 0, 0, 0 }, { 0, Sy, 0, 0 }, { 0, 0, Sz, 0 }, { 0, 0, 0, 1 } };

		A = matrixMultiply(A, scale);
		B = matrixMultiply(B, scale);
		C = matrixMultiply(C, scale);
		D = matrixMultiply(D, scale);
		E = matrixMultiply(E, scale);
		F = matrixMultiply(F, scale);
		G = matrixMultiply(G, scale);
		H = matrixMultiply(H, scale);

		makeCube();
	}

	// Performs the rotate about an arbitrary axis transformation on the world coordinates of the cube
	public void rotate3D(double angle) {
		Line l = AB;
		
		double x = l.x1 - l.x0;
		double y = l.y1 - l.y0;
		double z = l.z1 - l.z0;

		double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

		double a = x / d;
		double b = y / d;
		double c = z / d;

		d = Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2));
		double[][] T = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { -l.x0, -l.y0, -l.z0, 1 } };
		double[][] Rx = { { 1, 0, 0, 0 }, { 0, c / d, b / d, 0 }, { 0, -b / d, c / d, 0 }, { 0, 0, 0, 1 } };
		double[][] Ry = { { d, 0, a, 0 }, { 0, 1, 0, 0 }, { -a, 0, d, 0 }, { 0, 0, 0, 1 } };

		double cosX = Math.cos(Math.toRadians(angle));
		double sinX = Math.sin(Math.toRadians(angle));

		double[][] Rz = { { cosX, sinX, 0, 0 }, { -sinX, cosX, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		double[][] Ryn = { { d, 0, -a, 0 }, { 0, 1, 0, 0 }, { a, 0, d, 0 }, { 0, 0, 0, 1 } };
		double[][] Rxn = { { 1, 0, 0, 0 }, { 0, c / d, -b / d, 0 }, { 0, b / d, c / d, 0 }, { 0, 0, 0, 1 } };
		double[][] Tn = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { l.x0, l.y0, l.z0, 1 } };

		double[][] result;
		result = matrixMultiply(T, Rx);
		result = matrixMultiply(result, Ry);
		result = matrixMultiply(result, Rz);
		result = matrixMultiply(result, Ryn);
		result = matrixMultiply(result, Rxn);
		result = matrixMultiply(result, Tn);

		A = matrixMultiply(A, result);
		B = matrixMultiply(B, result);
		C = matrixMultiply(C, result);
		D = matrixMultiply(D, result);
		E = matrixMultiply(E, result);
		F = matrixMultiply(F, result);
		G = matrixMultiply(G, result);
		H = matrixMultiply(H, result);

		makeCube();
	}
	
	// Performs the rotate about the x axis transformation on the world coordinates of the cube
	public void rotateX(double angle) {
		double cosX = Math.cos(Math.toRadians(angle));
		double sinX = Math.sin(Math.toRadians(angle));
		double[][] rotate = { {1, 0, 0, 0}, {0, cosX, sinX, 0}, {0, -sinX, cosX, 0}, {0, 0, 0, 1} };

		A = matrixMultiply(A, rotate);
		B = matrixMultiply(B, rotate);
		C = matrixMultiply(C, rotate);
		D = matrixMultiply(D, rotate);
		E = matrixMultiply(E, rotate);
		F = matrixMultiply(F, rotate);
		G = matrixMultiply(G, rotate);
		H = matrixMultiply(H, rotate);

		makeCube();
	}
	
	// Performs the rotate about the y axis transformation on the world coordinates of the cube
	public void rotateY(double angle) {
		double cosX = Math.cos(Math.toRadians(angle));
		double sinX = Math.sin(Math.toRadians(angle));
		double[][] rotate = { {cosX, 0, -sinX, 0}, {0, 1, 0, 0}, {sinX, 0, cosX, 0}, {0, 0, 0, 1} };

		A = matrixMultiply(A, rotate);
		B = matrixMultiply(B, rotate);
		C = matrixMultiply(C, rotate);
		D = matrixMultiply(D, rotate);
		E = matrixMultiply(E, rotate);
		F = matrixMultiply(F, rotate);
		G = matrixMultiply(G, rotate);
		H = matrixMultiply(H, rotate);

		makeCube();
	}
	
	// Performs the rotate about the z axis transformation on the world coordinates of the cube
	public void rotateZ(double angle) {
		double cosX = Math.cos(Math.toRadians(angle));
		double sinX = Math.sin(Math.toRadians(angle));
		double[][] rotate = { {cosX, sinX, 0, 0}, {-sinX, cosX, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1} };

		A = matrixMultiply(A, rotate);
		B = matrixMultiply(B, rotate);
		C = matrixMultiply(C, rotate);
		D = matrixMultiply(D, rotate);
		E = matrixMultiply(E, rotate);
		F = matrixMultiply(F, rotate);
		G = matrixMultiply(G, rotate);
		H = matrixMultiply(H, rotate);

		makeCube();
	}

}
