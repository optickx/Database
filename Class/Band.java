package ArtistsDatabase.Class;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;

public class Band extends Artist {
    private String description;
    private ArrayList<Musician> members = new ArrayList<Musician>();

    /**
     * Calls the original constructor
     * from the super class.
     * 
     * @see Artist
     */
    public Band(
            int pID, String pName,
            boolean pActive, LocalDate pDebut) {
        super(pID, pName, pActive, pDebut);
    }

    /**
     * Calls the second constructor
     * from the super class.
     * 
     * @see Artist
     * @param pLabel is included
     */
    public Band(
            int pID, String pName, String pLabel,
            boolean pActive, LocalDate pDebut) {
        super(pID, pName, pLabel, pActive, pDebut);
    }


    public String getDescription() {
        return description;
    }
    public ArrayList <Musician> getMembers() {
        return members;
    }


    /**
     * Private internal auxiliar class, suposed to
     * be helpful, but probably will have to be removed.
     * @see Artist
     */
    public class Musician implements Serializable {
        // Do not forget to make the class private.
        private String name;
        private ArrayList<String> instruments = new ArrayList<String>();

        public Musician(String pName) {
            name = pName;
        }

        public String getName() {
            return name;
        }

        public ArrayList<String> getInstruments() {
            return instruments;
        }
    }
}
