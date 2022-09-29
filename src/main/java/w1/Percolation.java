package w1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// the row and column indices are integers between 1 and n, where (1, 1)
// in StdIn, StdOut, StdRandom, StdStats, WeightedQuickUnionUF, and java.lang.
public class Percolation {
    private final WeightedQuickUnionUF ds;
    private final boolean[][] arr;
    private int count = 0;
    private final int top;
    private final int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("block size should be > 0");

        top = 0;
        bottom = n * n + 1;
        arr = new boolean[n][n];

        ds = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;

        if (row == 1) connectEleWithTop(row, col);
        if (row == arr.length) connectEleWithBottom(row, col);

        arr[row - 1][col - 1] = true;
        count++;
        unionOnAdjacentSites(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndexes(row, col);
        return arr[row - 1][col - 1];
    }

    // is the site (row, col) full?
    // an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites.
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && ds.find(top) == ds.find(dsIndexOf(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return ds.find(top) == ds.find(bottom);
    }


    private void connectEleWithBottom(int row, int col) {
        ds.union(bottom, dsIndexOf(row, col));
    }

    private void connectEleWithTop(int row, int col) {
        ds.union(top, dsIndexOf(row, col));
    }

    private void unionOnAdjacentSites(int row, int col) {
        // left
        if (col - 1 > 0 && isOpen(row, col - 1))
            ds.union(dsIndexOf(row, col), dsIndexOf(row, col - 1));
        // right
        if (col + 1 <= arr.length && isOpen(row, col + 1))
            ds.union(dsIndexOf(row, col), dsIndexOf(row, col + 1));
        // lower
        if (row + 1 <= arr.length && isOpen(row + 1, col))
            ds.union(dsIndexOf(row, col), dsIndexOf(row + 1, col));
        // upper
        if (row - 1 > 0 && isOpen(row - 1, col))
            ds.union(dsIndexOf(row, col), dsIndexOf(row - 1, col));
    }

    private void validateIndexes(int row, int col) {
        if (row > arr.length || col > arr.length || row <= 0 || col <= 0) {
            throw new IllegalArgumentException("Range is not supported: " + row + " - " + col);
        }
    }

    private int dsIndexOf(int row, int col) {
        return ((row - 1) * arr.length) + (col - 1) + 1;
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 4;
        System.out.println("Applying for n = " + n);
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            System.out.println("Opening " + row + " - " + col);
            p.open(row, col);
            System.out.println(p.ds.count());
            System.out.println(p.isFull(row, col));
        }
        System.out.println("Probability = " + (p.count * 1.0 / (n * n)));

    }
}
