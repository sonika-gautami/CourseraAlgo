package w3;

import java.util.Arrays;

public class FastCollinearPoints2 {
    private final Point[] points;
    private LineSegment[] lineSegments;

    public FastCollinearPoints2(Point[] points) {   // finds all line segments containing 4 points
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
        LineSegment[] t = new LineSegment[points.length];
        boolean[] alreadyDone = new boolean[points.length];
        int index = 0;

        for (int p = 0; p < points.length - 1; p++) {
            double[] slopes = sortedSlopes(p);

            int matchCount = 0;
            double lastSlope = slopes[0];

            for (int q = 1; q < slopes.length; q++) {
                if (slopes[q] == lastSlope) {
                    System.out.println("slop matched: " + points[q] + " with " + points[p]);
                    matchCount++;
                } else {
                    if (matchCount >= 2) {
                        LineSegment ls = addLineSegmentIfMoreThanThree(matchCount, p, q, alreadyDone);
                        if (ls != null) t[index++] = ls;
                        break;
                    }

                    lastSlope = slopes[q];
                    matchCount = 0;
                }
            }
            if (matchCount >= 2) {
                LineSegment ls = addLineSegmentIfMoreThanThree(matchCount, p, slopes.length, alreadyDone);
                if (ls != null) t[index++] = ls;
            }
        }

        return Arrays.copyOfRange(t, 0, index);
    }

    private double[] sortedSlopes(int p) {
        double[] slopes = new double[points.length - 1];
        int count = 0;
        for (int q = 0; q < points.length; q++) {
            if (q != p) {
                slopes[count++] = points[p].slopeTo(points[q]);
            }
        }
        Arrays.sort(slopes);
        return slopes;
    }

    private LineSegment addLineSegmentIfMoreThanThree(int matchCount,
                                                      int p,
                                                      int q,
                                                      boolean[] alreadyDone) {
        int doneCount = 0;
        Point[] pointsInLine = new Point[matchCount + 1];
        pointsInLine[0] = points[p];
        if (alreadyDone[p]) doneCount++;
        alreadyDone[p] = true;

        for (int i = 1; i <= (matchCount); i++) {
            pointsInLine[i] = points[q - i];
            if (alreadyDone[q - i]) doneCount++;
            alreadyDone[q - i] = true;
            System.out.println("--" + pointsInLine[i]);
        }

        if (doneCount >= 2) return null;

        Arrays.sort(pointsInLine, (p1, p2) -> p1.compareTo(p2));
        return new LineSegment(pointsInLine[0], pointsInLine[pointsInLine.length - 1]);
    }
}

/*

 0 1-N
 1
 */