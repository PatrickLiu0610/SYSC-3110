import org.junit.Before;
import org.junit.Test;

import javax.xml.transform.TransformerException;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class TestAddressBook {

    private AddressBook addressBook;
    private BuddyInfo buddyInfo1;
    private BuddyInfo buddyInfo2;
    private BuddyInfo buddyInfo3;
    public final static String FILENAME = "output.txt";
    public final static String XML_FILENAME = "XMLoutput.txt";

    @Before
    public void setUp(){
        addressBook = new AddressBook();
        buddyInfo1 = new BuddyInfo("patrick", "red", "613");
        buddyInfo2 = new BuddyInfo("John", "green", "316");
        buddyInfo3 = new BuddyInfo("peter", "blue", "163");

        addressBook.addBuddy(buddyInfo1);
        addressBook.addBuddy(buddyInfo2);
        addressBook.addBuddy(buddyInfo3);
    }

    @Test
    public void case1() throws IOException, ClassNotFoundException {
        addressBook.output(FILENAME);

        AddressBook book2 = AddressBook.input(FILENAME);

        assertEquals(book2.getBuddyList().get(0).getName(), addressBook.getBuddyList().get(0).getName());
    }

    @Test
    public void case2() throws IOException, TransformerException {
        addressBook.outputXML(XML_FILENAME);

        AddressBook book2 = AddressBook.inputXML(XML_FILENAME);

        assertEquals(book2.getBuddyList().get(0).getName(), addressBook.getBuddyList().get(0).getName());
    }
}
