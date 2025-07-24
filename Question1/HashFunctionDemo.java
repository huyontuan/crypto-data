package Question1;

import java.security.MessageDigest;
import java.util.Scanner;

public class HashFunctionDemo {

    // Function: hash
    // Purpose: Takes an input string and returns its SHA-256 hash in hexadecimal
    
    public static String hash(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Get SHA-256 hash function
        byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));   // Hash the input string
        StringBuilder hexString = new StringBuilder();

        for (byte b : hashBytes)
            hexString.append(String.format("%02x", b)); // Convert each byte to hex
        return hexString.toString(); // Return hex-encoded hash
    }

    // Function: hammingDistance
    // Purpose: Counts the number of character differences between two hex strings
    // Used to illustrate the avalanche effect
    
    public static int hammingDistance(String hash1, String hash2) {
        int distance = 0;
        for (int i = 0; i < hash1.length(); i++) {
            if (hash1.charAt(i) != hash2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    // Demo 1: Avalanche Effect
    // Purpose: Show how a small input change causes a large hash change
    
    public static void avalancheDemo() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string to hash: ");
        String input = scanner.nextLine();

        // Compute original hash
        String hashOriginal = hash(input);
        System.out.println("Original Hash:  " + hashOriginal);

        // Modify input slightly (append a character)
        String modified = input + "1";
        String hashModified = hash(modified);
        System.out.println("Modified Input: " + modified);
        System.out.println("Modified Hash: " + hashModified);

        // Show difference between hashes
        int distance = hammingDistance(hashOriginal, hashModified);
        System.out.println("Hamming Distance between hashes: " + distance + "/64");
    }
    
    // Demo 2: Pre-image Resistance
    // Purpose: Try to find an input that matches a known hash (brute-force search)
    // This simulates how hard it is to reverse a secure hash
    
    public static void preImageDemo() throws Exception {
        String targetInput = "abc";              // Known input
        String targetHash = hash(targetInput);   // Compute its hash
        System.out.println("Target input: \"" + targetInput + "\"");
        System.out.println("Target Hash: " + targetHash);

        int maxAttempts = 1_000_000;             // Limit attempts to avoid long runtime
        boolean found = false;
        String attempt = "";

        for (int i = 0; i < maxAttempts; i++) {
            attempt = Integer.toHexString(i);    // Try a different string each time
            String attemptHash = hash(attempt);

            if (attemptHash.equals(targetHash)) { // Check if hashes match
                found = true;
                System.out.println("Match found! Input: " + attempt);
                break;
            }
        }

        if (!found) {
            System.out.println("No pre-image found after " + maxAttempts + " attempts.");
        }
    }
    
    // Main Menu
    // Allows the user to choose which demo to run
    
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Cryptographic Hash Function Demo");
        System.out.println("1. Avalanche Effect Demo");
        System.out.println("2. Pre-image Resistance Demo");
        System.out.print("Choose an option (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline

        if (choice == 1) {
            avalancheDemo();
        } else if (choice == 2) {
            preImageDemo();
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
