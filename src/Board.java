/* Sun Zhicheng
 * coursera course PA2
 * An implementation of 8 puzzle
 * this is board class
 */

import java.util.Arrays;

import edu.princeton.cs.algs4.Queue;

public class Board {
    private final int[][] board;
    private final int N; //dimension
    
    public Board(int[][] blocks) {
        board = blocks.clone(); //this is only shallow clone
        N = board.length;
    }
    
    public int dimension() {
        return N;
    }
    
    //return hamming distance
    public int hamming() {
        int wrong = 0; //wrong position 
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0)            continue;
                if (board[i][j] != mat2ind(i, j)) wrong++;                
            }
        }
        return wrong;
    }
    
    //return manhattan distance
    public int manhattan() {
        int dist = 0; //the distance altogether
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0)            continue;
                int[] mat = ind2mat(board[i][j]);
                dist += Math.abs(mat[0] - i) + Math.abs(mat[1] - j);
            }
        }
        return dist;
    }
    
    public boolean isGoal() {
        // how to change from manhattan to hamming?????
        return manhattan() == 0;
    }
    
    public Board twin() {
        int i = 0;
        int [] mat1 = new int[2];
        int [] mat2 = new int[2];

        mat1 = ind2mat(i);
        mat2 = ind2mat(i + 1);
        if (board[mat1[0]][mat1[1]] == 0)     mat1 = ind2mat(i + 2);
        else if(board[mat2[0]][mat2[1]] == 0) mat2 = ind2mat(i + 2);
        
        return exch(mat1[0], mat1[1], mat2[0], mat2[1]);
    }
    
    public boolean equals(Object other) {
        //cite date.java
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.board[i][j] != that.board[i][j]) 
                    return false;
            }
        }    
        return true;
    }
    
    //Iterable<Board> means any data type implements iterable?
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //find the zero block
                if (board[i][j] == 0) {
                    exch(q, i, j, i - 1, j);
                    exch(q, i, j, i + 1, j);
                    exch(q, i, j, i, j - 1);
                    exch(q, i, j, i, j + 1);
                    return q;
                }                                        
            }
        }
        // should throw an exception!!!
        return null;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int mat2ind (int i, int j) {
        return i * N + j + 1;
    }    
    
    // input i >= 1
    private int[] ind2mat (int i) {
        int[] mat = new int[2];
        i--;
        mat[0] = i / N;
        mat[1] = i % N;
        return mat;
    }
    
    //there should be enumerator up down left right
    //exchange and enqueue
    private void exch(Queue<Board> q, int i, int j, int x, int y) {
        if (x < 0 || x >= N || y < 0 || y >= N ) 
            return;
        Board board = exch(i, j, x, y);
        
        q.enqueue(board);     
    }
    
    //only used in twin
    //so no need to check 0
    private Board exch(int i, int j, int x, int y) {
        int[][] blks = new int[N][N];
        
        //.clone is only a shallow copy
        // this is the deep copy
        for (int line = 0; line < blks.length; line++)
            blks[line] = Arrays.copyOf(board[line], board.length);
        
        int t = blks[i][j];
        blks[i][j] = blks[x][y];
        blks[x][y] = t;
        
        return new Board(blks);     
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        

    }

}
