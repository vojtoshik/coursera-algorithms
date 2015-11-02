import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class Board {

    private final int[] board;
    private final int dimension;

    private final int zeroValueIndex;

    public Board(int[][] blocks) {
        this(flattenBoard(blocks));
    }

    private Board(int[] blocks) {
        board = blocks;
        dimension = (int)Math.sqrt(board.length);
        zeroValueIndex = findZeroValueIndex(blocks);
    }

    /**
     * @return board dimension N
     */
    public int dimension() {
        return dimension;
    }

    /**
     * @return number of blocks out of place
     */
    public int hamming() {

        int result = 0;

        for (int i = 0; i < board.length; i++) {

            boolean isPlaceForZero = i == board.length - 1;

            if (board[i] == i || isPlaceForZero && board[i] == 0) {
                continue;
            }

            result++;
        }

        return result;
    }

    /**
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {

        int result = 0;

        for (int i = 0; i < board.length; i++) {
            int expectedRow = getRow(board[i] - 1, dimension);
            int expectedColumn = getColumn(board[i] - 1, dimension);

            int actualRow = getRow(i, dimension);
            int actualColumn = getColumn(i, dimension);

            result += Math.abs(expectedRow - actualRow) + Math.abs(expectedColumn - actualColumn);
        }

        return result;
    }

    /**
     * @return board that that obtained by exchanging any pair of blocks
     */
    public Board twin() {

        int[] twinArray = copyOf(board);

        int firstIndex = findNextNonZeroValueIndex(twinArray, 0);
        int secondIndex = findNextNonZeroValueIndex(twinArray, firstIndex + 1);

        swap(twinArray, firstIndex, secondIndex);

        return new Board(twinArray);
    }

    public Iterable<Board> neighbors() {

        return () -> new NeighborsIterator();
    }

    /**
     * @return true, if all blocks are in correct place
     */
    public boolean isGoal() {

        // we don't check last element as it supposed to be 0
        for (int i = 0; i < board.length - 1; i++) {
            if (board[i] != i + 1) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board boardToCompareWith = (Board) o;

        for (int i = 0; i < dimension; i++) {
            if (board[i] != boardToCompareWith.board[i]) {
                return false;
            }
        }

        return true;
    }

    public String toString() {

        String result = String.valueOf(dimension);

        for (int i = 0; i < board.length; i++) {

            if (i % dimension == 0) {
                result += "\n";
            }

            result += " " + board[i];
        }

        return result;
    }

    private static int findNextNonZeroValueIndex(int[] array, int startFrom) {

        for (int i = startFrom; i < array.length; i++) {
            if (array[i] != 0) {
                return i;
            }
        }

        // should not happen in real life
        return -1;
    }

    private static int findZeroValueIndex(int[] array) {

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                return i;
            }
        }

        // should not happen in real life
        return -1;
    }

    private static int getRow(int index, int dimension) {
        return index / dimension;
    }

    private static int getColumn(int index, int dimension) {
        return index % dimension;
    }

    private static int[] flattenBoard(int[][] originalBoard) {
        int size = originalBoard.length;
        int[] flattenedBoard = new int[size * size];

        for (int i = 0; i < size * size; i++) {

            if (originalBoard[getRow(i, size)].length != size) {
                throw new IllegalArgumentException("Passed board has wrong dimension!");
            }

            flattenedBoard[i] = originalBoard[getRow(i, size)][getColumn(i, size)];
        }

        return flattenedBoard;
    }

    private static int[] copyOf(int[] array) {
        int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);

        return copy;
    }

    private static void swap(int[] array, int aIndex, int bIndex) {
        int tmp = array[aIndex];
        array[aIndex] = array[bIndex];
        array[bIndex] = tmp;
    }

    /**
     * This iterator relies on the fact that Board is immutable
     */
    private class NeighborsIterator implements Iterator<Board> {

        private final int[][] directions = {
                {-1, 0}, // up
                {0, 1},  // right
                {1, 0},  // down
                {-1, 0}  // left
        };

        private final int DIRECTION_INDEX_UP = 0;
        private final int DIRECTION_INDEX_RIGHT = 1;
        private final int DIRECTION_INDEX_DOWN = 2;
        private final int DIRECTION_INDEX_LEFT = 3;

        private int lastTimeMovedToDirection = -1;

        private final int zeroRow;
        private final int zeroColumn;

        public NeighborsIterator() {
            zeroRow = getRow(zeroValueIndex, dimension);
            zeroColumn = getRow(zeroValueIndex, dimension);
        }

        @Override
        public boolean hasNext() {
            return
                    lastTimeMovedToDirection < DIRECTION_INDEX_UP    && canMove(DIRECTION_INDEX_UP) ||
                    lastTimeMovedToDirection < DIRECTION_INDEX_RIGHT && canMove(DIRECTION_INDEX_RIGHT) ||
                    lastTimeMovedToDirection < DIRECTION_INDEX_DOWN  && canMove(DIRECTION_INDEX_DOWN) ||
                    lastTimeMovedToDirection < DIRECTION_INDEX_LEFT  && canMove(DIRECTION_INDEX_LEFT);
        }

        @Override
        public Board next() {

            for (int i = lastTimeMovedToDirection + 1; i < 4; i++) {
                if (canMove(i)) {
                    return move(i);
                }
            }

            throw new NoSuchElementException("Iterator is exhausted!");
        }

        private boolean canMove(int direction) {

            int newRow = zeroRow + directions[direction][0];
            int newColumn = zeroColumn + directions[direction][1];

            return newRow < 0 || newColumn < 0 || newRow >= dimension || newColumn >= dimension;
        }

        private Board move(int direction) {

            int newRow = zeroRow + directions[direction][0];
            int newColumn = zeroColumn + directions[direction][1];

            int[] newBoard = copyOf(board);
            swap(newBoard, zeroValueIndex, newRow * dimension + newColumn);

            lastTimeMovedToDirection = direction;

            return new Board(newBoard);
        }
    }
}
