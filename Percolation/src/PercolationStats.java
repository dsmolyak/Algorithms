/**
 * Created by dsmolyak on 6/18/14.
 */
public class PercolationStats {

    private int length;
    private double[] experiments;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        length = N;
        experiments = new double[T];
        for (int i = 0; i < T; i++) {
            experiments[i] = simulation();
        }
    }

    private double simulation() {
        Percolation tank = new Percolation(length);
        int counter = 0;
        do {
            //open site
            int i = StdRandom.uniform(length);
            int j = StdRandom.uniform(length);
            if (!tank.isOpen(i + 1, j + 1)) {
                tank.open(i + 1, j + 1);
                counter++;
            }
            //check if percolates
        } while (!tank.percolates());
        return ((double) counter / (double) (length * length));
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(experiments);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(experiments);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(experiments.length));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(experiments.length));
    }

    // test client, described below
    public static void main(String[] args) {
        int length = Integer.valueOf(args[0]);
        int numExperiments = Integer.valueOf(args[1]);
        PercolationStats stats = new PercolationStats(length, numExperiments);
        StringBuilder result = new StringBuilder("% java PercolationStats ")
        .append(length).append(" ").append(numExperiments).append("\n")
        .append("mean = ").append(stats.mean()).append("\n")
        .append("stddev = ").append(stats.stddev()).append("\n")
        .append("confidence interval = ").append(stats.confidenceLo()).append(", ").append(stats.confidenceHi());
        System.out.println(result.toString());
    }
}
