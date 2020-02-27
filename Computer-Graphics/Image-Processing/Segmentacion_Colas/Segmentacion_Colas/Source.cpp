#include "opencv2/core/core.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/highgui/highgui.hpp"
#include <iostream>
#include <cmath>
#include <queue>

using namespace cv;
using namespace std;

const char* filename = "cosa.png";
Mat image = imread(filename, CV_LOAD_IMAGE_GRAYSCALE);
int height = image.rows;
int width = image.cols;
int regs = 0;
int value = 255;
Mat output(height, width, CV_8UC3, Scalar(0, 0, 0));
Mat V(height, width, CV_32FC1, Scalar(0));
void segmento(int, int);
int nV = 8;

int main() {

	for (int i = 0; i < height; i++)
	{
		for (int j = 0; j < width; j++)
		{
			if (image.at<uchar>(i, j) > 128)
				image.at<uchar>(i, j) = 0;
			else
				image.at<uchar>(i, j) = 255;
		}
	}
	namedWindow("Regiones", WINDOW_KEEPRATIO);
	for (int i = 1; i < height - 1; i++) {
		for (int j = 1; j < width - 1; j++) {
			if ((image.at<uchar>(i, j) == value) && (V.at<float>(i, j) == 0))
			{
				segmento(j, i);
			}
		}
	}
	cout << regs << endl;
	float factor = 256.0f /regs;
	for (int i = 0; i < height; i++)
	{
		for (int j = 0; j < width; j++)
		{
			/*if (V.at<float>(i, j) != 0) 
			{
				if (V.at<float>(i, j) % 6 == 0)
					output.at<Vec3b>(i, j) = Vec3b(V.at<float>(i, j) * factor,255- V.at<float>(i, j) * factor, 255);
				if (V.at<float>(i, j) % 6 == 2)
					output.at<Vec3b>(i, j) = Vec3b(255 - V.at<float>(i, j) * factor, 255, V.at<float>(i, j) * factor);
				if (V.at<float>(i, j) % 6 == 1)
					output.at<Vec3b>(i, j) = Vec3b(255, V.at<float>(i, j) * factor, 255 - V.at<float>(i, j) * factor);
				if (V.at<float>(i, j) % 6 == 3)
					output.at<Vec3b>(i, j) = Vec3b(0,0,255);
				if (V.at<float>(i, j) % 6 == 4)
					output.at<Vec3b>(i, j) = Vec3b(0,255,0);
				if (V.at<float>(i, j) % 6 == 5)
					output.at<Vec3b>(i, j) = Vec3b(255,0,0);
					
			}*/
		}
	}
	imshow("Regiones", output);

	namedWindow("Original", WINDOW_NORMAL);
	imshow("Original", image);
	waitKey(0);
}

void segmento(int x, int y)
{
	queue<Point2d> cola;
	cola.push(Point2d(x, y));
	regs++;
	V.at<float>(y, x) = (float)regs;
	while (!cola.empty())
	{
		Point2d current = cola.front();
		cola.pop();

		if ((current.y > 0) && (image.at<uchar>((int)current.y - 1, (int)current.x) == value) && (V.at<float>((int)current.y - 1, (int)current.x) == 0))
		{
			cola.push(Point2d(current.x, current.y - 1));
			V.at<float>((int)current.y - 1, (int)current.x) = regs;
		}
		if ((current.y < height - 1) && (image.at<uchar>((int)current.y + 1, (int)current.x) == value) && (V.at<float>((int)current.y + 1, (int)current.x) == 0))
		{
			cola.push(Point2d(current.x, current.y + 1));
			V.at<float>((int)current.y + 1, (int)current.x) = regs;
		}
		if ((current.x > 0) && (image.at<uchar>((int)current.y, (int)current.x - 1) == value) && (V.at<float>((int)current.y, (int)current.x - 1) == 0))
		{
			cola.push(Point2d(current.x - 1, current.y));
			V.at<float>((int)current.y, (int)current.x - 1) = regs;
		}
		if ((current.x < width - 1) && (image.at<uchar>((int)current.y, (int)current.x + 1) == value) && (V.at<float>((int)current.y, (int)current.x + 1) == 0))
		{
			cola.push(Point2d(current.x + 1, current.y));
			V.at<float>((int)current.y, (int)current.x + 1) = regs;
		}
		if (nV == 8)
		{
			if ((current.x > 0) && (current.y > 0) && (image.at<uchar>((int)current.y - 1, (int)current.x - 1) == value) && (V.at<float>((int)current.y - 1, (int)current.x - 1) == 0))
			{
				cola.push(Point2d(current.x - 1, current.y - 1));
				V.at<float>((int)current.y - 1, (int)current.x - 1) = regs;
			}
			if ((current.x > 0) && (current.y < height - 1) && (image.at<uchar>((int)current.y + 1, (int)current.x - 1) == value) && (V.at<float>((int)current.y + 1, (int)current.x - 1) == 0))
			{
				cola.push(Point2d(current.x - 1, current.y + 1));
				V.at<float>((int)current.y + 1, (int)current.x - 1) = regs;
			}
			if ((current.x < width - 1) && (current.y > 0) && (image.at<uchar>((int)current.y - 1, (int)current.x + 1) == value) && (V.at<float>((int)current.y - 1, (int)current.x + 1) == 0))
			{
				cola.push(Point2d(current.x + 1, current.y - 1));
				V.at<float>((int)current.y - 1, (int)current.x + 1) = regs;
			}
			if ((current.x < width - 1) && (current.y < height - 1) && (image.at<uchar>((int)current.y + 1, (int)current.x + 1) == value) && (V.at<float>((int)current.y + 1, (int)current.x + 1) == 0))
			{
				cola.push(Point2d(current.x + 1, current.y + 1));
				V.at<float>((int)current.y + 1, (int)current.x + 1) = regs;
			}
		}
		if (V.at<float>(current.y, current.x) != 0)
		{
			
			if ((int)V.at<float>(current.y, current.x) % 4 == 0)
				output.at<Vec3b>(current.y, current.x) = Vec3b(0, 255, 0);
			if ((int)V.at<float>(current.y, current.x) % 4 == 1)
				output.at<Vec3b>(current.y, current.x) = Vec3b(255, 255, 0);
			if ((int)V.at<float>(current.y, current.x) % 4 == 2)
				output.at<Vec3b>(current.y, current.x) = Vec3b(255, 0, 255);
			if ((int)V.at<float>(current.y, current.x) % 4 == 3)
				output.at<Vec3b>(current.y, current.x) = Vec3b(255, 0, 0);
		}

		//imshow("Regiones", output);
		//waitKey(1);
	}
	
}
