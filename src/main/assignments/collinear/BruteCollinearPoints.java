/**
 * Write a program BruteCollinearPoints.java that examines 4 points at a time and checks whether they all lie on the
 * same line segment, returning all such line segments. To check whether the 4 points p, q, r, and s are collinear,
 * check whether the three slopes between p and q, between p and r, and between p and s are all equal.
 *
 * The method segments() should include each line segment containing 4 points exactly once. If 4 points appear on a line
 * segment in the order p→q→r→s, then you should include either the line segment p→s or s→p (but not both) and you
 * should not include subsegments such as p→r or q→r. For simplicity, we will not supply any input to
 * BruteCollinearPoints that has 5 or more collinear points.
 *
 * <i>Corner cases</i>
 * Throw a {@link java.lang.NullPointerException} either the argument to the constructor is null or if any point in the
 * array is null.
 * Throw a {@link java.lang.IllegalArgumentException} if the argument to the constructor contains a repeated point.
 *
 * <i>Performance requirement</i>
 * The order of growth of the running time of your program should be N^4 in the worst case and it should use space
 * proportional to N plus the number of line segments returned.
 *
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class BruteCollinearPoints {
    public BruteCollinearPoints(Point[] points) {

    }

    public int numberOfSegments() {
        return 0;
    }

    public LineSegment[] segments() {
        return null;
    }
}
