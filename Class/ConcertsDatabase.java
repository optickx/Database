package Database.Class;

import java.io.*;
import java.util.*;

import Database.Tools.*;

public abstract class ConcertsDatabase {
    /**
     * Do I have to add all the concerts in
     * an ArrayList. In that case, is
     * ArrayList a serializable class?
     * @see ArrayList
     */
    private static final String PATH = "directory of the file itself";
    private static File f = new File(PATH);

    // Modules.

    /**Add concerts to the database.
     * @see Concert
     */
    public static void addConcert(Concert pConcert) {
        // TODO: if (isContained(pConcert.getID()))
        // do stuff.
        try {
            if (f.exists()) {
                MyObjectOutputStream moos = new MyObjectOutputStream();
                moos.writeObject(pConcert);
                moos.close();
            } else {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, true));
                oos.writeObject(pConcert);
                oos.close();
            }
        } catch (Exception e) {
            System.out.println("There was some sort of error.");
        }
    }

    // TODO: Improve this shitty code.
    public static boolean isContained(int pCode) {
        boolean a = false;
        if (f.exists()) {
            int length = Util.fileLength(f);
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                for (int i = 0; i < length && !a; i++)
                    if (((Concert) ois.readObject()).getID() == pCode)
                        a = true;
                ois.close();
            } catch (Exception e) {
                System.out.println("Error 404 not found.");
                a = false;
            }
        }
        return a;
    }

    /**
     * @param pCode is the code of a concert that
     *              will be deleted only if it's contained inside the
     *              databse.
     */
    public static void deleteConcert(int pCode) {
        // TODO do stuff
    }

    public static void showAllDatabase() { // TODO: this shouldn't exists. concerts are only accesible from an artist.
        // if (!f.exists())

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(f));
            int length = Util.fileLength(f);
            for (int i = 0; i < length; i++)
                System.out.println(((Concert) ois.readObject()).toString());

        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found.");
        } catch (EOFException eofe) {
        } catch (IOException ioe) {
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Error reading the data.");
        }

        try {
            ois.close();
        } catch (IOException e) {
            System.out.println("Error closing the file.");
        }
    }

    public static ArrayList <Concert> searchConcertsFromArtist(int pID) {
        ArrayList <Concert> c = new ArrayList <Concert> ();
        return c;
    }
}
