/* Sun Zhicheng
 * coursera course PA2
 * An implementation of 8 puzzle
 * this is solver class
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<Node> pq = new MinPQ<Node>();
    private boolean isSov = false;
    private Node end; //the final point
    
    private static class Node implements Comparable<Node> {
        private Board b;    //current board
        private Board pb;   //previous board
        private int   cnt;  //number of moves already made
        private int   cost; //hamming dist or manhattan dist
        
        //this is only for initial Node
        public Node(Board initial) {
            b    = initial; //should clone or not?
            pb   = null;
            cnt  = 0;
            cost = b.manhattan() + cnt;
        }
        
        //common constructor
        public Node(Board cur, Board pre, int count) {
            b    = cur; //should clone or not?
            pb   = pre;
            cnt  = count;
            cost = b.manhattan() + count;
        }
        
        //for comparable interface
        public  int compareTo(Node that) {
            return Integer.compare(cost, that.cost);    
        }
    }
    
    public Solver(Board initial) {
        Node in = new Node(initial);
              
        while (!in.b.isGoal()) {
            for (Board i : in.b.neighbors()) {
                if (!i.equals(in.pb)) {
                    int count = in.cnt;
                    count++;
                    Node n = new Node(i, in.b, count);
                    pq.insert(n);
                }
            }
            in = pq.delMin();
        }
        end = in;
        isSov = true;
    }
    
    public boolean isSolvable() {
        return isSov;
    }
    
    public int moves() {
        return end.cnt;
    }
    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            StdOut.println(filename + ": " + solver.moves());
            
            // print solution to standard output
/*            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution())
                    StdOut.println(board);
            }*/
            
        }
    }

}
