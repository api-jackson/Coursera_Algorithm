import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int N;
	// wquf1 for test percolation connected top and bottom
	private WeightedQuickUnionUF wquf1;
	// wquf2 for test full connected top : avoid backwash
//	private WeightedQuickUnionUF wquf2;

	private boolean isPercolate = false;

	// blockState :
	// 0: closed
	// 1: open but connect to top(not full) or bottom
	// 2: open and connect to bottom but not top(not full)
	// 3: open and connect to top(full)
	private byte[] blockState;

	public Percolation(int N) {
		// TODO Auto-generated constructor stub
		this.validateN(N);
		this.N = N;
		this.wquf1 = new WeightedQuickUnionUF(N * N + 1);
//		this.wquf2 = new WeightedQuickUnionUF(N * N + 1);
		this.blockState = new byte[N * N + 2];
		blockState[0] = 3;
		blockState[N * N + 1] = 1;
		for (int i = 1; i <= N * N; i++) {
			blockState[i] = 0;
		}
	}

	public void open(int i, int j) {
		this.validateIJ(i, j);
		int virtualNum = this.transformToVirtualArrayNum(i, j);

		// only have 1 block
		if (N * N == 1) {
			isPercolate = true;
			this.wquf1.union(0, j);
			blockState[virtualNum] = 3;
			return;
		}

		if (i == 1) {
			// this.blockState[virtualNum] = 1;
			// this.wquf1.union(0, j);
			// this.wquf2.union(0, j);
			// int virtualDownNum = this.virtualDownNum(i, j);
			// if (this.blockState[virtualDownNum] == 1) {
			// this.wquf1.union(virtualNum, virtualDownNum);
			// }

			/********************************************************/
			this.blockState[virtualNum] = 3;
			int virtualDownNum = this.virtualDownNum(i, j);
			int rootDownNum = this.wquf1.find(virtualDownNum);
			// down is open
			if (this.blockState[rootDownNum] > 0) {
				if (this.blockState[rootDownNum] == 2) {
					isPercolate = true;
				}
				blockState[rootDownNum] = 3;
				wquf1.union(virtualNum, rootDownNum);
			}

			/********************************************************/

		} 
		else if (i == N) {
			// this.blockState[virtualNum] = 1;
			// this.wquf1.union(N * N + 1, virtualNum);
			// int virtualUpNum = this.virtualUpNum(i, j);
			// if (this.blockState[virtualUpNum] == 1) {
			// this.wquf1.union(virtualNum, virtualUpNum);
			// this.wquf2.union(virtualNum, virtualUpNum);
			// }

			/********************************************************/
			// handle left and right
			if (blockState[virtualNum] == 0)
			{
				this.blockState[virtualNum] = 2;			
			}

			// i == N, j == 1 : down left num
			if (j == 1) {
				int virtualRightNum = this.virtualRightNum(i, j);
				int virtualUpNum = this.virtualUpNum(i, j);
				int rootRightNum = this.wquf1.find(virtualRightNum);
				int rootUpNum = this.wquf1.find(virtualUpNum);
				if ((blockState[rootRightNum] == 3) || (blockState[rootUpNum] == 3)) {
					isPercolate = true;
					blockState[virtualNum] = 3;
					if (blockState[rootRightNum] > 0) {
						this.blockState[rootRightNum] = 3;
						this.wquf1.union(rootRightNum, virtualNum);
					}
					if (blockState[rootUpNum] > 0) {
						this.blockState[rootUpNum] = 3;
						this.wquf1.union(rootUpNum, virtualNum);
					}
				} else {
					if (blockState[rootRightNum] > 0) {
						blockState[rootRightNum] = 2;
						this.wquf1.union(virtualNum, rootRightNum);
					}
					if (blockState[rootUpNum] > 0) {
						blockState[rootUpNum] = 2;
						this.wquf1.union(virtualNum, rootUpNum);
					}
				}
			}
			// i == N, j == N : down right num
			else if (j == N) {
				int virtualLeftNum = this.virtualLeftNum(i, j);
				int virtualUpNum = this.virtualUpNum(i, j);
				int rootLeftNum = this.wquf1.find(virtualLeftNum);
				int rootUpNum = this.wquf1.find(virtualUpNum);

				if ((blockState[rootLeftNum] == 3) || (blockState[rootUpNum] == 3)) {
					isPercolate = true;
					blockState[virtualNum] = 3;
					if (blockState[rootLeftNum] > 0) {
						blockState[rootLeftNum] = 3;
						this.wquf1.union(rootLeftNum, virtualNum);
					}
					if (blockState[rootUpNum] > 0) {
						blockState[rootUpNum] = 3;
						this.wquf1.union(rootUpNum, virtualNum);
					}
				} else {
					if (blockState[rootLeftNum] > 0) {
						blockState[rootLeftNum] = 2;
						this.wquf1.union(rootLeftNum, virtualNum);
					}
					if (blockState[rootUpNum] > 0) {
						blockState[rootUpNum] = 2;
						this.wquf1.union(rootUpNum, virtualNum);
					}
				}
			}
			// i == N : down middle num
			else {
				int virtualRightNum = this.virtualRightNum(i, j);
				int virtualLeftNum = this.virtualLeftNum(i, j);
				int virtualUpNum = this.virtualUpNum(i, j);
				int rootRightNum = this.wquf1.find(virtualRightNum);
				int rootLeftNum = this.wquf1.find(virtualLeftNum);
				int rootUpNum = this.wquf1.find(virtualUpNum);

				if ((blockState[rootLeftNum] == 3) || (blockState[rootRightNum] == 3) 
						|| (blockState[rootUpNum] == 3)) {
					blockState[virtualNum] = 3;
					isPercolate = true;
					if (blockState[rootLeftNum] > 0) {
						blockState[rootLeftNum] = 3;
						this.wquf1.union(rootLeftNum, virtualNum);
					}
					if (blockState[rootRightNum] > 0) {
						blockState[rootRightNum] = 3;
						this.wquf1.union(virtualNum, rootRightNum);
					}
					if (blockState[rootUpNum] > 0) {
						blockState[rootUpNum] = 3;
						this.wquf1.union(rootUpNum, virtualNum);
					}
				} else {
					if (blockState[rootLeftNum] > 0) {
						blockState[rootLeftNum] = 2;
						this.wquf1.union(rootLeftNum, virtualNum);
					}
					if (blockState[rootRightNum] > 0) {
						blockState[rootRightNum] = 2;
						this.wquf1.union(virtualNum, rootRightNum);
					}
					if (blockState[rootUpNum] > 0) {
						blockState[rootUpNum] = 2;
						this.wquf1.union(rootUpNum, virtualNum);
					}
				}
			}
			/********************************************************/
		} 
		else {
			// this.blockState[virtualNum] = 1;
			// int virtualUpNum = this.virtualUpNum(i, j);
			// int virtualDownNum = this.virtualDownNum(i, j);
			// if (blockState[virtualUpNum] == 1) {
			// this.wquf1.union(virtualNum, virtualUpNum);
			// this.wquf2.union(virtualNum, virtualUpNum);
			// }
			// if (blockState[virtualDownNum] == 1) {
			// this.wquf1.union(virtualNum, virtualDownNum);
			// this.wquf2.union(virtualNum, virtualDownNum);
			// }

			/********************************************************/
			if (blockState[virtualNum] == 0)
			{
				blockState[virtualNum] = 1;				
			}

			// i != 1 or N, j == 1 : middle left num
			if (j == 1) {
				int virtualRightNum = this.virtualRightNum(i, j);
				int virtualUpNum = this.virtualUpNum(i, j);
				int virtualDownNum = this.virtualDownNum(i, j);
				int rootRightNum = this.wquf1.find(virtualRightNum);
				int rootUpNum = this.wquf1.find(virtualUpNum);
				int rootDownNum = this.wquf1.find(virtualDownNum);
				if ((blockState[rootRightNum] == 3) || (blockState[rootUpNum] == 3) 
						|| (blockState[rootDownNum] == 3)) 
				{
					blockState[virtualNum] = 3;

					if ((blockState[rootUpNum] == 2) || (blockState[rootDownNum] == 2)
							|| (blockState[rootRightNum] == 2)) {
						isPercolate = true;
					}

					if (blockState[rootRightNum] > 0) {
						this.blockState[rootRightNum] = 3;
						this.wquf1.union(rootRightNum, virtualNum);
					}
					if (blockState[rootUpNum] > 0) {
						this.blockState[rootUpNum] = 3;
						this.wquf1.union(rootUpNum, virtualNum);
					}
					if (blockState[rootDownNum] > 0) {
						this.blockState[rootDownNum] = 3;
						this.wquf1.union(rootDownNum, virtualNum);
					}
				} 
				else if ((blockState[rootRightNum] == 2) || (blockState[rootUpNum] == 2)
						|| (blockState[rootDownNum] == 2)) 
				{
					this.blockState[virtualNum] = 2;
					
					if (blockState[rootRightNum] > 0) {
						this.wquf1.union(rootRightNum, virtualNum);
						this.blockState[rootRightNum] = 2;
					}
					if (blockState[rootUpNum] > 0) {
						this.wquf1.union(rootUpNum, virtualNum);
						this.blockState[rootUpNum] = 2;
					}
					if (blockState[rootDownNum] > 0) {
						this.wquf1.union(rootDownNum, virtualNum);
						this.blockState[rootDownNum] = 2;
					}
				} 
				else 
				{
					if (blockState[rootRightNum] > 0) {
						this.wquf1.union(rootRightNum, virtualNum);
					}
					if (blockState[rootUpNum] > 0) {
						this.wquf1.union(rootUpNum, virtualNum);
					}
					if (blockState[rootDownNum] > 0) {
						this.wquf1.union(rootDownNum, virtualNum);
					}
				}
			}
			// i != 1 or N, j == N : middle right num
			else if (j == N) {
				int virtualLeftNum = this.virtualLeftNum(i, j);
				int virtualUpNum = this.virtualUpNum(i, j);
				int virtualDownNum = this.virtualDownNum(i, j);
				int rootLeftNum = this.wquf1.find(virtualLeftNum);
				int rootUpNum = this.wquf1.find(virtualUpNum);
				int rootDownNum = this.wquf1.find(virtualDownNum);
				if ((blockState[rootLeftNum] == 3) || (blockState[rootUpNum] == 3) 
						|| (blockState[rootDownNum] == 3)) 
				{
					blockState[virtualNum] = 3;

					if ((blockState[rootUpNum] == 2) || (blockState[rootDownNum] == 2)
							|| (blockState[rootLeftNum] == 2)) 
					{
						isPercolate = true;
					}

					if (blockState[rootLeftNum] > 0) {
						this.blockState[rootLeftNum] = 3;
						this.wquf1.union(rootLeftNum, virtualNum);
					}
					if (blockState[rootUpNum] > 0) {
						this.blockState[rootUpNum] = 3;
						this.wquf1.union(rootUpNum, virtualNum);
					}
					if (blockState[rootDownNum] > 0) {
						this.blockState[rootDownNum] = 3;
						this.wquf1.union(rootDownNum, virtualNum);
					}
				} 
				else if ((blockState[rootLeftNum] == 2) || (blockState[rootUpNum] == 2)
						|| (blockState[rootDownNum] == 2)) 
				{
					if (blockState[rootLeftNum] > 0) {
						this.blockState[rootLeftNum] = 2;
						this.wquf1.union(rootLeftNum, virtualNum);
					}
					if (blockState[rootUpNum] > 0) {
						this.blockState[rootUpNum] = 2;
						this.wquf1.union(rootUpNum, virtualNum);
					}
					if (blockState[rootDownNum] > 0) {
						this.blockState[rootDownNum] = 2;
						this.wquf1.union(rootDownNum, virtualNum);
					}
				} else {
					if (blockState[rootLeftNum] > 0) {
						this.wquf1.union(rootLeftNum, virtualNum);
					}
					if (blockState[rootUpNum] > 0) {
						this.wquf1.union(rootUpNum, virtualNum);
					}
					if (blockState[rootDownNum] > 0) {
						this.wquf1.union(rootDownNum, virtualNum);
					}
				}
			}
			// i != 1 or N, j != 1 or N : middle middle num
			else {
				int virtualUpNum = this.virtualUpNum(i, j);
				int virtualDownNum = this.virtualDownNum(i, j);
				int virtualLeftNum = this.virtualLeftNum(i, j);
				int virtualRightNum = this.virtualRightNum(i, j);
				int rootUpNum = this.wquf1.find(virtualUpNum);
				int rootDownNum = this.wquf1.find(virtualDownNum);
				int rootLeftNum = this.wquf1.find(virtualLeftNum);
				int rootRightNum = this.wquf1.find(virtualRightNum);

				if ((blockState[rootUpNum] == 3) || (blockState[rootDownNum] == 3) 
						|| (blockState[rootLeftNum] == 3) || (blockState[rootRightNum] == 3)) 
				{
					blockState[virtualNum] = 3;
					if ((blockState[rootUpNum] == 2) || (blockState[rootDownNum] == 2) 
							|| (blockState[rootLeftNum] == 2) || (blockState[rootRightNum] == 2)) {
						isPercolate = true;
					}

					if (blockState[rootUpNum] > 0) {
						blockState[rootUpNum] = 3;
						this.wquf1.union(virtualNum, rootUpNum);
					}
					if (blockState[rootDownNum] > 0) {
						blockState[rootDownNum] = 3;
						this.wquf1.union(virtualNum, rootDownNum);
					}
					if (blockState[rootLeftNum] > 0) {
						blockState[rootLeftNum] = 3;
						this.wquf1.union(virtualNum, rootLeftNum);
					}
					if (blockState[rootRightNum] > 0) {
						blockState[rootRightNum] = 3;
						this.wquf1.union(virtualNum, rootRightNum);
					}
				}

				else if ((blockState[rootUpNum] == 2) || (blockState[rootDownNum] == 2)
						|| (blockState[rootLeftNum] == 2) || (blockState[rootRightNum] == 2)) 
				{
					blockState[virtualNum] = 2;

					if (blockState[rootUpNum] > 0) {
						blockState[rootUpNum] = 2;
						this.wquf1.union(virtualNum, rootUpNum);
					}
					if (blockState[rootDownNum] > 0) {
						blockState[rootDownNum] = 2;
						this.wquf1.union(virtualNum, rootDownNum);
					}
					if (blockState[rootLeftNum] > 0) {
						blockState[rootLeftNum] = 2;
						this.wquf1.union(virtualNum, rootLeftNum);
					}
					if (blockState[rootRightNum] > 0) {
						blockState[rootRightNum] = 2;
						this.wquf1.union(virtualNum, rootRightNum);
					}
				} else {
					if (blockState[rootUpNum] > 0) {
						this.wquf1.union(virtualNum, rootUpNum);
					}
					if (blockState[rootDownNum] > 0) {
						this.wquf1.union(virtualNum, rootDownNum);
					}
					if (blockState[rootLeftNum] > 0) {
						this.wquf1.union(virtualNum, rootLeftNum);
					}
					if (blockState[rootRightNum] > 0) {
						this.wquf1.union(virtualNum, rootRightNum);
					}
				}
			}
			/********************************************************/
		}

		// if (j == 1) {
		// this.blockState[virtualNum] = 1;
		// int virtualRightNum = this.virtualRightNum(i, j);
		// if (this.blockState[virtualRightNum] == 1) {
		// this.wquf1.union(virtualNum, virtualRightNum);
		// }
		// } else if (j == N) {
		// this.blockState[virtualNum] = 1;
		// int virtualLeftNum = this.virtualLeftNum(i, j);
		// if (this.blockState[virtualLeftNum] == 1) {
		// this.wquf1.union(virtualNum, virtualLeftNum);
		// this.wquf2.union(virtualNum, virtualLeftNum);
		// }
		// } else {
		// this.blockState[virtualNum] = 1;
		// int virtualLeftNum = this.virtualLeftNum(i, j);
		// int virtualRightNum = this.virtualRightNum(i, j);
		// if (blockState[virtualLeftNum] == 1) {
		// this.wquf1.union(virtualNum, virtualLeftNum);
		// this.wquf2.union(virtualNum, virtualLeftNum);
		// }
		// if (blockState[virtualRightNum] == 1) {
		// this.wquf1.union(virtualNum, virtualRightNum);
		// this.wquf2.union(virtualNum, virtualRightNum);
		// }
		// }
	}

	public boolean isOpen(int i, int j) {
		this.validateIJ(i, j);
		int virtualNum = this.transformToVirtualArrayNum(i, j);
		if (this.blockState[virtualNum] > 0) {
			return true;
		}
		return false;
	}

	public boolean isFull(int i, int j) {
		this.validateIJ(i, j);
		int virtualNum = this.transformToVirtualArrayNum(i, j);
		int rootNum = this.wquf1.find(virtualNum);
		if (this.blockState[rootNum] == 3) {
			return true;
		}
		return false;
	}

	public boolean percolates() {
		if (isPercolate == true)
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
