package Database.Class;

import java.time.LocalDate;
import java.util.ArrayList;

public class Album implements Comparable <Album> {
    public String name;
    public LocalDate released;
    //Collection attributes.
    public ArrayList <Song> trackList;
    public ArrayList <String> genres = new ArrayList <String> ();
    public ArrayList <String> personnel = new ArrayList <String> ();

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
        int total = 0;
        for (Song s : trackList) 
            total += s.getLength();
        return total;
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
        boolean ans = false;
        for (Song s : trackList) 
            if (s.getTitle().equals(pSong.getTitle()) &&
                s.getLength() == pSong.getLength())
                    ans = true;
        return ans;
    }

    public ArrayList <Song> searchByTitle(String pTitle) {
        ArrayList <Song> search = new ArrayList <Song> ();
        for (Song s : trackList)
            if (s.getTitle().contains(pTitle)) //TODO: improve this shit.
                search.add(s);
        return search;
    }

    
    /**Comparators by different values. Used later to 
     * sort the discography of an artist more easily.
    * @see Artist
    */
    @Override
    public int compareTo(Album pAlbum) {
        // comparator by date. default comparator.
        return pAlbum.getReleasingDate().compareTo(released);
    }
    public int compareYear(Album pAlbum) {
        // comparator by year.
        return getYear()- pAlbum.getYear();   
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
        return "Shitty text explaining an album.";
    }
}
