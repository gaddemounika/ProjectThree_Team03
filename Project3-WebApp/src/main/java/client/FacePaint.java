package client;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import utility.FaceExpressionData;

/**
 * Class to draw a face and represent the facial expressions.
 * 
 * @SER516 Project3_Team03
 * @version 1.0
 */
public class FacePaint extends JPanel {
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private static final int head_radiusval = 30;
	private static final int eye_radiusval = 5;
	private static final int eye_left_xpos = 40;
	private static final int eye_right_xpos = 60;
	private static final int eye_ypos = 40;
	private static final int pupil_size = 2;
	private static final int eyebrow_left_left_x = 35;
	private static final int eyebrow_left_right_x = 45;
	private static final int eyebrow_right_left_x = 55;
	private static final int eyebrow_right_right_x = 65;
	private static final int eyebrow_y = 30;
	private static final int mouth_ypos = 65;
	private static final int nose_apex_x = 50;
	private static final int nose_apex_y = 45;
	private static final int nose_ypos = 54;
	private static final int nose_width = 8;
	private double x_factor, y_factor;
	private int x_origin, y_origin;

	// GetVectors v = new GetVectors();

	/**
	 * Draws the face with the required expressions
	 * 
	 * @param g
	 *            Is a Graphics Object
	 * @param v
	 *            Contains the array of expressions
	 * @param x
	 *            Specifies the starting point on the x axis
	 * @param y
	 *            Specifies the starting point on the y axis
	 * @param height
	 *            Specifies the height of the window
	 * @param width
	 *            Specifies the width of the window
	 */
	public void drawFace(Graphics g, int x, int y, int height, int width) {
		// FaceExpressionData f = ClientSocket.getInstance().getFaceExpressionData();
		FaceExpressionData f = new FaceExpressionData();
		double[] v = f.fetchVectors();
		System.out.println(v);
		String direction = "Center";
		boolean blinkl = true;
		boolean blinkr = false;
		if (v[8] == 1) {
			blinkl = true;
			blinkr = true;
		} else if (v[9] == 1) {
			blinkl = true;
		} else if (v[10] == 1) {
			blinkr = true;
		} else if (v[11] == 1) {
			direction = "Left";
		} else if (v[12] == 1) {
			direction = "Right";
		}
		calc_scaleFactors(x, y, height, width);
		make_head(g);
		make_eye(g, blinkl, blinkr);
		make_pupil(g, direction, blinkl, blinkr);
		make_eyebrows(g, v[1], v[2]);
		make_nose(g);
		make_mouth(g, v[3], v[4], v[5], v[6], v[7]);
	}

	/**
	 * Finds the scaling factors so as to scale the face according to the
	 * background.
	 * 
	 * @param x
	 *            Specifies the starting point on the x axis
	 * @param y
	 *            Specifies the starting point on the y axis
	 * @param height
	 *            Specifies the height of the window
	 * @param width
	 *            Specifies the width of the window
	 */
	public void calc_scaleFactors(int x, int y, int height, int width) {
		x_factor = width / 100.0;
		y_factor = height / 100.0;
		x_origin = x;
		y_origin = y;
	}

	/**
	 * Gets the dimension of the outer panel.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	/**
	 * Makes outer structure of the face
	 * 
	 * @param g
	 *            Is a Graphics Object
	 */
	public void make_head(Graphics g) {
		createCircle(g, 50, 50, head_radiusval);
	}

	/**
	 * Makes the eyes along with the various expressions needed
	 * 
	 * @param g
	 *            Is a Graphics Object
	 * @param bl
	 *            Contains value for blinking of the left eye
	 * @param br
	 *            Contains value for blinking of the right eye
	 */
	public void make_eye(Graphics g, boolean bl, boolean br) {
		if (bl == true && br == true) {
			createLine(g, eye_left_xpos + eye_radiusval, eye_ypos, eye_left_xpos - eye_radiusval, eye_ypos);
			createLine(g, eye_right_xpos - eye_radiusval, eye_ypos, eye_right_xpos + eye_radiusval, eye_ypos);
		} else if (bl == true) {

			createOval(g, eye_left_xpos, eye_ypos, eye_radiusval, eye_radiusval);
			createLine(g, eye_right_xpos - eye_radiusval, eye_ypos, eye_right_xpos + eye_radiusval, eye_ypos);
		} else if (br == true) {
			createLine(g, eye_left_xpos + eye_radiusval, eye_ypos, eye_left_xpos - eye_radiusval, eye_ypos);
			createOval(g, eye_right_xpos, eye_ypos, eye_radiusval, eye_radiusval);
		} else {
			createOval(g, eye_left_xpos, eye_ypos, eye_radiusval, eye_radiusval);
			createOval(g, eye_right_xpos, eye_ypos, eye_radiusval, eye_radiusval);
		}
	}

