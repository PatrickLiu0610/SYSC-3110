/* Yuguo Liu */
/* 101142730 */

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class AddressBook implements Serializable{

    //ArrayList for storing BuddyInfo objects
    private ArrayList<BuddyInfo> buddyList = new ArrayList<>();

    public ArrayList<BuddyInfo> getBuddyList(){
        return buddyList;
    }

    //addBuddy is used for adding BuddyInfo objects into the ArrayList
    public void addBuddy(BuddyInfo buddy){

        boolean added = false;

        if(buddyList.isEmpty()){
            buddyList.add(buddy);
        }
        else{
            // avoid duplicate buddyInfo objects
            for (BuddyInfo num : buddyList) {
                if(num.equals(buddy)){
                    added = true;
                    break;
                }
            }

            if(!added){
                buddyList.add(buddy);
            }
        }
    }

    //removeBuddy is used for adding BuddyInfo objects into the ArrayList
    public void removeBuddy(String name){
        buddyList.removeIf(num -> num.getName().equals(name));
    }

    public void clear(){
        buddyList.clear();
    }

    @Override
    public String toString(){
        String eol = System.getProperty("line.separator");
        String s = "<AddressBook>" + eol;
        for (BuddyInfo b: buddyList){
            s += b.toString();
        }
        return s + eol + "</AddressBook>";
    }

    public void output(String name)throws IOException {
        FileOutputStream fileOutputStream
                = new FileOutputStream(name);
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public static AddressBook input(String name)throws IOException, ClassNotFoundException{
        FileInputStream fileInputStream
                = new FileInputStream(name);
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        AddressBook book = (AddressBook) objectInputStream.readObject();
        objectInputStream.close();
        return book;
    }

    public void outputXML(String filename) throws IOException, TransformerException{
        final String xmlStr = this.toString();

        Document doc = convertStringToXMLDocument( xmlStr );

        DOMSource source = new DOMSource(doc);
        FileWriter writer = new FileWriter(filename);
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

    public static AddressBook inputXML(String xmlFilename){
        AddressBook addressBook = new AddressBook();
        try
        {
            File file = new File(xmlFilename);
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
