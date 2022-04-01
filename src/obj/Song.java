package obj;

public class Song implements Comparable <Song> {

	private int number; // unique value.
	private String title;
	private int length; // seconds.
	
	// Constructors.

	/**All the attributes are obligatory.
	 * In this case, there's no primary key.
	 * @param pTitle
	 * @param pNumber
	 * @param pLength
	 */	
	public Song(
	String pTitle, int pNumber, int pLength) {
		number = pNumber;
		title = pTitle;
		length = pLength;
	}

	// Setters.

	public String getTitle() {
		return title;
	}
	public int getNumber() {
		return number;
	}
	public void setTitle(String pTitle) {
		title = pTitle;
	}
	public int getLength() {
		return length;
	}
	/**@return the value of the length 
	 * transformed to the classic format.
	 */
	public String getLengthMinutes() {
		return length/60 + ":" + length%60;
	}

	// Getters.
	
	public void setNumber(int pNumber) {
		number = pNumber;
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
		// comparator by track number (default comparator).
		return pSong.getNumber() - number;
	}
	public int compareTitle(Song pSong) {
		// comaprator by name.
		return pSong.getTitle().compareToIgnoreCase(title);
	}
	public int compareLength(Song pSong) {
		// comparator by length.
		return pSong.getLength() - length;
	}
}
