/* Yuguo Liu */
/* 101142730 */

import java.io.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class BuddyInfo implements Serializable {

    private String name;
    private String address;
    private String phoneNumber;

    public BuddyInfo(String name, String address, String phoneNumber){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public BuddyInfo(){
        this.name = "Guest";
        this.address = "Not Registered";
        this.phoneNumber = "XXX";
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        String eol = System.getProperty("line.separator");
        return "<Buddy>" + eol + "<name>" + name + "</name>" + eol+ "<address>" + address + "</address>" + eol + "<phoneNumber>"+ phoneNumber + "</phoneNumber>" + eol + "</Buddy>" + eol;
    }

    public static BuddyInfo importString(String text){
        String[] parts = text.split("#");
        return new BuddyInfo(parts[0], parts[1], parts[2]);
    }

    public void output()throws IOException{
        FileOutputStream fileOutputStream
                = new FileOutputStream("output.txt");
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public static BuddyInfo input()throws IOException, ClassNotFoundException{
        FileInputStream fileInputStream
                = new FileInputStream("output.txt");
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        BuddyInfo buddy = (BuddyInfo) objectInputStream.readObject();
        objectInputStream.close();
        return buddy;
    }

    public static void main(String [] args) throws IOException, ClassNotFoundException {
        BuddyInfo buddyInfo1 = new BuddyInfo("patrick", "red", "613");

        buddyInfo1.output();

        buddyInfo1.testBuddy();
    }

    @Test
    public void testBuddy() throws IOException, ClassNotFoundException {
        BuddyInfo buddy = input();

        assertEquals(buddy.getName(), "patrick");
        assertEquals(buddy.getAddress(), "red");
        assertEquals(buddy.getPhoneNumber(), "613");
    }
}
