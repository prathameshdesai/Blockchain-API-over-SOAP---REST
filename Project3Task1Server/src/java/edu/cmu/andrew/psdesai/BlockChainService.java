/*
 * This class retreives the client input and the respective method is executed.
 * That method executes the respective method in the Blockchain class and returns the output.
 */
package edu.cmu.andrew.psdesai;

import javax.jws.Oneway;
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
     *
     * @return
     */
    @WebMethod(operationName = "isChainValid")
    public boolean isChainValid() {
        //Calls the isChainValid method of blockchain
        boolean check = bc.isChainValid();
        return check;
    }

    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "viewBlockChain")
    public String viewBlockChain() {
        //Calls the toString method of blockchain
        return bc.toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "createGenesisBlock")
    @Oneway
    public void createGenesisBlock() {

        //Initialize the blockchain object and set the variable values for genesis block
        String data = "Genesis";
        int index = 0;
        int difficulty = 2;

        //Initialize the genesis block and set previousHash value
        Block block = new Block(index, bc.getTime(), data, difficulty);
        block.setPreviousHash("");

        //Call the addBlock method
        bc.addBlock(block);

    }

    /**
     * Web service operation
     *
     * @param difficulty
     * @param data
     */
    @WebMethod(operationName = "addBlock")
    @Oneway
    public void addBlock(@WebParam(name = "difficulty") int difficulty, @WebParam(name = "data") String data) {

        //Initialize a block with the details retreived from the user
        Block b = new Block(bc.getChainSize(), bc.getTime(), data, difficulty);
        //Call the addBlock method
        bc.addBlock(b);
    }
}
