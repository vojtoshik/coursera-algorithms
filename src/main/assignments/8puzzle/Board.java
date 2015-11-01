/**
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class Board {

    private int[] board;
    private int dimension;

    public Board(int[][] blocks) {
        this(flattenBoard(blocks));
    }

    private Board(int[] blocks) {
        board = blocks;
        dimension = (int)Math.sqrt(board.length);
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
        return null;
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
        return "";
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

            if (originalBoard[i].length != size) {
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
}
