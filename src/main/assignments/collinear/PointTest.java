import org.testng.annotations.Test;

import java.util.Comparator;

import static org.testng.Assert.assertEquals;

/**
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class PointTest {
    @Test
    public void testSlopeToWorksAsExpectedForEqualPoints() {
        Point p1 = new Point(1, 1),
              p2 = new Point(1, 1);

        assertEquals(p1.slopeTo(p2), Double.NEGATIVE_INFINITY);
    }

    @Test
    public void testSlopeToWorksAsExpectedForPointsOnVerticalLine() {
        Point p1 = new Point(1, 10),
              p2 = new Point(1, 12);

        assertEquals(p1.slopeTo(p2), Double.POSITIVE_INFINITY);
    }

    @Test
    public void testSlopeToWorksAsExpected() {
        Point p1 = new Point(0, 0),
              p2 = new Point(1, 1);

        assertEquals(p1.slopeTo(p2), 1d);
    }

    @Test
    public void testCompareToWorksAsExpected() {
        Point p1 = new Point(0, 0),
              p2 = new Point(0, 1);

        assertEquals(p1.compareTo(p2), -1);

        p2 = new Point(0, -1);
        assertEquals(p1.compareTo(p2), 1);

        p2 = new Point(1, 0);
        assertEquals(p1.compareTo(p2), -1);

        p2 = new Point(-1, 0);
        assertEquals(p1.compareTo(p2), 1);

        p2 = new Point(0, 0);
        assertEquals(p1.compareTo(p2), 0);

        p2 = new Point(1, 1);
        assertEquals(p1.compareTo(p2), -1);

        p2 = new Point(-1, 1);
        assertEquals(p1.compareTo(p2), -1);
    }

    @Test
    public void testSlopeOrder() {
        Comparator<Point> comparator = new Point(0, 0).slopeOrder();

        Point p1 = new Point(5, 5),
              p2 = new Point(6, 6);
        assertEquals(comparator.compare(p1, p2), 0);

        p2 = new Point(5, -5);
        assertEquals(comparator.compare(p1, p2), 1);
        assertEquals(comparator.compare(p2, p1), -1);
    }
}
