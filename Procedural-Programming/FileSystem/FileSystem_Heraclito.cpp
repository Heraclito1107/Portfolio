#include <iostream>
#include <fstream>
#include <cstring>
#include <map>
#include <cstdlib>
#include <vector>
#include <cmath>
#include <ctime>
using namespace std;

enum Cmds {mount = 1, unmount, load, download, ls, rm, info, details, close};
map<string, Cmds> cmdValues;
int blockSize, blockNum, totalSize;
char *volume, *blockStatus;
char fsName[1000];
fstream myFileSystem;
string* fileIndex;
int** savePositions;
int fileCounter;
int freeBlocks;
int systemReserved;
int dataBlockStart;

void mountSystem();
void unmountSystem();
void loadFile (char*, char*);
void downloadFile (char*, char*);
void listElements();
void removeFile(char*);
void displaySystemInfo();
void displayFileDetails(char*);
void initializeMap();
int findIndex(string);
int main()
{
	initializeMap();
	
	char source[1000], destination[1000], targetFile[1000];
	
	do
	{
		Cmds command;
		string cmd;
		cin >> cmd;
		command = cmdValues[cmd];
		switch (command)
		{
			case mount:
				cin >> fsName;
				mountSystem();
				break;
			
			case unmount:
				unmountSystem();
				break;
				
			case load: 
				cin >> source >> destination;
				loadFile(source, destination);
				break;
			
			case download:
				cin >> source >> destination;
				downloadFile(source, destination);
				break;
				
			case ls:
				listElements();
				break;
			
			case rm:
				cin >> targetFile;
				removeFile(targetFile);
				break;
								
			case info:
				displaySystemInfo();
				break;
			
			case details:
				cin >> targetFile;
				displayFileDetails(targetFile);
				break;
				
			case close:
				return 0;
				break;
				
			default:
				cout << "Command unrecognized. Try again \n Options are: \n mount \tunmount" << endl;
				system("pause");
				cmd = '0';
				break;
		}
	}
	while (true);
}


void initializeMap()
{
	cmdValues["mount"] = mount;
	cmdValues["unmount"] = unmount;
	cmdValues["load"] = load;
	cmdValues["download"] = download;
	cmdValues["ls"] = ls;
	cmdValues["rm"] = rm;
	cmdValues["info"] = info;
	cmdValues["details"] = details;
	cmdValues["close"] = close;
 }
 

void mountSystem()
{
	ifstream in(fsName, ios::binary);
	if (in.good())
	{
		in.seekg(0, ios::beg);
		in.read(reinterpret_cast<char*>(&fileCounter), sizeof(int));
		in.read(reinterpret_cast<char*>(&blockSize), sizeof(int));
		in.read(reinterpret_cast<char*>(&blockNum), sizeof(int));
		blockStatus= (char*) malloc(sizeof(char)*blockNum);
		in.read(blockStatus, blockNum);
		totalSize = blockSize*blockNum;
		fileIndex = new string[blockNum];
		for (int i = 0; i < fileCounter; i++)
		{
			int size;
			in.read(reinterpret_cast<char*>(&size), sizeof(int));
			char* cFile = new char[size];
			in.read(cFile, size);
			string sFile = cFile;
			fileIndex[i] = sFile;
		}
		
		savePositions = (int**) malloc(sizeof(int**)*blockNum);
		for (int i = 0; i < fileCounter; i++)
		{
			int size;
			in.read(reinterpret_cast<char*>(&size), sizeof(int));
			int* svPos = new int[size+2];
			svPos[0] = size;
			for (int j = 1; j < (size+2); j++)
			{
				int cPos;
				in.read(reinterpret_cast<char*>(&cPos), sizeof(int));
				svPos[j] = cPos;
				
			}
			savePositions[i] = svPos;
		}
		
		in.seekg(0, ios::beg);
		volume = (char*) malloc(sizeof(char)*totalSize);
		in.read(volume, totalSize);
		int usedBlocks = 0;
		for(int i = 0; i < blockNum; i++)
		{
			if (blockStatus[i] == '1')
				usedBlocks++;
		}
		freeBlocks = blockNum-usedBlocks;
		systemReserved = (sizeof(char)+sizeof(char)+sizeof(string)+sizeof(int**))*blockNum;
		dataBlockStart = systemReserved/blockSize + 1;
	}
	else
	{
		cin >> blockSize >> blockNum;
		totalSize = blockSize*blockNum;
		volume = (char*) malloc(sizeof(char)*totalSize);
		blockStatus= (char*) malloc(sizeof(char)*blockNum);
		fileIndex = new string[blockNum];
		
		savePositions = (int**) malloc(sizeof(int**)*blockNum);
		fileCounter = 0;
		for(int i = 0; i < blockNum; i++)
		{
			blockStatus[i] = '0';
		}	
		freeBlocks = blockNum;
		systemReserved = (sizeof(char)+sizeof(char)+sizeof(string)+sizeof(int**))*blockNum;
		dataBlockStart = systemReserved/blockSize + 1;
		for(int i = 0; i < dataBlockStart; i++)
		{
			blockStatus[i] = '1';
		}	
		freeBlocks-=dataBlockStart;
	}
	
}

