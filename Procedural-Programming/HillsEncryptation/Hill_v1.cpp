#include<iostream>
#include <bitset>
#include <cstring>
using namespace std;


typedef bitset<8> byte;

int main()
{
	char mat[8][8] ={1, 0, 1, 1, 0, 1, 1, 1, 
					 0, 1, 1, 1, 0, 0, 1, 0,
					 0, 0, 1, 1, 1, 0, 0, 0,
					 1, 0, 0, 1, 1, 1, 0, 1,
					 1, 1, 0, 0, 1, 0, 0, 1,
					 1, 0, 1, 0, 1, 0, 1, 0,
					 0, 0, 1, 1, 1, 1, 1, 1,
					 1, 1, 1, 0, 1, 1, 0, 1}; 

	char matInv[8][8] ={1, 0, 0, 1, 0, 1, 0, 0, 
					    0, 0, 1, 0, 0, 1, 1, 1,
					    1, 1, 0, 1, 0, 1, 1, 1,
					    1, 1, 1, 0, 0, 0, 0, 1,
					    0, 0, 0, 1, 0, 1, 1, 0,
					    1, 1, 0, 1, 1, 1, 1, 0,
					    0, 1, 0, 1, 0, 0, 0, 1,
					    1, 0, 1, 0, 1, 1, 0, 1};
	
	int res[256];
	
	string a;
	string x = "";
	
	//cin >> a;
	getline(cin, a);
	for(int n = 0; n < a.size(); n++)
	{
		byte b{a.at(n)};
		
		byte c;
		int t[8] = {b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7]};
		for(int i = 0; i < 8; i++)
		{
			int temp = 0;
			for (int j = 0; j < 8; j++)
			{
				temp += mat[j][i]*t[j];
			}
			if(temp%2 == 1)
				c.set(i);
		}
		res[n] = c.to_ulong();
		x+=(char)res[n];
		cout << a.at(n) << "\t" << b << endl << (char)c.to_ulong() << "\t" << c << endl << endl ;
		c.reset();
	}
	cout<< endl << "======CONVERTIDO======" <<endl <<endl;
	cout << x << endl;
	cout<< endl << "======RECUPERADO======" <<endl <<endl;
	
	string y = "";
	for(int n = 0; n < a.size(); n++)
	{
		
		byte b{res[n]};
		
		byte c;
		int t[8] = {b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7]};
		for(int i = 0; i < 8; i++)
		{
			int temp = 0;
			for (int j = 0; j < 8; j++)
			{
				temp += matInv[j][i]*t[j];
			}
			if(temp%2 == 1)
				c.set(i);
		}
		//res[n] = c.to_ulong();
		//cout << n << "\t" << b << endl << c.to_ulong() << "\t" << c << endl << endl ;
		y+=(char)c.to_ulong();
		//cout << (char)c.to_ulong() << endl;
		c.reset();
	}
	cout << endl << y << endl;
}
