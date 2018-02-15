import java.util.ArrayList;
import java.util.Arrays;

public class State {

	//Data
	boolean myTurn;
	private int width;
	private int height;
	int size;
	char[] board;
	int whitePawns;
	int blackPawns;
	boolean isTerminal;
	
	//Constructor
	public State(int w, int h) { //Initial State
		width = w;
		height = h;
		size = width*height;
		whitePawns = width*2; //Missing in the Second Constructor unless information is ported somehow.
		blackPawns = width*2;  //For now it only returns 0,0 or -1 if a piece has been eater
		board = new char[size];
		initState(); //Puts the pieces in the starting position
		printBoard();
		isTerminal = false;
	}
	
	public State(boolean myT, int w, int h, char[] b) { //Second Constructor
		myTurn = myT;
		width = w;
		height = h;
		size = width*height;
		board = b;
		isTerminal = false;

	}
	
	//Methods
	private void initState() { //Method that initializes the first State
		
		Arrays.fill(board, 'e');
		
		for (int i = 0; i < width; i++) {
			board[i] = 'b';
			board[i+width] = 'b';
			board[i+width*(height-2)] = 'w';
			board[i+width*(height-1)] = 'w';	
		}
	}
		
	public void updateState( String role, int x1, int y1, int x2, int y2) { //Update Board of the State, using the move that created it as input
		char color;
		int indx1 = width*(height-y1)+x1-1; //Transform coordinates to index in our array
		int indx2 = width*(height-y2)+x2-1;
		
		if (role == "black") {
			color = 'b';
		} else {
			color = 'w';
		}
		if (board[indx2] != 'e') {
			if (color == 'b') {
				// TODO Subtract number of white pawns
				whitePawns--;
			} else {
				// TODO Subtract number of black pawns
				blackPawns--;
			}

		}
		board[indx1] = 'e';
		board[indx2] = color;
		
	}
	
	public ArrayList<String> getLegalMoves(String role) { //Returns an ArrayList of Strings containing all legal moves
		char color;
		int match = 0;
		int x1;
		int y1;
		int x2;
		int y2;
		ArrayList<String> legalMoves = new ArrayList<String>();
		legalMoves.clear();
		if (role == "black") { //TODO There is a bug and does not work well when the agent is the Black player!
			color = 'b';
		} else {
			color = 'w';
		}
		for (int i = 0; i < board.length; i++) {
			if (board[i] == color) {
				match++;
				if (freeAhead(i,color)) {
					//TODO Add a move to the Legal Moves list: move ahead
					x1 = (i+1) % width;
					y1 = height - i/width;		
					if (x1 == 0) { //Workaround to getting 0 from the remainder-less division
						x1 = width;
					}
					x2 = x1; //Since the move is ahead
					if (color == 'b') {
						y2 = y1 - 1;
					} else {
						y2 = y1 + 1;
					}
					
					legalMoves.add("(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")");
					System.out.println("The " + role + " piece at " + x1 + ", " + y1 + " has a free tile ahead at " + x2 + ", " + y2);
				}
				if (	(i+1)%width != 1 && enemyInLeftd(i,color)) { //If it's not in the leftmost column and has an enemy in the Left Diagonal
					x1 = (i+1) % width;
					y1 = height - i/width;	
					if (x1 == 0) { //Workaround to getting 0 from the remainder-less division
						x1 = width;
					}
					if (color == 'b') {
						x2 = x1-1;
						y2 = y1-1;
					} else {
						x2 = x1-1;
						y2 = y1+1;
					}
					legalMoves.add("(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")");
					System.out.println("The " + role + " piece at " + x1 + ", " + y1 + " has an enemy piece at the Left Diagonal at " + x2 + ", " + y2);
				}
				if ((i+1)%width != 0 && enemyInRightd(i,color)) { //If it's not in the rightmost column and has an enemy in the Right Diagonal
					x1 = (i+1) % width;
					y1 = height - i/width;
					if (x1 == 0) { //Workaround to getting 0 from the remainder-less division
						x1 = width;
					}
					if (color == 'b') {
						x2 = x1+1;
						y2 = y1-1;
					} else {
						x2 = x1+1;
						y2 = y1+1;
					}
					legalMoves.add("(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")");
					System.out.println("The " + role + " piece at " + x1 + ", " + y1 + " has an enemy piece at the Right Diagonal at " + x2 + ", " + y2);
				}
			}
		}
		if(legalMoves.isEmpty()) {
			isTerminal = true;
		}
		System.out.println("There are " + match + " " + role + " pieces in total");
		return legalMoves;
	}
	
	private boolean freeAhead(int i, char color) { //Checks if the tile "ahead" (depending on color) is free
		boolean out = false;
		if (color == 'b') {
			if (board[i+width] == 'e') {
				out = true;
			}
		} else {
			if (board[i-width] == 'e') {
				out = true;
			}
		}
		return out;
	}
	
	private boolean enemyInLeftd( int i, char color) { //Checks if there is an enemy in the Left Diagonal (depending on color).
		boolean out = false;
		if (color == 'b') {
			if (board[i+width-1] == 'w') {
				out = true;
			}
		} else {
			if (board[i-(width+1)] == 'b') {
				out = true;			
			}
		}
		return out;
	}
	
	private boolean enemyInRightd( int i, char color) { //Checks if there is an enemy in the Right Diagonal (depending on color).
		boolean out = false;
		if (color == 'b') {
			if (board[i+width+1] == 'w') {
				out = true;
			}
		} else {
			if (board[i-(width-1)] == 'b') {
				out = true;			
			}
		}
		return out;
	}
	
	public boolean testIfTerminal() {
		for (int i = 0; i < width; i++) {
			if ((board[i] == 'w') || (board[i + size - width] == 'b')) {
				isTerminal = true;
			}
		}
		
		return isTerminal;
	}
	
	public boolean getMyTurn() {
		return myTurn;
	}
	
	public char[] getBoard() {
		return board.clone();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	
	public void printBoard() {
		//Code that prints the board in this State
		for (int i = 0; i < height; i++) {
			System.out.println(Arrays.copyOfRange(board, width*i, width*i + width));	
		}
		System.out.println(whitePawns + " White Pawns and " + blackPawns + " Black Pawns");
	}
	
	
}
