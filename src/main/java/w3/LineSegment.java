package w3;

public class LineSegment {
    private Point p;
    private Point q;

    public LineSegment(Point p, Point q) {    // constructs the line segment between points p and q
        this.p = p;
        this.q = q;
    }

    public void draw() {                    // draws this line segment
        p.drawTo(q);
    }

    public String toString() {                  // string representation
        return p.compareTo(q) <= 1 ?
                p + "-" + q :
                q + "-" + p;
    }
}