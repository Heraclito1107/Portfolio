#include <iostream>

using namespace std;

struct Estudiante
{
	int numLista;
	string nombre;
	float nota;
};

int notaMayor(Estudiante estudiantes[], int tamano);

int main()
{
	int numEstudiantes, indexNotaMayor;
	bool numListaOk, estudianteNuevo, notaOk;
	
	cout << "Ingrese el numero de estudiantes registrados: ";
	cin >> numEstudiantes;

	
	int indexEstudiante[numEstudiantes];	
	Estudiante estudiantes[numEstudiantes];
	
	for (int i = 0; i < numEstudiantes; i++)
	{
		do
		{
			numListaOk = true;
			estudianteNuevo = true;
			cout << "Ingrese el numero de lista: ";
			cin >> indexEstudiante[i];
			
			if ((indexEstudiante[i] < 1) || (indexEstudiante[i] > numEstudiantes))
			{
				cout << "El numero que ingreso esta fuera del rango, intente de nuevo por favor." << endl;
				numListaOk = false;
			}
			
			if (numListaOk)
			{
				for (int j = 0; j < numEstudiantes; j++)
				{
					if (i != j)
					{
						if (indexEstudiante[i] == indexEstudiante[j])
						{
							estudianteNuevo = false;
							j = numEstudiantes;
							cout << "Ese numero de lista ya esta asigado a otro estudiante, intente de nuevo por favor." << endl;
						}
					}
				}
			}
		} while ((!estudianteNuevo) || (!numListaOk));
		
		estudiantes[(indexEstudiante[i] - 1)].numLista = indexEstudiante[i];
		cout << "Escriba el nombre del alumno: ";
		cin >> estudiantes[(indexEstudiante[i] - 1)].nombre;
		
		do
		{
			notaOk = true;
			cout << "Ingrese la nota obtenida (0.0 - 10.0): ";
			cin >> estudiantes[(indexEstudiante[i] - 1)].nota;
			
			if((estudiantes[(indexEstudiante[i] - 1)].nota < 0.0) || (estudiantes[(indexEstudiante[i] - 1)].nota > 10.0))
			{
				cout << "La nota ingresada esta fuera del rango. Intente de nuevo" << endl;
				notaOk = false;
			}
		} while (!notaOk);	
	}
	
	indexNotaMayor = notaMayor(estudiantes, numEstudiantes);
	
	cout << "El estudiante que obtuvo la mayor nota fue: " << endl;
	cout << estudiantes[indexNotaMayor].numLista << "." << estudiantes[indexNotaMayor].nombre << " con una calificacion de: " << estudiantes[indexNotaMayor].nota <<endl;
}

int notaMayor(Estudiante estudiantes[], int tamano)
{
	int index = 0;
	float nota = 0.0;
	
	for (int i = 0; i < tamano; i++)
	{
		if (nota < estudiantes[i].nota)
		{
			nota = estudiantes[i].nota;
			index = i;
		}
	}
	
	return index;
}
