import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;

public class Solver {
	private Board board;
	// private Board twinBoard;
	private boolean solvable = false;
	// private MinPQ<SearchNode> preSolution;
	private int step = -1;
	private Stack<Board> solution = null;
	// private HashSet<SearchNode> solutionSet;

	public Solver(Board initial) {
		// TODO Auto-generated constructor stub
		this.board = initial;
		Board twinBoard = initial.twin();
		int step = 0;

		MinPQ<SearchNode> boardSolution = new MinPQ<>();
		MinPQ<SearchNode> twinSolution = new MinPQ<>();
		HashSet<SearchNode> searchSet = new HashSet<SearchNode>();
		HashSet<SearchNode> twinSearchSet = new HashSet<SearchNode>();

		Board nowB = this.board;
		SearchNode nowS = new SearchNode(nowB, step, null);
		searchSet.add(nowS);
		Board twinNowB = twinBoard;
		SearchNode twinNowS = new SearchNode(twinNowB, step, null);
		twinSearchSet.add(twinNowS);
		// System.out.println(nowB.toString());

		while ((!nowB.isGoal()) && (!twinNowB.isGoal())) {
			// System.out.println("-------------------------");
			// step++;
			Iterable<Board> nowBNext = nowB.neighbors();
			for (Board b : nowBNext) {
				// System.out.println(b.toString());
				SearchNode newSN = new SearchNode(b, nowS.step + 1, nowS);
				if ((!searchSet.contains(newSN))) {
					boardSolution.insert(newSN);
					searchSet.add(newSN);
				}
			}

			Iterable<Board> twinNowBNext = twinNowB.neighbors();
			for (Board b : twinNowBNext) {
				SearchNode twinNewSN = new SearchNode(b, twinNowS.step + 1, twinNowS);
				if ((!twinSearchSet.contains(twinNewSN))) {
					twinSolution.insert(twinNewSN);
					twinSearchSet.add(twinNewSN);
				}
			}

			nowS = boardSolution.delMin();
			twinNowS = twinSolution.delMin();

			nowB = nowS.getBoard();
			twinNowB = twinNowS.getBoard();
		}
		if (nowB.isGoal()) {
			this.solvable = true;
			step = 0;
			solution = new Stack<>();
			while (nowS.getPreviousNode() != null) {
				solution.push(nowS.getBoard());
				step++;
				nowS = nowS.getPreviousNode();
			}
			solution.push(nowS.getBoard());
		}
		this.step = step;
	}

	public boolean isSolvable() {
		return this.solvable;
	}

	public int moves() {
		return step;
	}

	public Iterable<Board> solution() {
		return this.solution;
	}

	private class SearchNode implements Comparable<SearchNode> {
		private Board board;
		private int step;
		private int manhattan;
		private SearchNode previousNode;

		SearchNode(Board board, int step, SearchNode previous) {
			// TODO Auto-generated constructor stub
			this.board = board;
			this.step = step;
			previousNode = previous;
			this.manhattan = board.manhattan();
		}

		public int getPriority() {
			return this.manhattan+this.step;
		}

		public Board getBoard() {
			return this.board;
		}

		@Override
		public int compareTo(SearchNode node) {
			// TODO Auto-generated method stub
			if (this.getPriority() > node.getPriority()) {
				return 1;
			} else if (this.getPriority() < node.getPriority()) {
				return -1;
			} else {
				return 0;
			}
		}

		public SearchNode getPreviousNode() {
			return previousNode;
		}

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			SearchNode node = (SearchNode) obj;
			if (this.board.equals(node.board)) {
				return true;
			}
			return false;
		}

		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return this.manhattan;
		}
	}

	public static void main(String[] args) {

		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution()) {
				StdOut.println(board);
			}
		}
	}
}
