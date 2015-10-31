/**
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class Board {

    private int[][] board;
    private int dimension;

    public Board(int[][] blocks) {

        dimension = blocks.length;
        board = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {

            if (blocks[i].length != dimension) {
                throw new IllegalArgumentException("Board has illegal size!");
            }

            for (int j = 0; j < dimension; j++) {
                board[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        return 0;
    }

    public boolean isGoal() {

        for (int i = 0; i < dimension; i++) {

            for (int j = 0; j < dimension; j++) {

                boolean isPlaceForZero = i + 1 == dimension && j + 1 == dimension;
                int expectedValue = i * dimension + j + 1;

                if (board[i][j] == expectedValue || isPlaceForZero && board[i][j] == 0) {
                    continue;
                }

                return false;
            }
        }

        return true;
    }

    public int manhattan() {
        return 0;
    }

    public Board twin() {
        return null;
    }

    public Iterable<Board> neighbors() {
        return null;
    }

    public boolean equals(Object y) {
        return false;
    }

    public String toString() {
        return "";
    }
}
