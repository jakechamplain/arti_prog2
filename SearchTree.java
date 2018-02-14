import java.util.ArrayList;

public class SearchTree {
	private Node root;
	private String role;
	eval = new EvalFunction(this.width,this.height);
	
	public SearchTree(Node r, String role) {
		this.role = role;
		root = r;
	}
	
	public int negaMax(int depth, Node n, String r) {
		ArrayList<String> moves = new ArrayList<String>();
		String r2;
		if (n.getState().testIfTerminal()) {
			return 2;
		}
		int bestValue = Integer.MIN_VALUE;
		if (r.equals("black")) {
			r2 = "white";
		} else {
			r2 = "black";
		}
		moves = n.getState().getLegalMoves(r2);
		for (int i = 0; i < moves.size(); i++) {
			//Create an new Node and call MiniMax again!
		}
		
	}
}