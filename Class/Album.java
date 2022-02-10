package ArtistsDatabase.Class;

import java.time.LocalDate;
import java.util.ArrayList;

import ArtistsDatabase.Tools.Util;

public class Album implements Comparable <Album> {
    public String name;
    public LocalDate released;
    //Collection attributes.
    public ArrayList <Song> trackList;
    public ArrayList <String> genres = new ArrayList <String> ();
    public ArrayList <String> personnel = new ArrayList <String> ();

    /**The name of the album is not unique I guess.
     * However, to create one the name is obligatory.
     * @param pName
     */
    public Album(String pName) {
        setName(pName);
    }
    public Album(String pName, LocalDate pReleased) {
        setName(pName);
        setReleasingDate(pReleased);
    }    

    public void setName(String pName) {
        name = pName;
    }
    public void setReleasingDate(LocalDate pReleased) {
        released = pReleased;
    }
    public String getName() {
        return name;
    }
    public String getReleasingDate() {
        //TODO WTF bro. Doesn't convince me.
        return Util.dateToString(released);
    }
    public int getLength() {
        int total = 0;
        for (Song s : trackList) 
            total += s.getLength();
        return total;
    }

    public int getYear() {
        return released.getYear();
    }

    //Collection stuff.------------------------------------------------

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
    public void updateTrackNumbers() {
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


    /**Method that gives each song a unique number 
     * that identifies their position in the list.
     * Has to match their indexes.
     */
    
    
    @Override
    public int compareTo(Album pAlbum) {
        return getYear()- pAlbum.getYear();
    }
}
