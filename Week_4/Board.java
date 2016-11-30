import java.util.ArrayList;

public class Board {

    private final int[][] blocks;
//    private static int[][] goal;
    private final int dimension;
    private int manhattan = -1;
    
    public Board(int[][] blocks) {
        // TODO Auto-generated constructor stub
        this.dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
//        this.blocks = blocks;
//        goal = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
//        goal[dimension - 1][dimension - 1] = 0;
                
    }
    
    public int dimension() {
        return this.dimension;
    }
    
    public int hamming() {
        int hamming = 0;
        for (int i = 0, k = 1; i < dimension; i++) {
            for (int j = 0; j < dimension; j++, k++) {
                if ((this.blocks[i][j] != k) && (this.blocks[i][j] != 0)) {
                    hamming++;
                } else {
                    continue;
                }
            }
        }
        return hamming;
    }
    
    public int manhattan() {
        if (manhattan != -1) {
            return manhattan;
        }
        manhattan = 0;
        for (int i = 0, k = 1; i < dimension; i++) {
            for (int j = 0; j < dimension; j++, k++) {
                if ((this.blocks[i][j] != k) && (this.blocks[i][j] != 0)) {
                    manhattan += 
                            (Math.abs(((this.blocks[i][j] - 1) / dimension) - i) 
                            + Math.abs(((this.blocks[i][j] - 1) % dimension) - j));
                } else {
                    continue;
                }
            }
        }
        return manhattan;
    }
    
    public boolean isGoal() {
        if (this.manhattan() == 0) {        
            return true;
        }
            
        return false;
    }
    
    public Board twin() {
        int blank = 0;
        int[][] blocksTemp = new int[dimension][dimension];
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                blocksTemp[i][j] = this.blocks[i][j];
            }
        }
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    blank = i * dimension + j;
                    break;
                }
            }
        }
        
        int first = 0;
        if (first == blank) {
            first++;
        }
        int second = first;
        second++;
        if (second == blank) {
            second++;
        }
        blocksTemp[(first / dimension)][(first % dimension)] = 
                this.blocks[(second / dimension)][(second % dimension)];
        blocksTemp[(second / dimension)][(second % dimension)] = 
                this.blocks[(first / dimension)][(first % dimension)];
        Board boardTemp = new Board(blocksTemp);
        return boardTemp;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj == null) {
            return false;
        } else if (obj instanceof String) {
            String strBoard = (String) obj;
            return strBoard.equals(toString());
        } else if (obj instanceof Board) {
            Board board = (Board) obj;
            if (this.dimension != board.dimension) {
                return false;
            }
            for (int i = 0; i < this.dimension; i++) {
                for (int j = 0; j < this.dimension; j++) {
                    if (this.blocks[i][j] == board.blocks[i][j]) {
                        continue;
                        } else {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    public Iterable<Board> neighbors() {
        Board temp;
        int blank = blankBlock();
        int blankRow = getRow(blank);
        int blankColumn = getColumn(blank);
        ArrayList<Board> boards = new ArrayList<>();
        if (blankRow > 0) {
            temp = new Board(blocks);
            temp.blocks[blankRow][blankColumn] = blocks[blankRow - 1][blankColumn];
            temp.blocks[blankRow - 1][blankColumn] = 0;
            boards.add(temp);
        }
        if (blankRow < dimension - 1) {
            temp = new Board(blocks);
            temp.blocks[blankRow][blankColumn] = blocks[blankRow + 1][blankColumn];
            temp.blocks[blankRow + 1][blankColumn] = 0;
            boards.add(temp);
        }
        if (blankColumn > 0) {
            temp = new Board(blocks);
            temp.blocks[blankRow][blankColumn] = blocks[blankRow][blankColumn - 1];
            temp.blocks[blankRow][blankColumn - 1] = 0;
            boards.add(temp);
        }
        if (blankColumn < dimension - 1) {
            temp = new Board(blocks);
            temp.blocks[blankRow][blankColumn] = blocks[blankRow][blankColumn + 1];
            temp.blocks[blankRow][blankColumn + 1] = 0;
            boards.add(temp);
        }
        return boards;
    }
    
    
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuilder buffer = new StringBuilder();
        buffer.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                buffer.append(String.format("%2d ", blocks[i][j]));
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }
    
    private int getRow(int num) {
        return num / dimension;
    }
    
    private int getColumn(int num) {
        return num % dimension;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
    
    private int blankBlock() {
        for (int i = 0, k = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++, k++) {
                if (blocks[i][j] == 0) {
                    return k;
                }
            }
        }
        
        return 0;
    }
}
