// The class with a `main(...)` method for testing `Minimax`
public class TestMinimax {
    public static void main(String[] args) {
        final int[][] grid = {{1, 1, 0}, {-2, -2, 0}, {1, -1, 0}};
        final MinimaxNode root = new MinimaxNode_TicTacToe(true, grid);
        final Minimax minimax0 = new Minimax(root, 10, false, false);
        final Minimax minimax1 = new Minimax(root, 10, false, true);
        final Minimax minimax2 = new Minimax(root, 10, true, false);
        final Minimax minimax3 = new Minimax(root, 10, true, true);
        final double result0 = minimax0.minimax();
        final double result1 = minimax1.minimax();
        final double result2 = minimax2.minimax();
        final double result3 = minimax3.minimax();

        System.out.println("Root node:");
        System.out.println();
        System.out.println("root == " + root);
        System.out.println();
      
        System.out.println();
        System.out.println();

        System.out.println("Minimax test objects:");
        System.out.println();
        System.out.println("minimax0 == " + minimax0);
        System.out.println();
        System.out.println("minimax1 == " + minimax1);
        System.out.println();
        System.out.println("minimax2 == " + minimax2);
        System.out.println();
        System.out.println("minimax3 == " + minimax3);
        System.out.println();
      
        System.out.println();
        System.out.println();

        System.out.println("Minimax test results:");
        System.out.println();
        System.out.println("result0 == " + result0);
        System.out.println();
        System.out.println("result1 == " + result1);
        System.out.println();
        System.out.println("result2 == " + result2);
        System.out.println();
        System.out.println("result3 == " + result3);
        System.out.println();
    }
}
