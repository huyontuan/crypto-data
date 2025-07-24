package Question4;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

//   Simulates a basic blockchain with timestamping and cryptographic linking between blocks.
//   Each block contains mock transaction data and is chained via previous hash.

public class SimpleBlockchainDemo {

    static class Block {
        int id;
        long timestamp;
        String data;
        String previousHash;
        int nonce;
        String hash;

        //   Constructor for the Block class.
        //   Automatically sets timestamp, random nonce, and calculates the hash.

        public Block(int id, String data, String previousHash) {
            this.id = id;
            this.timestamp = System.currentTimeMillis();
            this.data = data;
            this.previousHash = previousHash;
            this.nonce = new Random().nextInt(100000);  // Random nonce for variety
            this.hash = calculateHash();
        }

        //   Calculates the SHA-256 hash of the block's content.
        //   Ensures the block is uniquely and securely linked.

        public String calculateHash() {
            try {
                String input = id + Long.toString(timestamp) + data + previousHash + nonce;
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = digest.digest(input.getBytes());

                // Convert byte array to hexadecimal string
                StringBuilder hexString = new StringBuilder();
                for (byte b : hashBytes) {
                    hexString.append(String.format("%02x", b));
                }
                return hexString.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //   Returns a human-readable string representation of the block.

        public String toString() {
            return "Block ID: " + id +
                   "\nTimestamp: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp)) +
                   "\nData: " + data +
                   "\nNonce: " + nonce +
                   "\nPrevious Hash: " + previousHash +
                   "\nHash: " + hash +
                   "\n---------------------------";
        }
    }

    // Generate fake transaction data

    public static String generateFakeData(int blockNum) {
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
        String sender = names[new Random().nextInt(names.length)];
        String receiver = names[new Random().nextInt(names.length)];

        // Prevent same-name transactions
        while (receiver.equals(sender)) {
            receiver = names[new Random().nextInt(names.length)];
        }

        int amount = new Random().nextInt(100) + 1;
        return sender + " -> " + receiver + " : " + amount + " tokens";
    }

    public static void main(String[] args) {
        List<Block> blockchain = new ArrayList<>();
        int numberOfBlocks = 5;

        // Step 1: Create Genesis Block (first block)
        Block genesis = new Block(0, "Genesis Block: Initial setup", "0");
        blockchain.add(genesis);
        System.out.println(genesis);

        // Step 2: Create subsequent blocks
        for (int i = 1; i < numberOfBlocks; i++) {
            try {
                Thread.sleep(1000); // Simulated block delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            String fakeData = generateFakeData(i);
            Block previous = blockchain.get(i - 1);
            Block newBlock = new Block(i, fakeData, previous.hash);
            blockchain.add(newBlock);
            System.out.println(newBlock);
        }
    }
}
