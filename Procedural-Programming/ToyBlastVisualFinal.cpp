// ToyBlastVisual.cpp: define el punto de entrada de la aplicación de consola.
//

//#include "stdafx.h"
#include <iostream>
#include <string>
#include <cstdlib>
#include <cstdio>
#include <ctime>
#include <Windows.h>

using namespace std;

//Colores numeros:
// 1 = azul
// 2 = verde
// 3 = aqua
// 4 = rojo
// 5 = fucsia
// 6 = amarillo
// 0 = negro

int initialBoard[10][10]; //Matriz de enteros de 10x10
int board[10][10]; //Matriz de enteros 10x10 
int destroyedBlocksCounter[10]; //Arreglo de enteros con 10 espacios
HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE); //Declara los colores como constantes
int colors[6]; // arreglo para contar la cantidad de bloques de cda color
int lvlIndex; //Indice de los niveles
int destroyedBalls = 0;

struct level // Esta estrucutra contiene lo necesario para crear niveles
{
	int height; //Alto del nivel
	int width; //Ancho del nivel
	int movements; //Movimientos permitidos
	bool passed; //Si ya pasaste el nivel
	int holeNum; //Numero de hoyos para dar forma a los tableros
	int cBlocks[6];
	int holes[20][2]; //Coordenadas de los hoyos
	int toys[4][2]; // Coordenadas de los juguetes 
};

//Inicializacion del arreglo de niveles
level levels[5] = { { 6, 9, 15, false,  0, {10,0,0,4,0,0}},
					{ 9, 9, 30, false, 16, {0,12,0,0,25,12},{0,0,
											1,0,
											0,1,
											1,1,
											7,0,
											8,0,
											7,1,
											8,1,
											0,7,
											0,8,
											1,7,
											1,8,
											7,7,
											8,8,
											8,7,
											7,8}},
					{ 9, 7, 20, false,  9, {0,0,0,0,0,0}, { 0,0,
															2,0,
															3,0,
															4,0,
															6,0,
															3,1,
															3,2,
															3,3,
															3,4} ,{ 1,0,5,0, 10,10,10,10 }},
					{ 9, 9, 30, false, 10, {0,0,0,0,0,0},  {1,0,
															3,0,
															5,0,
															7,0,
															0,4,
															8,4} ,{ 0,0,2,0,6,0,8,0 }},
					{ 9, 9, 35, false, 20, {0,0,0,0,0,0},  {0,0,
															0,1,
															0,2,
															0,3,
															1,0,
															1,1,
															1,2,
															2,0,
															2,1,
															3,0,
															5,0,
															6,0,
															6,1,
															7,0,
															7,1,
															7,2,
															8,0,
															8,1,
															8,2,
															8,3} ,{ 0,0,0,0,0,0,0,0 }}};

//Declaracion de funciones
bool destroyableBlock(int, int);
bool goalAchieved();
bool isColored(int);
bool isHole(int, int);
int blockCreator();
int colorCheck(int);
int printMenu();
void blockDestroyer(int, int);
void collapseBlocks();
void createBoard();
void printBoard();
void updateInitialBoard();
void surroundingBlocksCheck(int, int);
bool isToy(int, int);
bool isBall(int, int);
void surroundingBalls(int, int);