void unmountSystem()
{
	int wrPos = 0;
	char* temp = new char[sizeof(int)];
	temp = reinterpret_cast<char*>(&fileCounter);
	for (int i = 0; i < sizeof(int); i++)
		volume[i] = temp[i];
		
	wrPos += sizeof(int);
	char* temp1 = new char[sizeof(int)];
	temp1 = reinterpret_cast<char*>(&blockSize);
	for (int i = 0; i < sizeof(int); i++)
		volume[i+wrPos] = temp1[i];
	
	wrPos += sizeof(int);
	char* temp2 = new char[sizeof(int)];
	temp2 = reinterpret_cast<char*>(&blockNum);
	for (int i = 0; i < sizeof(int); i++)
		volume[i+wrPos] = temp2[i];
	
	wrPos += sizeof(int);
	for (int i = 0; i < blockNum; i++)
		volume[i+wrPos] = blockStatus[i];
		
	wrPos += blockNum;
	for (int j = 0; j < fileCounter; j++)
	{
		char* temp4 = new char[sizeof(int)];
		int size = fileIndex[j].size();
		temp4 = reinterpret_cast<char*>(&size);
		for (int i = 0; i < sizeof(int); i++)
			volume[i+wrPos] = temp4[i];
		wrPos+=sizeof(int);	
		for (int i = 0; i < fileIndex[j].size(); i++)
			volume[i+wrPos] = fileIndex[j].at(i);
		wrPos += fileIndex[j].size();	
	}
	
	for (int j = 0; j < fileCounter; j++)
	{
		for (int i = 0; i < (savePositions[j][0]+2); i++)
		{
			char* temp3 = new char[sizeof(int)];
			int s = savePositions[j][i];
			temp3 = reinterpret_cast<char*>(&s);
			for(int k = 0; k < sizeof(int); k++)
				volume[k+wrPos] = temp3[k];	
			wrPos += sizeof(int);
		}
			
	}
	ofstream outFile;
	outFile.open(fsName, ios_base::binary);
	outFile.seekp(0, ios::beg);
	outFile.write(volume, totalSize);
	outFile.close();
}
void loadFile (char* source, char* dest)
{
	srand (time(NULL));
	streampos begin, end;
	int fileSize = 0, blocks = 0;
	ifstream loadingFile (source, ios::binary);
	
	loadingFile.seekg(0, ios::beg);
	begin = loadingFile.tellg();
	loadingFile.seekg(0, ios::end);
	end = loadingFile.tellg();
	fileSize = end-begin;
	blocks = (fileSize/blockSize) + 1;
	if (blocks > freeBlocks)
	{
		cout << "There's no enough space in the volume, try removing some files or loading a smaller one." << endl;
		return;
	}
		
	int *blockPos = new int[blocks+2];
	//string destination = dest;
	fileIndex[fileCounter] = dest;
	int currentBlock = 2;
	blockPos[0] = blocks;
	blockPos[1] = fileSize;
	do
	{
		int index = rand() % blockNum;
		if (blockStatus[index] == '0')
		{
			blockPos[currentBlock] = index;
			blockStatus[index] = '1';
			currentBlock++;
		}
	}while(currentBlock < (blocks+2));
	savePositions[fileCounter] = blockPos;
	streampos x = blockSize;
	for (int i = 0; i < blocks; i++)
	{
		
		loadingFile.seekg(x*i, ios::beg);
		char *block = new char[blockSize];
		loadingFile.read(block, x); 
		
		for (int j  = 0; j <blockSize; j++)
		{	
				volume[(blockSize*blockPos[i+2])+j] = block[j];
		}
		delete[] block;
	}
	loadingFile.close();
	freeBlocks -= blocks;
	fileCounter++;
}

