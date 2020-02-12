/**
 * A pthread program illustrating how to
 * create a simple thread and some of the pthread API
 * This program implements the summation function where
 * the summation operation is run as a separate thread.
 *
 * gcc thrd.c -lpthread
 *
 */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int max, min, med, avg, stdv, size; /* this data is shared by the thread(s) */

void *maximum(void *nums);
void *minimum(void *nums);
void *average(void *nums);
void *median(void *nums);
void *stdDev(void *nums);

int main(int argc, char *argv[])
{
size = argc;
int i, nums[size];

for (i = 1; i<argc; i++)
{
	nums[i] = atoi(argv[i]);
}



pthread_t tid[5]; /* the thread identifier */
pthread_attr_t attr; /* set of attributes for the thread */

if (argc < 2) {
	fprintf(stderr,"usage: a.out <integer value>\n");
	/*exit(1);*/
	return -1;
}


/* get the default attributes */
pthread_attr_init(&attr);

/* create the thread */
pthread_create(&tid[0],&attr,maximum,(void*)nums);
pthread_create(&tid[1],&attr,minimum,(void*)nums);
pthread_create(&tid[2],&attr,average,(void*)nums);
pthread_create(&tid[3],&attr,median,(void*)nums);
pthread_create(&tid[4],&attr,stdDev,(void*)nums);

/* now wait for the thread to exit */
for (i = 0; i < 5; i++)
{
	pthread_join(tid[i],NULL);
}


printf("Max = %d\n", max);
printf("Min = %d\n", min);
printf("Average = %d\n", avg);
printf("Median = %d\n", med);
printf("Standard Deviation = %d\n", stdv);
}
/**
 * The thread will begin control in this function
 */
void *maximum(void *nums)
{
	int i, temp = 0;
	int *dataCollection = (int*)nums;

	for(i = 1; i < size; i++)
	{
		if (temp < dataCollection[i])
			temp = dataCollection[i];
	}	
	max = temp;
	pthread_exit(0);
}
void *minimum(void *nums)
{	
	int i, temp;
	int *dataCollection = (int*)nums;
	temp = dataCollection[1];
	for(i = 1; i < size; i++)
	{
		if (temp > dataCollection[i])
			temp = dataCollection[i];
	}	
	min = temp;
	pthread_exit(0);
}
void *average(void *nums)
{
	int i, temp = 0;
	int *dataCollection = (int*)nums;
	for(i = 1; i < size; i++)
	{
		temp+= dataCollection[i];
	}
	avg = temp/(size-1);
	pthread_exit(0);
}
void *median(void *nums)
{
	int i, j, temp, aux;
	int *dataCollection = (int*)nums;
	for (i = 1; i<size; i++)
	{
		for(j = 1; j < (size-1); j++)
		{
			if (dataCollection[j+1] < dataCollection[j])
			{
				aux = dataCollection[j];
				dataCollection[j] = dataCollection[j+1];
				dataCollection[j+1] = aux;
			}
		}
	
	}

	if(size%2 ==0)
	{
		i = size/2;
		med = dataCollection[i];
	}
	else
	{
		i = (size-1)/2;
		temp = (dataCollection[i] + dataCollection[i+1])/2;
		med = temp;
	}	
	
	pthread_exit(0);
}
void *stdDev(void *nums)
{
	int i, temp = 0, sqDist = 0;
	int *dataCollection = (int*)nums;
	for(i = 1; i < size; i++)
	{
		temp+= dataCollection[i];
	}
	temp = temp/(size-1);
	for(i = 1; i < size; i++)
	{
		sqDist+= ((dataCollection[i]-temp) * (dataCollection[i]-temp));
	}
	temp = (sqDist/(size-1));
	stdv = (int)sqrt(temp);
	pthread_exit(0);
}

