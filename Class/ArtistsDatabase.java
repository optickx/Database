package Database.Class;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import Database.Tools.*;

public abstract class ArtistsDatabase {
    /**
     * The ConcertsDatabase works in a very similar way.
     * 
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
    private static void alta() {
        print("¿Quieres meter una banda o un artista en solitario? [b/s]");
        char opt = Util.readChar('b', 's');
        switch (opt) {
            case 'b':
                Band a = createBand();
                break;
            case 's':
                Solo s = createSolo();

        }
    }

    private static Solo createSolo() {
        print("¿Cuál es el nombre del grupo?");
            String pName = Util.readText();
        print("¿Cuál es el sello?");
            String pLabel = Util.readText();
        print("¿Sigue el artista activo? s/n");
            boolean pActive = ('s' == Util.readChar('s', 'n'));
        print("¿Cuándo debutó la banda?");
            LocalDate pDebut = Util.readDateYMD();
        print("¿Cuál es su nombre real?");
            String pRealName = Util.readText();
        return new Solo(pk(), pName, pLabel, pActive, pDebut, pRealName);
    }


    private static Band createBand() {
        print("¿Cuál es el nombre del grupo?");
            String pName = Util.readText();
        print("¿Cuál es el sello?");
            String pLabel = Util.readText();
        print("¿Sigue el artista activo? s/n");
            boolean pActive = ('s' == Util.readChar('s', 'n'));
        print("¿Cuándo debutó la banda?");
            LocalDate pDebut = Util.readDateYMD();
        return new Band(pk(), pName, pLabel, pActive, pDebut);
    }

    private static int pk() {
        // TODO: generator of primary keys.
        return 0;
    }

    private static void print(Object obj) {
        System.out.print(obj);
    }

}