	/**
	 * Makes the pupils along with the various expressions needed.
	 * 
	 * @param g
	 *            Is a Graphics Object
	 * @param direction
	 *            Contains the direction values for looking left or right
	 * @param bl
	 *            Contains value for blinking of the left eye
	 * @param br
	 *            Contains value for blinking of the right eye
	 */
	public void make_pupil(Graphics g, String direction, boolean bl, boolean br) {
		double d = 0.5;
		if (direction.equals("Right")) {
			d = 0.3;
			fillOval(g, eye_left_xpos + (int) ((d - 0.5) * 10), eye_ypos, pupil_size, pupil_size);
			fillOval(g, eye_right_xpos + (int) ((d - 0.5) * 10), eye_ypos, pupil_size, pupil_size);
		} else if (direction.equals("Left")) {
			d = 0.3;
			fillOval(g, eye_left_xpos - (int) ((d - 0.5) * 10), eye_ypos, pupil_size, pupil_size);
			fillOval(g, eye_right_xpos - (int) ((d - 0.5) * 10), eye_ypos, pupil_size, pupil_size);
		} else {
			if (bl == true) {
				fillOval(g, eye_left_xpos - (int) ((d - 0.5) * 10), eye_ypos, pupil_size, pupil_size);
			} else if (br == true) {
				fillOval(g, eye_right_xpos + (int) ((d - 0.5) * 10), eye_ypos, pupil_size, pupil_size);

			} else if (bl == false && br == false) {
				fillOval(g, eye_left_xpos - (int) ((d - 0.5) * 10), eye_ypos, pupil_size, pupil_size);
				fillOval(g, eye_right_xpos + (int) ((d - 0.5) * 10), eye_ypos, pupil_size, pupil_size);
			}
		}
	}

	/**
	 * Makes the eyebrows along with the various expressions needed.
	 * 
	 * @param g
	 *            Is a Graphics Object
	 * @param p1
	 *            Raises the brows according to this input
	 * @param p2
	 *            Furrows the brows according to this input
	 */
	public void make_eyebrows(Graphics g, double p1, double p2) {
		p1 = 0.0;
		p2 = 0.0;
		int y1, y2;
		if (p1 != 0.0) {
			y1 = eyebrow_y + (int) (p1 * 5);
			y2 = eyebrow_y - (int) (p1 * 5);
		} else if (p2 != 0.0) {
			y1 = eyebrow_y - (int) (p2 * 5);
			y2 = eyebrow_y + (int) (p2 * 5);
		} else {
			y1 = eyebrow_y;
			y2 = eyebrow_y;
		}
		createLine(g, eyebrow_left_left_x, y1, eyebrow_left_right_x, y2);
		createLine(g, eyebrow_right_left_x, y2, eyebrow_right_right_x, y1);
	}

	/**
	 * Makes the nose.
	 * 
	 * @param g
	 *            Is a Graphics Object
	 */
	public void make_nose(Graphics g) {
		createLine(g, nose_apex_x, nose_apex_y, nose_apex_x - (nose_width / 2), nose_ypos);
		createLine(g, nose_apex_x - (nose_width / 2), nose_ypos, nose_apex_x + (nose_width / 2), nose_ypos);
		createLine(g, nose_apex_x + (nose_width / 2), nose_ypos, nose_apex_x, nose_apex_y);
	}

	/**
	 * Makes the mouth along with the expressions needed.
	 * 
	 * @param g
	 *            Is a Graphics Object
	 * @param p3
	 *            Contains input for making the face smile
	 * @param p4
	 *            Contains input to make the face clench
	 * @param p5
	 *            Contains input for making the face smirk left
	 * @param p6
	 *            Contains input for making the face smirk right
	 * @param p7
	 *            Contains input for making the face laugh
	 */
	public void make_mouth(Graphics g, double p3, double p4, double p5, double p6, double p7) {
		double x1 = 40;
		double y1 = mouth_ypos;
		double x2 = 60;
		double y2 = mouth_ypos;
		double x3 = ((x2 - x1) / 2) + x1;
		double y3 = mouth_ypos;
		double y3lower = mouth_ypos;
		p3 = 0.0;
		p4 = 0.0;
		p5 = 0.0;
		p6 = 0.0;
		p7 = 0.0;
		if (p3 > 0.0) {
			x1 = x1 - (p3 * 5);
			x2 = x2 + (p3 * 5);
			y3 = y3 + ((p3 / 2.0) * 10);
			y3lower = y3;
		}
		if (p4 > 0.0) {
			x1 = x1 - (p4 * 5);
			x2 = x2 + (p4 * 5);
			y3 = y3 - ((p4 / 2.0) * 10);
			y3lower = y3lower + ((p4 / 2.0) * 10);
		}
		if (p5 > 0.0) {
			x1 = x1 + (p5 * 3);
			x2 = x2 + (p5 * 3);
			y2 = y2 - (p5 * 5);
		}
		if (p6 > 0.0) {
			x1 = x1 - (p6 * 3);
			x2 = x2 - (p6 * 3);
			y1 = y1 - (p6 * 5);
		}
		if (p7 > 0.0) {
			x1 = x1 - (p7 * 5);
			x2 = x2 + (p7 * 5);
			y3lower = y3lower + ((p7 / 2.0) * 10);
		}
		make_lips(g, x1, y1, x2, y2, x3, y3);
		make_lips(g, x1, y1, x2, y2, x3, y3lower);
	}

