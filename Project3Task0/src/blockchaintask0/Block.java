/*
 * This class holds all the information of a block in the blcokchain
 */
package blockchaintask0;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author psdes
 */
public class Block {

    //Block Variables
    int index;
    java.sql.Timestamp timestamp;
    java.lang.String data;
    int difficulty;
    String previousHash = "";
    BigInteger nonce = new BigInteger("0");

    //Block Constructor
    public Block(int index, java.sql.Timestamp timestamp, java.lang.String data, int difficulty) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.difficulty = difficulty;
    }

    //This method computes the hash for the string that is passed to it using SHA-256
    //Returns output in String format by calling convertToHex method
    public java.lang.String calculateHash() {

        try {

            String text = Integer.toString(getIndex()) + getTimestamp().toString() + getData() + getPreviousHash() + getNonce().toString() + Integer.toString(getDifficulty());
            // Create a SHA256 digest
            MessageDigest digest;
            digest = MessageDigest.getInstance("SHA-256");
            // allocate room for the result of the hash
            byte[] hashBytes;
            // perform the hash
            digest.update(text.getBytes("UTF-8"), 0, text.length());
            // collect result
            hashBytes = digest.digest();
            return javax.xml.bind.DatatypeConverter.printHexBinary(hashBytes);
        } catch (NoSuchAlgorithmException nsa) {
            System.out.println("No such algorithm exception thrown " + nsa);
        } catch (UnsupportedEncodingException uee) {
            System.out.println("Unsupported encoding exception thrown " + uee);
        }
        return null;
    }

    //Getter for Nonce
    public java.math.BigInteger getNonce() {
        return nonce;
    }

    //proofOfWork finds a hash in such a manner that it first checks the difficulty level for the block
    // Then checks those leftmost hex digits of the hash equal to the difficulty if they are equal to "0"
    //It keeps on finding the hash by increasing a BigInteger nonce until it gets as many leftmost hex 0's as difficulty
    public java.lang.String proofOfWork() {

        //Biginteger nonce
        nonce = BigInteger.valueOf(0);

        //boolean to check if the proofofwork condition is met
        boolean flag = false;

        String hashedString = null;

        //loop until the proofofwork condition is met
        while (flag == false) {

            int zerocount = 0;

            //send the string to calculateHash
            hashedString = calculateHash();

            //chop off leftmost digits equal to the difficulty
            String check = hashedString.substring(0, difficulty);

            //save the chopped digits in an array by splitting at each digit
            String[] checkarray = check.split("");

            //Loop over the array and check if each digit is equal to "0"
            for (int i = 0; i < checkarray.length; i++) {
                String c = checkarray[i];

                //checks if the digit is equal to "0" and increments the count
                if (c.equals("0")) {
                    zerocount++;
                }
            }

            //If there are as many leftmost 0's as much as the difficulty, set the flag to true to get out of the loop
            //Otherwise increment the nonce and again enter the loop to check the new hash
            if (zerocount == difficulty) {
                flag = true;
            } else {
                nonce = nonce.add(BigInteger.ONE);
            }
        }
        return hashedString;
    }

    //Getter for Difficulty
    public int getDifficulty() {
        return difficulty;
    }

    //Setter for Difficulty
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    //Override the toString method to display the output in JSON Format
    @Override
    public java.lang.String toString() {

        try {
            //Initialize a JSONObject
            JSONObject json = new JSONObject();

            //Code Reference for below code is Stack Overflow
            //As Json information does not stay ordered, converting the default hashmap od json to LinkedHashMap
            Field map = null;
            try {
                map = json.getClass().getDeclaredField("map");
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            }
            map.setAccessible(true);//because the field is private final...
            try {
                map.set(json, new LinkedHashMap<>());
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            }
            map.setAccessible(false);//return flag

            //Store all the values of the variables in that object
            json.put("index", getIndex());
            json.put("timestamp", getTimestamp());
            json.put("Tx ", getData());
            json.put("previousHash", getPreviousHash());
            json.put("nonce", getNonce());
            json.put("difficulty", getDifficulty());

            //Return the object by calling its toString method
            return json.toString();
        } catch (JSONException ex) {
            Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Setter for PreviousHash, as in the hash calculated for the block previous to the current one
    public void setPreviousHash(java.lang.String previousHash) {
        this.previousHash = previousHash;
    }

    //Getter for PreviousHash, as in the hash calculated for the block previous to the current one
    public java.lang.String getPreviousHash() {
        return previousHash;
    }

    //Getter for Index  
    public int getIndex() {
        return index;
    }

    //Setter for Index    
    public void setIndex(int index) {
        this.index = index;
    }

    //Setter for Timestamp of the block 
    public void setTimestamp(java.sql.Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    //Getter for Timestamp of the block 
    public java.sql.Timestamp getTimestamp() {
        return timestamp;
    }

    //Getter for Data of the block 
    public java.lang.String getData() {
        return data;
    }

    //Setter for Data of the block 
    public void setData(java.lang.String data) {
        this.data = data;
    }

    //Main method of the Block
    public static void main(java.lang.String[] args) {

    }
}
