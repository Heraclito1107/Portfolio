/**
 * Validator of sudoku's solutions
 * reads from a 9 line txt file
 * and returns a sentence indicating if the solution is correct or not.
 *
 * @author Heraclito
 * @version 1.6
 * 
 */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>


/* this data is shared by the thread(s) */
bool isValid = true;

void *runner(void *param); /*the thread */

int main()
{
pthread_t tid[27]; /* the thread identifier */
pthread_attr_t attr; /* set of attributes for the thread */
int sudoku[9][9];
int row1[9], row2[9], row3[9], row4[9], row5[9], row6[9], row7[9], row8[9], row9[9];
int col1[9], col2[9], col3[9], col4[9], col5[9], col6[9], col7[9], col8[9], col9[9];
int quad1[9], quad2[9], quad3[9], quad4[9], quad5[9], quad6[9], quad7[9], quad8[9], quad9[9];
int *rows[9] = {row1, row2, row3, row4, row5, row6, row7, row8, row9};
int *cols[9] = {col1, col2, col3, col4, col5, col6, col7, col8, col9};
int *quads[9] = {quad1, quad2, quad3, quad4, quad5, quad6, quad7, quad8, quad9};
FILE *file;

//if (argc != 2) {
//	fprintf(stderr,"usage: a.out <string source file name>\n");
	/*exit(1);*/
//	return -1;
//}

file = fopen("sudoku.txt", "r");
int i, j;
for (i = 0; i < 9; i++)
{
	for (j = 0; j < 9; j++)
	{
		sudoku[i][j] = fgetc(file) - 48;
	}
	int n = fgetc(file);
}

/* get the default attributes */
pthread_attr_init(&attr);

for (i = 0; i < 9; i++)
{	
	
	for (j = 0; j < 9; j++)
	{
		rows[i][j] = sudoku[i][j];
		cols[i][j] = sudoku[j][i];
	}
	/* create the thread */
	pthread_create(&tid[i],&attr,runner,(void*)rows[i]);
	pthread_create(&tid[i+9],&attr,runner,(void*)cols[i]);

}

for (i = 0; i < 9; i++)
{
	for (j = 0; j < 9; j++)
	{
		
		quads[i][j] = sudoku[((j/3)+((i%3)*3))][(j%3)+((i/3)*3)];
	}
	
}

for (i = 18; i < 27; i++)
{
	pthread_create(&tid[i],&attr,runner,(void*)quads[i-18]);
}

/* now wait for the thread to exit */
for (i = 0; i < 27; i++)
	pthread_join(tid[i],NULL);

printf("%s\n", isValid ? "The solution is correct!" : "Incorrect solution!");
}

/**
 * The thread will begin control in this function
 */
void *runner(void *param) 
{
int *collection = (int *)param;
int i, j;
for(i = 0; i < 9; i++)
{
	for(j = i+1; j < 9;j++)
	{
		if(collection[i] == collection[j])
			isValid = false;
	}
}
	pthread_exit(0);
}
