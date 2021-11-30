import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import static org.junit.Assert.assertEquals;


public class StringtoXML {
    private BuddyInfo buddyInfo1;
    private BuddyInfo buddyInfo2;
    private BuddyInfo buddyInfo3;
    private AddressBook addressBook;

    public StringtoXML(){
        buddyInfo1 = new BuddyInfo("patrick", "red", "613");
        buddyInfo2 = new BuddyInfo("John", "green", "316");
        buddyInfo3 = new BuddyInfo("peter", "blue", "163");
        addressBook = new AddressBook();
        addressBook.addBuddy(buddyInfo1);
        addressBook.addBuddy(buddyInfo2);
        addressBook.addBuddy(buddyInfo3);
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    public static void main(String[] args) throws IOException, TransformerException {

        StringtoXML s = new StringtoXML();
        output(s.getAddressBook());

        s.testoutput();
    }

    @Test
    private void testoutput(){
        AddressBook book2 = input();

        assertEquals(book2.getBuddyList().get(0).getName(), buddyInfo1.getName());
        assertEquals(book2.getBuddyList().get(1).getName(), buddyInfo2.getName());
        assertEquals(book2.getBuddyList().get(2).getName(), buddyInfo3.getName());
    }

    private static void output(AddressBook addressBook) throws IOException, TransformerException{
        final String xmlStr = addressBook.toString();

        Document doc = convertStringToXMLDocument( xmlStr );

        String a = "output.txt";
        File myObj = new File(a);
        DOMSource source = new DOMSource(doc);
        FileWriter writer = new FileWriter(myObj);
        StreamResult result = new StreamResult(writer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);
    }

    private static Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static AddressBook input(){
        AddressBook addressBook = new AddressBook();
        try
        {
            String a = "output.txt";
            File file = new File(a);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Buddy");
            for (int itr = 0; itr < nodeList.getLength(); itr++)
            {
                Node node = nodeList.item(itr);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;
                    /*System.out.println("Name: "+ eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Address: "+ eElement.getElementsByTagName("address").item(0).getTextContent());
                    System.out.println("Phone Number: "+ eElement.getElementsByTagName("phoneNumber").item(0).getTextContent());*/

                    addressBook.addBuddy(new BuddyInfo(eElement.getElementsByTagName("name").item(0).getTextContent(),
                            eElement.getElementsByTagName("address").item(0).getTextContent(),
                            eElement.getElementsByTagName("phoneNumber").item(0).getTextContent()));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return addressBook;
    }
}
