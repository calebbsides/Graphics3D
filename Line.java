import java.awt.image.BufferedImage;

public class Line {

	int x0, x1, y0, y1, z0, z1, Cx, Cy, offsetX, offsetY;
	BufferedImage image = ImagePanel.img;
	int[] end0 = new int[4];
	int[] end1 = new int[4];
	
	//Constructor
	public Line(int[] end0, int[] end1) {
		offsetX = 0;
		offsetY = 0;

		this.x0 = end0[0] + offsetX;
		this.y0 = end0[1] + offsetY;
		this.z0 = end0[2];
		
		this.x1 = end1[0] + offsetX;
		this.y1 = end1[1] + offsetY;
		this.z1 = end1[2];

		Cx = ((this.x0 + this.x1) / 2);
		Cy = ((this.y0 + this.y1) / 2);

		this.end0[0] = this.x0;
		this.end0[1] = this.y0;
		this.end0[2] = this.z0;
		this.end0[3] = 1;

		this.end1[0] = this.x1;
		this.end1[1] = this.y1;
		this.end1[2] = this.z1;
		this.end1[3] = 1;
	}

	// toString
	public String toString() {
		return (x0 - offsetX) + " " + (y0 - offsetY) + " " + (x1 - offsetX) + " " + (y1 - offsetY);
	}
}