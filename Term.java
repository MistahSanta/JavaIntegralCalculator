public class Term implements Comparable<Term>
{
	
	private double coefficient;
	//Hold the fraction 
	private int numerator; 
	private int denominator; 
	
	private int exponent; 
	//Definite integral
	private double defInt; 
	private int divisor;
	public Term(double co, int e, int a, int b)
	{
		//Simplify the numerator and denominator 
		divisor = GCD( (int)co, e);
		numerator = (int) co/divisor;
		denominator = e / divisor;
		

		exponent = e;
		coefficient = co / e; //save as a decimal value
		
		if (denominator < 0)
		{
			denominator *= (-1); //flip the sign
			numerator  *= (-1);
		}
		//Calculate the definite integral 
		defInt = (coefficient * Math.pow(b, exponent)) - (coefficient * Math.pow(a,exponent) );
		
		
	}
	
	
	//Simplifed fraction
	public int GCD(int a, int b)
	{
		int GCD = 1;
		
		//Find highest divisor 
		for (int i = a; i > 1; i--)
		{
			if ( (a % i) == 0 && (b % i) == 0)
			{
				GCD = i;
				break; // exit for loop
			}
		}
		
		return GCD;
	}
	
	
	
	//getters
	public double getCofficient() { return coefficient; }
	public int getExponent() { return exponent; }
	public double getdefInt() { return defInt; }
	public int getNumer() { return numerator * divisor;}
	//setter
	public void addCoefficient(double co)
	{
		
		numerator = numerator * divisor;
		numerator += (int) co;
		
		denominator *= divisor;
	
		coefficient = ( ((double) numerator) / denominator);
		divisor = GCD(numerator, denominator);
		denominator /= divisor;
		numerator /= divisor;
	}
	
	//Check if a number is a whole number
	public boolean isWholeNum(double co)
	{
		boolean flag = false;
		if ( co % 1 == 0)
		{
			return true;
		}
		
		return flag; 
	}
	
	@Override 
	public String toString()
	{
		if ( (coefficient < 0 ) && (!isWholeNum(coefficient) && (exponent > 1 || exponent < 0 ) ))
		{
			return " + (" +  numerator  + "/" + Math.abs(denominator) + ")" + "x^" + exponent;
		} else if ( (!isWholeNum(coefficient) && (exponent > 1 || exponent < 0) ) )
		{
			return " + (" + numerator + "/" + denominator + ")" +"x^" + exponent;
		} else if (coefficient == 0)
		{
			return "0"; // return nothing
		}
		else if (exponent < 1 || exponent > 1)
		{
			//positive coefficient 
			if (coefficient > 0)
			{
				return " + " + ( (int) coefficient) + "x^" + exponent;
			} else
			{
			return " - " +( (int) Math.abs( coefficient ) ) +"x^" + exponent;
			}
		} else if (exponent == 1)
		{
			//Negative coefficient 
			if (coefficient > 0)
			{
				return " + " + (int) coefficient + "x"; 
			} else
			{
				return (int) coefficient + "x"; 
			}
		} 
		else {
			if ( coefficient > 0)
			{
				return " + " + (int) coefficient;
			}
			return " - " + (int) Math.abs(coefficient) ; 
		}
	}
	
	
	@Override
	public int compareTo(Term o)
	{
		return ( o.exponent - this.exponent);  
	}
	
}