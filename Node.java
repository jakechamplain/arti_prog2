import java.util.ArrayList;

public class Node {
	private State state;
	private Node parent;
	private ArrayList<Node> children;
	
	//Constructor #1
	public Node(String role, int w, int h) { //Root Node
		state = new State(w,h); //We are NOT using 'role', at least for now
		parent = null;
		children = new ArrayList<Node>();
	}
	
	public Node(String role, boolean t, int w, int h, char[] b, Node p, int x1, int y1, int x2, int y2) { //Every other Node
		state = new State(t,w,h,b);
		state.updateState(role, x1, y1, x2, y2);
		parent = p;
		children = new ArrayList<Node>();
	}
	
	//Methods
	
	
	public State getState() {
		return new State(state.getMyTurn(), state.getHeight(), state.getHeight(), state.getBoard()); //Careful with this, maybe we'll need to clone instead
	}
 }
