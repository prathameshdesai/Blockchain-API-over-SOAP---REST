/*
 *This class retrieves the user information,and check which operation is to be performed
 * Likewise it calls the specific method of Blockchain and returns the respective output back to client.
 */
package edu.cmu.andrew.psdesai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author psdes
 */
@WebServlet(name = "BlockChainService", urlPatterns = {"/BlockChainService/*"})
public class BlockChainService extends HttpServlet {

    //Initialize Blockchain object
    BlockChain bc = new BlockChain();
    
    // GET returns a value given a key
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String result = "";
        int operation = 0;
        
        // The name is on the path /name so skip over the '/'
        String inputString = (request.getPathInfo()).substring(1);
        
        operation = Integer.parseInt(inputString);
        
        String value = null;
        //operation for isChainValid
        if (operation == 2) {
            //Calls the isChainValid method of blockchain
            boolean check = bc.isChainValid();

            //return boolean in the form of String
            if (check == true) {
                value =  "True";
            }
            if (check == false) {
                value = "False";
            }
        }
        
        //operation for view
        else if(operation == 3){
        //Calls the toString method of blockchain
        value =  bc.toString();
        }
        
        
        // Things went well so set the HTTP response code to 200 OK
        response.setStatus(200);
        // tell the client the type of the response
        response.setContentType("text/plain;charset=UTF-8");

        // return the value from a GET request
        result = value;
        PrintWriter out = response.getWriter();
        out.println(result);            
    }
    

    // POST is used to create a new variable
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //variables of the block
        int operation = 0;
        int difficulty = 0;
        String data = null;
        
        // Read what the client has placed in the POST data area
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        data = br.readLine();
        
        //split based on comma
            String[] details = data.split(",");
            //1st element is the digit signifying the operation to be performed
            operation = Integer.parseInt(details[0]);
            //2nd element is the difficulty
            difficulty = Integer.parseInt(details[1]);
            //3rd element is the data
            data = details[2];
            
             //operation for addblck
        if (operation == 1) {
            //Initialize a block with the details retreived from the user
            Block b = new Block(bc.getChainSize(), bc.getTime(), data, difficulty);
            //Call the addBlock method
            bc.addBlock(b);
        }        
    }  
}
