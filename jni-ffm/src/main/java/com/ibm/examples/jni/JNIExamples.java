package com.ibm.examples.jni;

public class JNIExamples {

    static {
        System.loadLibrary("jni-examples");
    }

    public int improveRNGSeed(String initialSeed) {
        if (initialSeed.length() > 1024) {
            initialSeed = initialSeed.substring(0, 1023);
        }
        if (initialSeed == null) {
            initialSeed = "";
        }
        return improveRNGSeed0(initialSeed);
    }

    public native int improveRNGSeed0(String initialSeed);

    public static void setInfo(int fromNative) {
        System.out.println("Calling back from native: "+ fromNative);
    }
}
