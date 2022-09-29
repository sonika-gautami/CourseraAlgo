package w3;

import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private final Point[] points;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        if (null == points) throw new IllegalArgumentException("should not be null");
        if (points[0] == null) throw new IllegalArgumentException("equal points not accepted");

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) throw new IllegalArgumentException("equal points not accepted");

                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("equal points not accepted");
            }
        }

        this.points = Arrays.copyOf(points, points.length);
    }

    public int numberOfSegments() {      // the number of line segments
        return segments().length;
    }

    public LineSegment[] segments() {              // the line segments
        if (lineSegments == null) {
            lineSegments = generateSegments();
        }
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    private LineSegment[] generateSegments() {
        LineSegment[] t = new LineSegment[points.length];
        int index = 0;

        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = q + 1; r < points.length - 1; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        double qSlope = points[p].slopeTo(points[q]);
                        double rSlope = points[p].slopeTo(points[r]);
                        double sSlope = points[p].slopeTo(points[s]);

                        if (qSlope == rSlope && qSlope == sSlope) {
                            Point[] pointsInLine = {points[p], points[q], points[r], points[s]};
                            Arrays.sort(pointsInLine, Comparator.naturalOrder());
                            t[index++] = new LineSegment(pointsInLine[0], pointsInLine[3]);
                        }
                    }
                }
            }
        }

        return Arrays.copyOfRange(t, 0, index);
    }
}
