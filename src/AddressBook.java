/* Yuguo Liu */
/* 101142730 */

import java.util.ArrayList;

public class AddressBook {

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
}
