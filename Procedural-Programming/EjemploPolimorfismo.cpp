//Polimorfismo
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
		virtual void imprimirInfo()
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
		void imprimirInfo()
		{
			cout << index << ". " << nombre << ". " << edad << " anios. "<< peso << "kg."<< endl;
		}
			
	};
	
int main()
{
	Prueba *pP1, p1;
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
	
	p1.index = 1;
	p1.ingresarInfo(nomProv, edadProv);
	pP1 = &p1;
	p2.ingresarInfo(nomProv, edadProv);
	p2.ingresarPeso(pesoProv);
	cout << "Info SuperClass" << endl;
	pP1->imprimirInfo();	
	cout << "Info Hijo" << endl;
	pP1 = &p2;
	pP1->imprimirInfo();
}
