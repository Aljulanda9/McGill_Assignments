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
	struct StudentRecord* left;
	struct StudentRecord* right;

};


void insert_new_node(struct StudentRecord** root, struct StudentRecord SRecords)
{//function to insert nodes to the binary tree


	if((*root) == NULL)
	{//adds the first node
		struct StudentRecord* cur_root = (struct StudentRecord*)malloc(sizeof(struct StudentRecord));

		*cur_root = SRecords;
		*root = cur_root;
		(*root)->left = 0;
		(*root)->right = 0;


	}else if(strcasecmp(SRecords.LastNames, (*root)->LastNames)<0)
	{//if new node is less than the other one, it goes left
		insert_new_node(&((*root)->left),SRecords);
	}else
	{//if new node is more than the other one, it goes right
		insert_new_node(&((*root)->right),SRecords);
	}
}

void traverse(struct StudentRecord *root,struct StudentRecord *parr[],int *counter){

		if(root !=0){//traverse left head right

		traverse(root->left,parr,&(*counter));

		*counter +=1;
		 parr[*counter]= &(*root);


		 traverse(root->right,parr,&(*counter));
		}

}

int binarySearch(struct StudentRecord **parr, int l, int r, char* x){//binary search function
	while (l < r){
		int m = l + (r-l)/2; //finds midpoint

		if (strcasecmp(x, parr[m]->LastNames) == 0){//if the searched name happens to be in midpoint
			return m;
		}
		else if (strcasecmp(x, parr[m]->LastNames) < 0){//if searched name is below midpoint, function will only search lower half
			r = m ;
		}
		else //if searched name is above midpoint, function will only search upper half
			l = m +1;
	}
	return -1;//if not found
}

int main(int argc, char * argv[]) {

	struct StudentRecord SRecords;
	struct StudentRecord* NRecords = NULL;
	int numrecords = 0;
	int counter = 0;

	//Read in Names and ID data
	FILE * NamesIDsDataFile;
		if((NamesIDsDataFile = fopen(argv[1], "r")) == NULL){
			printf("Can't read from file %s\n", argv[1]);
			exit(1);
		}

	//Read in marks data
	FILE * MarksDataFile;
		if((MarksDataFile = fopen(argv[2], "r")) == NULL){
			printf("Can't read from file %s\n", argv[2]);
			exit(1);
		}

	//read two files simultaneously
    	while (fscanf(NamesIDsDataFile,"%s%s%d",&(SRecords.FirstNames[0]),
    			&(SRecords.LastNames[0]), &(SRecords.IDNums)) != EOF && fscanf(MarksDataFile,"%d",&(SRecords.Marks)) != EOF) {
            numrecords++;
            insert_new_node(&NRecords,SRecords);
 	}

	//close files
	fclose(NamesIDsDataFile);
	fclose(MarksDataFile);

	if(argc == 3 && argc !=4){//if wrong number of args
	  		printf("Wrong number of arguments\n");
	  	}

 struct StudentRecord **parr=(struct StudentRecord **)malloc(numrecords*sizeof(struct StudentRecord **));//creates a new pointer pointer array

 traverse(NRecords,parr,&counter);

  int  result= binarySearch(parr,0, numrecords ,argv[3]); //assign the result of binary function

  //prints the result
  (result == -1)? printf("No record found for student with last name %s\n", argv[3]) : printf("The following record was found:\nName: %s %s\nStudent ID: %d\nStudent Grade: %d\n", parr[result]->FirstNames, parr[result]->LastNames, parr[result]->IDNums, parr[result]->Marks);

}


