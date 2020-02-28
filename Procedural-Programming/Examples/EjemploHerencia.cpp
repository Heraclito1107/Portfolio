//Herencia
#include <iostream>

using namespace std;

class Prueba {
	
	public:
		int index;
	protected:
		string nombre;
		int edad;
		
	public:
		void ingresarInfo(string capNombre, int capEdad)
		{
			nombre = capNombre;
			edad = capEdad;
		}
		void imprimirInfo()
		{
			cout << index << ". " << nombre << ". " << edad << " anios."<< endl;
		}
		
};

class PruebaHijo : public Prueba
{
	private:
		float peso;
		
	public:
		void ingresarPeso(float capPeso)
		{
			peso = capPeso;
		}
		void imprimirInfoHijo()
		{
			cout << index << ". " << nombre << ". " << edad << " anios. "<< peso << "kg."<< endl;
		}
			
	};
	
int main()
{
	PruebaHijo p2;
	int edadProv;
	string nomProv;
	float pesoProv;
	
	p2.index = 1;
	cout << "Ingrese el nombre: ";
	cin >> nomProv;
	cout << "Ingrese la edad: ";
	cin >> edadProv;
	cout << "Ingrese el peso: ";
	cin >> pesoProv;
	
	p2.ingresarInfo(nomProv, edadProv);
	p2.ingresarPeso(pesoProv);
	p2.imprimirInfoHijo();
	
	
	
}