int main()
{
	int x = 0, y = 0, cont = 0, currentLvl = 0, tries = 0;

	srand(time(NULL));
	//	createBoard();
	//	printBoard();

	lvlIndex = printMenu(); //Almacena en nivel que escogio el usuario en la variable.
	
	createBoard(); //Crea el tablero
	system("cls");
	tries = levels[lvlIndex].movements; //Almacena los movimientos permitidos del nivel en una variable de intentos
	do
	{
		if (lvlIndex != 5)  //Si el indice es diferente a 5
		{
			printBoard(); //Te imprime el tablero
			//	for (int i = 0; i < 10; i++)
			for (int i = 0; i < levels[lvlIndex].height; i++) //Te recorre la altura del nivel
			{
				destroyedBlocksCounter[i] = 0; //Inicializa todo en 0
			}

			do
			{
				cout << "Coordenada x: "; 
				cin.get();
				cin >> y; //Leer la coordenada de x del usuario
				cout << "Coordenada y: ";
				cin.get();
				cin >> x; //Leer la coordenada y del usuario

				if ((isHole(x, y)) || (!isColored(board[x][y])) || (!destroyableBlock(x, y))) //Si en un hoyo vuelve a pedir las coordenadas
				{
					cout << " Coordenada invalida, intentalo de nuevo." << endl;
				}
			} while ((isHole(x, y)) || (!isColored(board[x][y])));


			tries-= 1; //Intentos va decrementando

			if (destroyableBlock(x, y)) //Si el bloque se puede destruir
			{
				blockDestroyer(x, y); //Destruye el bloque
			}

			printBoard(); //Te imprime el tablero modificado
			cout << "___________________________________________________" << endl;
			collapseBlocks(); //Baja los bloques que quedan volando
			printBoard(); //Te vuelve a imprimir el tablero con la modificacion de bloques
			cout << "Movimientos restantes: " << tries << endl;
			levels[lvlIndex].passed = goalAchieved();
			system("pause");
			system("cls");
			if (levels[lvlIndex].passed) { //Si se consigue la meta entonces te manda al menu
				cout << "Felicidades, pasaste al siguiente nivel" << endl;
				lvlIndex = printMenu();
				tries = lvlIndex;

			}
		}

	} while (tries > 0);//mientras tus intentos sigan siendo mayores a 0;
	if(tries  == 0)
	cout << "Nivel no logrado" << endl;//Si no, te avisa que no pasaste de nivel
	system("pause");

	return 0; //Regresa el control a la maquina
}

void createBoard()
{
	if (lvlIndex == 4)
	{
		for (int i = 0; i < 5; i++) //Recorre columnas
		{
			for (int j = 0; j < levels[lvlIndex].width; j++) //Recorre las filas
			{
				if (isHole(i, j)) //Si es un hoyo
				{
					initialBoard[i][j] = 7; //Te crea un cuadrito negro
				}
				if ((isToy(i, j)) && (lvlIndex > 1) && (lvlIndex < 4)) //Si es un mono y el nivel es mayor a uno y menor a 5
				{
					initialBoard[i][j] = 8; //Te imprime de otro color
				}
				if ((!isHole(i, j)) && (!isToy(i, j))) //Si no es hoyo ni mono 
				{
					initialBoard[i][j] = blockCreator();//Te crea un bloque te color
				}

				board[i][j] = initialBoard[i][j]; //Almacena el tablero inicial en el tablero 
			}//for
		}//for

		for (int i = 5; i < levels[lvlIndex].height; i++)
		{
			for (int j = 0; j < levels[lvlIndex].width; j++)
			{
				board[i][j] = 9;
				initialBoard[i][j] = board[i][j];
			}
		}
	}
	else
	{
		for (int i = 0; i < levels[lvlIndex].height; i++) //Recorre columnas
		{
			for (int j = 0; j < levels[lvlIndex].width; j++) //Recorre las filas
			{
				if ((isHole(i, j)) && (lvlIndex !=0)) //Si es un hoyo
				{
					initialBoard[i][j] = 7; //Te crea un cuadrito negro
				}
				else if ((isToy(i, j)) && (lvlIndex > 1) && (lvlIndex < 4)) //Si es un mono y el nivel es mayor a uno y menor a 5
				{
					initialBoard[i][j] = 8; //Te imprime de otro color
				}
				else //Si no es hoyo ni mono 
				{
					initialBoard[i][j] = blockCreator();//Te crea un bloque te color
				}

				board[i][j] = initialBoard[i][j]; //Almacena el tablero inicial en el tablero 
			}//for
		}//for
	}
	

}

