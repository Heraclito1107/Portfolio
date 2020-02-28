#include<iostream>
#include<stdlib.h>
#include<time.h>
#include<fstream>

//Mariana Ramirez, Daniel Heraclito Perez

using namespace std;

int main()
{
	int dado1, dado2, suma;
	int frecuencia[11] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	float proporcionReal;
	float proporcionIdeal[11] = {(1/36.0), (2/36.0), (3/36.0), (4/36.0), (5/36.0), (6/36.0), (5/36.0), (4/36.0), (3/36.0), (2/36.0), (1/36.0)};
	ofstream salida;
	srand (time(NULL));
	for (int i = 0; i < 36000; i++)
	{
		dado1 = rand() % 6 + 1;
		dado2 = rand() % 6 + 1;
		suma = dado1 + dado2;
		
		for (int j = 0; j < 11; j++)
		{
			if (suma == (j+2))
			{
				frecuencia[j]++;
			}
		}	
	}
	
	salida.open ("frecuencias.txt");
	
	for (int i = 0; i < 11; i++)
	{
		salida << "Frecuencia " << i+2 << ": " << frecuencia[i] << endl;
		proporcionReal = frecuencia[i] /36000.0;
		salida << "		Proporcion real: " << proporcionReal << endl;
		salida << "		Proporcion ideal: " << proporcionIdeal[i] << endl;
		if ((proporcionReal > (proporcionIdeal[i] - 0.05)) && (proporcionReal < (proporcionIdeal[i] + 0.05)))
		{
			salida << "Proporcion dentro de parametros razonables" << endl;
		}
		salida << endl;
	}
	
	salida.close();
	
}
