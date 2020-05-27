#include "Vector.h"

namespace cpp_speech_features {
		
	Vector::Vector(int size):Matrix(size, 1)
	{
		if (size <= 0) {
			//throw std::exception("size <= 0");
		}		
		this->size = size;		
	}

	Vector::Vector(int size, arrtype array) :Matrix(size, 1, array)
	{		
		if (size <= 0) {
			//throw std::exception("size <= 0");
		}		
		this->size = size;		
	}

	Vector::Vector(Matrixp m) :Matrix(m->getColumn(), 1) { 

		if (m == nullptr) {
			//throw std::exception("Array construction m == nullptr");
		}		
		if (m->getRow() != 1) {
			//throw std::exception("Array 's row must be 1");
		}
		this->size = m->getColumn() * m->getRow();		
		for (int i = 0; i < this->size; i++) {
			this->set(i, m->get(i));
		}
	}

	Vector::~Vector()
	{
		//std::cout << "realse Array " << this << std::endl;
	}


	int Vector::getSize()
	{
		return this->size;
	}

	Vectorp Vector::arange(accuracy start, accuracy end, accuracy step)
	{		
		int length = (int)((end - start) / step);
		Vectorp narray = create_verctorp(length);
		int index = 0;
		for (accuracy i = start; i < end; i += step) {
			narray->set(index++, i);			
		}		
		
		return narray;
	}

	Vectorp Vector::skip(int s)
	{
		int abss = abs(s);
		if (abss >= size) {
			//throw std::exception("skip size >= array size");
		}
		Vectorp narray = create_verctorp(size - abss);
		if (s > 0) {			
			int index = 0;
			for (int i = abss; i < size; i++) {
				narray->set(index++, get(i));
			}			
		}
		else {			
			for (int i = 0; i < size - abss; i++) {
				narray->set(i, get(i));
			}			
		}
		return narray;
	}

	Vectorp Vector::take(int s)
	{
		int abss = abs(s);
		if (abss >= size) {
			//throw std::exception("take size >= array size");
		}
		Vectorp narray = create_verctorp(abss);
		if (s > 0) {
			for (int i = 0; i < s; i++) {
				narray->set(i, get(i));
			}
		}
		else {
			int index = 0;
			for (int i = size - abss; i < size; i++) {
				narray->set(index++, get(i));
			}
		}
		return narray;
	}
	Vectorp Vector::take(int s1, int s2)
	{
		if (s2 > size) {
			//throw std::exception("take s2 > array size");
		}
		if (s1 >= s2) {
			//throw std::exception("take s1 >= s2");
		}
		Vectorp narray = create_verctorp(s2 - s1);
		int index = 0;
		for (int i = s1; i < s2; i++) {
			narray->set(index++, get(i));
		}
		return narray;
	}
	Vectorp Vector::dot(Vectorp b)
	{
		if (b == nullptr) {
			//throw std::exception("Vector multiply b == nullptr");
		}
		if (size != b->getSize()) {
			//throw std::exception("Vector multiply a's size != b's size");
		}
		Vectorp narray = create_verctorp(size);
		for (int i = 0; i < size; ++i) {
			narray->set(i, get(i) * b->get(i));
		}
		return narray;
	}

	Vectorp Vector::multiply(Matrixp b)
	{
		return create_verctorp(Matrix::multiply(b));
	}

	Vectorp Vector::multiply(accuracy b)
	{
		return create_verctorp(Matrix::multiply(b));
	}

	Vectorp Vector::divide(accuracy b)
	{
		return create_verctorp(Matrix::divide(b));
	}

	Vectorp Vector::add(accuracy b)
	{
		return create_verctorp(Matrix::add(b));
	}

	Vectorp Vector::subtract(Vectorp b)
	{
		return create_verctorp(Matrix::subtract(b));
	}

	
	Vectorp Vector::concatenate(Vectorp b)
	{
		return create_verctorp(Matrix::concatenate(b));
	}

	Vectorp Vector::logarithms()
	{
		return create_verctorp(Matrix::logarithms());
	}

	Vectorp Vector::map(accuracy(*func)(int, accuracy))
	{
		Vectorp narray = create_verctorp(size);
		for (int i = 0; i < size; ++i) {
			narray->set(i, func(i, get(i)));
		}
		return narray;
	}

	accuracy Vector::sum()
	{
		accuracy s = 0;
		for (int i = 0; i < size; ++i) {
			s += get(i);
		}
		return s;
	}

	Vectorp Vector::concatenate(accuracy a, Vectorp b)
	{
		if (b == nullptr) {
			//throw std::exception("concatenate b == nullptr");
		}
		int new_size = b->getSize() + 1;
		Vectorp narray = create_verctorp(new_size);
		narray->set(0, a);
		for (int i = 1; i < new_size; i++) {
			narray->set(i, b->get(i - 1));
		}
		return narray;
	}


}