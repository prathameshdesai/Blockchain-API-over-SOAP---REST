/*
 * This class performs all the methods related to the Blockchain
 * All the operations of adding a block, viewing the status of blockchain, checking if the chain is valid,
 * Corrupting a chain, repairing a chain are implemented in this class.
 */
package blockchaintask0;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
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

    //This method calculates the number of times a hash can be calculated by this computer in 1 second
    public int hashesPerSecond() {

        //String to be hashed
        String text = "00000000";

        Block b = new Block(0, getTime(), text, 2);
        b.setPreviousHash("");
        boolean exit = false;

        //count of hashes in a second
        int countofHash = 0;

        //time before beginning to calculate the hash
        long starttime = System.currentTimeMillis();

        //keep on calculating the hash for 1 second
        while (exit == false) {

            //calls the method to calculate hash
            b.calculateHash();
            //increments the count of hashes
            countofHash++;

            //get out of the loop after 1 second ends (1 second = 1000 milliseconds)
            if (System.currentTimeMillis() >= (starttime + 1000)) {
                exit = true;
            }
        }
        //return the count of hashes in a second
        return countofHash;
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
            
            //if the continueflag is false, it means that all of the conditions are not met
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

    //This method repairs the chain.
    //It checks the hashes of each block and recomputes hash for those blocks that have illegal hash.
    public void repairChain() {

        boolean continueflag = true;
        
        //Loop over the entire blockchain 
        for (int i = 0; i < blocklist.size() - 1; i++) {
            
            //calculate hash for the initial block
            String hash1 = blocklist.get(i).calculateHash();
            
            //Retreive value of previousHash field of next block
            String hash2 = blocklist.get(i + 1).previousHash;

            //check whether the two values are equal
            //and set the continueflag to true/false accordingly
            //If the hashes are not equal, recompute the hash by calling proofOfWork
            if (hash1.equals(hash2)) {
                continueflag = true;
            } else {
                continueflag = false;
                //compute proofOfWork for current block
                hash1 = blocklist.get(i).proofOfWork();
                //set previousHash of next block to the recomputed current block's hashed value
                blocklist.get(i + 1).setPreviousHash(hash1);
            }
        }

        //If the calsulated hash for the latest block is not equal to the variable chainHash
        //Recompute the hash for the latest block by calling proofOfWork
        if (!blocklist.get(getChainSize() - 1).calculateHash().equals(chainHash)) {
            chainHash = blocklist.get(getChainSize() - 1).proofOfWork();
        }
    }

    public static void main(java.lang.String[] args) {

        //Initialize the blockchain object and set the variable values for genesis block
        BlockChain bc = new BlockChain();
        String data = "Genesis";
        int index = 0;
        int difficulty = 2;
        
        //Initialize the genesis block and set previousHash value
        Block block = new Block(index, bc.getTime(), data, difficulty);
        block.setPreviousHash("");
        bc.blocklist = new ArrayList<>();
        
        //Call the addBlock method
        bc.addBlock(block);
     
        boolean exit = false;
        //Keep on looping and ask for user input after every selection
        while (exit == false) {

            //Display the options to the user
            System.out.println("Block Chain Menu");
            System.out.println("0. View basic blockchain status.");
            System.out.println("1. Add a transaction to the blockchain.");
            System.out.println("2. Verify the blockchain.");
            System.out.println("3. View the blockchain.");
            System.out.println("4. Corrupt the chain.");
            System.out.println("5. Hide the corruption by repairing the chain.");
            System.out.println("6. Exit.");

            //Retrieve the input from the user
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            sc.nextLine();

            //Switch over each case as per the user selection
            switch (input) {
                
                //Display the blockchain status
                case 0: {
                    //Display Current size of chain by calling by calling the getChainSize method
                    System.out.println("Current size of chain: " + bc.getChainSize());
                    //Display the number of hashes the machine can compute in one second by calling hashesPerSecond method
                    System.out.println("Current hashes per second by this machine : " + bc.hashesPerSecond());
                    //Display the difficulty of the most recent block by calling the getLatestBlock and getDifficulty method
                    System.out.println("Difficulty of most recent block : " + bc.getLatestBlock().getDifficulty());
                    //Display the Nonce for most recent block by calling the getLatestBlock and getNonce method
                    System.out.println("Nonce for most recent block : " + bc.getLatestBlock().getNonce());
                    //Display the value of the chainHash field of the blockchain
                    System.out.println("Chain hash : " + bc.chainHash);

                    break;
                }
                //Add a transaction to the blockchain
                case 1: {
                    //Prompt the user to enter the difficulty and retrieve the user input
                    System.out.println("Enter difficulty > 0");
                    difficulty = sc.nextInt();
                    sc.nextLine();
                    //Prompt the user to enter transaction details and retrieve the user input
                    System.out.println("Enter transaction");
                    //Increment index
                    index++;
                    data = sc.nextLine();
                    
                    //Initialize a block with the details retreived from the user
                    Block b = new Block(index, bc.getTime(), data, difficulty);
                    //Store the current time before adding the block
                    long starttime = System.currentTimeMillis();
                    //Call the addBlock method
                    bc.addBlock(b);
                    //Store the current time after adding the block
                    long endtime = System.currentTimeMillis();
                    //Calculate the totalexecutiontime by computing the difference between the starttiem and endtime
                    long totalexecutiontime = endtime - starttime;
                    //Display the totalexecutiontime
                    System.out.println("Total execution time to add this block was " + totalexecutiontime + " milliseconds");
                    
                    //It was observed that when the blocks with difficulty level for 4 were added,
                    //The total execution time for adding the block was far lesser as compared to
                    //the total execution time required for adding blocks with difficulty 5
                    //To get a clear picture, several transactions were done with adding blocks of difficulty 4
                    //The average of total execution time was (88 + 297 + 531 + 130 + 159) / 5 = 241 milliseconds
                    
                    //Likewise several blocks with difficulty level for 5 were added,
                    //The average of total execution time was (3183 + 863 + 2140 + 2810 + 3770) / 5 = 2553.2 milliseconds
                    
                    //So, basically it was observed that the total execution time for adding a block increased to a great extent
                    //with increase in the difficulty levels.
                    //In this case, it was observed that the total execution time for adding blocks with difficulty 5 was almost
                    // 10 times as compared to the total execution time for adding blocks with difficulty 4.
                  
                    break;
                }
                //Verify the contents of the blockchain
                case 2: {
                    System.out.println("Verifying entire chain");
                    //Store the current time before verification
                    long starttime = System.currentTimeMillis();
                    //call the isChainValid method to validate
                    boolean valid = bc.isChainValid();
                    //Store the current time after verification
                    long endtime = System.currentTimeMillis();
                    //Display the result of the verification
                    System.out.println("Chain verification: " + valid);
                    //Calculate the totalexecutiontime by computing the difference between the starttiem and endtime
                    long totalexecutiontime = endtime - starttime;
                    //Display the totalexecutiontime
                    System.out.println("Total execution time required to verify the chain was " + totalexecutiontime + " milliseconds");
                    
                    //It was observed that the total execution time for verification of the blocks was very low, 
                    //approximately 0 milliseconds, as it showed o milliseconds with chain of various sizes
                    //Thus, it can be said that whatever the difficulty of blocks in the blockchain is,
                    //The time for verification is not affected much with the level of difficulty of blocks
                    //and verification is done quickly as seen it gets compelted in almost 0 milliseconds
                    
                    break;
                }
                //View the blockchain
                case 3: {
                    //Call the blockchain's toString method
                    System.out.println(bc.toString());
                    break;
                }
                //Corrupt the chain
                case 4: {
                    System.out.println("Corrupt the Blockchain");
                    //Ask user for the index of the block to corrupt and retreive the user input
                    System.out.println("Enter block ID of block to Corrupt");
                    int corrupt_index = sc.nextInt();
                    sc.nextLine();
                    //Ask the user for the new data
                    System.out.println("Enter new data for block " + corrupt_index);
                    String corrupt_data = sc.nextLine();

                    //Retreive that block from the blockchain
                    Block b = bc.blocklist.get(corrupt_index);
                    //Replace the previosu data of the block with the new data
                    b.setData(corrupt_data);
                    System.out.println("Block " + corrupt_index + " now holds " + corrupt_data);
                    break;
                }
                //Repair the chain to hide the corruption
                case 5: {
                    System.out.println("Repairing the entire chain");
                    //Store the current time before repairment
                    long starttime = System.currentTimeMillis();
                    //call the repairChain method
                    bc.repairChain();
                    //Store the current time after repairment
                    long endtime = System.currentTimeMillis();
                    //Calculate the totalexecutiontime by computing the difference between the starttiem and endtime
                    long totalexecutiontime = endtime - starttime;
                    System.out.println("Total execution time required to repair the chain was " + totalexecutiontime + " milliseconds");
                    
                    //The average of total execution time for repairing a chain of 4 blocks of difficulty 4 was 432 milliseconds
                    //The average of total execution time for repairing a chain of 4 blocks of difficulty 5 was 4233 milliseonds
                    //Thus, it is observed that as the difficulty of blocks in the blockchain is increases, the time required for
                    //repairing the blockchain increases to a huge extent (in this case it increases 10 times when difficulty 
                    //increased from 4 to 5.
                    
                    break;
                }
                case 6: {
                    //Set the boolean to true to stop the program
                    exit = true;
                    break;
                }
                default: {
                    System.out.println("Please enter a valid  option");
                    break;
                }
            }
        }
    }
}
