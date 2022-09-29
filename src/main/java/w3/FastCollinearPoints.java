package w3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final Point[] points;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (null == points) throw new IllegalArgumentException("should not be null");
        if (points.length >= 1 && null == points[0]) throw new IllegalArgumentException("should not be null");

        Point[] temp = Arrays.copyOf(points, points.length);
        Arrays.sort(temp, (p1, p2) -> p1.compareTo(p2));

        for (int i = 1; i < temp.length; i++) {
            if (temp[i] == null || temp[i - 1].compareTo(temp[i]) == 0)
                throw new IllegalArgumentException("equal points not accepted");
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
        List<Double> slopes = new ArrayList<Double>();

        for (int p = 0; p < points.length - 2; p++) {
            Point[] sortedBySlope = sortedSlopes(p);

            double lastSlope = points[p].slopeTo(sortedBySlope[0]);
            int matchCount = 0;
            for (int q = 1; q < sortedBySlope.length; q++) {
                double currSlope = points[p].slopeTo(sortedBySlope[q]);
                if (currSlope == lastSlope) {
                    matchCount++;
                } else {
                    if (matchCount >= 2) {
                        addLine(matchCount, p, q, lines, sortedBySlope, slopes, lastSlope);
                    }
                    lastSlope = currSlope;
                    matchCount = 0;
                }
            }
            if (matchCount >= 2) {
                addLine(matchCount, p, sortedBySlope.length, lines, sortedBySlope, slopes, lastSlope);
            }
        }

        LineSegment[] lss = new LineSegment[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            lss[i] = makeLineSegment(lines.get(i));
        }
        return lss;
    }

    private Point[] sortedSlopes(int p) {
        Point[] sortedBySlope = Arrays.copyOfRange(points, p + 1, points.length);
        Arrays.sort(sortedBySlope, points[p].slopeOrder());
        return sortedBySlope;
    }

    private void addLine(int matchCount,
                         int p,
                         int q,
                         List<Point[]> lines,
                         Point[] sortedBySlope,
                         List<Double> slopes,
                         double lastSlope) {
        Point[] pointsInLine = new Point[matchCount + 1 + 1];
        pointsInLine[0] = points[p];
        for (int i = 0; i <= matchCount; i++) {
            pointsInLine[i + 1] = sortedBySlope[q - 1 - i];
        }
        Arrays.sort(pointsInLine, (p1, p2) -> p1.compareTo(p2));
        if (validateNotSubSegment(pointsInLine, lines, slopes)) {
            lines.add(new Point[]{pointsInLine[0], pointsInLine[matchCount + 1]});
            slopes.add(lastSlope);
        }
    }

    private LineSegment makeLineSegment(Point[] pointsInLine) {
        return new LineSegment(pointsInLine[0], pointsInLine[1]);
    }

    private boolean validateNotSubSegment(Point[] ps,
                                          List<Point[]> lines,
                                          List<Double> slopes) {
        for (int i = 0; i < lines.size(); i++) {
            var minE1 = lines.get(i)[0].slopeTo(ps[0]);
            var minE2 = lines.get(i)[1].slopeTo(ps[1]);
            if (minE1 == slopes.get(i) && minE2 == slopes.get(i)) return false;
            else if (minE1 == Double.NEGATIVE_INFINITY && minE2 == slopes.get(i)) return false;
            else if (minE2 == Double.NEGATIVE_INFINITY && minE1 == slopes.get(i)) return false;
        }
        return true;
    }
}

/*

 0 1-N
 1
 */