/*
 * This class holds all the data and methods for the Blockchain
 */
package edu.cmu.andrew.psdesai;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author psdes
 */
public class BlockChain {

    //Variables of Blockchain
    ArrayList<Block> blocklist;
    String chainHash;

    //BlockChain constructor
    public BlockChain() {
        blocklist = new ArrayList<>();
        chainHash = "";
        
        //Initialize the blockchain object and set the variable values for genesis block
        String data = "Genesis";
        int index = 0;
        int difficulty = 2;

        //Initialize the genesis block and set previousHash value
        Block block = new Block(index, getTime(), data, difficulty);
        block.setPreviousHash("");

       //Call the addBlock method
        addBlock(block);
    }

    //Getter for the current system time
    public java.sql.Timestamp getTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    //Getter for the latest block on the blockchain
    public Block getLatestBlock() {
        return blocklist.get(blocklist.size() - 1);
    }

    //Getter for the size of the blockchain(length of the block chain)
    public int getChainSize() {
        return blocklist.size();
    }


    //This method adds a block to the blockchain
    public void addBlock(Block newBlock) {

        //check if chain consists of any block or not
        if (getChainSize() > 0) {
            //set the previousHash value of the new block equal to the chainHash
            newBlock.previousHash = chainHash;
            //add the block to chain
            blocklist.add(newBlock);
            //calculate proofOfWork for new block
            chainHash = newBlock.proofOfWork();
        } else {
            //if there is no block in the chain previusly, first add the new block to the chain
            blocklist.add(newBlock);
            //calculate proofOfWork for new block
            chainHash = newBlock.proofOfWork();
        }
    }

    //This method overrides the toString method of the blockchain 
    @Override
    public java.lang.String toString() {

        //Initialize a json object
        JSONObject json = new JSONObject();
        //Initialize a json array
        JSONArray array = new JSONArray();
        
        //Loop over all the blocks of the chain and call the toString method of each block
        //Store the json string of each block in a json array object
        for (Block b : blocklist) {
            array.put(b.toString());
        }

       //Store the contents of the array object and variable chainHash into json object
        try {
            json.put("ds_chain", array);
        } catch (JSONException ex) {
            Logger.getLogger(BlockChain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            json.put("chainHash", chainHash);
        } catch (JSONException ex) {
            Logger.getLogger(BlockChain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json.toString());
        String prettyJsonString = gson.toJson(je);
        
        //return json string
        return prettyJsonString.replace("\\","");
      
    }

    //This method checks if the Blockchain is tampered or not
    public boolean isChainValid() {

        int size = getChainSize();

        //check if the blockchain only contains the genesis block or it has more blocks
        //Chheck the validity of chain accordingly
        if (size == 1) {

            //calculate hash of the genesis block
            String hashedString = getLatestBlock().calculateHash();

            //variable to count the number of 0's
            int zerocount = 0;

            //chop off leftmost digits equal to the difficulty
            String check = hashedString.substring(0, getLatestBlock().getDifficulty());

            //save the chopped digits in an array by splitting at each digit
            String[] checkarray = check.split("");

            //flags to check whether the count of 0's and the hashing was correct
            boolean countflag = false;
            boolean hashflag = false;

            //Loop over the array and check if each digit is equal to "0"
            for (int i = 0; i < checkarray.length; i++) {
                String c = checkarray[i];

                //checks if the digit is equal to "0" and increments the count
                if (c.equals("0")) {
                    zerocount++;
                }
            }

            //Check if the count of zeros and the difficulty value are equal
            //Set the countflag to true/false accordingly
            if (zerocount == getLatestBlock().getDifficulty()) {
                countflag = true;
            } else {
                countflag = false;
            }

            //Check if the chainhash is equal to the computed hash of the genesis block
            //Set the hashflag to true/false accordingly
            if (chainHash.equals(hashedString)) {
                hashflag = true;
            } else {
                hashflag = false;
            }

            //If both the conditions are met, it means that chain is valid, hence return true
            //If any of the condition is not met, it means that chain is not valid, hence return false;
            if (countflag == true && hashflag == true) {
                return true;
            } else {
                return false;
            }
        } else {

            boolean continueflag = true;
            
            //Loop over the entire blockchain 
            for (int i = 0; i < blocklist.size() - 1; i++) {

                //calculate hash for the initial block
                String hash1 = blocklist.get(i).calculateHash();
                
                //Retreive value of previousHash field of next block
                String hash2 = blocklist.get(i + 1).previousHash;

                //check whether the two values are equal
                //and set the continueflag to true/false accordingly
                if (hash1.equals(hash2)) {
                    continueflag = true;
                } else {
                    continueflag = false;
                  //get difficulty level of block  
                  int diff =  blocklist.get(i).getDifficulty();
                  //create a string of zeros as much as the difficulty
                  String zerostring = "";
                  for(int j=0; j<diff; j++){
                      zerostring += "0";
                  }
                  //Display the details of block where the verification failed
                    System.out.println("..Improper hash on node " + i + " Does not begin with " + zerostring);
                    break;
                }
            }
            
            //if the continueflag is false, it means that ine of the condition is not met
            //and the chain is invalid, hence return false
            if (continueflag == false) {
                return false;
            } 
            //if the the continueflag is still true even after looping through the entire blockchain
            //then check the condition for the chainhash for latest block
            else {
                //if the computed hash and chainHash is equal for the latest block on the chain,
                //then it means that the chain is valid and hence return true
                //if they are not equal, then the chain is invalid, hence return false
                if (blocklist.get(blocklist.size() - 1).calculateHash().equals(chainHash)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public static void main(java.lang.String[] args) {
        
    }
}
