package Database.Class;

import java.time.LocalDate;
import java.util.ArrayList;

public class Concert {
	
	private final int ID; // primary key.
	private final int aID; // primary key of the artist very importantt!!!! ---------------------------------------
	private LocalDate date;
	private String place;
	private String openingAct; // ID of an artist.
	private String description;
	private float price;
	// Collection.
	private ArrayList <String> artists = new ArrayList <String>();

	/*TODO: Considerar que es un festival 
	si tiene más de dos artistas distintos.
	*/
	
	/**Primary constructor. */
	
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
	 * @param pOpening
	 * @param pPrice
	 */
	public Concert(
	int pID, int pAID, LocalDate pDate, String pPlace,
	String pOpeningAct, String pDescription, float pPrice) {
		this(pID, pAID, pDate, pPlace);
		openingAct = pOpeningAct;
		description = pDescription;
		price = pPrice;
	}


	public int getID() {
		return ID;
	}

	public int getaID() {
		return aID;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public String getOpeningAct() {
		return openingAct;
	}


	public void setOpeningAct(String openingAct) {
		this.openingAct = openingAct;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public ArrayList<String> getArtists() {
		return artists;
	}


	public void setArtists(ArrayList<String> artists) {
		this.artists = artists;
	}


	@Override
	public String toString() {
		String r = "El concierto tuvo lugar en " + date.getYear() + ", el " + date.getDayOfMonth() + " de " +date.getMonth();

		return r;

	} 
	
	
	
	
	
	
	
}
