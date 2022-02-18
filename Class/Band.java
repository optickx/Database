package Database.Class;

import java.util.*;
import java.time.LocalDate;

public class Band extends Artist {
    private String description;
    private ArrayList <String> members = new ArrayList <String> ();

    // Constructors

    /**Calls the original constructor
     * from the super class. Works the same way.
     * @see Artist
     */
    public Band(
    int pID, String pName,
    boolean pActive, LocalDate pDebut) {
        super(pID, pName, pActive, pDebut);
    }


    /**Calls the second constructor from the super class.
     * With label, descripton is obligatory.
     * @see Artist
     * @param pLabel is included
     * @param pDescription is included.
     */
    public Band(
    int pID, String pName, String pLabel,
    boolean pActive, LocalDate pDebut, String pDescription) {
        super(pID, pName, pLabel, pActive, pDebut);
        setDescription(pDescription);
    }


    // Setters.
    
    public void setDescription(String pDescription) {
        description = pDescription;
    }

    // Getters.

    public String getDescription() {
        return description;
    }


    /**Design some way of getting all the artists from the band
     * extracted from the database.
     */

    /**Stuff refered to the values of the band members.
     * Please do a decent design because right now it sucks.
     */
    public void addMember(String pMember) {
        if(null == hasMember(pMember))
            members.add(pMember);
    }


    public ArrayList <String> getMembers() {
        return members;
    }

    public String getMembersNames() {
        String ans = "";
        for (String m : members) 
            ans += m + ", ";
        
       return ans;
    }


    /**Returns the string contained in the 
     * list in case of being stored, and 
     * null in case of not finding anything.
     * @param pMember
     */
    public String hasMember(String pMember) {
        Collections.sort(members);
        Optional <String> ans = 
            members.stream()
                .filter(m -> m.equalsIgnoreCase(pMember))
                .findFirst(); 
        
        return ans.get();
    }


    /**Removes the member if it's contained 
     * inside the list. */
    public void deleteMember(String pMember) {
        if (null != hasMember(pMember))
            hasMember(pMember);    
    }

    @Override
    public String toString() { // TODO
        return super.toString() + description +
        "The members are: \n" + getMembersNames() ;
    }
}
