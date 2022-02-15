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
