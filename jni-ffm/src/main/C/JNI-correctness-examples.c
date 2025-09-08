int calledALot(JNIEnv* env, jobject obj, jobject allValues){
   jclass cls = (*env)‑>GetObjectClass(env,allValues); 
   jfieldID a = (*env)‑>GetFieldID(env, cls, "a", "I");
   jfieldID b = (*env)‑>GetFieldID(env, cls, "b", "I");
   jfieldID c = (*env)‑>GetFieldID(env, cls, "c", "I");
   jfieldID d = (*env)‑>GetFieldID(env, cls, "d", "I");
   jfieldID e = (*env)‑>GetFieldID(env, cls, "e", "I");
   jfieldID f = (*env)‑>GetFieldID(env, cls, "f", "I");

}

jclass getObjectClassHelper(jobject object){ 
   /* use globally cached JNIEnv */
   return cls = (*globalEnvStatic)‑>GetObjectClass(globalEnvStatic, allValues); 
}

void withexceptions() {
    jclass objectClass;
    jfieldID fieldID;
    jchar result = 0;

    objectClass = (*env)‑>GetObjectClass(env, obj);
    fieldID = (*env)‑>GetFieldID(env, objectClass, "charField", "C");
    if((*env)‑>ExceptionOccurred(env)) {
        return;
    }

    fieldID = (*env)‑>GetFieldID(env, objectClass, "charField", "C");
    if (fieldID == NULL){
       fieldID = (*env)‑>GetFieldID(env, objectClass,"charField", "D");
    }
    result = (*env)‑>GetCharField(env, obj, fieldID);
}

void checkclasses() {
    clazz = (*env)‑>FindClass(env, "com/ibm/j9/HelloWorld");
    method = (*env)‑>GetStaticMethodID(env, clazz, "main", "([Ljava/lang/String;)V");
    (*env)‑>CallStaticVoidMethod(env, clazz, method, NULL);
}