void printBoard()
{

	cout << "   0 1 2 3 4 5 6 7 8" << endl; //Imprime las coordenadas en x
	for (int i = 0; i < levels[lvlIndex].height; i++) //Recorre las columnas
	{
		SetConsoleTextAttribute(hConsole, 15); //Da formato
		cout << i << " "; //Imprime las coordenadas en y
		for (int j = 0; j < levels[lvlIndex].width; j++) //Recorre las filas
		{
			if (j % levels[lvlIndex].width == (levels[lvlIndex].width-1)) //* 9 = (levels[lvlIndex].height)-1 //Da formato 
			{
				if (isBall(i, j))
				{
					SetConsoleTextAttribute(hConsole, 240); //Da formato
					cout << " O" << endl; //Te imprime una T para señalar que es un juguete
				}
				else if ((isToy(i, j)) && (lvlIndex < 4) && (lvlIndex > 1)) //Si es un juguete y el nivel es menor a 4 y mayor a 1
				{
					SetConsoleTextAttribute(hConsole, 15); //Da formato
					cout << " T" << endl; //Te imprime una T para señalar que es un juguete
				}
				else
				{
					SetConsoleTextAttribute(hConsole, colorCheck(board[i][j])); //Imprime la t del mismo color que el cuadrito
					cout << " T" << endl;
				}		
			} //if
			else //Sino
			{
				if (isBall(i, j))
				{
					SetConsoleTextAttribute(hConsole, 240); //Da formato
					cout << " O"; //Te imprime una T para señalar que es un juguete
				}
				else if ((isToy(i, j)) && (lvlIndex < 4) && (lvlIndex > 1)) // Si es un jueguete y el nivel es 
				{
					SetConsoleTextAttribute(hConsole, 15);
					cout << " T";
				}
				else
				{
					SetConsoleTextAttribute(hConsole, colorCheck(board[i][j]));
					cout << " T";
				}
				
			}
		}
	}
	SetConsoleTextAttribute(hConsole, 15);
}

int blockCreator()
{
	int block; //Variable local
	block = rand() % 6 + 1; //Crea un numero random entre 1 y 6

	return block; //Te regresa en random
}

void surroundingBlocksCheck(int xCoord, int yCoord)
{
	int currentBlock = initialBoard[xCoord][yCoord]; //Bloque actual = Elemento de la matriz en esa coordenada

	if ((xCoord > 0) && ((initialBoard[xCoord - 1][yCoord] == currentBlock))) //Va revisando que si los bloques hacia la izquierda son iguales al bloque que escogio el usuario
	{
		if (board[xCoord - 1][yCoord] != 0) //Si las coordenadas en x son diferentes a 0
			blockDestroyer(xCoord - 1, yCoord); //Destruye el bloque
	}
	if ((yCoord > 0) && ((initialBoard[xCoord][yCoord - 1] == currentBlock))) //Va revisando que si los bloques de abajo son iguales al bloque que escogio el usuario
	{
		if (board[xCoord][yCoord - 1] != 0) //Si las coordenadas en y son differentes a 0
			blockDestroyer(xCoord, yCoord - 1); //Destruye el bloque
	}
	if ((yCoord < (levels[lvlIndex].height) - 1) && ((initialBoard[xCoord][yCoord + 1] == currentBlock))) //Revisa si los bloques hacia arriba son iguales al bloque que escogio el usuario
	{
		if (board[xCoord][yCoord + 1] != 0) //Si las coordenadas en y son differentes a 0
			blockDestroyer(xCoord, yCoord + 1); //Destruye el bloque
	}
	if ((xCoord < (levels[lvlIndex].height) - 1) && ((initialBoard[xCoord + 1][yCoord] == currentBlock))) //Revisa si los bloques hacia la derecha son iguales al bloque que escogio el usuario
	{
		if (board[xCoord + 1][yCoord] != 0) //Si las coordenadas en x son differentes a 0
			blockDestroyer(xCoord + 1, yCoord); //Destruye el bloque
	}
}

bool destroyableBlock(int xCoord, int yCoord)
{
	int currentBlock = board[xCoord][yCoord]; //Almacena las coordenadas en el bloque que estamos revisando

	if (((currentBlock > 0) && (currentBlock < 7)) && 
		(((xCoord > 0) && (board[xCoord - 1][yCoord] == currentBlock)) ||
		((yCoord > 0) && (board[xCoord][yCoord - 1] == currentBlock)) ||
			((yCoord < (levels[lvlIndex].height) - 1) && (board[xCoord][yCoord + 1] == currentBlock)) || 
			((xCoord < (levels[lvlIndex].height) - 1) && (board[xCoord + 1][yCoord] == currentBlock))))  //Si el bloque se puede destruir porque es del mismo color del que escogio el usiario entonces regresa verdadero
	{
		return true;
	}
	else //Sino regresa falso
	{
		return false;
	}
}

