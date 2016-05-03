/* Sun Zhicheng
 * coursera course PA2
 * An implementation of 8 puzzle
 * this is solver class
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private MinPQ<Node> pqR = new MinPQ<Node>(); //solve for real node
    private MinPQ<Node> pqT = new MinPQ<Node>(); //solve for twin node
    private final boolean isSov;
    private final Node end; //the final point
    
    private static class Node implements Comparable<Node> {
        private Board b;      //board
        private Node pNode;   //previous node
        private int   cnt;  //number of moves already made
        private int   cost; //hamming dist or manhattan dist
        
        //this is only for initial Node
        Node(Board initial) {
            b     = initial; //should clone or not?
            pNode = null;
            cnt   = 0;
            cost  = b.manhattan() + cnt;
        }
        
        //common constructor
        Node(Board cur, Node pre, int count) {
            b     = cur; //should clone or not?
            pNode = pre;
            cnt   = count;
            cost  = b.manhattan() + count;
        }
        
        //for comparable interface
        public  int compareTo(Node that) {
            return Integer.compare(cost, that.cost);    
        }
    }
    
    public Solver(Board initial) {
        Node nodeR = new Node(initial); //the real node
        Node nodeT = new Node(initial.twin()); //the twin node
        
        while (!nodeR.b.isGoal() && !nodeT.b.isGoal()) {
            //real node
            for (Board i : nodeR.b.neighbors()) {
                // == null means the first enqueue
                if (nodeR.pNode == null || !i.equals(nodeR.pNode.b)) { 
                    int count = nodeR.cnt;
                    count++;
                    Node n = new Node(i, nodeR, count);
                    pqR.insert(n);
                }
            }
            nodeR = pqR.delMin();
            //twin node
            for (Board i : nodeT.b.neighbors()) {
                // == null means the first enqueue
                if (nodeT.pNode == null || !i.equals(nodeT.pNode.b)) {
                    int count = nodeT.cnt;
                    count++;
                    Node n = new Node(i, nodeT, count);
                    pqT.insert(n);
                }
            }
            nodeT = pqT.delMin();
        }
        
        if (nodeR.b.isGoal()) {
            isSov = true;
            end = nodeR;
        }       
        else {
            isSov = false;
            end = null;
        }
    }
    
    public boolean isSolvable() {
        return isSov;
    }
    
    public int moves() {
        if (isSolvable())
            return end.cnt;
        else
            return -1;
    }    
    
    public Iterable<Board> solution() {
        if (!isSov) return null;
        
        Stack<Board> s = new Stack<Board>();
        
        Node cur = end;
        while (cur != null) {
            s.push(cur.b);
            cur = cur.pNode;
        }
        
        return s;
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
            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution())
                    StdOut.println(board);
            }           
        }
    }

}
