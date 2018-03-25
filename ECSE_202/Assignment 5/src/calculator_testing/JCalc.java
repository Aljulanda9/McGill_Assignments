/*Aljulanda Al Abri*/
package calculator_testing;

import java.util.StringTokenizer;
import acm.program.ConsoleProgram;

public class JCalc extends ConsoleProgram{

	//method to check if string is number
	static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	//method for precedence
	static int Prec(String operator) {
		switch(operator) {
		case "+":
		case "-":
			return 1;
		case "*":
		case "/":
			return 2;
		case "^":
			return 3;
		case "u+":
		case "u-":
			return 4; 
		}
		return -1;
	}

	public boolean program = true;//to run an infinite while loop

	public void run() {
		while(program) {//infinite while loop
			
			Queue outputQ = new Queue();//input queue
			Queue inputQ = new Queue();//output queue
			Stack opsStack = new Stack();//infix to postfix stack
			Stack pfStack = new Stack();//value stack for postfix expression
			boolean unary = true;//for unary operators
			
			//expression to be converted
			String exp = readLine("Enter expression: ");
			//delete spaces from string
			exp = exp.replaceAll("\\s+","");
			//tokenize string
			StringTokenizer expTokens = new StringTokenizer(exp,"+-*()^/",true);

			//input string to input queue
			while (expTokens.hasMoreTokens()) {
				inputQ.Enqueue(expTokens.nextToken());
			}

			//start looping through tokens
			while(!inputQ.isEmpty())	{
				
				//variable to store tokens
				String token = inputQ.dequeue();

				if (isNumeric(token)) {//if number enqueue

					outputQ.Enqueue(token);
					unary = false;//there can't be a unary operator after a number
					
				}else if(token.equals("(")){//if ( add to operators stack

					opsStack.push(token);

				}else if(token.equals(")")) {//if )

					//pops and enqueue until find (
					while (!opsStack.isEmpty() && !opsStack.peek().equals("(")) {
						outputQ.Enqueue(opsStack.pop());

					}
					if(!opsStack.isEmpty() && !opsStack.peek().equals("(")) {

						println("Invalid expression");//invalid expression

					}else { 

						opsStack.pop();

					}
				}else {
					//if unary add u to the token (will be used in evaluation)
					if(unary) {
						token = "u" + token;
						opsStack.push(token);
						
					}else {
						//when operator is found
						while (!opsStack.isEmpty() && Prec(token) <= Prec(opsStack.peek())) {
							outputQ.Enqueue(opsStack.pop());

						}

						opsStack.push(token);
					}
					unary = true;//an operator after an operator is unary
				}
			}

			//pop all operators from stack and enqueue in output queue
			while(!opsStack.isEmpty()) {
				outputQ.Enqueue(opsStack.pop());
			}

			while(!outputQ.isEmpty()) {
				//store tokens as it dequeues
				String tokenEvaluate = outputQ.dequeue();
				
				if(isNumeric(tokenEvaluate)) {
					//if number, push to stack
					pfStack.push(tokenEvaluate);
				}else{
					//if operator
					double firstNumber = Double.parseDouble(pfStack.pop());//pop first number on stack
					double result = 0;//store result of operation of 
					//if unary -
					if(tokenEvaluate.equals("u-")) {
						
						pfStack.push((firstNumber*(-1))+"");
						
					}else if(tokenEvaluate.equals("u+")) {
						//if unary + 
						pfStack.push(firstNumber+"");
						
					}else {
						//all other operators
						double secondNumber = Double.parseDouble(pfStack.pop());//pop second number on stack
						if(tokenEvaluate.equals("+")) {
							result = firstNumber + secondNumber;
						}else if(tokenEvaluate.equals("-")) {
							result = secondNumber - firstNumber;
						}else if(tokenEvaluate.equals("/")) {
							result = secondNumber / firstNumber;
						}else if(tokenEvaluate.equals("*")){
							result = firstNumber*secondNumber;
						}else {
							result = Math.pow(secondNumber, firstNumber);
						}
						pfStack.push(result+"");
					}
				}
			}
			//print the result
			println(exp + " = " + pfStack.pop());
		}
	}
}
