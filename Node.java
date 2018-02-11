
public class Node {
	private State state;
	private Node parent;
	// private Node children;
	
	//Constructor #1
	public Node(String role, int w, int h) { //Root Node
		state = new State(w,h); //We are NOT using 'role', at least for now
	}
	
	public Node(String role, boolean t, int w, int h, char[] b, int x1, int y1, int x2, int y2) { //Every other Node
		state = new State(t,w,h,b);
		state.updateState(role, x1, y1, x2, y2);
		
	}
	
	//Methods
	private boolean terminalTest() { //Method that will check if a Node is a Terminal Node. TO BE DISCUSSED
		//return state.isTerminal()
		return false;
	}
	
	public State getState() {
		return state; //Careful with this, maybe we'll need to clone instead
	}
 }
