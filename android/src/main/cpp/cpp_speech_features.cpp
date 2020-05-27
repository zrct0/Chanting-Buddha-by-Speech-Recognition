#ifndef COUNTOR_CPP_SPEECH_FEATURES_H
#define COUNTOR_CPP_SPEECH_FEATURES_H
#include <jni.h>
#include <string>
#include "include/SpeechFeatures.h"

using namespace cpp_speech_features;

static const char *className = "com/miaoyue91/speechrecognize/speechRecognize/SpeechRecognizeMatch";

jfloatArray JNICALL extract_speech_features(JNIEnv *env, jobject clazz_object, jfloatArray jdataArray) {
    jfloat * jdata = env->GetFloatArrayElements(jdataArray, 0);
    jfloatArray resultArray;
    gc_start
    try {
        Vectorp data = new Vector(1024, jdata);
        Matrixp mfcc_feat0 = SpeechFeatures::mfcc(data);
        Matrixp mfcc_feat1 = SpeechFeatures::delta(mfcc_feat0, 1);
        Matrixp mfcc_feat2 = SpeechFeatures::delta(mfcc_feat0, 2);
        Matrixp feature = mfcc_feat0->concatenate(mfcc_feat1)->concatenate(mfcc_feat2);
        int size = feature->getRow() * feature->getColumn();
        resultArray = env->NewFloatArray(size);
        env->SetFloatArrayRegion(resultArray, 0, size, feature->getData());
    }
    catch (std::exception& e) {
        env->ThrowNew(env->FindClass("java/lang/Exception"), e.what());
    }
    gc_release
    env->ReleaseFloatArrayElements(jdataArray, jdata, 0);
    return resultArray;
}

static JNINativeMethod gJni_Methods_table[] = {
        {"extractSpeechFeaturesNative", "([F)[F", (void*)extract_speech_features},
};

static int jniRegisterNativeMethods(JNIEnv* env, const char* className, const JNINativeMethod* gMethods, int numMethods)
{
    jclass clazz;
    clazz = (env)->FindClass( className);
    if (clazz == NULL) {
        return -1;
    }
    int result = 0;
    if ((env)->RegisterNatives(clazz, gJni_Methods_table, numMethods) < 0) {
        result = -1;
    }
    (env)->DeleteLocalRef(clazz);
    return result;
}

jint JNI_OnLoad(JavaVM* vm, void* reserved){
    JNIEnv* env = NULL;
    jint result = -1;
    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        return result;
    }
    jniRegisterNativeMethods(env, className, gJni_Methods_table, sizeof(gJni_Methods_table) / sizeof(JNINativeMethod));
    return JNI_VERSION_1_4;
}

#endif //COUNTOR_CPP_SPEECH_FEATURES_H
