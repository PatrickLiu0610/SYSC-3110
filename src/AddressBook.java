/* Yuguo Liu */
/* 101142730 */

import java.util.ArrayList;

public class AddressBook {

    //ArrayList for storing BuddyInfo objects
    private ArrayList<BuddyInfo> buddyList = new ArrayList<>();

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
    public void removeBuddy(BuddyInfo buddy){
        buddyList.removeIf(num -> num.equals(buddy));
    }

    public static void main(String args[]){
        BuddyInfo buddy1 = new BuddyInfo("Patrick", "Carleton", "613");
        BuddyInfo buddy2 = new BuddyInfo("Jack", "Kanata", "611");
        AddressBook addressBook = new AddressBook();

        //Duplicate objects will not be added to the Arraylist
        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy1);

        //New objects should be added
        addressBook.addBuddy(buddy2);

        //Checking the content of the Arraylist
        for (BuddyInfo num : addressBook.buddyList) {
            System.out.println(num.getName() + " " + num.getAddress() + " " + num.getPhoneNumber());
        }

        //Remove buddy1
        addressBook.removeBuddy(buddy1);

        //Removing an object that no longer exists in the Arraylist should not produce an error
        addressBook.removeBuddy(buddy1);

        //Checking the content of the Arraylist
        for (BuddyInfo num : addressBook.buddyList) {
            System.out.println("\nAfter removing buddy1:\n" + num.getName() + " " + num.getAddress() + " " + num.getPhoneNumber());
        }

        //small changes
        // a bit extra I added
        // Branch 1 created
    }
}
