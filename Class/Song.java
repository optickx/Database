package Database.Class;

public class Song implements Comparable <Song> {

	private int number; // unique value.
	private String title;
	private int length; // seconds.
	
	
	public Song(
	String pTitle, int pNumber, int pLength) {
		number = pNumber;
		title = pTitle;
		length = pLength;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String pTitle) {
		title = pTitle;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int pNumber) {
		number = pNumber;
	}
	public int getLength() {
		return length;
	}
	public String getLengthMinutes() {
		// TODO: jajan't
		return "";
	}
	public void setLength(int pLength) {
		length = pLength;
	}


	@Override
	public String toString() {
		return number + " " + title + " " + getLengthMinutes();
	}


	@Override
	public int compareTo(Song pSong) {
		return pSong.getLength() - length;
	}
}
