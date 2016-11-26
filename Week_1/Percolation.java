import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    // wquf1 for test percolation connected top and bottom
    private WeightedQuickUnionUF wquf1;
    // wquf2 for test full connected top : avoid backwash
    private WeightedQuickUnionUF wquf2;
    private byte[] blockStat;

    public Percolation(int N) {
        // TODO Auto-generated constructor stub
        this.validateN(N);
        this.N = N;
        this.wquf1 = new WeightedQuickUnionUF(N * N + 2);
        this.wquf2 = new WeightedQuickUnionUF(N * N + 1);
        this.blockStat = new byte[N * N + 2];
        blockStat[0] = 1;
        blockStat[N * N + 1] = 1;
        for (int i = 1; i <= N * N; i++) {
            blockStat[i] = 0;
        }
    }

    public void open(int i, int j) {
        this.validateIJ(i, j);
        int virtualNum = this.transformToVirtualArrayNum(i, j);
        if (i == 1) {
            this.blockStat[virtualNum] = 1;
            this.wquf1.union(0, j);
            this.wquf2.union(0, j);
            int virtualDownNum = this.virtualDownNum(i, j);
            if (this.blockStat[virtualDownNum] == 1) {
                this.wquf1.union(virtualNum, virtualDownNum);
                if (virtualDownNum <= (N*N))
                	this.wquf2.union(virtualNum, virtualDownNum);
            }
        } else if (i == N) {
            this.blockStat[virtualNum] = 1;
            this.wquf1.union(N * N + 1, virtualNum);
            int virtualUpNum = this.virtualUpNum(i, j);
            if (this.blockStat[virtualUpNum] == 1) {
                this.wquf1.union(virtualNum, virtualUpNum);
                this.wquf2.union(virtualNum, virtualUpNum);
            }
        } else {
            this.blockStat[virtualNum] = 1;
            int virtualUpNum = this.virtualUpNum(i, j);
            int virtualDownNum = this.virtualDownNum(i, j);
            if (blockStat[virtualUpNum] == 1) {
                this.wquf1.union(virtualNum, virtualUpNum);
                this.wquf2.union(virtualNum, virtualUpNum);
            }
            if (blockStat[virtualDownNum] == 1) {
                this.wquf1.union(virtualNum, virtualDownNum);
                this.wquf2.union(virtualNum, virtualDownNum);
            }
        }

        if (j == 1) {
            this.blockStat[virtualNum] = 1;
            int virtualRightNum = this.virtualRightNum(i, j);
            if (this.blockStat[virtualRightNum] == 1) {
                this.wquf1.union(virtualNum, virtualRightNum);
                if (virtualRightNum <= (N*N))
                	this.wquf2.union(virtualNum, virtualRightNum);
            }
        } else if (j == N) {
            this.blockStat[virtualNum] = 1;
            int virtualLeftNum = this.virtualLeftNum(i, j);
            if (this.blockStat[virtualLeftNum] == 1) {
                this.wquf1.union(virtualNum, virtualLeftNum);
                this.wquf2.union(virtualNum, virtualLeftNum);
            }
        } else {
            this.blockStat[virtualNum] = 1;
            int virtualLeftNum = this.virtualLeftNum(i, j);
            int virtualRightNum = this.virtualRightNum(i, j);
            if (blockStat[virtualLeftNum] == 1) {
                this.wquf1.union(virtualNum, virtualLeftNum);
                this.wquf2.union(virtualNum, virtualLeftNum);
            }
            if (blockStat[virtualRightNum] == 1) {
                this.wquf1.union(virtualNum, virtualRightNum);
                this.wquf2.union(virtualNum, virtualRightNum);
            }
        }
    }

    public boolean isOpen(int i, int j) {
        this.validateIJ(i, j);
        int virtualNum = this.transformToVirtualArrayNum(i, j);
        if (this.blockStat[virtualNum] == 1) {
            return true;
        }
        return false;
    }

    public boolean isFull(int i, int j) {
        this.validateIJ(i, j);
        int virtualNum = this.transformToVirtualArrayNum(i, j);
        if (this.wquf2.connected(0, virtualNum)) {
            return true;
        }
        return false;
    }

    public boolean percolates() {
        if (this.wquf1.connected(0, N * N + 1))
            return true;
        return false;
    }


    private int transformToVirtualArrayNum(int i, int j) {
        return N * (i - 1) + j;
    }

    private int virtualUpNum(int i, int j) {
        return transformToVirtualArrayNum(i - 1, j);
    }

    private int virtualDownNum(int i, int j) {
        return transformToVirtualArrayNum(i + 1, j);
    }

    private int virtualLeftNum(int i, int j) {
        return transformToVirtualArrayNum(i, j - 1);
    }

    private int virtualRightNum(int i, int j) {
        return transformToVirtualArrayNum(i, j + 1);
    }

    private void validateN(int vN) {
        if (vN <= 0)
            throw new java.lang.IllegalArgumentException(N + " should be greater than 0!");
    }

    private void validateIJ(int i, int j) {
        if (i < 1 || i > N)
            throw new java.lang.ArrayIndexOutOfBoundsException("i=" + i + " is out of bound");
        if (j < 1 || j > N)
            throw new java.lang.ArrayIndexOutOfBoundsException("j=" + j + " is out of bound");
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Percolation perco = new Percolation(10);
        for (int i = 1; i <= 10; i++) {
            perco.open(i, i % 2 + 1);
        }
        System.out.println(perco.percolates());
    }

}
