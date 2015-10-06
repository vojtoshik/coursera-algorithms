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

    private int numberOfSegments = 0;
    private LineSegment[] segmentsContainer = new LineSegment[1];

    public BruteCollinearPoints(Point[] points) {
        validateInputData(points);

        sort(points);
        checkForDuplicates(points);

        if (points.length <= 4) {
            return;
        }

        searchSegmentsWithBruteForce(points);
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return segmentsContainer;
    }

    private void checkForDuplicates(Point[] points) {

        for (int i = 1; i < points.length; i++) {

            if (points[i - 1].compareTo(points[i]) == 0) {
                throw new IllegalArgumentException("Duplicates are not allowed");
            }
        }
    }

    private void searchSegmentsWithBruteForce(Point[] points) {

        for (int i = 0; i < points.length; i++) {

            for (int j = i + 1; j < points.length; j++) {

                for (int k = j + 1; k < points.length; k++) {

                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) {
                        continue;
                    }

                    for (int l = k + 1; l < points.length; l++) {

                        if (points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])) {
                            addLineSegment(new LineSegment(points[i], points[l]));
                            // we may stop at this point as there are only 4 points in the same segment
                            break;
                        }
                    }

                    // there's not need to iterate through rest of the points as soon as we identified 3rd
                    break;
                }
            }
        }

        resizeSegmentsContainer(numberOfSegments);
    }

    private void throwExceptionIfNull(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }

    private void validateInputData(Point[] points) {
        throwExceptionIfNull(points);

        for (int i = 0; i < points.length; i++) {
            throwExceptionIfNull(points[i]);
        }
    }

    private void addLineSegment(LineSegment newLineSegment) {
        if (numberOfSegments + 1 >= segmentsContainer.length) {
            resizeSegmentsContainer(2 * segmentsContainer.length);
        }

        segmentsContainer[numberOfSegments++] = newLineSegment;
    }

    private void resizeSegmentsContainer(int newSize) {
        LineSegment[] newSegments = new LineSegment[newSize];

        for (int i = 0; i < numberOfSegments; i++) {
            newSegments[i] = segmentsContainer[i];
        }

        segmentsContainer = newSegments;
    }

    /**
     * For this particular case Insertion sort should be sufficient
     *
     * @param array array to be sorted
     */
    private void sort(Point[] array) {

        for (int i = 1; i < array.length; i++) {

            for (int j = i; j > 0; j--) {

                if (array[j].compareTo(array[j - 1]) > -1) {
                    break;
                }

                Point tmp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = tmp;
            }
        }
    }
}
