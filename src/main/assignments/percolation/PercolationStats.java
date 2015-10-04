import edu.princeton.cs.algs4.StdStats;

import java.util.Random;

/**
 * @author Anton Voitovych <vojtoshik@gmail.com>
 */
public class PercolationStats {

    private final double[] results;

    private double mean;

    private double stddev;

    public PercolationStats(int N, int T) {

        if (T <= 0 || N <= 0) {
            throw new IllegalArgumentException("Bad input arguments!");
        }

        results = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);

            int openedCellsNumber = 0;

            while (!percolation.percolates()) {
                int randomCellIndex = getRandomOccupiedCell(percolation, N);
                percolation.open(randomCellIndex / N + 1, randomCellIndex % N + 1);
                openedCellsNumber++;
            }

            results[i] = (double) openedCellsNumber/N/N;
        }
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            throw new IllegalArgumentException("N and T values have to be provided!");
        }

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(N, T);

        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " +stats.confidenceHi());
    }

    public double mean() {

        if (mean == 0) {
            mean = StdStats.mean(results);
        }

        return mean;
    }

    public double stddev() {

        if (stddev == 0) {
            stddev = StdStats.stddev(results);
        }

        return stddev;
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev()/Math.sqrt(results.length);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev()/Math.sqrt(results.length);
    }

    private int getRandomOccupiedCell(Percolation percolation, int N) {
        Random randomGenerator = new Random();

        int randomNumber;
        int i, j;

        do {
            randomNumber = randomGenerator.nextInt(N * N);
            i = randomNumber / N + 1;
            j = randomNumber % N + 1;
        } while (percolation.isOpen(i, j));

        return randomNumber;
    }
}
