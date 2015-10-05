import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class PointTest {
    @Test
    public void testSlopeToWorksAsExpectedForEqualPoints() {
        Point p1 = new Point(1, 1),
              p2 = new Point(1, 1);

        assertEquals(Double.NEGATIVE_INFINITY, p1.slopeTo(p2));
    }

    @Test
    public void testSlopeToWorksAsExpectedForPointsOnVerticalLine() {
        Point p1 = new Point(1, 10),
              p2 = new Point(1, 12);

        assertEquals(Double.POSITIVE_INFINITY, p1.slopeTo(p2));
    }

    @Test
    public void testSlopeToWorksAsExpected() {
        Point p1 = new Point(0, 0),
              p2 = new Point(1, 1);

        assertEquals(1d, p1.slopeTo(p2));
    }
}
