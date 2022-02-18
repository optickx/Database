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
    public static final String PATH = "directory of the file itself";
    public static File f = new File(PATH);
    // An auxiliar object of each type.
    /*
     * public static Artist art = null;
     * public static Album alb = null;
     * public static Song song = null;
     * public static Concert con = null;
     */

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
            if (f.exists())
                if (!isContained(pArtist.getID())) {
                    MyObjectOutputStream moos = new MyObjectOutputStream();
                    moos.writeObject(pArtist);
                    moos.close();
                } else {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, true));
                    oos.writeObject(pArtist);
                    oos.close();
                }
        } catch (Exception e) {
            print("Error.");
        }
    }

    /**
     * This module deletes an artist from the database
     * using its primary key. Horrible code, but there's
     * no other way of doin' it.
     * 
     * @param pID id of the artist to be deleted.
     */
    public static void deleteArtist(int pID) {
        if (!f.exists())
            print("The file is empty.");
        else {
            // file is stored in an ArrayList.
            int size = Util.fileLength(f);
            ArrayList<Artist> fileCopy = new ArrayList<Artist>(size);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                for (int i = 0; i < size; i++)
                    fileCopy.add(i, (Artist) ois.readObject());

                ois.close();
            } catch (Exception e) {
                print("Something went wrong.");
            }

            for (int i = 0; i < fileCopy.size(); i++) {
                if (fileCopy.get(i).getID() == pID) {
                    print(fileCopy.get(i));
                    System.out.println("Are you sure about deleting this artist?");
                    if (Util.readChar('y', 'n') == 'y') {
                        fileCopy.remove(i);
                        print(fileCopy.get(i).getName() + " has been deleted.");
                        break;
                    } else
                        print("Delete operation aborted.");
                }
            }

            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                for (int i = 0; i < size - 1; i++) {
                    oos.writeObject(fileCopy.get(i));
                }
                oos.close();
            } catch (Exception e) {
                print("There was an error.");
            }
        }
    }

    /**
     * public and auxiliar method to add a concert to the database.
     * 
     * @param pCode
     * @return boolean indicating if the concert is already.
     */
    public static boolean isContained(int pCode) {
        boolean a = false;
        if (f.exists()) {
            int size = Util.fileLength(f);
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                for (int i = 0; i < size && !a; i++)
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

    /** Input the data of the artist to be deleted. */
    public static void getArtistsByName(String pName) {
        ObjectInputStream ois = null;
        Artist a = null;
        ArrayList<Artist> auxCopy = new ArrayList<Artist>();

        if (!f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                // Concert aux;
                for (int i = 0; i < length; i++) {
                    a = (Artist) ois.readObject();
                    if (a.isKnownAs(pName) != null)
                        auxCopy.add(a);
                }

            } catch (Exception e) {
                print("Something went wrong.");
            }
            try {
                ois.close();
            } catch (IOException e) {
                System.out.println("Error closing the file.");
            }
        }

        if (auxCopy.isEmpty())
            print("No se ha encontrado ningún artista con el nombre " + pName + ".");
        else {
            for (int i = 0; i < auxCopy.size(); i++)
                print(i + ". " + auxCopy.get(i));
            print("¿Cuál de ellos quieres modificar?");
            int pNumber = Util.readInt(0, auxCopy.size());

            if (!f.exists()) {
                try {
                    ois = new ObjectInputStream(new FileInputStream(f));
                    int length = Util.fileLength(f);
                    // Concert aux;
                    for (int i = 0; i < length; i++) {
                        a = (Artist) ois.readObject();
                        if (a.getID() == auxCopy.get(pNumber).getID())
                            auxCopy.add(a);
                        // TODO:
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
        }

    }

    /**
     * The only way of obtaining concerts from the Database if
     * by using the ID of an artist.
     * 
     * @returns null in case of not finding anything.
     */
    public static ArrayList<Artist> searchArtistsByName(String pName) {
        ObjectInputStream ois = null;
        Artist a = null;
        ArrayList<Artist> ans = new ArrayList<Artist>();

        if (f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                // Concert aux;
                for (int i = 0; i < length; i++) {
                    a = (Artist) ois.readObject();
                    if (a.isKnownAs(pName) != null)
                        ans.add(a);
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
        return ans;
    }

    public static Artist searchArtistByName(String pName) {
        ObjectInputStream ois = null;
        Artist a = null;

        if (!f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                // Concert aux;
                for (int i = 0; i < length; i++) {
                    a = (Artist) ois.readObject();
                    if (a.getName().equalsIgnoreCase(pName))
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

    public static void searchSongsByTitle() {
        print("¿Cuál es el título de la canción que quieres buscar?");
        String pTitle = Util.readText();

        ObjectInputStream ois = null;
        Artist a = null;
        ArrayList<Song> songs = new ArrayList<Song>();

        if (!f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                // Concert aux;
                for (int i = 0; i < length; i++) {
                    a = (Artist) ois.readObject();
                    if (a.hasSongsWithName(pTitle) != null)
                        songs.addAll(a.hasSongsWithName(pTitle));
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
    }

    public static void alta() { // TODO
        print("¿Quieres meter una banda o un artista en solitario? [b/s]");
        // char opt = Util.readChar('b', 's');
        // use a ternary to convert to integer.
        createArtist(0);
    }

    public static Artist createArtist(int pOpt) {
        Artist a = null;
        ;
        String pLabel = null;
        // TODO:

        int pID = new Random().nextInt(2000);
        print("Name of the artist: ");
        String pName = Util.readText();
        print("Is the artist still active? [y/n] ");
        boolean pActive = ('s' == Util.readChar('y', 'n'));
        print("Debut of the artist: ");
        LocalDate pDebut = Util.readDateYMD();
        print("Do you want to add the label? [y/n] ");
        if ('s' == Util.readChar('s', 'n')) {
            print("Input the label: ");
            pLabel = Util.readText();
        }

        switch (pOpt) {
            // creating a band.
            case 1:
                if (pLabel != null) {
                    print("Input the description: ");
                    String pDescription = Util.readText();
                    a = new Band(pID, pName, pLabel, pActive, pDebut, pDescription);
                } else
                    a = new Band(pID, pName, pActive, pDebut);
                break;
            // creating a solitaire artist.
            case 2:
                print("Real name: ");
                String pRealName = Util.readText();
                if (pLabel != null)
                    a = new Solo(pID, pName, pLabel, pActive, pDebut, pRealName);
                else
                    a = new Solo(pID, pName, pActive, pDebut, pRealName);
                break;
        }
        return a;
    }

    public static Album createAlbum() {
        print("¿Cuál es el nombre del album?");
        String pName = Util.readText();
        print("¿Cuándo se publico?");
        LocalDate pReleased = Util.readDateYMD();
        return new Album(pName, pReleased);
    }

    public static Concert creatConcert() {
        print("¿De qué artista fue el concierto?");
        String pName = Util.readText();
        int pAID = searchArtistByName(pName).getID();
        print("¿Cuándo es/fue el concierto?");
        LocalDate pDate = Util.readDateYMD();
        print("¿Dónde se celebró el concierto?");
        String pPlace = Util.readText();
        return new Concert(pk(), pAID, pDate, pPlace);
    }

    public static int pk() {
        // TODO: generator of primary keys.
        return 0;
    }

    /**
     * This method modifies a given artist.
     * 
     * @param pArtist
     */
    public static void modifyArtist(Artist pArtist) {
        deleteArtist(pArtist.getID());
        print(pArtist.toString());
        if (pArtist instanceof Band) {
            do {
                print("¿Qué datos quieres modificar del artista?" +
                        "\n1. Nombre." +
                        "\n2. Activo." +
                        "\n3. Fecha de debut" +
                        "\n4. Sello discográfico." +
                        "\n5. Descripción." +
                        "\n6. Añadir seudonimo." +
                        "\n7. Borrar seudónimo." +
                        "\n8. Añadir género." +
                        "\n9. Borrar género." +
                        "\n10. Añadir disco." +
                        "\n11. Borrar disco." +
                        "\n12. Añadir miembro." +
                        "\n13. Borrar miembro.");

                switch (Util.readInt(1, 13)) {
                    case 1:
                        print("¿Cuál es el nuevo nombre del artista? ");
                            pArtist.setName(Util.readText());
                        break;

                    case 2:
                        print("¿Sigue el artista en activo? [s/n] ");
                            pArtist.setActive('s' == Util.readChar('s', 'n'));
                            break;

                    case 3:
                        print("¿Cuál es la fecha debut? ");
                            pArtist.setDebut(Util.readDateYMD());
                        break;

                    case 4:
                        print("¿Cuál es el sello discográfico? ");
                            pArtist.setLabel(Util.readText());
                        break;

                    case 5: // only for bands.
                        print("¿Cuál es la descripción? ");
                            ((Band) pArtist).setDescription(Util.readText());
                        break;

                    case 6:
                        print("Introduce el seudónimo que quieres añadir: ");
                            pArtist.addPseudonym(Util.readText());
                        break;

                    case 7:
                        print("Estos son los seudónimos del artista:"
                                + pArtist.getPseudonyms() +
                                "¿Cuál quieres borrar?");
                        pArtist.deletePseudonym(Util.readText());
                        break;

                    case 8:
                        print("Introduce el género que quieres añadir: ");
                            pArtist.addGenres(Util.readText());
                        break;

                    case 9:
                        print("Estos son los géneros del artista: "
                                + pArtist.getGenres() +
                                "¿Cuál quieres borrar?");
                            pArtist.deleteGenre(Util.readText());
                        break;

                    case 10:
                        pArtist.addAlbum(createAlbum());
                        break;

                    case 11:
                        print(pArtist.getAlbumsInfo() +
                                "¿Cuál quieres borrar?");
                        pArtist.deleteAlbum(Util.readText());
                        break;

                    case 12:
                        print("¿Qué miembro quieres añadir?");
                        ((Band) pArtist).addMember(Util.readText());
                        break;

                    case 13:
                        print("Estos son todos los miembros de la banda:\n" +
                                ((Band) pArtist).getMembersNames() +
                                "¿Cuál quieres borrar?");
                        ((Band) pArtist).deleteMember(Util.readText());

                        break;
                }
                print("¿Quieres hacer más cambios? [y/n]");
            } while (Util.readChar('y', 'n') == 'y');
        }
        addArtist(pArtist);
        print("Los cambios se han guardado.");
    }

    public static void print(Object obj) {
        System.out.print(obj);
    }

}
