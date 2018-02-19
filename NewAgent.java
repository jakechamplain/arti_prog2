import java.util.ArrayList;
import java.util.Random;

public class NewAgent implements Agent {

	private Random random = new Random();

	private String role; // the name of this agent's role (white or black)
	private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
	private boolean myTurn; // whether it is this agent's turn or not
	private int width, height; // dimensions of the board
	
	//Test
	Node node0;
	Node node1;
	ArrayList<String> legalMoves;
	EvalFunction eval;
	int counter = 0;
	char[] board;
	String nextMove;

	
	/*
		init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "white" or "black" and playclock is the number of seconds after which nextAction must return.
	*/
    public void init(String role, int width, int height, int playclock) {
		this.role = role;
		this.playclock = playclock;
		myTurn = !role.equals("white"); //True if 'role' is "Black"
		this.width = width;
		this.height = height;
		
		// TODO: add your own initialization code here
		eval = new EvalFunction(this.width,this.height);
		node0 = new Node(this.role, this.width, this.height);
		board = node0.getState().getBoard();
		
    }

	// lastMove is null the first time nextAction gets called (in the initial state)
    // otherwise it contains the coordinates x1,y1,x2,y2 of the move that the last player did
    public String nextAction(int[] lastMove) {
    	if (lastMove != null) {
    		int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
    		String roleOfLastPlayer;
    		if (myTurn && role.equals("white") || !myTurn && role.equals("black")) {
    			roleOfLastPlayer = "white";
    		} else {
    			roleOfLastPlayer = "black";
    		}
   			System.out.println(" " + roleOfLastPlayer + " moved from " + x1 + "," + y1 + " to " + x2 + "," + y2);
   			
    		// TODO: 1. update your internal world model according to the action that was just executed	
   			if (counter > 0) { //In the first turn "board" will come from 'node0' instead
   	   			board = node1.getState().getBoard();
   			}
   			counter++;
   			node1 = new Node(roleOfLastPlayer, myTurn, width, height, board, node1 ,x1,y1,x2,y2); 
   	   	   	System.out.println("Resulting Board:");
   			node1.getState().printBoard();
 
    	} else { //WARNING Spaghetti code
    		node1 = node0; //Used so that the code after this runs even if "lastMove" is 'null'.
    	}
		
    	// update turn (above that line it myTurn is still for the previous state)
    	   	System.out.println("*****************************************");
       	System.out.println("   NEW TURN   ");
    	   	System.out.println("*****************************************");
    	   	

    	
		myTurn = !myTurn;
		if (myTurn) {
			// TODO: 2. run alpha-beta search to determine the best move
    		long startTime = 0;
    		long estimatedTime = 0;
    		long totalTime = 0;
    		
			startTime = System.currentTimeMillis();
	       	System.out.println("   ...Starting Search...   ");
	       	int depth = 1;
	       	String bestYet;
			SearchTree search = new SearchTree(node1, role, width, height, startTime, playclock);


			
			try {
				while (true) {
					depth++;
					//bestYet = search.initNegaMax(depth, node1, role);
					bestYet = search.initAlphaBeta(depth, node1, role, Integer.MIN_VALUE, Integer.MAX_VALUE);
					nextMove = bestYet;
					estimatedTime = System.currentTimeMillis() - startTime;
					System.out.println(" 'Estimated Time' of this loop is " + estimatedTime + "miliseconds");
					totalTime = totalTime + estimatedTime;
					if (depth > 100) { //Just in case something goes wrong
						throw new customException();
					}
					}
				
			} catch (customException e){
				System.out.println("EXCEPTION THROWN AND VALUE RETURNED");
				System.out.println("The solution we used needed " + totalTime + " miliseconds to be found");
				System.out.println("and reached depth " + depth);
				System.out.println("The move deemed to be optimal is: " + nextMove);
				return nextMove;
			}

		} else {
			return "noop";
		}
	}

	// is called when the game is over or the match is aborted
	@Override
	public void cleanup() {
		// TODO: cleanup so that the agent is ready for the next match
	}

}


