package algs11;
import java.awt.Color;
import stdlib.*;

/**
 * For this assignment, you must do two things:
 * 
 * 1. Complete the MyRectangle class at the bottom of this file. It is not a
 * public class, but don't worry about that. I've put two classes in one file so
 * that you need only hand in a single file.
 * 
 * In particular, you should get the testEquals method to work without throwing
 * any exceptions.
 * 
 * 2. Modify the draw method of MyRecursiveRectangles so that it behaves as
 * demonstrated here:
 * 
 * <pre>
 * extras/RecursiveRectangles.mp4 in Riely ZipFile
 * </pre>
 * 
 * To do this only requires very minor modifications to the function.
 * 
 * It is not necessary, but if you want to learn more about the drawing classes,
 * see here:
 * 
 * <pre>
 * extras/doc/stdlib/StdDraw.html  in Riely ZipFILE
 * Library borrowed from Princeton
 * </pre>
 */

public class HW3 {
	public static Color randomColor() {
		int r = StdRandom.uniform(256);
		int g = StdRandom.uniform(256);
		int b = StdRandom.uniform(256);
		return new Color(r, g, b);
	}

	public static final int VARIANCE = 60;

	public static int randomByteAbove(int i) {
		return StdRandom.uniform(Math.max(0, i - 1),
				Math.min(i + VARIANCE, 256));
	}

	public static int randomByteBelow(int i) {
		return StdRandom.uniform(Math.max(0, i - VARIANCE),
				Math.min(i + 1, 256));
	}

	public static Color randomColorLight(Color c) {
		int r = randomByteAbove(c.getRed());
		int g = randomByteAbove(c.getGreen());
		int b = randomByteAbove(c.getBlue());
		return new Color(r, g, b);
	}

	public static Color randomColorDark(Color c) {
		int r = randomByteBelow(c.getRed());
		int g = randomByteBelow(c.getGreen());
		int b = randomByteBelow(c.getBlue());
		return new Color(r, g, b);
	}

	public static void draw(MyRect rect, int level, Color c) {
		// draw 0th level and too-small ellipses but don't recurse
		StdDraw.setPenColor(c);
         rect.drawEllipse();
        StdDraw.show (50);
        if(level == 0 || rect.area() < 0.1) return;
        StdDraw.setPenColor (StdDraw.BLACK);
        if (level % 2 == 0) {
            double xmid = StdRandom.uniform(rect.xmin(), rect.xmax());
            StdDraw.line(xmid, rect.ymin(), xmid, rect.ymax());
            MyRect left = new MyRect(rect.xmin(), rect.ymin(), xmid, rect.ymax());
           // StdDraw.setPenColor(randomColorLight(c));
           //left.drawEllipse();
            // change above two lines to a recursive call 
            // TODO: draw(...);
            draw (left, level-1, (randomColorLight(c))); // left 
            MyRect right = new MyRect(xmid, rect.ymin(), rect.xmax(), rect.ymax());
            //StdDraw.setPenColor(randomColorDark(c));
            //right.drawEllipse();
            // change above two lines to a recursive call 
            // TODO: draw(...);
            draw (right,level-1, (randomColorDark(c))); // right
        } else {
            double ymid = StdRandom.uniform(rect.ymin(), rect.ymax());
            StdDraw.line (rect.xmin(), ymid, rect.xmax(), ymid);
            MyRect below = new MyRect(rect.xmin(), rect.ymin(), rect.xmax(), ymid);
           // StdDraw.setPenColor(randomColorLight(c));
            //below.drawEllipse();
            // change above two lines to a recursive call 
            // TODO: draw(...);
            draw (below,level-1, (randomColorLight(c))); // below 
            MyRect above = new MyRect(rect.xmin(), ymid, rect.xmax(), rect.ymax());
            //StdDraw.setPenColor(randomColorDark(c));
            //above.drawEllipse();
            // change above two lines to a recursive call 
            // TODO: draw(..);
            draw(above,level-1,(randomColorDark(c))); //above
        }
	}

	public static void testEquals() {
		// this method should not throw any exceptions!
		MyRect r1 = new MyRect(1, 1, 2, 2);
		if (!r1.equals(r1)) {
			throw new Error();
		}
		if (!r1.equals(new MyRect(1, 1, 2, 2))) {
			throw new Error();
		}
		if (r1.equals(null)) {
			throw new Error();
		}
		if (r1.equals(new Object())) {
			throw new Error();
		}
		if (r1.equals(new MyRect(0, 1, 2, 2))) {
			throw new Error();
		}
		if (r1.equals(new MyRect(1, 0, 2, 2))) {
			throw new Error();
		}
		if (r1.equals(new MyRect(1, 1, 3, 2))) {
			throw new Error();
		}
		if (r1.equals(new MyRect(1, 1, 2, 3))) {
			throw new Error();
		}
	}
	public final static double DIM = 1.0;

	public static void main(String[] args) {

		StdDraw.setCanvasSize(800, 800); // in pixels
		StdDraw.setXscale(-DIM, DIM);
		StdDraw.setYscale(-DIM, DIM);
		MyRect rect = new MyRect(-DIM, -DIM, DIM, DIM);
		Color initialColor = randomColor(); // new Color(127,127,127)
		long seed = StdRandom.getSeed();
		StdRandom.setSeed(seed);
		
		for (int numLevels = 1; numLevels < 8; numLevels += 1) {
			StdDraw.clear();
			draw(rect, numLevels, initialColor);
		    StdDraw.show(3000);
		 }
	}
}

// An axis aligned rectangle
class MyRect {
	double xmin, ymin, xmax, ymax;

	// construct the axis-aligned rectangle "[xmin, xmax] x [ymin, ymax]"
	public MyRect(double xmin, double ymin, double xmax, double ymax) {
		if (xmax < xmin || ymax < ymin) {
			throw new IllegalArgumentException("Invalid rectangle");
		}
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}

	// accessor methods for 4 coordinates
	public double xmin() {
		return xmin;
	}

	public double ymin() {
		return ymin;
	}

	public double xmax() {
		return xmax;
	}

	public double ymax() {
		return ymax;
	}

	// width, height, area of rectangle
	public double width() {
		return xmax - xmin;
	}

	public double height() {
		return ymax - ymin;
	}

	public double area() {
		return (xmax - xmin) * (ymax - ymin);
	}

	// halfway between xmin and xmax
	public double xmid() {
		return (xmax + xmin) / 2;
	}

	// halfway between ymin and ymax
	public double ymid() {
		return (ymax + ymin) / 2;
	}

	// follow the recipe given in the textbook (pp 102-103) to implement equals
	public boolean equals(Object y) {
		return true;
	}

	public void drawEllipse() {
		StdDraw.filledEllipse(xmid(), ymid(), width() / 2, height() / 2);
	}

	public void drawRectangle() {
		StdDraw.filledRectangle(xmid(), ymid(), width() / 2, height() / 2);
	}

	public String toString() {
		return "[" + xmin() + ", " + xmax() + "] x [" + ymin() + ", " + ymax()
				+ "]";
	}
}
