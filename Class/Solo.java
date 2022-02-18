package Database.Class;

import java.time.LocalDate;

public class Solo extends Artist {

    private String realName;

    // Constructors.

    /**Basically the original constructor adding
     * the attribute of the real name.
     * @param pRealName
     */
    public Solo(
            int pID, String pName, boolean pActive,
            LocalDate pDebut, String pRealName) {
        super(pID, pName, pActive, pDebut);
        realName = pRealName;

    }


    /**Also includes the label of the artist.
     * @param pLabel
     */
    public Solo(
            int pID, String pName, String pLabel,
            boolean pActive, LocalDate pDebut, String pRealName) {
        super(pID, pName, pLabel, pActive, pDebut);
        realName = pRealName;
    }

    // Setters.

    public void setRealName(String pRealName) {
        realName = pRealName;
    }

    // Getters.

    public String getRealName() {
        return realName;
    }


    /**Adds information to the toString from the superclass.
     * @see Artist
     */
    @Override
    public String toString() {
        return super.toString() + "The real name of " 
        + super.getName() + " is " + realName + ".";
    }
}
