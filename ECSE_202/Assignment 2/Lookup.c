//Aljulanda Al Abri 260739353

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//template code
#define MAXRECORDS 100
#define MAXNAMELENGTH 15

//Define structure to hold student data (template code)
struct StudentRecord
{
	char FirstNames[MAXNAMELENGTH];
	char LastNames[MAXNAMELENGTH];
	int IDNums;
	int Marks;
};

int binarySearch(struct StudentRecord *parr[], int l, int r, char* x){//binary search function
	while (l < r){
		int m = l + (r-l)/2; //finds midpoint

		if (strcmp(x, (*parr[m]).LastNames) == 0){//if the searched name happens to be in midpoint
			return m;
		}
		else if (strcmp(x, (*parr[m]).LastNames) < 0){//if searched name is below midpoint, function will only search lower half
			r = m ;
		}
		else //if searched name is above midpoint, function will only search upper half
			l = m +1;
	}
	return -1;//if not found
}
void swap(struct StudentRecord *parr[],int arg1, int arg2){//swap function
	struct StudentRecord *temp;
	temp = parr[arg1];
	parr[arg1] = parr[arg2];
	parr[arg2] = temp;

}

int partition(struct StudentRecord *parr[], int low, int high){//partition function

	int i = (low - 1);//sets i to -1 because low is zero

	for (int j = low; j <= high - 1; j++){//partition elements less than high to go before 'high' and higher elements go after 'high'

		if(strcmp((*parr[j]).LastNames, (*parr[high]).LastNames)<=0){
			i++;
			swap(parr, i, j);
		}
	}
	swap(parr, i+1, high);//puts pivot in correct position
	return (i + 1);
}

void quicksort(struct StudentRecord *parr[], int low, int high){//quicksort function
	if (low<high){

		int p = partition(parr, low, high); //partition index

		quicksort(parr, low, p-1);//sort elements below pivot
		quicksort(parr, p+1, high);//sort elements above pivot
	}
}

int main(int argc, char * argv[]) {
	//template code
	struct StudentRecord SRecords[MAXRECORDS];
    	int numrecords, nummarks;


	//Read in Names and ID data
	FILE * NamesIDsDataFile;
	if((NamesIDsDataFile = fopen(argv[1], "r")) == NULL){
		printf("Can't read from file %s\n", argv[1]);
		exit(1);
	}

	numrecords=0;
    	while (fscanf(NamesIDsDataFile,"%s%s%d",&(SRecords[numrecords].FirstNames[0]),
		      				&(SRecords[numrecords].LastNames[0]),
		      				&(SRecords[numrecords].IDNums)) != EOF) {
	  numrecords++;
 	}

	fclose(NamesIDsDataFile);

	//Read in marks data
	FILE * MarksDataFile;
	if((MarksDataFile = fopen(argv[2], "r")) == NULL){
		printf("Can't read from file %s\n", argv[2]);
		exit(1);
	}
	nummarks=0;
	while(fscanf(MarksDataFile,"%d",&(SRecords[nummarks].Marks)) != EOF) {
	    nummarks++;
	}

	fclose(MarksDataFile);

	//if user doesn't input a name to be searched
	if(argc == 3){
		printf("Wrong number of arguments\n");
	}

	//if all 4 arguments are there
	if(argc == 4){

		struct StudentRecord *parr[numrecords]; //the struct that will be used in the functions

		for (int i=0; i < numrecords; i++){//copies from SRecords to parr
			parr[i] = &SRecords[i];
		}
		quicksort(parr,0, numrecords-1);//quicksort function

		int result = binarySearch(parr, 0, numrecords, argv[3]);//binary search function

		//print results if found
		(result == -1)? printf("No record found for student with last name %s\n", argv[3]) : printf("The following record was found:\nName: %s %s\nStudent ID: %d\nStudent Grade: %d\n", (*parr[result]).FirstNames, (*parr[result]).LastNames, (*parr[result]).IDNums, (*parr[result]).Marks);

	}


	return EXIT_SUCCESS;
}
