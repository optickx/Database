package Database.Class;

import java.util.ArrayList;
import java.time.LocalDate;

public class Band extends Artist {
    private String description;
    private ArrayList <String> members = new ArrayList <String> ();

    // Constructors

    /**
     * Calls the original constructor
     * from the super class. Works the same way.
     * @see Artist
     */
    public Band(
    int pID, String pName,
    boolean pActive, LocalDate pDebut) {
        super(pID, pName, pActive, pDebut);
    }


    /**In this version, pLabel is included.
     * @param pLabel
     */
    public Band(
    int pID, String pName, String pLabel,
    boolean pActive, LocalDate pDebut) {
        super(pID, pName, pLabel, pActive, pDebut);
    }


    /**
     * Calls the second constructor
     * from the super class.
     * @see Artist
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
        if(!hasMember(pMember))
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

    public boolean hasMember(String pMember) {
        boolean ans = false;
        for (int i = 0; i < members.size() && !ans; i++) 
            if (members.get(i).equalsIgnoreCase(pMember))
                ans = true;
        
        return ans;
    }
}
