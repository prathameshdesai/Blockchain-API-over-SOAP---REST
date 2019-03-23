
package edu.cmu.andrew.psdesai;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.cmu.andrew.psdesai package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IsChainValid_QNAME = new QName("http://psdesai.andrew.cmu.edu/", "isChainValid");
    private final static QName _ViewBlockChainResponse_QNAME = new QName("http://psdesai.andrew.cmu.edu/", "viewBlockChainResponse");
    private final static QName _IsChainValidResponse_QNAME = new QName("http://psdesai.andrew.cmu.edu/", "isChainValidResponse");
    private final static QName _CreateGenesisBlock_QNAME = new QName("http://psdesai.andrew.cmu.edu/", "createGenesisBlock");
    private final static QName _AddBlock_QNAME = new QName("http://psdesai.andrew.cmu.edu/", "addBlock");
    private final static QName _ViewBlockChain_QNAME = new QName("http://psdesai.andrew.cmu.edu/", "viewBlockChain");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.cmu.andrew.psdesai
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IsChainValidResponse }
     * 
     */
    public IsChainValidResponse createIsChainValidResponse() {
        return new IsChainValidResponse();
    }

    /**
     * Create an instance of {@link CreateGenesisBlock }
     * 
     */
    public CreateGenesisBlock createCreateGenesisBlock() {
        return new CreateGenesisBlock();
    }

    /**
     * Create an instance of {@link AddBlock }
     * 
     */
    public AddBlock createAddBlock() {
        return new AddBlock();
    }

    /**
     * Create an instance of {@link ViewBlockChain }
     * 
     */
    public ViewBlockChain createViewBlockChain() {
        return new ViewBlockChain();
    }

    /**
     * Create an instance of {@link IsChainValid }
     * 
     */
    public IsChainValid createIsChainValid() {
        return new IsChainValid();
    }

    /**
     * Create an instance of {@link ViewBlockChainResponse }
     * 
     */
    public ViewBlockChainResponse createViewBlockChainResponse() {
        return new ViewBlockChainResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsChainValid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://psdesai.andrew.cmu.edu/", name = "isChainValid")
    public JAXBElement<IsChainValid> createIsChainValid(IsChainValid value) {
        return new JAXBElement<IsChainValid>(_IsChainValid_QNAME, IsChainValid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewBlockChainResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://psdesai.andrew.cmu.edu/", name = "viewBlockChainResponse")
    public JAXBElement<ViewBlockChainResponse> createViewBlockChainResponse(ViewBlockChainResponse value) {
        return new JAXBElement<ViewBlockChainResponse>(_ViewBlockChainResponse_QNAME, ViewBlockChainResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsChainValidResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://psdesai.andrew.cmu.edu/", name = "isChainValidResponse")
    public JAXBElement<IsChainValidResponse> createIsChainValidResponse(IsChainValidResponse value) {
        return new JAXBElement<IsChainValidResponse>(_IsChainValidResponse_QNAME, IsChainValidResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateGenesisBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://psdesai.andrew.cmu.edu/", name = "createGenesisBlock")
    public JAXBElement<CreateGenesisBlock> createCreateGenesisBlock(CreateGenesisBlock value) {
        return new JAXBElement<CreateGenesisBlock>(_CreateGenesisBlock_QNAME, CreateGenesisBlock.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://psdesai.andrew.cmu.edu/", name = "addBlock")
    public JAXBElement<AddBlock> createAddBlock(AddBlock value) {
        return new JAXBElement<AddBlock>(_AddBlock_QNAME, AddBlock.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewBlockChain }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://psdesai.andrew.cmu.edu/", name = "viewBlockChain")
    public JAXBElement<ViewBlockChain> createViewBlockChain(ViewBlockChain value) {
        return new JAXBElement<ViewBlockChain>(_ViewBlockChain_QNAME, ViewBlockChain.class, null, value);
    }

}
