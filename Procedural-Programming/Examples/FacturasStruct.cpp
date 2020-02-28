#include <iostream>
using namespace std;

struct Cliente
{	
	string nombre;
	int unidadesSolicitadas;
	float precioUnidad[];
	float totalFacturado;
	bool solvente;
};

int main()
{
	int numClientes, status;
	float limiteDeuda;
	
	cout << "Numero de clientes a registrar: ";
	cin >> numClientes;
	
	Cliente clientes[numClientes];
	
	for (int i = 0; i < numClientes; i++)
	{
		cout << "Nombre del cliente " << i+1 << ": ";
		cin >> clientes[i].nombre;
		
		cout << "Unidades solicitadas por el cliente: ";
		cin >> clientes[i].unidadesSolicitadas;
		
		clientes[i].precioUnidad[clientes[i].unidadesSolicitadas];
		clientes[i].totalFacturado = 0.0;
		
		for (int j = 0; j < clientes[i].unidadesSolicitadas; j++)
		{
			cout << "Precio de la unidad " << j+1 << " del cliente " << i+1 << ": ";
			cin >> clientes[i].precioUnidad[j];
			clientes[i].totalFacturado += clientes[i].precioUnidad[j];
		}
		
		do
		{
			status = 0;
			clientes[i].solvente = false;
			cout << "Estatus de la cuenta: " << endl << "1) Solvente" << endl << "2) Atrasado" << endl;
			cin >> status;
			if (status == 1)
			{
				clientes[i].solvente = true;
			}
			if ((status != 1) && (status != 2))
			{
				cout << "Por favor elija una opcion valida." << endl;
			}
		} while ((status != 1) && (status != 2));
		cout << "************************" << endl << "************************" << endl;			
	}
	
	cout << "Por favor establezca un limite de facturacion para clientes con atraso: ";
	cin >> limiteDeuda;
	
	cout << endl << "Cuentas con atraso: " << endl;
	for (int i = 0; i < numClientes; i++)
	{
		if (!clientes[i].solvente)
		{
			cout << "Nombre: " << clientes[i].nombre << endl;
		}
	}
	cout << endl << "De las cuales, las siguientes presentan facturas por encima del limite previamente establecido: " << endl;
	for (int i = 0; i < numClientes; i++)
	{
		if ((!clientes[i].solvente) && (clientes[i].totalFacturado > limiteDeuda))
		{
			cout << "Nombre: " << clientes[i].nombre << endl << "	Adeudo total: " << clientes[i].totalFacturado << endl;
		}
	}
	
	
	
}
