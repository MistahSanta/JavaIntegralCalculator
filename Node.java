
public class Node<G extends Comparable<G>> implements Comparable<Node<G>>
{
	public Node<G> right, left; 
	
	private G term; //This variable will hold the object
	
	//Constructor 
	public Node(G g)
	{
		term = g; // set the term 
	}
	//getter
	public G getData()
	{
		return term;
	}
	
	//setter
	public void set(G g)
	{
		term = g;
	}
	
	
	
	@Override
	public int compareTo(Node<G> node)
	{
		return (this.getData()).compareTo(node.getData());
	}
	
	
}