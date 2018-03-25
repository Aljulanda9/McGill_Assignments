package calculator_testing;

//Aljulanda Al Abri
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.StringTokenizer;
import acm.gui.TableLayout;
import acm.program.Program;

public class GCalc extends Program implements ChangeListener, ActionListener{

	/**
	 *  declare fields
	 */
	
	String prefix = "";
	String result = "";
	JTextField input = new JTextField(""); // input text field is empty
	JTextField output = new JTextField(""); // output text field is empty
	JTextField prec_tf = new JTextField(""); // precision text field is empty for now
	JSlider prec_slider;
	
	public void init()
	{
	
		//size of window
		setSize(500, 500);
		
		//creates TableLayout
		setLayout(new TableLayout(8, 4));

		output.setEditable(false);
		output.setBackground(Color.WHITE);

		// add input and output with constraints
		add(input,"gridwidth = 4 height = 25");
		add(output,"gridwidth = 4 height = 25");

		String BUTTON_SIZE = "70";
		String button_label[]= {"C","(",")","/","7","8","9","*","4","5","6","-","1","2","3","+","0",".","^","="};

		String constraint = "width=" + BUTTON_SIZE + " height=" + BUTTON_SIZE;

		
		//add buttons to calculator
		for (int i = 0; i < button_label.length; i++) {

			JButton cur_button = new JButton(button_label[i]);
			cur_button.setFont(new Font("Arial", Font.PLAIN, 20));
			cur_button.addActionListener(this);

			add(cur_button, constraint);
		}

		//Label
		add(new JLabel("Precision"));

		//Slider
		int default_val = 6;
		String default_val_str = default_val + "";
		prec_slider = new JSlider(0,10,default_val);
		//prec_slider
		prec_slider.addChangeListener(this);
		add(prec_slider,"gridwidth = 2");

		//Precision text field
		prec_tf.setText(default_val_str);
		prec_tf.setEditable(false);
		prec_tf.setBackground(Color.WHITE);
		add(prec_tf);

	}


	@Override
	public void stateChanged(ChangeEvent arg0) {
		
		//System.out.println("Slider changes");
		int prec_slider_value = prec_slider.getValue();
		prec_tf.setText(prec_slider_value+"");

		//change precision as slider moves
		String cutOut = "";
		cutOut = result;
		
		Double truncate = Double.parseDouble(cutOut);
		//use BigDecimal class to change precision to prec_slider_value
		//NOTE: RoundingMode is HALF_EVEN
		
		Double precTruncate = BigDecimal.valueOf(truncate)
				.setScale(prec_slider_value, RoundingMode.HALF_EVEN).doubleValue();
		
		//printout the result
		output.setText(precTruncate+"");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e.getActionCommand());
		// 

		if(e.getActionCommand()=="=")
		{
			//calculator is now a method below
			result = calculator(prefix);
			
			//set default precision to 4
			Double toPrint = Double.parseDouble(result); 
			Double finalToPrint = BigDecimal.valueOf(toPrint)
				    .setScale(6, RoundingMode.HALF_EVEN)
				    .doubleValue();

			//printout result
			output.setText(finalToPrint +"");
		}else if(e.getActionCommand()=="C"){
			//empty input, output and prefix
			input.setText("");
			prefix = "";
			output.setText("");
		}else{
			//adds tokens to prefix as pressed in the calculator
			prefix += e.getActionCommand();
			input.setText(prefix);
		}
	}

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

	public static String calculator(String exp) {
		
		Queue outputQ = new Queue();//input queue
		Queue inputQ = new Queue();//output queue
		Stack opsStack = new Stack();//infix to postfix stack
		Stack pfStack = new Stack();//value stack for postfix expression
		boolean unary = true;//for unary operators

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

					return "";

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
		String finalResult = pfStack.pop();

		return finalResult; 
	}

}
