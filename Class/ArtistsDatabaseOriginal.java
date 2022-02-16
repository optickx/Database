package Database.Class;

import java.io.*;
import java.util.*;

import Database.Tools.*;

public abstract class ArtistsDatabaseOriginal {
    /**The ConcertsDatabase works in a very similar way.
     * @see ArrayList
     * @see ConcertsDatabase
     */
    private static final String PATH = "directory of the file itself";
    private static File f = new File(PATH);
    
    // Modules.

    /**
     * Add artists to the database. Before adding anything,
     * the database is checked to be sure that the artists
     * itself exists, by calling the auxiliar method "isContained".
     * 
     * @see Concert
     */
    public static void addArtist(Artist pArtist) {
        try {
            if (f.exists()) {
                if (!isContained(pArtist.getID())) {
                    MyObjectOutputStream moos = new MyObjectOutputStream();
                    moos.writeObject(pArtist);
                    moos.close();
                } else {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, true));
                    oos.writeObject(pArtist);
                    oos.close();
                }
            }
            else {
                // TODO: Create a new file to add the values.
            }
        } catch (Exception e) {
            System.out.println("There was some sort of error.");
        }
    }


    /**
     * Private and auxiliar method to add a concert to the database.
     * @param pCode
     * @return boolean indicating if the concert is already.
     */
    private static boolean isContained(int pCode) {
        boolean a = false;
        if (f.exists()) {
            int length = Util.fileLength(f);
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                for (int i = 0; i < length && !a; i++)
                    if (((Artist) ois.readObject()).getID() == pCode)
                        a = true;
                ois.close();
            } catch (Exception e) {
                System.out.println("Error 404 not found.");
                a = false;
            }
        }
        return a;
    }


    /**The only way of obtaining concerts from the Database if
     * by using the ID of an artist.
     * @returns null in case of not finding anything.
     */
    public static Artist getArtistWithID(int pID) { 
        ObjectInputStream ois = null;
        Artist a = null;

        if (!f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                //Concert aux;
                for (int i = 0; i < length; i++) {
                    a = (Artist) ois.readObject();
                    if (a.getID() == pID)
                        break;
                }

            } catch (FileNotFoundException fnfe) {
                System.out.println("File not found.");
            } catch (Exception e) {
                System.out.println("Well, something went wrong because files in java suck. What else can I say?");
            }
            try {
                ois.close();
            } catch (IOException e) {
                System.out.println("Error closing the file.");
            }
        }
        return a;
    }


        
}
