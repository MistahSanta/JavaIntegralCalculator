public class BinTree<G extends Comparable<G>> 
{
	Node<G> root = null;
	int counter = 0;

	
	
	//getter 
	public Node<G> getRoot() { return root; }
	
	
	public Node<G> insertHelper(Node<G> newNode, Node<G> cur, Node<G> parent)
	{
		//Break case
		if ( cur == null) 
			{
				//Insert the node and check which way the parent is 
				if ( (parent).compareTo(newNode) < 0) 
				{
					parent.left = newNode;
				} else 
				{
					parent.right = newNode;
				}
				
				return cur;
			}
			else
			{
			int num = (cur).compareTo(newNode);
			parent = cur; // update parent
			if ( num < 0)
			{
				cur = cur.left; 
			} else if ( num >= 0)
			{
				cur = cur.right;
			}
			
			}
			
		return insertHelper(newNode, cur, parent);
	}
	public void insert(G g, Node<G> cur, Node<G> parent)
	{
		//Create a node inside and inside inside binary tree
		Node<G> node = new Node<G>(g); 
		
		if ( cur == null)
		{
			//list is empty. Insert the new Node;
			root = node; // insert the node
			
		}
		else 
		{
			insertHelper(node, cur, parent);
		}
		
	}
	
	public Node<G> SearchHelper( Node <G> find, Node<G> cur)
	{

		
		return SearchHelper(find, cur);
		
	}
	
	public Node<G> Search( Node<G> find, Node<G> cur)
	{
		if ( cur == null)
		{
			//No element 
			return null;
		} else if ( find.compareTo(cur) == 0)
		{
			//Value is found
			return cur; 
		} else if ( find.compareTo(cur) < 0 )
		{
			cur = cur.right;
		} else if ( find.compareTo(cur) > 0)
		{
			cur = cur.left;
		}
		return Search(find, cur); // Keep calling function recursively until value is found or cur == null
	}
	
	//Remove
	public Node<G> Remove(Node<G> node)
	{
		return RemoveHelper(root , node );
	}
	
	public Node<G> RemoveHelper(Node<G> cur, Node<G> del)
	{
		if ( cur == null)
		{
			return null; // empty binery tree
		}
		//Trasversal 
		if ( cur.compareTo(del) < 0)
		{
			cur.left = RemoveHelper(cur.left, del);
		} else if ( cur.compareTo(del) > 0)
		{
			cur.right = RemoveHelper(cur.right, del);
		} else 
		{
			//Found the desired node
			if ( cur.left == null && cur.right == null)
			{
				//Check if the current node is a root node in the binary tree
				if ( cur == root)
				{
					root = null; // set root equals to null
				}
				else 
				{
					cur = null; 
				}
			} else if (cur.right != null)
			{
				cur.set( findSuccessor(cur.right) );
				cur.right = RemoveHelper(cur.right, cur);
			} else 
			{
				cur.set( findSuccessor( cur.left) );
				cur.left = RemoveHelper(cur.left, cur);
			}
		
		}
		
		return cur;
	}
	public G findPredecessor(Node<G> c)
	{
		while (c.right != null)
		{
			c = c.right;
		}
		//on the last node's data
		return c.getData();
	}
	public G findSuccessor(Node<G> c) 
	{
		while (c.left != null)
		{
			c = c.left;
		}
		//on the last node's data
		return c.getData();
	}
	
	
	public void printHelper(Node<G> cur)
	{
		//Base case 
		if (cur == null)
		{
			return;
		} else 
		{
			//go right 
			printHelper(cur.right);
			//print 
			if ( counter == 0)
			{
				//print without the addition and subtraction sign
				String s = cur.getData().toString(); 
				System.out.print( removeSign(s) );
				counter++;
			}
			else
			{
				System.out.print( remove1((cur.getData()).toString())); //Use toString function in term to print entire term; 
			}
			//remove the this node
			
			printHelper(cur.left);
			
		}
		Remove(cur);
	}
	public String remove1(String s)
	{
		if (s == "0")
		{
			return s;
		}
	
		int xIndex;
		//remove the 1 next to the x
		xIndex = s.indexOf('x');
		if ( s.charAt(xIndex - 1) == '1')
		{
			if ( xIndex >= 2 && Character.isDigit( s.charAt(xIndex - 2)))
			{
				// do nothing
			} else 
			{
				s =  s.substring(0, xIndex - 1) + s.substring( xIndex);
			}
			
		}
		//Move the negative inside the parenthesis outside
		int parIndex = s.indexOf('(');
		if ( (parIndex != -1 ) && s.charAt(parIndex + 1)  == '-')
		{
			//There is a negative sign, so we need to move it outside 
			s = s.substring(0,parIndex + 1) + s.substring(parIndex + 2);
			for (int i = parIndex - 1; i >= 0; i--)
			{
				if (s.charAt(i) == '+')
				{
					s = s.replaceFirst("\\+", "-");
					break;
				} 
				if ( s.charAt(i) == '-')
				{
					s = s.replaceFirst("-", "+");
					break;
				}
				
			}
		}
		
		return s;
	}
	
	public String removeSign(String s )
	{
		//Remove the sign at the beginning of the expression
		int subIndex = s.indexOf('-');
		int addIndex = s.indexOf('+');
		int index;

		if ( subIndex != -1 && addIndex != -1)
		{
			index = Math.min(subIndex , addIndex); //return the index of the closest sign 
		} else 
		{
			index = Math.max(subIndex, addIndex);
		}

		if (s == "0")
		{
			return s;
		}
	/*
		int xIndex;
		//remove the 1 next to the x
		xIndex = s.indexOf('x');
		if ( s.charAt(xIndex - 1) == '1')
		{
			if ( xIndex >= 2 && Character.isDigit( s.charAt(xIndex - 2)))
			{
				// do nothing
			} else 
			{
				s =  s.substring(0, xIndex - 1) + s.substring( xIndex);
			}
			
		}
		*/
		
		//Check if there is a parenthesis on the right side 
		if (s.charAt(index + 1) == '(')
		{
			return s.substring(index + 1);
		} else if ( s.charAt(index + 1 ) == ' ')
		{ 
			return s.substring(index + 2); // remove the spaces 
		}
		return s;
	}
	
	public void  print(Node<G> cur)
	{
		counter = 0;
		printHelper(cur);
	
	}
	
}