#pragma once
#include<exception>
#define gctype Matrix
#define gcmax 1000
#define gc_start AutoGC::startGC();
#define gc_release AutoGC::releaseAllWithout();
#define gc_release2(p) AutoGC::releaseAllWithout(p);
#define gc_print AutoGC::print();
#define gc_print2(t) AutoGC::print(t);
namespace cpp_speech_features {
	class Matrix;
	class AutoGC
	{
	public:
		static void startGC();
		static void insert(gctype * p);
		static int releaseAllWithout(gctype * p = nullptr);		
		static int survivalCount;
		static void print(const char * tag = nullptr);
	private:
		static bool GC_Enable;
		static int GC_pIndex;
		static gctype *GC_pArray[gcmax];
		static void print(int beforeCount, int afterCount, int releaseCount);
	};
}

