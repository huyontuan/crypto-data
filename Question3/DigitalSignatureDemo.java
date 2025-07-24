//Name: On Tuan Huy
//sID: s4028018

package Question3;

import java.security.*;
import java.util.Base64;
import java.util.Scanner;

//   This simulates how blockchains verify authenticity and integrity of data.

public class DigitalSignatureDemo {

    //   Generates an RSA key pair (public/private keys).
     
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // 2048-bit RSA key
        return keyGen.generateKeyPair();
    }

    //   Signs a message using the private key.
     
    public static byte[] sign(String message, PrivateKey privateKey)
            throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey); // Use private key to sign
        privateSignature.update(message.getBytes());
        return privateSignature.sign(); // Returns raw byte signature
    }

    //   Verifies a signature with the public key.
 
    public static boolean verify(String message, byte[] signature, PublicKey publicKey)
            throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey); // Use public key to verify
        publicSignature.update(message.getBytes());
        return publicSignature.verify(signature); // true if valid
    }

    public static void main(String[] args) {
        try {
            // Input from user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a message to sign: ");
            String message = scanner.nextLine();
            scanner.close();

            // 1. Generate key pair
            KeyPair keyPair = generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // 2. Sign the message
            byte[] signatureBytes = sign(message, privateKey);
            String signatureBase64 = Base64.getEncoder().encodeToString(signatureBytes);

            // 3. Verify the signature
            boolean isValid = verify(message, signatureBytes, publicKey);

            // 4. Output everything
            System.out.println("\n--- Digital Signature Output ---");
            System.out.println("Original Message: " + message);
            System.out.println("Signature (Base64): " + signatureBase64);
            System.out.println("Public Key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            System.out.println("Private Key: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
            System.out.println("Signature Verified: " + isValid);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

