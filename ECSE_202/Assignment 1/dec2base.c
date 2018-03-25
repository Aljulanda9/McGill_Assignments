/* Aljulanda Al Abri */

#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]){

	/* array of all characters needed*/
	char characters[36] = {'0', '1', '2', '3', '4', '5', '6', '7','8', '9',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'Z'};

	/*define variables*/
	long int input;
	int output[64], Base, index=0;

	/*case when argc is 1 or not (2&3)*/
	if (argc != 3 && argc!= 2){
		printf("wrong number of arguments\n");
		return(0);
	}

	/*case when argc is 2. So that if user only enters "number to be converted", the program will convert the number to base-2*/
	if ( argc == 2){
		Base = 2;
	}

	/* takes input (number to be converted) from user*/
	sscanf(argv[1],"%ld", &input);

	/* new temp variable to store the value of input to be printed. If not stored in a new variable, the program will print 0 as input will be 0 at the end of the while loop*/
	long int temp = input;

	/*case when argc is 3. Stores the third string in Base*/
	if(argc == 3){
		sscanf(argv[2],"%d", &Base);
	}

	/* algorithm to convert input to desired base. first finds remainder, then quotient then increment the counter (index) */
	while( input != 0 ){
		output[index] = input % Base;
		input = input / Base;
		++index;
	}

	/*decrement index because it's one digit larger than the number of digits in output (converted number). if not decremented, c will assign an arbitrary value to the additional index in the array of output*/
	--index;

	/*prints array with characters of the output where each character is an element of the array output[]. Print backwards because the last element of array output[] has to be on the most left of the printed number*/

	printf("The Base-%d form of %ld is: ", Base, temp);
	for ( ; index>=0; index--){
		printf("%c", characters[output[index]]);
	}
	printf("\n");

}
