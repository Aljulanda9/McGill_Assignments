/*Aljulanda Al Abri*/
package calculator_testing;

import java.util.StringTokenizer;
import acm.program.ConsoleProgram;

public class In2pJ extends ConsoleProgram{

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
public boolean exit = true;
	public void run() {
		while(exit) {
		//two queues for input and output, and stack for operators
		Queue outputQ = new Queue();
		Queue inputQ = new Queue();
		Stack opsStack = new Stack();
		boolean unary = true;
		//expression to be converted
		String exp = readLine("Enter String: ");
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
			
			String token = inputQ.dequeue();
			
			if (isNumeric(token)) {//if number enqueue
				outputQ.Enqueue(token);
				unary = false;
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
				unary = true; 
			}
		}

		//pop all operators from stack and enqueue in output queue
		while(!opsStack.isEmpty()) {
			outputQ.Enqueue(opsStack.pop());
		}

		print("Postfix: ");
		while(!outputQ.isEmpty()) {//prints final queue
			print(outputQ.dequeue() + " ");
		}
		println();
	}
	}
}
