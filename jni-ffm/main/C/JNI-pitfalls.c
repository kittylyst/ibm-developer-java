#include "jni.h"

int sumValues2(JNIEnv* env, jobject obj, jobject allValues){

   jint avalue = (*env)->GetIntField(env, allValues, a);
   jint bvalue = (*env)->GetIntField(env, allValues, b);
   jint cvalue = (*env)->GetIntField(env, allValues, c);
   jint dvalue = (*env)->GetIntField(env, allValues, d);
   jint evalue = (*env)->GetIntField(env, allValues, e);
   jint fvalue = (*env)->GetIntField(env, allValues, f);

   return avalue + bvalue + cvalue + dvalue + evalue + fvalue;
}


