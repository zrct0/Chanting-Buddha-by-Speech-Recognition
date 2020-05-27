#include "Vector.h"

namespace cpp_speech_features {

	int AutoGC::survivalCount = 0;
	bool AutoGC::GC_Enable = false;
	int AutoGC::GC_pIndex = 0;
	gctype * AutoGC::GC_pArray[gcmax] = { nullptr };

	void AutoGC::startGC()
	{
		GC_Enable = true;		
	}

	void AutoGC::insert(gctype * p)
	{
		if (!GC_Enable) {
			return;
		}
		bool insertSuccessed = false;
		for (int i = 0; i < gcmax; ++i) {
			if (GC_pArray[i] == nullptr) {				
				GC_pArray[i] = p;
				++survivalCount;
				insertSuccessed = true;
				break;
			}
		}
		if (!insertSuccessed) {
			throw "AutoGC insert object fail";
		}
	}

	int AutoGC::releaseAllWithout(gctype * p)
	{		
		int before = survivalCount;
		int releaseCount = 0;
		int skipCount = 0;
		for (int i = 0; i < gcmax; ++i) {
			if (GC_pArray[i] != nullptr) {
				gctype *c = GC_pArray[i];
				if (c != p) {
					delete c;
					GC_pArray[i] = nullptr;
					++releaseCount;
				}
				else {
					++skipCount;
				}
			}
		}		
		survivalCount -= releaseCount;
		GC_Enable = false;
		print(before, survivalCount, releaseCount);

		return releaseCount;
	}

	void AutoGC::print(int beforeCount, int afterCount, int releaseCount)
	{

	}

	void AutoGC::print(const char * tag)
	{

	}


}
