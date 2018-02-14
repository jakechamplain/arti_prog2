

public class SearchTree {
	private Node root;
	private String role;
	//private int bestValue;
	
	public SearchTree(Node r, String role) {
		this.role = role;
		root = r;
	}
	
	public int negaMax(Node n) {
		if (n.getState().testIfTerminal()) {
			return n.getState().whitePawns;
		}
		int bestValue = Integer.MIN_VALUE;
		n.getState().getLegalMoves(role);
		
	}
}