void blockDestroyer(int xCoord, int yCoord)
{
	if (board[xCoord][yCoord] > 0) //Si las coordenadas del tablero son mayores a 0
	{
		colors[board[xCoord][yCoord] - 1]++;
		board[xCoord][yCoord] = 0; //Ponerlas a 0
		destroyedBlocksCounter[yCoord]++;//Aumenta los bloques destruidos
		if (!isBall(xCoord, yCoord))
		{
			surroundingBalls(xCoord, yCoord);
		}
		
	}
	if (!isBall(xCoord, yCoord))
	{
		surroundingBlocksCheck(xCoord, yCoord); //Revisa los bloques de alrededor
		
	}
}

void collapseBlocks()
{
	for (int i = 0; i < levels[lvlIndex].width; i++) //Recorre las filas
	{
		for (int j = (levels[lvlIndex].height - 1); j > 0; j--) //Recorre de abajo hacia arriba las columnas
		{
			for (int k = j; k > 0; k--) //Comienza a revisar desde abajo
			{
				if ((isColored(board[k - 1][i])) && (board[j][i] == 0)) //Si el tablero tiene color y es 0
				{
					if (isToy(k-1, i)) //Si es un juguete
					{
						for (int l = 0; l < 4; l++) //Recorre ek arreglo
						{
							if ((levels[lvlIndex].toys[l][0] == i) && (levels[lvlIndex].toys[l][1] == k - 1))
							{
								levels[lvlIndex].toys[l][1] = j;
							}
						}
					}
					board[j][i] = board[k - 1][i];
					board[k - 1][i] = 0;
				}
			}	
		}
	}

	printBoard(); //Imprime el tablero
	cout << "____________________________________________" << endl;

	for (int i = 0; i < levels[lvlIndex].width; i++) //Recorre las filas
	{
		for (int j = 0; j < levels[lvlIndex].height; j++)//Recorre las columnas
		{
			if (isColored(initialBoard[j][i])) //Si tiene colo
			{
				for (int k = 0; k < destroyedBlocksCounter[i]; k++) //Recorre cuantos bloques fueron detruidos
				{
					board[j + k][i] = blockCreator();//Crea nuevos vloques
				}
				j = levels[lvlIndex].height;
			}
		}
	}
	updateInitialBoard();//Actualiza el tablero incial
}

void updateInitialBoard()
{
	for (int i = 0; i < 10; i++)
	{
		destroyedBlocksCounter[i] = 0;
	}
	for (int i = 0; i < levels[lvlIndex].height; i++) //Recorre las columnas
	{
		for (int j = 0; j < levels[lvlIndex].width; j++) //Recorre las filas
		{
			initialBoard[i][j] = board[i][j]; //Iguala el tablero con el tablero inicial
		}
	}
}

int colorCheck(int printableBlock)
{
	int colorKey = 0; //Inicua con el color en 0
	if (isColored(printableBlock)) //Si tiene color
	{
		colorKey = (printableBlock + 8) * 17; //Almacena en la clave de color
	}
	return colorKey; //Regresa esa clave de color
}

bool goalAchieved() {
	//Level 1: 4 rojos y 10 azules
	//Level 2: 12 verdes, 25 morados, 12 amarillos
	//level 3: 1 carrito, 1 cohete
	//Level 4: 1 carrito, 1 cohete, 1 compu, 1 profe
	//level 5: 36 pelotas

	switch (lvlIndex)
	{
	case 0:
		cout << "Cuadros Azules destruidos: " << colors[0] << " de 10" << endl;
		cout << "Cuadros Rojos destruidos: " << colors[3] << " de 4" << endl;
		if ((colors[0] >= 10) && (colors[3] >= 4))
			return true;
		else
			return false;
		break;
	case 1:
		cout << "Cuadros Verdes destruidos: " << colors[1] << " de 12" << endl;
		cout << "Cuadros Fucsia destruidos: " << colors[4] << " de 25" << endl;
		cout << "Cuadros Amarillo destruidos: " << colors[5] << " de 12" << endl;
		if ((colors[1] >= 12) && (colors[4] >= 25) && (colors[5] >= 12))
			return true;
		else
			return false;
		break;
	case 2:
		if ((board[9][1] == 8) && (board[9][5] == 8))
			return true;
		else
			return false;
		break;
	case 3:
		if ((board[9][0] == 8) && (board[9][2] == 8) && (board[9][6] == 8) && (board[9][8] == 8))
			return true;
		else
			return false;
		break;
	case 4:
		cout << "Pelotas destruidas: " << destroyedBalls << " de 36." << endl;
		if (destroyedBalls == 36)
			return true;
		else
			return false;
		break;
	}
}

