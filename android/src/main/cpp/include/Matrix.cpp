#include "Vector.h"

namespace cpp_speech_features {
	
	Matrix::Matrix(int col, int row)
	{
		if (col <= 0 || row <= 0) {
			//throw std::exception("col <= 0 || row <= 0");
		}		
		this->column = col;
		this->row = row;
		createZerosArray(col * row);
		AutoGC::insert(this);
	}

	Matrix::Matrix(int col, int row, arrtype data)
	{
		if (col <= 0 || row <= 0) {
			//throw std::exception("col <= 0 || row <= 0");
		}		
		
		this->column = col;
		this->row = row;	
		pArray = data;
	}

	Matrix::Matrix(Matrixp b)
	{				
		this->column = b->column;
		this->row = b->row;
		createZerosArray(column * row);		
		arrtype data = b->getData();
		for (int i = 0; i < column * row; ++i) {
			set(i, b->get(i));
		}
		AutoGC::insert(this);
	}

	void Matrix::createZerosArray(int size)
	{		
		pArray = create_arrayp(size, 0);
	}

	Matrix::~Matrix()
	{				
		delete[] pArray;
	}

	accuracy Matrix::get(int x)
	{
		return pArray[x];
	}

	accuracy Matrix::get(int y, int x) {
		return pArray[y * column + x];
	}

	void Matrix::set(int x, accuracy v)
	{		
		if (x >= row * column) {
			//throw new std::exception("Matrix::set (x >= row * column)");
		}
		pArray[x] = v;
	}

	void Matrix::set(int y, int x, accuracy v) {
		if (y >= row || x >= column) {
			//throw new std::exception("Matrix::set (y >= row || x >= column)");
		}
		pArray[y * column + x] = v;
	}

	int Matrix::getColumn()
	{
		return column;
	}

	int Matrix::getRow()
	{
		return row;
	}

	arrtype Matrix::getData()
	{
		return pArray;
	}

	Vectorp Matrix::getRow(int row)
	{
		Vectorp narray = create_verctorp(column);
		for (int i = 0; i < column; ++i) {
			narray->set(i, get(row, i));
		}
		return narray;
	}

	void Matrix::setRow(int row, Vectorp r)
	{
		if (r == nullptr) {
			//throw std::exception("setRow r == nullptr");
		}

		for (int i = 0; i < column; ++i) {
			set(row, i, r->get(i));
		}
	}
		
	Matrixp Matrix::multiply(Matrixp b) {
		if (b == nullptr) {
			//throw std::exception("Matrix multiply b == nullptr");
		}
		if (column != b->row) {
			//throw std::exception("A's column do not match and B's row");
		}
		Matrixp nm = create_matrixp(b->column, row);
		accuracy cellValue;
		for (int x = 0; x < b->column; ++x) {
			for (int y = 0; y < row; ++y) {
				cellValue = 0;
				for (int i = 0; i < column; ++i) {
					cellValue += get(y, i) * b->get(i, x);
				}
				nm->set(y, x, cellValue);
			}
		}
		return nm;
	}

	
	Matrixp Matrix::multiply(accuracy b)
	{
		Matrixp nm = create_matrixp(column, row);
		for (int x = 0; x < column; ++x) {
			for (int y = 0; y < row; ++y) {						
				nm->set(y, x, get(y, x) * b);
			}
		}
		return nm;
	}

	Matrixp Matrix::divide(accuracy b)
	{
		Matrixp nm = create_matrixp(column, row);
		for (int x = 0; x < column; ++x) {
			for (int y = 0; y < row; ++y) {
				nm->set(y, x, get(y, x) / b);
			}
		}
		return nm;
	}


	
	Matrixp Matrix::subtract(Matrixp b)
	{
		if (b == nullptr) {
			//throw std::exception("subtract b == nullptr");
		}
		if (row != b->row && column != b->column) {
			//throw std::exception("A's dimension do not match and B's dimension");
		}

		Matrixp nm = create_matrixp(column, row);
		for (int x = 0; x < column; ++x) {
			for (int y = 0; y < row; ++y) {
				nm->set(y, x, get(y, x) - b->get(y, x));
			}
		}
		return nm;
	}

