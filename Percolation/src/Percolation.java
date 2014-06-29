/**
 * Created by dsmolyak on 6/18/14.
 */
public class Percolation {

    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF realWeightedQuickUnionUF;

    private boolean[][] grid;
    private int length;
    private int topVirtualNodeIndex;
    private int bottomVirtualNodeIndex;

    // create N-by-N grid, with all grid blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        length = N;
        weightedQuickUnionUF = new WeightedQuickUnionUF(length * length + 2);
        realWeightedQuickUnionUF = new WeightedQuickUnionUF(length * length + 1);
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
        for (int i = 0; i < length; i++) {
            topVirtualNodeIndex = length * length;
            weightedQuickUnionUF.union(i, topVirtualNodeIndex);
            realWeightedQuickUnionUF.union(i, topVirtualNodeIndex);
            bottomVirtualNodeIndex = length * length + 1;
            weightedQuickUnionUF.union(i + (length - 1) * length, bottomVirtualNodeIndex);
        }
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        grid[i - 1][j - 1] = true;
        Site site = new Site(i - 1, j - 1);
        Site[] sites = site.findNeighbors();
        connectToNeighbors(sites, site);
    }



    private void connectToNeighbors(Site[] neighbors, Site center) {
        for (Site neighbor: neighbors) {
            if (neighbor != null && isOpen(neighbor.row + 1, neighbor.col + 1)) {
                weightedQuickUnionUF.union(center.coordinateId(), neighbor.coordinateId());
                realWeightedQuickUnionUF.union(center.coordinateId(), neighbor.coordinateId());

            }
        }
    }


    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return grid[i - 1][j - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return (isOpen(i, j) && isConnectedToTop(i, j));
    }

    private boolean isConnectedToTop(int i, int j) {
        return realWeightedQuickUnionUF.connected(getUnionFindIndex(i, j), topVirtualNodeIndex);
    }

    // does the system percolate?
    public boolean percolates() {
        if (length != 1) {
            return weightedQuickUnionUF.connected(topVirtualNodeIndex, bottomVirtualNodeIndex);
        }
        else {
            return (isOpen(1, 1));
        }
    }

    private int getUnionFindIndex(int i, int j) {
        return (i - 1) * length + (j - 1);
    }

    private class Site {
        public static final int MAX_NEIGHBORS = 4;
        private int row;
        private int col;

        private Site(int r, int c) {
            this.row = r;
            this.col = c;
        }

        private int coordinateId() {
            return getUnionFindIndex(row + 1, col + 1);
        }

        private Site[] findNeighbors() {
            Site[] result = new Site[MAX_NEIGHBORS];
            if (row > 0) {
                result[0] = new Site(row - 1, col);
            }
            if (row < length - 1) {
                result[1] = new Site(row + 1, col);
            }
            if (col > 0) {
                result[2] = new Site(row, col - 1);
            }
            if (col < length - 1) {
                result[3] = new Site(row, col + 1);
            }
            return result;
        }
    }


}
