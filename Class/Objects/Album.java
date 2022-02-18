package Database.Class.Objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Album implements Comparable <Album> {
    private String name;
    private LocalDate released;
    //Collection attributes.
    private ArrayList <Song> trackList;
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
    public void addSong(Song pSong) {
        if (pSong.getNumber() > trackList.size()) 
            trackList.add(pSong.getNumber(), pSong);
        updateTrackNumbers();
    }

    /**Method that gives each song a unique number 
     * that identifies their position in the list.
     * Has to match their indexes.
     */
    public void updateTrackNumbers() {
        Collections.sort(trackList);
        // after sorting the list 
        for (int i = 0; i < trackList.size(); i++) 
            trackList.get(i).setNumber(i);
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
    /**Does the same, but only compares the title of the
     * song when doing the search
     */
    public boolean containsSong(String pTitle) {
        Optional <Song> ans = trackList.stream().filter(
            s -> s.getTitle().equalsIgnoreCase(pTitle))
            .findFirst();
        return ans.isPresent();
    }


    /**@return A list of all the songs in the album
     * whose title matches the parameter. Uses a pattern
     * to see if the String is contained.
     */
    public ArrayList <Song> getSongsWithName(String pTitle) {
        ArrayList <Song> search = new ArrayList <Song> ();
        for (Song s : trackList)
            if (s.getTitle().contains(pTitle)) 
                search.add(s);
        /*Make a pattern and a match to search all the songs
        that contain the words of the title in any order.*/
        return search;
    }


    /**@return all the data of the songs contained 
     * inside the tracklist of the album.
     */
    public String getAllSongsInformation() {
        String ans = "";
        for (Song s : trackList) 
            ans += s.toString();
        return ans;
    }

    public void sortSongsByNumber() {
        Collections.sort(trackList);
    }
    public void sortSongsByTitle() {
        trackList.stream()
            .sorted((s1, s2) -> s1.compareTitle(s2));
    }
    public void sortSongsByLength() {
        trackList.stream()
            .sorted((s1, s2) -> s1.compareLength(s2));
    }


    /**@return a String that contains all 
     * the albums (if any) an artist has in their 
     * discography.
     */
    public String getSongs() {
        String ans = "";
        for (Song s : trackList) 
            ans += s.toString() + "\n";
        return ans;
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
        return 
            name + " was released the " + released.getDayOfMonth() +
            " of " + released.getMonth() + " of " + released.getYear() +
            "The album is considered from the genres of "  +
            "Here's the songs from the album:\n \t" + getSongs() + "\n";
    }
}