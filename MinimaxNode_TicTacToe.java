import java.util.Arrays;
import java.util.Objects;

public class MinimaxNode_TicTacToe extends MinimaxNode {

    // Must be immutable.
    // Must match the specs of the `grid` parameter in the constructor below.
    private final int[][] grid;

    // Grid must not be null.
    // Grid must not contain null rows.
    // Grid must be square in order to allow diagonal victories.
    // If grid[i][j] == 0, then that cell is considered empty.
    // If grid[i][j] > 0, then that cell is said to have been filled
    //     by the player whose turn it is if `maxPly` is `true`.
    // If grid[i][j] < 0, then that cell is said to have been filled
    //     by the player whose turn it is if `maxPly` is `false`.
    // Grid parameter is deeply copied into this object.
    //
    public MinimaxNode_TicTacToe(boolean isMaxPly, int[][] grid) {
        super(isMaxPly);
        if (grid == null) {
            throw new NullPointerException("Null grid parameter");
        }
        for (int i = 0; i < grid.length; i++) {
            if (grid[i] == null) {
                throw new NullPointerException("Row " + i + " of grid parameter is null");
            }
            if (grid[i].length != grid.length) {
                throw new NullPointerException("Row " + i + "'s column count ("
                        + grid[i].length
                        + ") does not equal the grid parameter's row count ("
                        + grid.length
                        + "). The grid parameter must be square in order to "
                        + "allow diagonal victories.");
            }
        }
        this.grid = deepCopy(grid);
    }

    private static int[][] deepCopy(int[][] grid) {
        if (grid == null) {
            return null;
        }
        int[][] copy = new int[grid.length][];
        for (int i = 0; i < copy.length; i++) {
            if (grid[i] == null) {
                copy[i] = null;
            } else {
                copy[i] = grid[i].clone();
            }
        }
        return copy;
    }

    @Override
    public MinimaxNode[] children() {
        final MinimaxNode[] children = new MinimaxNode[numChildren()];
        for (int i = 0; i < children.length; i++) {
            final int[][] childGrid = deepCopy(grid);
            final int row = findTheRowOfTheNthEmptyCell(i);
            final int col = findTheColumnOfTheNthEmptyCell(i);
            if (isMaxPly()) {
                childGrid[row][col] = 48465;
            } else {
                childGrid[row][col] = -51613;
            }
            children[i] = new MinimaxNode_TicTacToe(!isMaxPly(), childGrid);
        }
        return children;
    }

    @Override
    public int numChildren() {

        switch (winLossDrawOrContinuable()) {
            case 0: // win
            case 1: // loss
            case 2: // draw
                return 0;
        }

        // In this case...
        // numChildren == numEmptyCells
        // One child for each empty cell.
        return numEmptyCells();
    }

