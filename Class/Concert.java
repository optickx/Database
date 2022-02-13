package Database.Class;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Concert implements Serializable, Comparable <Concert> {
	
	private final int ID; // primary key.
	private final int aID; // primary key of the artist.
	private LocalDate date;
	private String place;
	private String description;
	private float price;
	// Collection.
	private ArrayList <Integer> artists = new ArrayList <Integer>();
	
	// Constructors. TODO: Es un festival si tiene más de dos artistas distintos.
	
	/**Original constructor. Uses all the obligatory
	 * attributes of the class.
	 * @param pID unique primary key of the concert.
	 * @param pAID unique primary key of the artist.
	 * @param pDate date of the concert.
	 * @param pPlace place of the concert.
	*/	
	public Concert(
	int pID, int pAID, LocalDate pDate, String pPlace) {
		ID = pID;
		aID = pAID;
		date = pDate;
		place = pPlace;
	}


	/**Calls the original constructor 
	 * and adds new parameters.
	 * @param pDescription
	 * @param pPrice
	 */
	public Concert(
	int pID, int pAID, LocalDate pDate, 
	String pPlace, String pDescription, float pPrice) {
		this(pID, pAID, pDate, pPlace);
		description = pDescription;
		price = pPrice;
	}

	// Setters.

	public void setDate(LocalDate pDate) {
		date = pDate;
	}
	public void setPlace(String pPlace) {
		place = pPlace;
	}
	public void setDescription(String pDescription) {
		description = pDescription;
	}
	public void setPrice(float pPrice) {
		if (pPrice >= 0) price = pPrice;
	}

	// Getters.

	public int getID() {
		return ID;
	}
	public int getAID() {
		return aID;
	}
	public LocalDate getDate() {
		return date;
	}
	public String getPlace() {
		return place;
	}
	public String getDescription() {
		return description;
	}
	public float getPrice() {
		return price;
	}


	/**Adds a new ID to the ArrayList of artists'
	 * primary keys. 
	 * @param pAID If the ID is the ID of the 
	 * principal artist who played in that concert, 
	 * then the value is not added.
	 */
	public void addArtist(int pAID) {
		if (!artists.contains(pAID) && (pAID != aID))
			artists.add(pAID);
	}
	/**Before returning the Array, the value of the 
	 * principal artist is added to it. Is it correct?
	 */
	public ArrayList <Integer> getArtists() {
		ArrayList <Integer> ans = new ArrayList <Integer> ();
		ans.add(0, aID);
		return ans;
	}

	@Override
	public String toString() {
		return "El concierto tuvo lugar el "
			+ date.getDayOfMonth() + " de "
			+ date.getMonth() + " de "
			+ date.getYear() + ". La actuación";
	}


	/**Very useful to design comparators in case of having
	 * concerts stored in a collection.
	 */
	@Override
	public int compareTo(Concert pConcert) {
		// TODO Auto-generated method stub
		return pConcert.getDate().compareTo(date);
	} 

	public int compareDate(Concert pConcert) {
		return pConcert.getDate().compareTo(date);
	}
	
	public int comparePlace(Concert pConcert) {
		return pConcert.getPlace().compareToIgnoreCase(place);
	}

	public int comparePrice(Concert pConcert) {
		return (int) (pConcert.getPrice() - price);
	}
}
