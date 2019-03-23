/*
 * This class contains client side program
 *User input is taken regarding which operation needs to be performed
 *Other data related to the operation is also retrieved from the user
 *Then the data is sent to the server side.
 *This is done using doGet and doPost methods by calling the GET and POST mehtods of server
 */
package project3task3client;

/**
 *
 * @author psdes
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class Project3Task3Client {

     public static void main(String[] args) throws Exception{
      
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
                    
                    String data = sc.nextLine();
                    
                    //Concatanate inputstring with input, difficulty and data along with comma as a delimiter
                    String inputstring = input + "," + difficulty + "," + data;

                    //Store the current time before adding the block
                    long starttime = System.currentTimeMillis();
                    //Call the addBlock method
                    addBlock(inputstring);
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
                    
                    //In this case, inputstring will consist only of input
                    String inputstring = Integer.toString(input);
                    
                    //Store the current time before verification
                    long starttime = System.currentTimeMillis();
                    //call the read method to validate
                    String valid = read(inputstring);
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
                    //In this case, inputstring will consist only of input
                    String inputstring = Integer.toString(input);
                    //call the read method
                    String output = read(inputstring);
                    
                    //Display the output
                    System.out.println(output);
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
   
    // This method sends data to add a block
    public static int addBlock(String inpuString) {
         //Call doPost method in the client
         int output =  doPost(inpuString);
         //return the output
         return output;
         
    }
    
    // read output from the server
    public static String read(String inputString) {
          //call doGet method with the inputstring
          String output =  doGet(inputString);
          //return the output
          return output;
    }
        
    // Low level routine to make an HTTP POST request
    // Note, POST does not use the URL line for its message to the server
    public static int doPost(String inpuString) {
          
        int status = 0;
        String output;
        
        try {  
                // Make call to a particular URL
		URL url = new URL("http://localhost:8090/Project3Task3Server/BlockChainService");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
                // set request method to POST and send name value pair
                conn.setRequestMethod("POST");
		conn.setDoOutput(true);
                // write to POST data area
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(inpuString);
                out.close();
                
                // get HTTP response code sent by server
                status = conn.getResponseCode();
                
                //close the connection
		conn.disconnect();
	    } 
            // handle exceptions
            catch (MalformedURLException e) {
		      e.printStackTrace();        
            } 
            catch (IOException e) {
		      e.printStackTrace();
	    }
       
            // return HTTP status
            return status;
    }
    
    //doGet method establishes connection with the client and calls GET method of client
     public static String doGet(String inputString) {
       
         // Make an HTTP GET passing the name on the URL line
         
         //r.setValue("");
         String response = "";
         HttpURLConnection conn;
         int status = 0;
         
         try {  
                
                // pass the name on the URL line
		URL url = new URL("http://localhost:8090/Project3Task3Server/BlockChainService" + "//" +inputString);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
                // tell the server what format we want back
		conn.setRequestProperty("Accept", "text/plain");
 	
                // wait for response
                status = conn.getResponseCode();
                
                String output = "";
                // things went well so let's read the response
                BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
 		
                //read the entire output
		while ((output = br.readLine()) != null) {
			response += output;
		}

		conn.disconnect();
	    } 
                catch (MalformedURLException e) {
		   e.printStackTrace();
	    }   catch (IOException e) {
		   e.printStackTrace();
	    }
            
         // return response to caller
         return response;
    } 
}
