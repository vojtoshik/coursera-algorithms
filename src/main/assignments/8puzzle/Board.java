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

    public int dimension() {
        return dimension;
    }

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

    public Board twin() {
        return null;
    }

    public Iterable<Board> neighbors() {
        return null;
    }

    public boolean isGoal() {
        // well, we could implement iteration through board and stop as soon as we find element on wrong place, but
        // to keep code cleaner let's reuse hamming() heuristic function for that
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        return false;
    }

    public String toString() {
        return "";
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
}