	/**
	 * Used to make the lips
	 * 
	 * @param g
	 *            Is a Graphics Object
	 * @param x1
	 *            Contains the starting point of the lips on the horizontal plane
	 * @param y1
	 *            Contains the starting point of the lips on the vertical plane
	 * @param x2
	 *            Contains the ending point of the lips on the horizontal plane
	 * @param y2
	 *            Contains the ending point of the lips on the vertical plane
	 * @param x3
	 *            Contains the center point of the lips on the horizontal plane
	 * @param y3
	 *            Contains the center point of the lips on the vertical plane
	 */
	public void make_lips(Graphics g, double x1, double y1, double x2, double y2, double x3, double y3) {
		int i, new_x, new_y, last_x, last_y;
		double denom = (Math.pow(x1, 2) * (x2 - x3)) + (x1 * (Math.pow(x3, 2) - Math.pow(x2, 2)))
				+ (Math.pow(x2, 2) * x3) + -(Math.pow(x3, 2) * x2);

		double a = ((y1 * (x2 - x3)) + (x1 * (y3 - y2)) + (y2 * x3) + -(y3 * x2)) / denom;

		double bb = ((Math.pow(x1, 2) * (y2 - y3)) + (y1 * (Math.pow(x3, 2) - Math.pow(x2, 2))) + (Math.pow(x2, 2) * y3)
				+ -(Math.pow(x3, 2) * y2)) / denom;

		double c = ((Math.pow(x1, 2) * ((x2 * y3) - (x3 * y2)))
				+ (x1 * ((Math.pow(x3, 2) * y2) - (Math.pow(x2, 2) * y3)))
				+ (y1 * ((Math.pow(x2, 2) * x3) - (Math.pow(x3, 2) * x2)))) / denom;

		for (i = (int) x1, last_x = (int) x1, last_y = (int) y1; i <= x2; i++) {
			new_x = i;
			new_y = (int) ((a * Math.pow(i, 2)) + (bb * i) + c);
			createLine(g, last_x, last_y, new_x, new_y);
			last_x = new_x;
			last_y = new_y;
		}
	}

	/**
	 * Creates a hollow Circle
	 * 
	 * @param g
	 *            Is a Graphics Object
	 * @param x
	 *            Contains the x position with respect to the background
	 * @param y
	 *            Contains the y position with respect to the background
	 * @param radius
	 *            Contains the radius of the circle to be drawn
	 */
	public void createCircle(Graphics g, int x, int y, int radius) {
		g.drawOval(scale_x(x - radius) + x_origin, scale_y(y - radius) + y_origin, scale_x(radius * 2),
				scale_y(radius * 2));
	}

	/**
	 * Creates a hollow Oval
	 * 
	 * @param g
	 *            Is a Graphics Object
	 * @param x
	 *            Contains the x position with respect to the background
	 * @param y
	 *            Contains the y position with respect to the background
	 * @param height
	 *            Contains the height to the oval to be drawn
	 * @param width
	 *            Contains the width of the oval to be drawn
	 */
	public void createOval(Graphics g, int x, int y, int height, int width) {
		g.drawOval(scale_x(x - width) + x_origin, scale_y(y - height) + y_origin, scale_x(width * 2),
				scale_y(height * 2));
	}

	/**
	 * Used to fill the hollow Oval
	 * 
	 * @param g
	 *            Is a Graphics Object
	 * @param x
	 *            Contains the x position with respect to the background
	 * @param y
	 *            Contains the y position with respect to the background
	 * @param height
	 *            Contains the height till which the oval to be filled
	 * @param width
	 *            Contains the width till which the oval to be filled
	 */
	public void fillOval(Graphics g, int x, int y, int height, int width) {
		g.fillOval(scale_x(x - width) + x_origin, scale_y(y - height) + y_origin, scale_x(width * 2),
				scale_y(height * 2));
	}

	/**
	 * Used to create a line
	 * 
	 * @param g
	 *            Is a Graphics Object
	 * @param x1
	 *            Contains the horizontal starting position of the line
	 * @param y1
	 *            Contains the vertical starting position of the line
	 * @param x2
	 *            Contains the horizontal ending position of the line
	 * @param y2
	 *            Contains the vertical ending position of the line
	 */
	public void createLine(Graphics g, int x1, int y1, int x2, int y2) {
		g.drawLine(scale_x(x1) + x_origin, scale_y(y1) + x_origin, scale_x(x2) + x_origin, scale_y(y2) + x_origin);
	}

	/**
	 * Used to scale the input according to the horizontal factor of the background
	 * 
	 * @param x
	 *            Parameter needed to be scaled
	 * @return Scaled parameter
	 */
	public int scale_x(int x) {
		return (int) (x * x_factor);
	}

	/**
	 * Used to scale the input according to the vertical factor of the background
	 * 
	 * @param y
	 *            Parameter needed to be scaled
	 * @return Scaled parameter
	 */
	public int scale_y(int y) {
		return (int) (y * y_factor);
	}

	/**
	 * Paints the desired face.
	 */
	public void paintComponent(Graphics g) {
		drawFace(g, 0, 0, getHeight(), getWidth());
	}

}
