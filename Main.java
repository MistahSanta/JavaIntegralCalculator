/*
 * Jonathan Le
 * Jxl200056
 */

import java.util.Scanner;
import java.io.*;

public class Main

{
	public static void main(String [] args) throws FileNotFoundException
	{
		Scanner scan = new Scanner(System.in);

		File file;
		//Make sure the correct file is placed
		do 
		{
			String filename = scan.nextLine(); 
			file = new File(filename);
			
		} while ( !( file.exists()) );
		
		scan.close();
		Scanner scnr = new Scanner(file); // work with file 
		
		//Instantiate the binary Tree object
		BinTree<Term> bt = new BinTree<>(); 
		
		
		//Read the file 
		while ( scnr.hasNext() )
		{
			//Local variables for the definite integral 
			boolean definiteIntegral = false;
			int a = 0, b = 0; 
			int counter = 0;

			//Save entire expression in a line 
			String line = scnr.nextLine();
			
			//System.out.println("* " + line);
			
			//Parse the string 
			int index = line.indexOf('|'); // find the index of the integral:
			int spaceIndex = line.indexOf(' ', index);
			double definiteValue = 0;
			if ( (line.charAt(index + 1) != ' ') )
			{
				// Check if this value is a definite integral. In this case, it would be  
				definiteIntegral = true;
				
				// Parse the string for the bounds
				// First check for negative
				spaceIndex = line.indexOf(' ', index); // find the first index of the space
				//Go backwards and find the bounds of the integral - for b 
				for ( int i = spaceIndex - 1; i >= index; i--)
				{
					if ( Character.isDigit( line.charAt(i)))
					{
						int digit = Character.getNumericValue( line.charAt(i) );
						b +=  digit * Math.pow(10, counter);
						counter++;
					} else if (line.charAt(i) == '-')
					{
						b *= (-1);
					}
				}
				// for the bounds of a 
				counter = 0;
				for ( int i = index; i >= 0; i--)
				{
					if ( Character.isDigit( line.charAt(i)))
					{
						int digit = Character.getNumericValue( line.charAt(i) );
						a +=  digit * Math.pow(10, counter);
						counter++;
					} else if ( line.charAt(i) == '-')
					{
						a *= (-1);
					}
				}
				
			}
			line = line.substring(spaceIndex + 1); // remove the integral 

			//Now, need to parse the expression
			while ( !(line.isEmpty() ))
			{
				//local variables;
				int exp = 0;
				double coef = 0; 
				int begIndex = 0;
				int eIndex = 0;
				int endIndex = 0;
				counter = 0;
				int indX = line.indexOf('x'); // find the index of the first x;
				
				//for dx
				if ( (indX >= 1) && line.charAt(indX - 1) == 'd')
				{
					//remove that part
					line = line.substring(0, indX - 1);
					
					if( !(line.isEmpty()))
					{
						//there is a constant inside
						counter = 0;
						for(int i = line.length() - 1; i >=0; i --)
						{
							if (Character.isDigit( line.charAt(i)) )
							{
								//Found coefficient
								int digit = Character.getNumericValue( line.charAt(i));
								coef += digit * Math.pow(10, counter );
								counter++;
							} else if ( line.charAt(i) == '-') 
							{
								coef *= -1;
								break;
							}
								
							
						}
						//remove string
						line = "";
					}
				}
				
				
				
				
				//Find the coefficient 
				int positive = 1;
				if ( !(line.isEmpty()))
				{
					for (int i = indX - 1; i >= 0; i--)
					{
						begIndex = i; // for substring
						if ( Character.isDigit( line.charAt(i) ) )
						{
							int digit = Character.getNumericValue( line.charAt(i));
							//There is a digit 
							coef += digit * Math.pow( 10, (indX - 1) - i);
						} else if ( line.charAt(i) == '-')
						{
							coef = coef * (-1);
							positive = -1;
							break; // leave for loop
						} else if ( line.charAt(i) == '+')
						{
							break; 
						}
					}
				if ( coef == 0)
				{
					coef = 1 * positive;
				}
				//Find the exponent 
				//go to the right side
				int negative  = 1;
				endIndex = findSignIndex(line, indX);
				//May not have a + or - 
				if ( endIndex == -1)
				{
					endIndex = line.indexOf(' ', indX); // find the next space
				}
				
				if ( line.charAt(indX + 1) != '^')
				{
					exp = 1;
					line = line.substring(0, begIndex) + line.substring(endIndex);
				}else
				{
				// There is a carrot hat, so need to find the exponent value 
				if ( line.charAt(indX + 2) == '-')
				{
					negative = -1;
					endIndex =  findSignIndex(line, indX + 3); 
					//May not have a + or - 
					if ( endIndex == -1)
					{
						endIndex = line.indexOf(' ', indX + 3); // find the next space
					}
				
				} 
				
			
				eIndex = endIndex;
				while ( endIndex > indX)
				{
					if (Character.isDigit( line.charAt(endIndex)))
					{
						//found digit 
						int digit = Character.getNumericValue( line.charAt(endIndex) );
						exp += digit * Math.pow(10,  counter);
						counter++;
					} else if ( line.charAt(endIndex) == '^')
					{
						break;
					}
					endIndex--;
				} 
				exp *= negative;
				//Remove that part of the string 
				line = line.substring(0, begIndex) + line.substring(eIndex);
				}
				
				}
				
				//Do the integral of the expression
				if ( coef != 0)
				{
					if ( exp != -1 || coef != 0)
					{
						exp = exp + 1; 
						//do the coef calculation inside term
					} 
					
				
				//Check for expressions with duplicate exponents that will need to add
				//Call the search function in binary tree and see if there is a duplicate exponent 
				Term t = new Term(coef, exp, a, b);
				definiteValue += t.getdefInt(); // calculate the definite integral
				//Create a new node object
				Node<Term> n = new Node<Term>(t); // to be used with the search function
				if ( bt.Search(n, bt.getRoot() ) != null)
				{
					//depulicate  value -- Update the node
					( (bt.Search(n, bt.getRoot() )).getData() ).addCoefficient(t.getNumer() ); // update the coefficient. //exponent stay the same
				} else 
				{
					//Create a new node and save it into the binary tree
					bt.insert(t, bt.getRoot(), bt.getRoot() );
				}
				
				}
			
			}
			//print
			bt.print( bt.getRoot() );
			if (definiteIntegral)
			{
				//Do calculation
				System.out.print(", " + a + "|" + b + " = " + String.format("%.3f", definiteValue) ) ;
			} else
			{
				System.out.print(" + C");
			}
			System.out.println(); // format
			
		
	
	
		}
		scnr.close(); //closes the scanner

	
	}

public static int findSignIndex(String s, int ind)
{
	//Loop through the string to find the first index of '-' or '+'
	for (int i = ind; i < s.length(); i++)
	{
		if ( s.charAt(i) == '-' || s.charAt(i) == '+')
		{
			return i;
		}
	}
	return -1; // null
}




}