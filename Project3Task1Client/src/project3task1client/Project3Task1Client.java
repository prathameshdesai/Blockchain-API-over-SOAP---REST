/*
 *This class contains client side program
 *User input is taken regarding which operation needs to be performed
 *Other data related to the operation is also retrieved from the user
 *Then the data is sent to the server side.
 *The method to which the data is sent depends on the type of operation to be performed.
 */
package project3task1client;

import java.util.Scanner;

/**
 *
 * @author psdes
 */
public class Project3Task1Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //call this method to create the genesis block and add it to the chain
        createGenesisBlock();

        boolean exit = false;
        //Keep on looping and ask for user input after every selection
        while (exit == false) {

            //Display the options to the user
            System.out.println("Block Chain Menu");
            System.out.println("1. Add a transaction to the blockchain.");
            System.out.println("2. Verify the blockchain.");
            System.out.println("3. View the blockchain.");
            System.out.println("4. Exit.");

            //Retrieve the input from the user
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            sc.nextLine();

            //Switch over each case as per the user selection
            switch (input) {

                //Add a transaction to the blockchain
                case 1: {
                    //Prompt the user to enter the difficulty and retrieve the user input
                    System.out.println("Enter difficulty > 0");
                    int difficulty = sc.nextInt();
                    sc.nextLine();
                    //Prompt the user to enter transaction details and retrieve the user input
                    System.out.println("Enter transaction");
                    //Increment index
                    //index++;
                    String data = sc.nextLine();

                    //Store the current time before adding the block
                    long starttime = System.currentTimeMillis();
                    //Call the addBlock method
                    addBlock(difficulty, data);
                    //Store the current time after adding the block
                    long endtime = System.currentTimeMillis();
                    //Calculate the totalexecutiontime by computing the difference between the starttiem and endtime
                    long totalexecutiontime = endtime - starttime;
                    //Display the totalexecutiontime
                    System.out.println("Total execution time to add this block was " + totalexecutiontime + " milliseconds");
                    break;
                }
                //Verify the contents of the blockchain
                case 2: {
                    System.out.println("Verifying entire chain");
                    //Store the current time before verification
                    long starttime = System.currentTimeMillis();
                    //call the isChainValid method to validate
                    boolean valid = isChainValid();
                    //Store the current time after verification
                    long endtime = System.currentTimeMillis();
                    //Display the result of the verification
                    System.out.println("Chain verification: " + valid);
                    //Calculate the totalexecutiontime by computing the difference between the starttiem and endtime
                    long totalexecutiontime = endtime - starttime;
                    //Display the totalexecutiontime
                    System.out.println("Total execution time required to verify the chain was " + totalexecutiontime + " milliseconds");
                    break;
                }
                case 3: {
                    //Call the viewBlockChain method
                    System.out.println(viewBlockChain());
                    break;
                }
                case 4: {
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

    //This method connects with the createGenesisBlock at the server side
    private static void createGenesisBlock() {
        edu.cmu.andrew.psdesai.BlockChainService_Service service = new edu.cmu.andrew.psdesai.BlockChainService_Service();
        edu.cmu.andrew.psdesai.BlockChainService port = service.getBlockChainServicePort();
        port.createGenesisBlock();
    }

    //This method connects with the isChainValid at the server side
    private static boolean isChainValid() {
        edu.cmu.andrew.psdesai.BlockChainService_Service service = new edu.cmu.andrew.psdesai.BlockChainService_Service();
        edu.cmu.andrew.psdesai.BlockChainService port = service.getBlockChainServicePort();
        return port.isChainValid();
    }

    //This method connects with the viewBlockChain at the server side
    private static String viewBlockChain() {
        edu.cmu.andrew.psdesai.BlockChainService_Service service = new edu.cmu.andrew.psdesai.BlockChainService_Service();
        edu.cmu.andrew.psdesai.BlockChainService port = service.getBlockChainServicePort();
        return port.viewBlockChain();
    }

    //This method connects with the addBlock at the server side
    private static void addBlock(int difficulty, java.lang.String data) {
        edu.cmu.andrew.psdesai.BlockChainService_Service service = new edu.cmu.andrew.psdesai.BlockChainService_Service();
        edu.cmu.andrew.psdesai.BlockChainService port = service.getBlockChainServicePort();
        port.addBlock(difficulty, data);
    }
}
