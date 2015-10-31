/**
 * Write a program to solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.
 *
 * Your program should work correctly for arbitrary N-by-N boards (for any 2 ≤ N < 128), even if it is too slow to solve
 * some of them in a reasonable amount of time.
 *
 * You may not call any library functions other those in java.lang, java.util, and algs4.jar. You must use MinPQ for the
 * priority queue(s).
 *
 * <i>Performance requirements</i>
 * Your implementation should support all Board methods in time proportional to N2 (or better) in the worst case.
 *
 * <i>Corner cases</i>
 * The constructor should throw a {@link java.lang.NullPointerException} if passed a null argument.
 *
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class Solver {

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("Initial board can't be null");
        }
    }

    public boolean isSolvable() {
        return false;
    }

    public int moves() {
        return 0;
    }

    public Iterable<Board> solution() {
        return null;
    }
}
