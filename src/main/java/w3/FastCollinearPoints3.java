package w3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints3 {
    private final Point[] points;
    private LineSegment[] lineSegments;

    public FastCollinearPoints3(Point[] points) {   // finds all line segments containing 4 points
        if (null == points) throw new IllegalArgumentException("should not be null");

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
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
        List<Point[]> lines = new ArrayList<Point[]>();

        for (int p = 0; p < points.length - 1; p++) {
            Point[] sortedBySlope = sortedSlopes(p);

            double lastSlope = points[p].slopeTo(sortedBySlope[0]);
            int matchCount = 0;
            for (int q = 1; q < sortedBySlope.length; q++) {
                double currSlope = points[p].slopeTo(sortedBySlope[q]);
                if (currSlope == lastSlope) {
                    matchCount++;
                } else {
                    if (matchCount >= 2) {
                        addLine(matchCount, p, q, lines, sortedBySlope);
                    }
                    lastSlope = currSlope;
                    matchCount = 0;
                }
            }
            if (matchCount >= 2) {
                addLine(matchCount, p, sortedBySlope.length, lines, sortedBySlope);
            }
        }

        LineSegment[] lineSegments = new LineSegment[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            lineSegments[i] = makeLineSegment(lines.get(i));
        }
        return lineSegments;
    }

    private Point[] sortedSlopes(int p) {
        Point[] sortedBySlope = new Point[points.length - 1 - p];
        int c = 0;
        for (int i = p + 1; i < points.length; i++) {
            sortedBySlope[c++] = points[i];
        }

        Arrays.sort(sortedBySlope, points[p].slopeOrder());
        return sortedBySlope;
    }

    private void addLine(int matchCount,
                         int p,
                         int q,
                         List<Point[]> lines,
                         Point[] sortedBySlope) {
        Point[] pointsInLine = new Point[matchCount + 1 + 1];
        pointsInLine[0] = points[p];
        for (int i = 0; i <= matchCount; i++) {
            pointsInLine[i + 1] = sortedBySlope[q - 1 - i];
        }
        Arrays.sort(pointsInLine, (p1, p2) -> p1.compareTo(p2));
        if (validateNotSubSegment(pointsInLine, lines))
            lines.add(pointsInLine);
    }

    private LineSegment makeLineSegment(Point[] pointsInLine) {
        return new LineSegment(pointsInLine[0], pointsInLine[pointsInLine.length - 1]);
    }

    private boolean validateNotSubSegment(Point[] ps, List<Point[]> lines) {
        for (Point[] pointsInLine : lines) {
            int index = 0;
            for (int i = 0; i < pointsInLine.length; i++) {
                if (ps[index].compareTo(pointsInLine[i]) == 0) {
                    index++;
                }
                if (index > 2) {
                    return false;
                }
            }
        }
        return true;
    }
}

/*

 0 1-N
 1
 */