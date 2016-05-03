/* Sun Zhicheng
 * coursera course PA2
 * An implementation of 8 puzzle
 * this is solver class
 */

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private MinPQ<Node> pq = new MinPQ<Node>();
    private boolean isSov = false;
    private Node end;
    
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
    
    public boolean isSolverable() {
        return isSov;
    }
    
    public int moves() {
        return end.cnt;
    }
    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
    }

}
