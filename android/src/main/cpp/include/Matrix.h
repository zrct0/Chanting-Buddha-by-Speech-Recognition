#pragma once
#include "AutoGC.h"

#define accuracy float
#define arrtype accuracy *
#define create_arrayp(size, ...) new accuracy[size]{__VA_ARGS__}
#define Matrixp Matrix *
#define create_matrixp(...) new Matrix( __VA_ARGS__ )
#define Vectorp Vector *
#define create_verctorp(...) new Vector( __VA_ARGS__ )
#define create_verctorp_by_array(size, ...) new Vector(size, create_arrayp(size, __VA_ARGS__ ))
#define display_accuracy 3
namespace cpp_speech_features {
	class Vector;
	class Matrix 
	{
	public:
		Matrix(int col, int row);
		Matrix(int col, int row, arrtype data);
		Matrix(Matrixp b);
		~Matrix();
		accuracy get(int x);
		accuracy get(int y, int x);		
		void set(int x, accuracy v);
		void set(int y, int x, accuracy v);
		int getColumn();
		int getRow();
		arrtype getData();
		Vectorp getRow(int row);
		void setRow(int row, Vectorp r);
		Matrixp multiply(Matrixp b);
		Matrixp multiply(accuracy b);	
		Matrixp divide(accuracy b);	
		Matrixp subtract(Matrixp b);
		Matrixp add(Matrixp b);
		Matrixp add(accuracy b);
		Matrixp square();
		Matrixp takeRow(int r1, int r2);
		Matrixp logarithms();
		Matrixp concatenate(Matrixp b);
		Matrixp vstack(Matrixp b);
		Matrixp T();
		Matrixp reshape(int row, int col);			
		Matrixp map(accuracy(*func)(int, int, accuracy));
		Matrixp mapRow(Vectorp(*func)(int, Vectorp r));
		void print(const char * tag= "");
	protected:
		arrtype pArray;
		void createZerosArray(int size);
	private:	
		int column;
		int row;
	};
}