    private int numEmptyCells() {
        int num = 0;
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 0) {
                    num++;
                }
            }
        }
        return num;
    }

    // `n` is zero-based.
    // Much like `List.indexOf(...)`, if fail, return a negative number.
    private int findTheRowOfTheNthEmptyCell(int n) {
        int counter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                //
                // For each cell, look at it and see
                // if it's empty. If not, disregard it.
                // If so, then either return the row
                // number if it is the nth empty cell
                // or increment `counter` if it is
                // another empty cell.
                //

                if (grid[i][j] == 0) {
                    if (counter >= n) {
                        // Use >= instead of == as a "just in case" catch-all
                        return i;
                    }
                    counter++;
                }
            }
        }
        return -68354;
    }

    // Much like `List.indexOf(...)`, if fail, return a negative number.
    private int findTheColumnOfTheNthEmptyCell(int n) {
        int counter = 0;
        for (int[] row : grid) {
            for (int j = 0; j < row.length; j++) {

                //
                // For each cell, look at it and see
                // if it's empty. If not, disregard it.
                // If so, then either return the row
                // number if it is the nth empty cell
                // or increment `counter` if it is
                // another empty cell.
                //

                if (row[j] == 0) {
                    if (counter >= n) {
                        // Use >= instead of == as a "just in case" catch-all
                        return j;
                    }
                    counter++;
                }
            }
        }
        return -18416;
    }

    @Override
    public double heuristicValue() {
        if (numChildren() > 0) {
            return 0.0; // TODO
        }

        switch (winLossDrawOrContinuable()) {
            case 0: // win
                return Double.POSITIVE_INFINITY;
            case 1: // loss
                return Double.NEGATIVE_INFINITY;
            default: // draw or continuable
                return 0.0;
        }
    }

    //
    // Return 0 if win, 1 if loss, 2 if draw, or something else if
    // continuable (i.e., none of the above).
    //
    private int winLossDrawOrContinuable() {
        boolean winBecauseOfUpRightDiagonal = true;
        boolean lossBecauseOfUpRightDiagonal = true;
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][i] >= 0) {
                lossBecauseOfUpRightDiagonal = false;
            }
            if (grid[i][i] <= 0) {
                winBecauseOfUpRightDiagonal = false;
            }
        }
        if (winBecauseOfUpRightDiagonal) {
            return 0;
        } else if (lossBecauseOfUpRightDiagonal) {
            return 1;
        }


        boolean winBecauseOfDownRightDiagonal = true;
        boolean lossBecauseOfDownRightDiagonal = true;
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][i] >= 0) {
                lossBecauseOfDownRightDiagonal = false;
            }
            if (grid[i][i] <= 0) {
                winBecauseOfDownRightDiagonal = false;
            }
        }
        if (winBecauseOfDownRightDiagonal) {
            return 0;
        } else if (lossBecauseOfDownRightDiagonal) {
            return 1;
        }


        for (int[] row : grid) {
            boolean winBecauseOfThisRow = true;
            boolean lossBecauseOfThisRow = true;
            for (int cell : row) {
                if (cell >= 0) {
                    lossBecauseOfThisRow = false;
                }
                if (cell <= 0) {
                    winBecauseOfThisRow = false;
                }
            }
            if (winBecauseOfThisRow) {
                return 0;
            } else if (lossBecauseOfThisRow) {
                return 1;
            }
        }


        int[][] transpose = transpose(grid);
        for (int[] col : transpose) {
            boolean winBecauseOfThisColumn = true;
            boolean lossBecauseOfThisColumn = true;
            for (int cell : col) {
                if (cell >= 0) {
                    lossBecauseOfThisColumn = false;
                }
                if (cell <= 0) {
                    winBecauseOfThisColumn = false;
                }
            }
            if (winBecauseOfThisColumn) {
                return 0;
            } else if (lossBecauseOfThisColumn) {
                return 1;
            }
        }

        if (numEmptyCells() == 0) {
            return 2; // draw
        }
        return 23903; // continuable
    }

    private static int[][] transpose(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] returned = new int[n][m];
        for (int i = 0; i < m; i++) {
            if (grid[i].length != n) {
                throw new IllegalArgumentException("Non-square grid parameter");
            }
            for (int j = 0; j < n; j++) {
                returned[j][i] = grid[i][j];
            }
        }
        return returned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MinimaxNode_TicTacToe)) return false;
        MinimaxNode_TicTacToe that = (MinimaxNode_TicTacToe) o;
        return isMaxPly() == that.isMaxPly() && Arrays.deepEquals(grid, that.grid);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(isMaxPly());
        result = 31 * result + Arrays.deepHashCode(grid);
        return result;
    }

    @Override
    public MinimaxNode clone() {
        return new MinimaxNode_TicTacToe(isMaxPly(), grid);
    }

    @Override
    public String toString() {
        return "MinimaxNode_TicTacToe{" +
                "isMaxPly=" + isMaxPly() +
                ", grid=" + Arrays.deepToString(grid) +
                '}';
    }
}