int printMenu() {
	int structLvlsIndex; //Variable para el nivel
	cout << "<<<<<<<<<<<<<<<<TOY BLAST>>>>>>>>>>>>>>>>>>>>" << endl;
	cout << "	  Selecciona un nivel" << endl;
	cout << "		1) Nivel 1" << endl;
	cout << "		2) Nivel 2" << endl;
	cout << "		3) Nivel 3" << endl;
	cout << "		4) Nivel 4" << endl;
	cout << "		5) Nivel 5" << endl;
	cout << "		6) Salir" << endl;

	cin >> structLvlsIndex; //Te lee el numero de nivel que quieres jugar

	for (int i = 0; i < 6; i++)
	{
		colors[i] = 0;
	}
	system("cls");

	return structLvlsIndex - 1; //Te devuelve el valor -1 porque los arreglos comienzan en 0
}

bool isHole(int xCoord, int yCoord)
{
	for (int i = 0; i < levels[lvlIndex].holeNum; i++) //Recorre la cantidad de hoyos que hay en el nivel
	{
		if ((xCoord == levels[lvlIndex].holes[i][1]) && (yCoord == levels[lvlIndex].holes[i][0])) //Si encuentra uno regresa verdadero
		{
			return true;
		}
	}//Sino regresa falso
	return false;
}

bool isColored(int block)
{
	if (((block < 7) && (block > 0)) || block ==8) //Si el bloque es menor a 7 y mayor a 0 o si es igual a 8
	{
		return true; //Regresa verdadero
	}
	return false; // Sino regresa falso
}

bool isToy(int xCoord, int yCoord)
{
	for (int i = 0; i < 4; i++) //Recorre 4 veces ya que es el numero maximo de jueguetes que podemos tener
	{
		if ((xCoord == levels[lvlIndex].toys[i][1]) && (yCoord == levels[lvlIndex].toys[i][0])) //Si las coordenadas coinciden 
		{
			return true; //Regresa verdadero
		}
	}
	return false; //Sino regresa falso
}

bool isBall(int xCoord, int yCoord)
{
	if (board[xCoord][yCoord] == 9)
	{
		return true;
	}
	return false;

}

void surroundingBalls(int xCoord, int yCoord)
{
	if ((xCoord > 0) && (isBall(xCoord - 1,yCoord)))
	{
		if (board[xCoord - 1][yCoord] != 0)
			board[xCoord - 1][yCoord] = 0;
		destroyedBalls++;
		destroyedBlocksCounter[yCoord]++;
	}
	if ((yCoord > 0) && (isBall(xCoord, yCoord - 1)))
	{
		if (board[xCoord][yCoord - 1] != 0) //Si las coordenadas en y son differentes a 0
			board[xCoord][yCoord - 1] = 0;
		destroyedBalls++;
		destroyedBlocksCounter[yCoord - 1]++;
	}
	if ((yCoord < (levels[lvlIndex].height) - 1) && (isBall(xCoord, yCoord + 1)))
	{
		if (board[xCoord][yCoord + 1] != 0) //Si las coordenadas en y son differentes a 0
			board[xCoord][yCoord + 1] = 0;
		destroyedBalls++;
		destroyedBlocksCounter[yCoord + 1]++;
	}
	if ((xCoord < (levels[lvlIndex].height) - 1) && (isBall(xCoord + 1, yCoord)))
	{
		if (board[xCoord + 1][yCoord] != 0) //Si las coordenadas en x son differentes a 0
			board[xCoord + 1][yCoord] = 0;
		destroyedBalls++;
		destroyedBlocksCounter[yCoord]++;
	}
}
