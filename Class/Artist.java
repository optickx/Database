package Database.Class;

import java.util.*;
import java.io.Serializable;
import java.time.LocalDate;


public abstract class Artist implements Comparable <Artist>, Serializable{
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
        && !isKnownAs(pPseudonym))
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


    /**@return A boolean telling if the value
     *  parameter is stored in the collection 
     * of pseudonyms. 
     * @param pPseudonym Ignores case.
     */
    private boolean isKnownAs(String pPseudonym) {
        Optional <String> ans = 
            aka.stream()
                .filter(p -> p.equalsIgnoreCase(pPseudonym))
                .findAny();
        return ans.isPresent();
    }

    // Genres.

    /**Adds a genre checking that is not
     * previously stored in genre collection.
     * @param pGenre Ignores case.
    */
    public void addGenres(String pGenre) {
        if (!playsGenre(pGenre))
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
     * @return A boolean if the value parameter is
     * stored in the collection of pseudonyms. 
     * @param pGenre Ignores case.
     */
    private boolean playsGenre(String pGenre) {
        Optional <String> ans = 
            genres.stream()
                .filter(g -> g.equalsIgnoreCase(pGenre))
                .findFirst();
        return ans.isPresent();
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
            System.out.println("The album " + pAlbum.getName() + 
                " is already in " + name + "'s discography.");
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


    /**Show all the concerts sorted. Calling to the databse*/
    public ArrayList <Concert> sortConcertsBy() {
        ArrayList <Concert> concerts = ConcertsDatabase.getConcerts(ID);
        return concerts;
    }

    public ArrayList <Concert> sortConcertsByYear() {
        ArrayList <Concert> concerts = ConcertsDatabase.getConcerts(ID);
        concerts.stream()
            .sorted((a1, a2) -> a1.compareDate(a2));
        return concerts;
    }


    /**Special methods that are override. Very useful really. */
    @Override
    public String toString() {
        // TODO: Return all the data of an artist.
        return "All this shitty text explaining the artist";
    }

    @Override
    public int compareTo(Artist pArtist) {
        return ID - pArtist.getID();
    }
}