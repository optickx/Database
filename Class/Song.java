package ArtistsDatabase.Class;

public class Song implements Comparable <Song> {
    private String title;
    private int number;
    private int length;


    public Song() {}

    public String getTitle() {
        return title;
    }
    public int getLength() {
        return length;
    }
    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(Song o) {
        // TODO Compares the values of the track number. @see number
        return 0;
    }
    
    
}
