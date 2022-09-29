package w3;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {                  // constructs the point (x, y)
        this.x = x;
        this.y = y;
    }

    public void draw() {                             // draws this point
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {                // draws the line segment from this point to that point
        StdDraw.line(x, y, that.x, that.y);
    }

    public String toString() {                         // string representation
        return "(" + x + "," + y + " )";
    }

    public int compareTo(Point that) {  // compare two points by y-coordinates, breaking ties by x-coordinates
        return this.y == that.y ?
                this.x < that.x ? -1 : (this.x > that.x ? 1 : 0) :
                this.y < that.y ? -1 : 1;
    }

    public double slopeTo(Point that) {      // the slope between this point and that point
        if (that.x == x && that.y == this.y) return Double.NEGATIVE_INFINITY;
        else if (that.x == x) return Double.POSITIVE_INFINITY;
        else if (that.y == y) return 0d;
        else return (1.0 * (that.y - this.y)) / (that.x - x);
    }

    public Comparator<Point> slopeOrder() {           // compare two points by slopes they make with this point
        return Comparator.comparingDouble(this::slopeTo);
    }
}