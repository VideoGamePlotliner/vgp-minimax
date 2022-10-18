import java.util.LinkedHashMap;

public class Minimax {

    private final MinimaxNode root;
    private final int maxDepth;
    private final LinkedHashMap<MinimaxNode, Double> transpositionTable; // null if not being used
    private final boolean useAlphaBeta;

    private long minimaxCallCount; // Should not be negative. You can use this with `toString()` to output the size of the tree whose root node equals the field `root`

    public Minimax(MinimaxNode root, int maxDepth, boolean useTranspositionTable, boolean useAlphaBeta) {
        if (root == null) {
            throw new NullPointerException("Null root parameter");
        }
        if (maxDepth < 0) {
            throw new IllegalArgumentException("Negative max-depth parameter");
        }
        this.root = root;
        this.maxDepth = maxDepth;
        this.transpositionTable = useTranspositionTable ? new LinkedHashMap<>() : null;
        this.useAlphaBeta = useAlphaBeta;
        reset();
    }

    // Set certain non-final fields to their default values.
    private void reset() {
        minimaxCallCount = 0;
    }

    public double minimax() {
        reset();
        return minimax(root, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    // Parameters `alpha` and `beta` are unused if `useAlphaBeta` is false.
    private double minimax(MinimaxNode node, int depth, double alpha, double beta) {
        minimaxCallCount++;
        if (node == null) {
            throw new NullPointerException("Null node parameter");
        } else if (depth >= maxDepth || node.isTerminal()) {
            // Use >= instead of == as a "just in case" catch-all

            //
            // Calculate the heuristic value -- or, if applicable,
            // load the heuristic value from the transposition table.
            //
            if (!isUsingTranspositionTable()) {
                return node.heuristicValue();
            } else {
                Double value = null;
                if (transpositionTable.containsKey(node)) {
                    value = transpositionTable.get(node);
                }
                if (value == null) {
                    value = node.heuristicValue();
                    transpositionTable.put(node, value);
                } else {
                    minimaxCallCount--; // that is, it's as if we skipped the action of calling `minimax(...)`
                }
                return value;
            }


        } else if (node.isMaxPly()) {

            double x = Double.NEGATIVE_INFINITY;
            for (MinimaxNode child : node.children()) {
                x = Math.max(x, minimax(child, depth + 1, alpha, beta));
                if (isUsingAlphaBetaPruning()) {
                    alpha = Math.max(alpha, x);
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            return x;


        } else {

            double y = Double.POSITIVE_INFINITY;
            for (MinimaxNode child : node.children()) {
                y = Math.min(y, minimax(child, depth + 1, alpha, beta));
                if (isUsingAlphaBetaPruning()) {
                    beta = Math.min(beta, y);
                    if (alpha >= beta) {
                        break;
                    }
                }
            }

            return y;


        }
    }

    @Override
    public String toString() {
        return "Minimax{" +
                "root=" + root +
                ", maxDepth=" + maxDepth +
                ", isUsingTranspositionTable=" + isUsingTranspositionTable() +
                ", isUsingAlphaBetaPruning=" + isUsingAlphaBetaPruning() +
                ", minimaxCallCount=" + minimaxCallCount +
                '}';
    }

    public boolean isUsingTranspositionTable() {
        return (transpositionTable != null);
    }

    public boolean isUsingAlphaBetaPruning() {
        return useAlphaBeta;
    }
}
