import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Percolation {

    private int[] grid;

    private final int gridSize;

    private final WeightedQuickUnionUF quickUnionContainer;

    public Percolation(int N) {
        gridSize = N;
        grid = new int[gridSize * gridSize];
        quickUnionContainer = new WeightedQuickUnionUF(gridSize * gridSize + 2);
    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("No filename with input data specified! Exiting...");
            return;
        }

        try {
            InputStream is = new FileInputStream(args[0]);
            Scanner scanner = new Scanner(is);

            int gridSize = scanner.nextInt();
            Percolation percolation = new Percolation(gridSize);

            while (scanner.hasNextInt()) {
                percolation.open(scanner.nextInt(), scanner.nextInt());
            }

            System.out.println("Percolates: " + (percolation.percolates() ? "yes" : "no"));
        } catch (FileNotFoundException e) {
            System.out.println("Failed to open file \"" + args[0] + "\" for reading! Exiting...");
        }
    }

    public boolean percolates() {
        return quickUnionContainer.connected(0, gridSize * gridSize + 1);
    }

    public void open(int i, int j) {
        grid[normalizeIndex(i, j)] = 1;

        if (i == 1) {
            quickUnionContainer.union(
                    quNormalizeIndex(i, j),
                    0
            );
        };

        if (i == gridSize) {
            quickUnionContainer.union(
                    quNormalizeIndex(i, j),
                    gridSize * gridSize + 1
            );
        }

        makeConnection(i, j, i - 1, j);
        makeConnection(i, j, i, j + 1);
        makeConnection(i, j, i + 1, j);
        makeConnection(i, j, i, j - 1);
    }

    public boolean isOpen(int i, int j) {
        return grid[normalizeIndex(i, j)] == 1;
    }

    public boolean isFull(int i, int j) {
        return !isOpen(i, j);
    }

    private void makeConnection(int i1, int j1, int i2, int j2) {
        try {
            if (isOpen(i2, j2)) {
                quickUnionContainer.union(
                        quNormalizeIndex(i1, j1),
                        quNormalizeIndex(i2, j2)
                );
            }
        } catch (IndexOutOfBoundsException e) {
            // i2, j2 indexes specify square outside of the grid
            // we just ignore that
        }
    }

    private int quNormalizeIndex(int i, int j) {
        return normalizeIndex(i, j) + 1;
    }

    private int normalizeIndex(int i, int j) {

        if (i < 1 || i > gridSize || j < 1 || j > gridSize) {
            throw new IndexOutOfBoundsException("Value has to be between 1 and " + gridSize + " " + i + " " + j);
        }

        return (i - 1) * gridSize + j - 1;
    }
}
