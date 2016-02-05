package com.example.lab2;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by bdtrev on 19/01/2016.
 */
public class LightsModel implements Serializable {

    int[][] grid;
    boolean notStrict = true;
    int n;

    public LightsModel(int n) {
        this.n = n;
        grid = new int[n][n];
    }

    public boolean isSwitchOn(int i, int j) {
        if (grid[i][j] == 0) {
            return false;
        }
        return true;
    }

    public void flipLines(int i, int j) {
        if (isSwitchOn(i, j)) {
            grid[i][j] = 0;
        } else {
            grid[i][j] = 1;
        }
        for (int x = 0; x < n; x++) {
            if (isSwitchOn(i, x)) {
                grid[i][x] = 0;
            } else {
                grid[i][x] = 1;
            }
            if (isSwitchOn(x, j)) {
                grid[x][j] = 0;
            } else {
                grid[x][j] = 1;
            }
        }
    }

    public void tryFlip(int i, int j) {
        try {
            if (isSwitchOn(i, j) || notStrict) {
                flipLines(i, j);
            }
        } catch (Exception e) {

        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(Arrays.toString(grid[i]) + "\n");
        }
        return sb.toString();
    }

    public int isSolved() {
        int accum = 0;
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if (isSwitchOn(x, y)) {
                    accum += 1;
                }
            }
        }
        return accum;
    }

    public int getScore() {
        return isSolved();
    }

    public void reset(int n) {
        this.n = n;
        grid = new int[this.n][this.n];
    }
}
