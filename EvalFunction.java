
public class EvalFunction {

	//Data
	private char[] board;
	private int width;
	private int height;
	
	//Constructor
	EvalFunction (int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	//Methods
	public int simpleEval( State state) { //This is the basic Evaluation Function that we are asked to implement in Task 3
		int score;
		board = state.getBoard();
		if (whiteWins()) {	
			return 100;	
		} else if (blackWins()) {
			return 0;
		} else {
			score = distanceClosestBlack(board) - distanceClosestWhite(board); 
			//distance of most advanced black pawn to row 1 - distance of most advanced white pawn to row H
			return score;
		}

	}
	
	private int distanceClosestBlack(char[] b) { //Distance from the winning line for the Black piece that is closest
		int indx = 0;
		for (int i = width*height-1; i >= 0 ; i--) { //Would probably be faster if we iterated from left to right keeping count of found Black pieces
			if (b[i] == 'b') {
				indx = i;
				break;
			}
		}
		int scoreb = height - indx/width - 1;
		System.out.println("The distance of the most advanced Black piece to the finish line is " + scoreb);
		return scoreb;
	}
	
	private int distanceClosestWhite(char[] b) { //Distance from the winning line for the White piece that is closest
		int indx = 0;
		for (int i = 0; i < b.length; i++) { //Would be faster if we instead iterated from right to left keeping count of found White pieces!
			if (b[i] == 'w') {
				indx = i;
				break;
			}
		}
		int scorew =  indx/width;
		System.out.println("The distance of the most advanced White piece to the finish line is " + scorew);
		return scorew;
	}
	
	private boolean whiteWins() { //Has White won?
		int winner = 0;
		for (int i = 0; i < width; i++) {
			if (board[i] == 'w') {
				winner++;
			}		
		}
		if (winner > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean blackWins() { //Has Black won?
		int winner = 0;
		for (int i = width*height - 1; i == width*(height-1); i--) { //Very possible mistake with indexes
			if (board[i] == 'w') {
				winner++;
			}		
		}
		if (winner > 0) {
			return true;
		} else {
			return false;
		}
	}
}