	Matrixp Matrix::add(Matrixp b)
	{
		if (b == nullptr) {
			//throw std::exception("add b == nullptr");
		}
		if (row != b->row && column != b->column) {
			//throw std::exception("A's dimension do not match and B's dimension");
		}

		Matrixp nm = create_matrixp(column, row);
		for (int x = 0; x < column; ++x) {
			for (int y = 0; y < row; ++y) {
				nm->set(y, x, get(y, x) + b->get(y, x));
			}
		}
		return nm;
	}

	Matrixp Matrix::add(accuracy b)
	{
		Matrixp nm = create_matrixp(column, row);
		for (int x = 0; x < column; ++x) {
			for (int y = 0; y < row; ++y) {
				nm->set(y, x, get(y, x) + b);
			}
		}
		return nm;
	}

	Matrixp Matrix::square()
	{
		Matrixp nm = create_matrixp(column, row);
		for (int x = 0; x < column; ++x) {
			for (int y = 0; y < row; ++y) {
				nm->set(y, x, pow(get(y, x), 2));
			}
		}
		return nm;		
	}

	Matrixp Matrix::takeRow(int r1, int r2)
	{
		if (r2 > row) {
			//throw std::exception("takeRow r2 > row");
		}
		if (r1 >= r2) {
			//throw std::exception("takeRow r1 >= r2");
		}
		Matrixp nm = create_matrixp(column, r2 - r1);
		int index = 0;
		for (int y = r1; y < r2; ++y) {
			for (int x = 0; x < column; ++x) {
				nm->set(index, x, get(y, x));
			}
			index++;
		}
		return nm;
	}	

	Matrixp Matrix::logarithms()
	{
		return map([](int y, int x, accuracy v) ->accuracy 
		{
			return log(v); 
		});
	}
	
	Matrixp Matrix::concatenate(Matrixp b)
	{
		if (b == nullptr) {
			//throw std::exception("concatenate b == nullptr");
		}
		if (row != b->row ) {
			//throw std::exception("the matrix must have the same row");
		}
		int newColumn = column + b->column;
		Matrixp nm = create_matrixp(newColumn, row);
		for (int y = 0; y < row; ++y) {
			for (int x = 0; x < newColumn; ++x) {
				if (x < column) {
					nm->set(y, x, get(y, x));
				}
				else
				{
					nm->set(y, x, b->get(y, x - column));
				}
			}
		}
		return nm;
	}

	Matrixp Matrix::vstack(Matrixp b)
	{
		if (b == nullptr) {
			//throw std::exception("hstack b == nullptr");
		}
		if (column != b->column) {
			//throw std::exception("the matrix must have the same column");
		}
		int newRow = row + b->row;
		Matrixp nm = create_matrixp(column, newRow);
		for (int y = 0; y < newRow; ++y) {
			for (int x = 0; x < column; ++x) {
				if (y < row) {
					nm->set(y, x, get(y, x));
				}
				else
				{
					nm->set(y, x, b->get(y - row, x));
				}
			}
		}
		return nm;
	}

	Matrixp Matrix::reshape(int row, int col)
	{		
		Matrixp nm = create_matrixp(col, row);
		arrtype data = nm->getData();
		for (int i = 0; i < col * row; ++i) {
			nm->set(i, get(i));
		}
		return nm;
	}

	Matrixp Matrix::map(accuracy (*func)(int, int, accuracy))
	{
		Matrixp nm = create_matrixp(column, row);
		int index = 0;
		for (int y = 0; y < row; ++y) {
			for (int x = 0; x < column; ++x) {
				nm->set(y, x, func(y, x, get(index++)));
			}
		}
		return nm;
	}

	Matrixp Matrix::mapRow(Vectorp(*func)(int, Vectorp r))
	{
		Matrixp nm = create_matrixp(column, row);
		for (int y = 0; y < row; ++y) {
			nm->setRow(y, func(y, getRow(y)));
		}
		return nm;
	}

	

	Matrixp Matrix::T()
	{
		Matrixp nm = create_matrixp(row, column);
		for (int x = 0; x < column; ++x) {
			for (int y = 0; y < row; ++y) {
				nm->set(x, y, get(y, x));
			}
		}
		return nm;
	}

	void Matrix::print(const char * tag)
	{
	
	}
	
}