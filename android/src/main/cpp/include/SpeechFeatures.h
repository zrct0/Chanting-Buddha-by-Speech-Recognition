#pragma once
#include "Vector.h"
#define MINIMAL_NUMBER 0.0000000000000002220446049250313
#define M_PI 3.14159265358979323846
namespace cpp_speech_features {
	class SpeechFeatures
	{
	public:
		static Matrixp mfcc(Vectorp signal, int samplerate = 16000, accuracy winlen = 0.025, accuracy winstep = 0.01, int numcep = 13, int nfilt = 26, int nfft = 512, int lowfreq = 0, int highfreq = -1, accuracy preemph = 0.97, int ceplifter = 22, bool appendEnergy = true, void * winfunc = nullptr);
		static Matrixp delta(Matrixp feat, int N);
	private:
		static Matrixp fbank(Vectorp * energy, Vectorp signal, int samplerate = 16000, accuracy winlen = 0.025, accuracy winstep = 0.01, int nfilt = 26, int nfft = 512, int lowfreq = 0, int highfreq = -1, accuracy preemph = 0.97, void * winfunc = nullptr);
		static Vectorp matrix_rowsum(Matrixp matrixp);
		static Matrixp feat_dct(Matrixp feat, int numcep);
		static Matrixp lifter(Matrixp cepstra, int L = 22);
		static Vectorp preemphasis(Vectorp signal, accuracy coeff = 0.95);
		static Matrixp framesig(Vectorp sig, accuracy frame_len, accuracy frame_step, void * winfunc = nullptr);
		static Matrixp powspec(Matrixp framesj, int NFFT);
		static Matrixp get_filterbanks(int nfilt = 26, int nfft = 512, int samplerate = 16000, int lowfreq = 0, int highfreq = -1);
		static Matrixp magspec(Matrixp framesj, int NFFT);
		static Matrixp tile(Vectorp a, Vectorp reps);
		static Matrixp array_extract(Vectorp a, Matrixp indices);
		static Matrixp dct(Matrixp data);
		static Vectorp dctWithOrtho(Vectorp data, accuracy scale);
		static void rfft(accuracy * x, int n);
	};
}

