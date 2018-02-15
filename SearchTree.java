import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.*;

public class SearchTree {
	private Node root;
	private String role;
	int width;
	int height; 
	EvalFunction eval;
	
	public SearchTree(Node r, String role, int w, int h) {
		this.role = role;
		root = r;
		width = w;
		height = h;
		eval = new EvalFunction(this.width, this.height);
	}
	
	public String initNegaMax(int depth, Node n, String r) {
		ArrayList<String> moves = new ArrayList<String>();
		String r2;
		int bestValue, score;
		Node child;
		int index = 4; //remember to change this later for other boards
		
		bestValue = Integer.MIN_VALUE;
		if (r.equals("black")) {
			r2 = "white";
		} else {
			r2 = "black";
		}
		moves = n.getState().getLegalMoves(r);
		for (int i = 0; i < moves.size(); i++) {
			//Create an new Node and call NegaMax again!
			child = expand(n, moves.get(i)); 
			//child.getState().printBoard();
			System.out.println("THIS WAS CREATED BY MOVE: " + moves.get(i));
			score = -negaMax(depth - 1, child, r2);
			System.out.println("CHILD SCORE IS: " + score);
			System.out.println("CURRENT BEST SCORE: " + bestValue);
			System.out.println("*****************************************");
			if (score > bestValue) {
				bestValue = score;
				index = i;
			}
			
		}
		return n.getState().getLegalMoves(r).get(index);
	}
	
	public int negaMax(int depth, Node n, String r) {
		ArrayList<String> moves = new ArrayList<String>();
		String r2;
		int bestValue, score;
		Node child;
		
		if (depth <= 0 || n.getState().testIfTerminal()) {
			return 2;
		}
		bestValue = Integer.MIN_VALUE;
		if (r.equals("black")) {
			r2 = "white";
		} else {
			r2 = "black";
		}
		moves = n.getState().getLegalMoves(r);
		for (int i = 0; i < moves.size(); i++) {
			//Create an new Node and call NegaMax again!
			child = expand(n, moves.get(i)); 
			score = -negaMax(depth - 1, child, r2);
			// bestValue = max(score, bestValue);
			if (score > bestValue) {
				bestValue = score;
			}
		}
		return bestValue;
	}
	
	public Node expand(Node currentNode, String currentMove) {

		int[] moveArray = null;
		Matcher m = Pattern.compile("\\(\\s*move\\s+([0-9]+)\\s+([0-9]+)\\s+([0-9]+)\\s+([0-9]+)\\s*\\)", Pattern.DOTALL).matcher(currentMove);
		if (m.find()) {
			moveArray = new int[4];
			for (int i = 0; i<4; i++) {
				moveArray[i] = Integer.parseInt(m.group(i+1)); 
			}
		}
		
		boolean turn = !currentNode.getState().getMyTurn();
		char[] parentBoard = currentNode.getState().getBoard().clone();
		
		int x1 = moveArray[0], y1 = moveArray[1], x2 = moveArray[2], y2 = moveArray[3];
		Node child = new Node(this.role, turn, this.width, this.height, parentBoard, currentNode, x1, y1, x2, y2);
		
		return child;
		
		
		//System.out.println("************************************************************************************");
		//System.out.println("CHILD TURN: " + turn + "\n MOVE FROM: " + x1 + ", " + y1 + " TO: " + x2 + ", " + y2);
		//System.out.println("************************************************************************************");
	}
	
	

}


