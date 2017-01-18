import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;

public class Buttons {

	SidePanel sidePanel;
	static ImagePanel imagePanel;
	static ArrayList<Line> dataLines = new ArrayList<Line>();
	static Scanner input = new Scanner(System.in);
	static Cube cube = new Cube();

	// Rotate Arbitrary
	public static JButton Rotate() {
		JButton rotateButton = new JButton("3D Rotate Arbitrary");

		rotateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.print("Angle: ");
				double angle = input.nextDouble();

				cube.rotate3D(angle);
				dataLines = cube.lines;
			}
		});
		return rotateButton;
	}
	
	// Rotate on the X axis
	public static JButton RotateX() {
		JButton rotateButton = new JButton("3D Rotate X");

		rotateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.print("Angle: ");
				double angle = input.nextDouble();

				cube.rotateX(angle);
				dataLines = cube.lines;
			}
		});
		return rotateButton;
	}
	
	// Rotate on the Y axis
	public static JButton RotateY() {
		JButton rotateButton = new JButton("3D Rotate Y");

		rotateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.print("Angle: ");
				double angle = input.nextDouble();

				cube.rotateY(angle);
				dataLines = cube.lines;
			}
		});
		return rotateButton;
	}
	
	// Rotate on the Z axis
	public static JButton RotateZ() {
		JButton rotateButton = new JButton("3D Rotate Z");

		rotateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.print("Angle: ");
				double angle = input.nextDouble();

				cube.rotateZ(angle);
				dataLines = cube.lines;
			}
		});
		return rotateButton;
	}
	

	// Translate
	public static JButton Translate() {
		JButton translateButton = new JButton("3D Translate");
		
		translateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.print("Tx: ");
				double Tx = input.nextDouble();
				System.out.print("Ty: ");
				double Ty = input.nextDouble();
				System.out.print("Tz: ");
				double Tz = input.nextDouble();
				
				cube.translate3D(Tx, Ty, Tz);
				dataLines = cube.lines;
			}
		});

		return translateButton;
	}

	// Scale
	public static JButton Scale() {
		JButton scaleButton = new JButton("3D Scale");

		scaleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.print("Sx: ");
				double Sx = input.nextDouble();
				System.out.print("Sy: ");
				double Sy = input.nextDouble();
				System.out.print("Sz: ");
				double Sz = input.nextDouble();
				
				cube.scale3D(Sx, Sy, Sz);
				dataLines = cube.lines;

			}
		});

		return scaleButton;
	}
	
	// Initialize a Cube object 
	public static JButton DrawCube() {
		JButton drawCubeButton = new JButton("Create a Cube");

		drawCubeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cube = new Cube();
				cube.makeCube();
				dataLines = cube.lines;
			}
		});
		
		return drawCubeButton;
	}
	
	// Display the resultant Cube
	public static JButton Draw() {
		JButton drawButton = new JButton("Draw Lines");
		
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataLines.isEmpty()) {
					System.out.println("No Lines To Draw!");
				} else {
					drawLines();	
				}
			}
		});
		
		return drawButton;
	}
	
	//Clear the canvas
	public static JButton Clear() {
		JButton clearButton = new JButton("Clear");
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagePanel.clear();
				dataLines = new ArrayList<Line>();
			}
		});
		
		return clearButton;
	}

	// Add all above buttons to the Side Panel 
	public static void addButtons(SidePanel sidePanel, ImagePanel imagePanel) {
		Buttons.imagePanel = imagePanel;
		
		sidePanel.add(Translate());
		sidePanel.add(Scale());
		sidePanel.add(Rotate());
		sidePanel.add(RotateX());
		sidePanel.add(RotateY());
		sidePanel.add(RotateZ());
		sidePanel.add(DrawCube());
		sidePanel.add(Draw());
		sidePanel.add(Clear());
	}
	
	// Displays the Lines on the Canvas
	private static void drawLines() {
		for(Line l : dataLines) {
			LineDrawing.drawLine(l, imagePanel);
		}
	}
}
