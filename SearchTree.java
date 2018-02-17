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
		int index = 4; //Remember to change this later for other boards
		
		bestValue = Integer.MIN_VALUE;
		if (r.equals("black")) {
			r2 = "white";
		} else {
			r2 = "black";
		}
		moves = n.getState().getLegalMoves(r);
		for (int i = 0; i < moves.size(); i++) {
			//Create an new Node and call NegaMax again!
			int sum = i + 1; //TODO delete this
			System.out.println("		OPTION NUMBER " + sum);
			child = expand(n, moves.get(i)); 
			//child.getState().printBoard();
			System.out.println("       Take the move: " + moves.get(i));
			score = -negaMax(depth - 1, child, r2);
			System.out.println("IF WE TOOK THIS OPTION, THE SCORE WOULD BE: " + score);
			System.out.println("CURRENT BEST SCORE: " + bestValue);
			System.out.println("  *****************************************");
			System.out.println("  *****************************************");
			if (score > bestValue) { //We'll always take the biggest value
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
			System.out.println(" THE BOARD OF THIS LEAF NODE IS:");
			n.getState().printBoard();
			if (r.equals("black")) {
				return -eval.simpleEval(n.getState());
			} else { 
				return eval.simpleEval(n.getState());
			}	
		}
		bestValue = Integer.MIN_VALUE;
		if (r.equals("black")) {
			r2 = "white";
		} else { 
			r2 = "black";
		}
		System.out.println(" ...then, the options of " + r + " are the following:");
		moves = n.getState().getLegalMoves(r);
		System.out.println(" So there are " + moves.size() + " avilable moves for " + r + " in total");
		for (int i = 0; i < moves.size(); i++) {
			System.out.println("OPTION " + i);
			//Create an new Node and call NegaMax again!
			child = expand(n, moves.get(i)); 
			score = -negaMax(depth - 1, child, r2);
			// bestValue = max(score, bestValue);
			if (score > bestValue) {
				bestValue = score;
			}
			System.out.println(" For " + r + " the obtained score was " + score);
			System.out.println("         *************************");
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
		String player;
		if (turn) {
			player = this.role;
		} else {
			if (role.equals("black")) {
				player = "white";
			} else {
				player = "black";
			}	
		}
		int x1 = moveArray[0], y1 = moveArray[1], x2 = moveArray[2], y2 = moveArray[3];
		Node child = new Node(player, turn, this.width, this.height, parentBoard, currentNode, x1, y1, x2, y2);
		
		return child;
	}
	
	

}
