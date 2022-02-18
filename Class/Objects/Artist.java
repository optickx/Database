package Database.Class.Objects;

import java.util.*;

import Database.Class.Databases.ConcertsDatabase;

import java.io.Serializable;
import java.time.LocalDate;


public abstract class Artist implements Comparable <Artist>, Serializable{
    
    private final int ID; // primary key
    private String name; 
    private String label = null;
    private boolean active; 
    private LocalDate debut; 
    //Colection type attributes.
    private ArrayList <String> aka = new ArrayList <String>();
    private ArrayList <String> genres = new ArrayList <String> ();
    private ArrayList <Album> discography = new ArrayList <Album> ();
    
    // Constructors.

    /**All the parameters are obligatory.
     * ID has to be unique in the database.
     * @param pID is the primary key, obligatory 
     * being Artist a serializable class. 
     */
    public Artist(
    int pID, String pName, // does not include lablel.
    boolean pActive, LocalDate pDebut) {
        ID = pID;
        setName(pName);
        setActive(pActive);
        setDebut(pDebut);
    }


    /**Includes the value of label.
     * @param pLabel optional.
    */
    public Artist(
    int pID, String pName, String pLabel,
    boolean pActive, LocalDate pDebut) {
        // invokes original constructor.
        this(pID, pName, pActive, pDebut);
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
        return ID; // constant value.
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
    public LocalDate getDebut() {
        return debut;
    }


    /**In case of being the year of the debut album 
     * or any concert before, the value is wrong. 
     * @return the first known year.
     * @see Album
     * @see Concert
    */ 
    public int getDebutYear() {
        int d = 0;
        if (getDebutAlbum() != null)
            // avoid null pointers.
            if (getDebutAlbum().getYear() < debut.getYear())
                d = getDebutAlbum().getYear();
        else d = debut.getYear();
        // the value is not updated. only-read.
        return d;
    }

    // Pseudonyms.
    
    /**Checks if any name stored in the artist
     * matches with the parameter before adding.
     * @param pPseudonym
    */
    public void addPseudonym(String pPseudonym) {
        if (!name.equalsIgnoreCase(pPseudonym) 
        && null == isKnownAs(pPseudonym))
            // cannot be the value of the name.
            aka.add(pPseudonym);
    }


    /**@return All pseudonyms concatenated.
     * Only the name if there are none.
     */
    public String getPseudonyms() {
        String ans = "";
        if (aka.isEmpty())
            // message text. the value of name is constant.
            ans = name + " doesn't have any pseudonyms.";
        else 
            for (String p : aka) ans += p + ", ";   
        return ans;
    }


    /**@return the string if the value is stored
     *  in the collection of pseudonyms, and 
     * null in case of not being stored. 
     * @param pPseudonym Ignores case.
     */
    public String isKnownAs(String pPseudonym) {
        if (name.toLowerCase().contains(pPseudonym.toLowerCase())) 
            return name;

        Optional <String> ans = 
            aka.stream()
                .filter
                    (p -> p.toLowerCase()
                    .contains(pPseudonym.toLowerCase()))
                .findAny();
        return ans.get();
    }


    /**This module deletes a given pseudonym
     * if and only if it's contained in the 
     * list of pseudonyms. Ignores case.
     * @param pPseudonym
     */
    public void deletePseudonym(String pPseudonym) {
        if (isKnownAs(pPseudonym)  != null)
            aka.remove(isKnownAs(pPseudonym));
    }

    // Genres.

    /**Adds a genre checking that is not
     * previously stored in genre collection.
     * @param pGenre Ignores case.
    */
    public void addGenres(String pGenre) {
        if (null == playsGenre(pGenre))
            genres.add(pGenre);
    }


    /**
     * @return All the genres concatenated.
     * A message if they're not genres stored.
     */
    public String getGenres() {
        String ans = "";
        if (genres.isEmpty())
            ans = name + "doesn't have any genres stored.";
        else 
            for (String g : genres) ans += g + ", ";
        return ans;
    }


    /**
     * @return A String if the value parameter is
     * stored in the collection of pseudonyms. 
     * Null if nothing is found.
     * @param pGenre Ignores case.
     */
    public String playsGenre(String pGenre) {
        Optional <String> ans = 
            genres.stream()
                .filter(g -> g.equalsIgnoreCase(pGenre))
                .findFirst();
        return ans.get();
    }


    /**Removes the inputted text from the list 
     * of genres if and only if it's stored.
     * Ignores case
     * @param pGenre
     */
    public void deleteGenre(String pGenre) {
        if (null != playsGenre(pGenre))
            genres.remove(playsGenre(pGenre));
    }

    // Discography.

    /**The album cannot be previously stored in discography.
     * Ignores case of the album's name.
     * @param pAlbum has to be instanced.
    */
    public void addAlbum(Album pAlbum) {
        if (!isAuthorOf(pAlbum)) 
            discography.add(pAlbum);
        else 
            System.out.println(
                "The album " + pAlbum.getName() + 
                " is already in " + name + "'s discography.");
    }


    /**@return the number of albums an artist.*/
    public int getNumberOfAlbums() {
        return discography.size();
    }


    /**Check the size of the list to avoid errors. It could be empty.
     * @return Collection containing all the 
     * albums from the selected year. 
     */
    public ArrayList <Album> getAlbumsFromYear(int pYear) {
        ArrayList <Album> filtered = new ArrayList <Album> ();
        for (Album al : discography) 
            if (al.getYear() == pYear)
            filtered.add(al);
        if (filtered.size() == 0)
            return null;
        else 
            return filtered;
    }


    /**@return a string with all the information of
     * all the albums an artist has.
     */
    public String getAlbumsInfo() {
        String ans = "";
        for (Album a : discography) 
            ans += a.toString() + "\n";
        return ans;
    }


    /**Sorts before returning the album. The change is permanent.
     * @return the first album by year.
     */
    public Album getDebutAlbum() {            
        Optional <Album> ans = 
        discography
            .stream()
            .min(Comparator.comparing(Album::getReleasingDate));
        // the values are technically able to be sorted.
        return ans.get();
    }


    /**This method returns the longest album
     * the artist has ever made.
     */
    public Album getLongestAlbum() {
        /*Optional <Album> ans = 
            discography.stream()
                .max(Comparator.comparing(Album::getLength));*/

        int min = getLongestAlbum().getLength();
        Album ans = null;
        for (Album a : discography) 
            if (a.getLength() < min) {
                min = a.getLength();
                ans = a;
            }
        return ans;
    }
    

    /**Returns a boolean if the name is
     * stored in the collection of pseudonyms. 
     * @param pAlbum has to be the name of the album.
     * Ignores case.
     */
    private boolean isAuthorOf(Album pAlbum) {
        Optional <Album> ans = 
        discography.stream()
            .filter(a -> a.getName().equalsIgnoreCase(pAlbum.getName())
                && a.getReleasingDate().equals(pAlbum.getReleasingDate()))
            .findFirst();
        return ans.isPresent();
    }


      /**Returns the album if the name is
     * stored in the collection of pseudonyms.
     * Null if not. 
     * @param pName has to be the name of the album.
     * Ignores case.
     */
    private Album isAuthorOf(String pName) {
        Collections.sort(discography);
        Optional <Album> ans = 
        discography.stream()
            .filter(a -> a.getName().equalsIgnoreCase(pName))
            .findFirst();
        return ans.get();
    }


    /**Removes an album, which has to be selected
     * externally by name. It's the first album
     * chronologically to be removed.
     * @param pName
     */
    public void deleteAlbum(String pName) {
        if (null != isAuthorOf(pName)) 
            discography.remove(isAuthorOf(pName));
    }


    /**
     * @param pOption
     * @return
     */
    public ArrayList <Song> hasSongsWithName(String pTitle) {
        ArrayList <Song> songs = new ArrayList <Song> ();
        for (Album a : discography) 
            if (a.containsSong(pTitle)) 
                songs.addAll(a.getSongsWithName(pTitle));
        return songs;
    }


    /**To make the process easier, albums are comparable. 
     * Albums implement the Comparable interface. Important
     * to note that the default comparator compares the releasing
     * date of an album, but it's easier to compare by year.
     * @param pOption
     */
    public ArrayList <Album> getAlbumsSorted(int pOption) {
        switch (pOption) {
            case 1:
                discography.stream()
                    .sorted((a1, a2) -> a1.compareYear(a2));
                break; 
            case 2:
                discography.stream()
                    .sorted((a1, a2) -> a1.compareName(a2));
                break;
            case 3:
                discography.stream()
                    .sorted((a1, a2) -> a1.compareLength(a2));
                break;
            default:
                Collections.sort(discography);
                break;
        }
        return discography;
    }


    // Concerts management.   
   
   /**The artist has access to the concert database (static,
    * yet to be designed) This way, to do all the management
    * of the concerts, they should be stored inside an ArrayList.
    * @see Concert being serializable objects
    * @see ConcertsDatabase beng a static class(?)
    * A concert is added if and only if the Artist ID from the Concert
     * matches this ID. 
     * @see Artist
    */
    public void addConcert(Concert pConcert) {
        if (pConcert.getAID() == ID)
            ConcertsDatabase.addConcert(pConcert);
    }


    /**@return all the concerts of the artist. */
    public ArrayList <Concert> getConcerts() {
        return ConcertsDatabase.getConcerts(this);
    }


    /**@return all the concerts sorted. Calling to the database.
     * @param pOption chooses which type of sorting. 
    */
    public ArrayList <Concert> sortConcertsBy(int pOption) {
        ArrayList <Concert> concerts = ConcertsDatabase.getConcerts(this);
        switch (pOption) {
            case 1:
            Collections.sort(concerts);
                break; 
            case 2:
            concerts.stream()
                    .sorted((a1, a2) -> a1.comparePlace(a2));
                break;
            case 3:
            concerts.stream()
                    .sorted((a1, a2) -> a1.comparePrice(a2));
                break;
            default:
                Collections.sort(concerts);
                break;
        }
        return concerts;
    }


    /**@return all the concerts of the artist from given year. 
     * @param pYear
    */
    public ArrayList <Concert> getConcertsFromYear(int pYear) {
        ArrayList <Concert> concerts = 
            ConcertsDatabase.getConcerts(this);

        for (Concert c : concerts) 
            if (c.getDate().getYear() != pYear) 
                concerts.remove(c);
        
        Collections.sort(concerts);        
        return concerts;
    }


    /**Deletes a given concert from an artist. */
    public void deleteConcert(Concert pConcert) {
        if (pConcert.getAID() == ID)
            ConcertsDatabase.deleteConcert(pConcert);
    }


    /**Special methods that are override. Very useful really. */
    @Override
    public String toString() {
        String ans = "", dLabel = "", dActive = name + " is ";

        if (label != null) 
            dLabel = "\nThe label of the artist is" + label + ".";
        
        if (active)
            dActive += " still active.";
        else
            dActive += "not still active.";

        ans = 
            "\nName of the artist: " + name + "." + 
            dLabel + dActive;
            
        return ans;
    }


    /**Same as toString, but simplificated. */
    public String quickInfo() {
        return name + " " ; //TODO
    }



    @Override
    public int compareTo(Artist pArtist) {
        return ID - pArtist.getID();
    }
}