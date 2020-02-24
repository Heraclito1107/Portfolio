#include <iostream>
#include <math.h>
#include <cstdlib>
#include <ctime>

using namespace std;

int det(int **m, int b);
int main()
{
	
	int **mat = NULL;
	mat = (int **)malloc(sizeof(int*)*8);
	for (int i = 0; i < 8; i++)
	{
		mat[i] = (int *)malloc(sizeof(int)*8);
	}
	srand(time(0));
	
	int d;
	do
	{
		for (int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				mat[i][j] = rand()%2;
			}
		}
		d = det(mat, 8);
		cout << d << endl;
	}while(d%2 == 0);
	cout << endl << "======RESULTADO=====\n\n" << "DET: " << d << endl; 
	for (int i = 0; i < 8; i++)
	{
		for(int j = 0; j < 8; j++)
		{
			cout << mat[i][j] << ",";
		}
		cout << "\b\n";
	}
	
}




int det(int **m, int b){
        int determinante = 0, aux = 0;

        int c;

        if(b==2)

                return m[0][0]*m[1][1] - m[1][0]*m[0][1];

        else{

                for(int j=0; j<b; j++){

                        int **menor = (int **)malloc(sizeof(int*)*(b-1));

                        for(int h=0; h<(b-1); h++) menor[h] = (int *)malloc(sizeof(int)*(b-1));

                        for(int k=1; k<b; k++){

                                c = 0;

                                for(int l=0; l<b; l++){

                                        if(l!=j){

                                                menor[k-1][c] = m[k][l];

                                                c++;

                                        }

                                }

                        }

                        aux = pow(-1, 2+j)*m[0][j]*det(menor, b-1);

                        determinante += aux;

                        for(int q = 0; q<(b-1); q++)

                                free(menor[q]);

                        free(menor);

                }

                return determinante;

        }

}
