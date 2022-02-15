package Database.Class;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class Album implements Comparable <Album> {
    private String name;
    private LocalDate released;
    //Collection attributes.
    private ArrayList <Song> trackList;
    private ArrayList <String> genres = new ArrayList <String> ();
    //Constructors.

    /**The name of the album is not unique I guess.
     * However, to create one the name is obligatory.
     * @param pName
     * @param pReleased
     */
    public Album(String pName, LocalDate pReleased) {
        setName(pName);
        setReleasingDate(pReleased);
    }     

    // Setters.

    public void setName(String pName) {
        name = pName;
    }
    public void setReleasingDate(LocalDate pReleased) {
        released = pReleased;
    }

    // Getters.

    public String getName() {
        return name;
    }
    public int getYear() {
        // only returns the value of the year.
        return released.getYear();
    }
    public LocalDate getReleasingDate() {
        return released;
    }
    public int getLength() {
        return trackList.stream().mapToInt(Song::getLength).sum();
    }

    // Tracklist.

    /**Add a song to the album. It's very important 
     * to check the values of the track number. 
     * @param pSong
     * the value of the attribute number might be modified.
    */
    public void addSong(Song pSong) {//TODO: check the values of the track number.
        if (pSong.getNumber() > trackList.size()) {
            //pSong.setNumber(trackList.size());TODO: Change value of the tracknumber 
            trackList.add(pSong);
        }
        else {

        }
    }

    /**Method that gives each song a unique number 
     * that identifies their position in the list.
     * Has to match their indexes.
     */
    public void updateTrackNumbers() {
        // TODO: implement this shit with lambda functions.
        //Collections.sort(trackList);
    }


    /**Returns true if the song is contained in the tracklist
     * of the album. A song is considered identical only if the
     * values of length and name are the same.
     * @param pSong has to be instanciated.
     */
    public boolean containsSong(Song pSong) {
        Optional <Song> ans = trackList.stream().filter(
            s -> s.getTitle().equalsIgnoreCase(pSong.getTitle()) &&
            s.getLength() == pSong.getLength())
            .findFirst();
        return ans.isPresent();
    }


    /**@return A list of all the songs in the album
     * whose title matches the parameter. Uses a pattern
     * to see if the String is contained.
     */
    public ArrayList <Song> searchSongsByTitle(String pTitle) {
        ArrayList <Song> search = new ArrayList <Song> ();
        for (Song s : trackList)
            if (s.getTitle().contains(pTitle)) 
                search.add(s);

        /* TODO: improve this shit. 
        Make a pattern and a match to search all the songs
        that contain the words of the title in any order.*/
        return search;
    }

    
    /* Comparators by different values. Used later to 
     * sort the discography of an artist more easily.
    */
    @Override
    public int compareTo(Album pAlbum) {
        // comparator by date. default comparator.
        return pAlbum.getReleasingDate().compareTo(released);
    }
    public int compareYear(Album pAlbum) {
        // comparator by year.
        return getYear() - pAlbum.getYear();   
    }
    public int compareName(Album pAlbum) {
        // comparator by name.
        return pAlbum.getName().compareToIgnoreCase(name);
    }
    public int compareLength(Album pAlbum) {
        // comparator by length;
        return pAlbum.getLength() - getLength();
    } 


    @Override
    public String toString() {
        return name + " was released the " + 
        released.getDayOfMonth() + " of " + 
        released.getMonth() + " of " + 
        released.getYear() + ".";
    }
}