void downloadFile (char *source, char *outputFile)
{
	string temp = source;
	int index = findIndex(temp);
	int fileSize = savePositions[index][1];
	streampos x = blockSize;
	ofstream outFile;
	outFile.open(outputFile, ios::binary);
	outFile.seekp(0, ios::beg);
	for (int i = 0; i < savePositions[index][0]; i++ )
	{
		char *block = new char[blockSize];
		for (int j  = 0; j <blockSize; j++)
		{
			block[j] = volume[(blockSize*savePositions[index][i+2])+j];
		}
		outFile << flush;
		if ((i+1) == savePositions[index][0])
		{
			outFile.write(block, (fileSize%blockSize));
		}
		else
		{
			outFile.write(block, blockSize);
		}
		delete[] block;
	}
	outFile.close();
}

void listElements()
{
	for (int i = 0; i < fileCounter; i++)
	{
		cout << fileIndex[i];
		if (i%4 == 3)
		{
			cout << endl;
		}
		else
		{
			cout << '\t';
		}
	}
	cout << endl;
}

int findIndex(string element)
{
	for (int i = 0; i < fileCounter; i++)
	{
		if (element == fileIndex[i])
		{
			return i;
			break;
		}
	}
	return 1000000000;
}
/*mount d 200 300
load Unicorn.png u.png
load test.txt t.txt
load troye.PNG tr.png
download u.png unic.png
download t.txt wa.txt
*/

void removeFile(char *file)
{
	string temp = file;
	int index = findIndex(temp);
	int blocks = savePositions[index][0];
	for (int i = 0; i <blocks; i++)
	{
		blockStatus[savePositions[index][i+2]] = '0';
		
	}
	for (int i = index; i < fileCounter; i++)
	{
		if((i+1)< blockNum)
		{
			fileIndex[i] = fileIndex[i+1];
			savePositions[i] = savePositions[i+1];
		}
	}
	
	
	freeBlocks += blocks;
	fileCounter -= 1;
}

void displaySystemInfo()
{
	cout << endl;
	cout << "Volume size: " << totalSize << endl
		 << "Used space: "	<< blockSize*(blockNum-freeBlocks) << endl
		 << "Free space: "	<< blockSize*freeBlocks << endl
		 << "Block size: "	<< blockSize << endl
		 << "Number of blocks: " << blockNum << endl
		 << "System reserved: " << (dataBlockStart)*blockSize<< endl
		 << endl;
	
}

void displayFileDetails(char *file)
{
	
	string temp = file;
	int index = findIndex(temp);
	int blocks = savePositions[index][0];
	cout << endl;
	cout << "File name: " << file << endl 
		 << "File size: " << savePositions[index][1] << " bytes" << endl
		 << "Number of blocks: " << blocks << endl
		 << "Blocks used:" << endl;
	for (int i = 0; i <blocks; i++)
	{
		cout << savePositions[index][i+2];
		if (i%6 == 5)
		{
			cout << endl;
		}
		else
		{
			cout << '\t';
		}
		
	}
	cout << endl << endl;
	
}

