/*
 * This class retrieves the user information,and check which operation is to be performed
 * Likewise it calls the specific method of Blockchain and returns the respective output back to client.
 */
package edu.cmu.andrew.psdesai;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author psdes
 */
@WebService(serviceName = "BlockChainService")
public class BlockChainService {

    //Initialize Blockchain object
    BlockChain bc = new BlockChain();
    

    /**
     * Web service operation
     * @param inputstring
     * @return 
     */
    @WebMethod(operationName = "operation")
    public String operation(@WebParam(name = "inputstring") String inputstring) {

        
        //variables of the block
        int operation = 0;
        int difficulty = 0;
        String data = null;
        
        //checks whether the inputstring contains comma
        //if yes, it means that the inputstring contains other information as well about difficulty and data
        if (inputstring.contains(",")) {
            //split based on comma
            String[] details = inputstring.split(",");
            //1st element is the digit signifying the operation to be performed
            operation = Integer.parseInt(details[0]);
            //2nd element is the difficulty
            difficulty = Integer.parseInt(details[1]);
            //3rd element is the data
            data = details[2];
        } else {
            operation = Integer.parseInt(inputstring);
        }

        //operation for addblck
        if (operation == 1) {
            //Initialize a block with the details retreived from the user
            Block b = new Block(bc.getChainSize(), bc.getTime(), data, difficulty);
            //Call the addBlock method
            bc.addBlock(b);
        }
        //operation for isChainValid
        else if (operation == 2) {
            //Calls the isChainValid method of blockchain
            boolean check = bc.isChainValid();

            //return boolean in the form of String
            if (check == true) {
                return "True";
            }
            if (check == false) {
                return "False";
            }
        }
        
        //operation for view
        else if(operation == 3){
        //Calls the toString method of blockchain
        return bc.toString();
        }

       return null; 
    }
}
