package com.ibm.examples.jni;

/**
 * Main class to demonstrate JNI functionality
 * 
 * This class shows how to call native C functions from Java
 * and demonstrates callback functionality from C to Java.
 */
public class JNIExampleMain {
    
    public static void main(String[] args) {
        System.out.println("=== JNI Examples Demo ===");
        System.out.println();
        
        // Create an instance of our JNI class
        JNIExamples jniExample = new JNIExamples();
        
        // Test cases with different seed strings
        String[] testSeeds = {
            "hello",
            "world",
            "java",
            "native",
            "interface",
            "random-seed-string",
            "this-is-a-longer-seed-value-for-testing",
            "",
            "123456789"
        };
        
        System.out.println("Testing improveRNGSeed function:");
        System.out.println("Input String -> Improved Seed");
        System.out.println("-----------------------------");
        
        for (String seed : testSeeds) {
            try {
                int improvedSeed = jniExample.improveRNGSeed(seed);
                System.out.printf("'%s' -> %d%n", seed, improvedSeed);
            } catch (Exception e) {
                System.err.printf("Error processing seed '%s': %s%n", seed, e.getMessage());
            }
        }
        
        System.out.println();
        System.out.println("=== Test with very long string ===");
        
        // Test with a very long string (should be truncated)
        StringBuilder longSeed = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            longSeed.append("long");
        }
        
        String longSeedStr = longSeed.toString();
        System.out.printf("Original length: %d characters%n", longSeedStr.length());
        
        try {
            int improvedSeed = jniExample.improveRNGSeed(longSeedStr);
            System.out.printf("Improved seed: %d%n", improvedSeed);
        } catch (Exception e) {
            System.err.printf("Error processing long seed: %s%n", e.getMessage());
        }
        
        System.out.println();
        System.out.println("=== Demo Complete ===");
        System.out.println("Note: The 'Calling back from native' messages show");
        System.out.println("the native C code calling back into Java methods.");
    }
} 