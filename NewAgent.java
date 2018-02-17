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
   			node1 = new Node(roleOfLastPlayer, myTurn, width, height, board, null ,x1,y1,x2,y2); 
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
			System.out.println("The value given FOR WHITE by the evaluation function is therefore " + eval.simpleEval(node1.getState()));
   			
	       	System.out.println("   ...Starting Search...   ");
			SearchTree search = new SearchTree(node1, role, width, height);
			nextMove = search.initNegaMax(3, node1, role);
			/*
			legalMoves = node1.getState().getLegalMoves(role);
   			if (legalMoves.isEmpty()) {
   				System.out.println("NO LEGAL MOVES AVILABLE!");
   			}
			// Here we just construct a random move (that will most likely not even be possible),
			// this needs to be replaced with the actual best move.
			
			int x1,y1,x2,y2;
			x1 = random.nextInt(width)+1;
			x2 = x1 + random.nextInt(3)-1;
			if (role.equals("white")) {
				y1 = random.nextInt(height-1);
				y2 = y1 + 1;
			} else {
				y1 = random.nextInt(height-1)+2;
				y2 = y1 - 1;
			}
			return "(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")";
			*/
   			
   			//int i = random.nextInt(legalMoves.size()); //Select a random move from the set of legal moves
   			return nextMove;//legalMoves.get(i);
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


