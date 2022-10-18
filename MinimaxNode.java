// Should be an immutable object.
public abstract class MinimaxNode {

    // Should be `true` for the root node.
    private final boolean isMaxPly;

    public MinimaxNode(boolean isMaxPly) {
        this.isMaxPly = isMaxPly;
    }

    public final boolean isMaxPly() {
        return isMaxPly;
    }

    public final boolean isTerminal() {
        return numChildren() == 0;
    }

    // Return value cannot be null.
    // Return value cannot contain null elements.
    // Return value must have length equal to `numChildren()`.
    public abstract MinimaxNode[] children();

    // Return value cannot be negative.
    public abstract int numChildren();

    public abstract double heuristicValue();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract MinimaxNode clone();

    @Override
    public abstract String toString();
}
