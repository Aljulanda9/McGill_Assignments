package ass2;

/*
Aljulanda Al Abri
 */

import java.util.Stack;
import java.util.ArrayList;

public class Expression  {
	private ArrayList<String> tempList;
	private ArrayList<String> tokenList;
	//  Constructor    
	/**
	 * The constructor takes in an expression as a string
	 * and tokenizes it (breaks it up into meaningful units)
	 * These tokens are then stored in an array list 'tokenList'.
	 */

	Expression(String expressionString) throws IllegalArgumentException{
		tempList = new ArrayList<String>();
		
		//ADD YOUR CODE BELOW HERE
		//REMEMEBR TO ADD [ ] TO THIS CODE
		String arr[] = expressionString.split("");
		
		for(int i=0; i<arr.length;i++) {
			tempList.add(arr[i]);
		}
		String tokenizedString = ""; 
		for(int i = 0; i<tempList.size(); i++) {
			if(tempList.get(i).equals(" ")) {//deletes spaces
				continue;
			}else if(!isInteger(tempList.get(i))){//adds operators
				if(tempList.get(i).equals("+")){//adds increments
					if(tempList.get(i-1).equals("+")) {
						tokenizedString = tokenizedString.substring(0, tokenizedString.length()-2);
						tokenizedString += "++ ";
					}else {
						tokenizedString+="+ ";
					}
				}else if(tempList.get(i).equals("-")){//adds decrements
						if(tempList.get(i-1).equals("-")) {
							tokenizedString = tokenizedString.substring(0, tokenizedString.length()-2);
							tokenizedString += "-- ";
						}else {
							tokenizedString+="- ";
						}
				}else {//adds all other operators other than ++ --
					tokenizedString+=tempList.get(i) + " ";
				}
			
			}else {
				if(isInteger(tempList.get(i-1))) {//adds integers, if more than one digit
					tokenizedString = tokenizedString.substring(0, tokenizedString.length()-2);
					tokenizedString += tempList.get(i-1) + tempList.get(i) + " "; 
				}else {//if only one digit
					tokenizedString+=tempList.get(i) + " ";
				}
			}
		}
		
		tokenList = new ArrayList<String>();
		
		String tempStr[] = tokenizedString.split(" ");//splits string into an array, then add it to an ArrayList
		for(int i =0; i<tempStr.length; i++) {
			tokenList.add(tempStr[i]);
		}
		
		
		//ADD YOUR CODE ABOVE HERE
	}

	/**
	 * This method evaluates the expression and returns the value of the expression
	 * Evaluation is done using 2 stack ADTs, operatorStack to store operators
	 * and valueStack to store values and intermediate results.
	 * - You must fill in code to evaluate an expression using 2 stacks
	 */
	public Integer eval(){
		
		//ADD YOUR CODE BELOW HERE

		Stack<String> ops = new Stack<String>();
		Stack<Integer> vals = new Stack<Integer>();
	
		for (int i = 0; i<tokenList.size(); i++) {
			if(tokenList.get(i).equals("(") || tokenList.get(i).equals("[")) {//skips ( and [
				continue;
			}else if(tokenList.get(i).equals("+") || tokenList.get(i).equals("-") || tokenList.get(i).equals("*") || tokenList.get(i).equals("/") || tokenList.get(i).equals("++") || tokenList.get(i).equals("--")) {//adds operators to stack except ]
				ops.push(tokenList.get(i));
			}else if(tokenList.get(i).equals(")")){//performs operations
				String op = ops.pop();
				Integer v = vals.pop();
				if(op.equals("+")) {
					v += vals.pop();
				}else if(op.equals("-")) {
					v = vals.pop() - v;
				}else if(op.equals("*")) {
					v *= vals.pop();
				}else if(op.equals("/")) {
					v = vals.pop() / v;
				}else if(op.equals("++")) {
					v = v+1;
				}else if(op.equals("--")) {
					v = v-1;
				}
				vals.push(v);
			}else if(tokenList.get(i).equals("]")) {//gets absolute value if ] is found
				if(vals.peek()>0) {
					continue;
				}
				Integer x = vals.pop();
				x = x *(-1);
				vals.push(x);
			}else {//adds integers to vals stack
				vals.push(Integer.parseInt(tokenList.get(i)));
			}
		}

		return vals.pop();
		
		//ADD YOUR CODE ABOVE HERE

		
	}

	//Helper methods
	/**
	 * Helper method to test if a string is an integer
	 * Returns true for strings of integers like "456"
	 * and false for string of non-integers like "+"
	 * - DO NOT EDIT THIS METHOD
	 */
	private boolean isInteger(String element){
		try{
			Integer.valueOf(element);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}

	/**
	 * Method to help print out the expression stored as a list in tokenList.
	 * - DO NOT EDIT THIS METHOD    
	 */

	@Override
	public String toString(){	
		String s = new String(); 
		for (String t : tokenList )
			s = s + "~"+  t;
		return s;		
	}

}

