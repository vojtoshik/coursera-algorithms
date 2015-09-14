import edu.princeton.cs.algs4.StdStats;

import java.util.Optional;
import java.util.Random;

public class PercolationStats {

    private final double[] results;

    private Double mean;

    private Double stddev;

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(100, 10000);

        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " +stats.confidenceHi());
    }

    public PercolationStats(int N, int T) {

        results = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);

            int openedCellsNumber = 0;

            while (!percolation.percolates()) {
                int randomCellIndex = getRandomOccupiedCell(percolation, N);
                percolation.open(randomCellIndex / N + 1, randomCellIndex % N + 1);
                openedCellsNumber++;
            }

            results[i] = (double)openedCellsNumber/N/N;
        }
    }

    public double mean() {
        return Optional.ofNullable(mean).orElseGet(() -> {
            mean = StdStats.mean(results);
            return mean;
        });
    }

    public double stddev() {
        return Optional.ofNullable(stddev).orElseGet(() -> {
            stddev = StdStats.stddev(results);
            return stddev;
        });
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
