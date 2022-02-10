package ArtistsDatabase.Class;

import java.util.*;
import java.io.Serializable;
import java.time.LocalDate;


public abstract class Artist implements Serializable {
    private final int ID; // primary key
    private String name; 
    private String label;
    private boolean active; 
    private LocalDate debut; 
    //Colection type attributes.
    private ArrayList <String> aka = new ArrayList <String>();
    private ArrayList <String> genres = new ArrayList <String> ();
    private ArrayList <Album> discography = new ArrayList <Album> ();
    
    // Constructors.

    /**All the parameters are obligatory.
     * ID has to be unique in the database.
     * @param pID primary key. 
     * Obligatory, Artists are serializable objects. 
     */
    public Artist(
    int pID, String pName, // does not include lablel.
    boolean pActive, LocalDate pDebut) {
        ID = pID;
        setName(pName);
        setActive(pActive);
        setDebut(pDebut);
    }


    /**Includes the value of label. Basically the original
     * constructor, but with all the parameters.
     * @param pLabel optional.
    */
    public Artist(
    int pID, String pName, String pLabel,
    boolean pActive, LocalDate pDebut) {
        this(pID, pName, pActive, pDebut);
        // invokes the other constructor.
        setLabel(pLabel);
    }


    // Setters.
    public void setName(String pName) {
        name = pName;
    }
    public void setLabel(String pLabel) {
        label = pLabel;
    }
    public void setActive(boolean pActive) {
        active = pActive;
    }
    public void setDebut(LocalDate pDebut) {
        debut = pDebut;
    }


    // Getters.
    public int getID() {
        return ID; // constant
    }    
    public String getName() {
        return name;
    }
    public String getLabel() {
        return label;
    }
    public boolean isActive() {
        return active;
    }


    /**In case of being the year of the
     *  debut album before, the value is wrong. 
     * @return the first known year of the 
     * artist, basically. (it could be checked 
     * in the concerts database.)*/
    public int debutYear() {
        int d = 0;
        if (getDebutAlbum() != null)
            if (getDebutAlbum().getYear() < debut.getYear())
                d = getDebutAlbum().getYear();
        else d = debut.getYear();
        // the value is not 
        return d;
    }


    // Pseudonyms. ----------------------------
    
    /**Checks if one the names stored in the
     * artist matches with the parameter.
    */
    public void addPseudonym(String pPseudonym) {
        if (!name.equals(pPseudonym) && !isKnownAs(pPseudonym))
            // cannot be the value of the name.
            aka.add(pPseudonym);
    }


    /**
     *  @return All pseudonyms concatenated.
     * Only the name if there are none.
     */
    public String pseudonyms() {
        String ans = "";
        if (aka.isEmpty())
            // message text. the value of name is constant.
            ans = name + " doesn't have any other name.";
        else 
            for (String p : aka) 
                ans += p + ", ";
        return ans;
    }


    /**
     *  @return A boolean telling if the value
     *  parameter is stored in the collection 
     * of pseudonyms. Ignores case.
     */
    public boolean isKnownAs(String pPseudonym) {
        boolean ans = false;
        Iterator <String> itr = aka.iterator();
        while (!ans && itr.hasNext()) 
            if (itr.next().equalsIgnoreCase(pPseudonym))
                ans = true;
        return ans;
    }
    

    /**Adds a genre checking that is not
     * previously stored in genre collection.
     * Ignores case. @param pGenre
    */
    public void addGenres(String pGenre) {
        if (!plays(pGenre))
            genres.add(pGenre);
    }


    /**
     * @return All the genres concatenated.
     * message if they're not genres stored.
     */
    public String getGenres() {
        String ans = "";
        if (genres.isEmpty())
            ans = "Doesn't have any genre stored.";
        else 
            for (String g : genres) ans += g + ", ";
        return ans;
    }


    /**
     * @return A boolean if the value parameter is
     * stored in the collection of pseudonyms. 
     * Ignores case.
     */
    private boolean plays(String pGenre) {
        boolean ans = false;
        Iterator <String> itr = genres.iterator();
        while (!ans && itr.hasNext()) 
            ans = (itr.next().equalsIgnoreCase(pGenre));
        return ans;
    }


    /**The album cannot be previously stored in discography.
     * Ignores case of the album's name.
     * @param pAlbum has to be instanced.
    */
    public void addAlbum(Album pAlbum) {
        if (!isAuthorOf(pAlbum.getName())) 
            discography.add(pAlbum);
        else 
            System.out.println("The album " + pAlbum.getName() + 
                " is already in " + name + "'s discography.");
        //TODO: is it necessary to do more stuff? the name isn't unique.
    }


    /**Check the size of the list to avoid errors. It could be empty.
     * @return Collection containing all the 
     * albums from the selected year. 
     */
    public ArrayList <Album> getAlbumsFromYear(int pYear) {
        ArrayList <Album> albumsFiltered = new ArrayList <Album> ();
        for (Album a : discography) 
            if (a.getYear() == pYear)
                albumsFiltered.add(a);
        return albumsFiltered;
    }


    /**Sorts before returning the album. The change is permanent.
     * @return the first album by year.
     */
    public Album getDebutAlbum() {
        sortAlbumsByYear();
        //TODO: The list could be empty stupid. Check that.
        return discography.get(0);
    }


    /**
     * Returns a boolean if the name is
     * stored in the collection of pseudonyms. 
     * @param pName has to be the name of the album.
     * Ignores case.
     */
    private boolean isAuthorOf(String pName) {
        for (Album a : discography)
            if (a.getName().equalsIgnoreCase(pName))
                return true;
        return false;
    }


    /**To make the process easier, albums are comparable. 
     * @see Album implements comparable interface.
     */

    private void sortAlbumsByYear() {
        Collections.sort(discography);
    }


   //Concert related stuff. -----------------------------------------------

   
    public ArrayList <Concert> searchConcertsByPlace() {
        ArrayList <Concert> filtered = new ArrayList <Concert> ();
        /**
         * It's suposed to access to the database and store all the data
         * from the file, and create a temporary ArrayList <Concert>.
         */
        return filtered;
    }

    public boolean playedThere() {// TODO: Should be private.
        // TODO: Search in the file.
        return false;
    }
}