package Database.Class;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import javax.swing.text.PlainDocument;

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

    private static void deleteArtist(int pID) {
        int sino = 0;
        if (!f.exists()) {
            System.out.println("No hay artistas para borrar.");
        } else {
            // Volcado del fero a un ArrayList

            int cont = Util.fileLength(f);
            ArrayList<Artist> fileCopy = new ArrayList<Artist>(cont);
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                fis = new FileInputStream(f);
                ois = new ObjectInputStream(fis);
                for (int i = 0; i < cont; i++) {
                    fileCopy.add(i, (Artist) ois.readObject());
                }
                ois.close();
                fis.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch (IOException e) {
                System.out.println(" ");
            } catch (ClassNotFoundException e) {
                System.out.println("Error en la lectura de datos.");
            }
            // Tratamiento del ArrayList como hacíamos en la 2ª evaluación
            System.out.println("Introduce el DNI de la persona a borrar:");
            for (int i = 0; i < fileCopy.size(); i++) {
                if (fileCopy.get(i).getID() == pID) {
                    print(fileCopy.get(i));
                    System.out.println("Está seguro de que desea borrar a este artista? (1=SI/2=N0)");
                    sino = Util.readInt(1, 2);
                    if (sino == 1) {
                        fileCopy.remove(i);
                        System.out.println("Artist borrado correctamente.");
                        break;
                    }
                }
            }
            if (sino == 0) {
                System.out.println("No se ha podido borrar ya que no se ha encontrado.");
            } else if (sino == 2) {
                System.out.println("Operación de borrado abortada.");
            } else {
                // Se han realizado cambios, por lo que volcamos el ArrayList modificado en el
                // fero (machacándolo)
                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    for (int i = 0; i < cont - 1; i++) {
                        oos.writeObject(fileCopy.get(i));
                    }
                    oos.close();
                    fos.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File not found.");
                } catch (IOException e) {
                    System.out.println(" ");
                }
            }
        }
    }

    /**
     * Private and auxiliar method to add a concert to the database.
     * 
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

    public static void changeArtist() {
        print("¿Cuál es el nombre del artista que quieres modificar?");
        String pName = Util.readText();
        ObjectInputStream ois = null;
        Artist a = null;
        ArrayList<Artist> listado = new ArrayList<Artist>();

        if (!f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                // Concert aux;
                for (int i = 0; i < length; i++) {
                    a = (Artist) ois.readObject();
                    if (a.getName().contains(pName))
                        listado.add(a);
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

        if (listado.isEmpty())
            print("No se ha encontrado ningún artista con el nombre " + pName + ".");
        else {
            for (int i = 0; i < listado.size(); i++)
                print(i + ". " + listado.get(i));
            print("¿Cuál de ellos quieres modificar?");
            int pNumber = Util.readInt(0, listado.size());

            if (!f.exists()) {
                try {
                    ois = new ObjectInputStream(new FileInputStream(f));
                    int length = Util.fileLength(f);
                    // Concert aux;
                    for (int i = 0; i < length; i++) {
                        a = (Artist) ois.readObject();
                        if (a.getID() == listado.get(pNumber).getID())
                            listado.add(a);
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
    public static Artist getArtistByID(int pID) {
        ObjectInputStream ois = null;
        Artist a = null;

        if (!f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                // Concert aux;
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

    private static void alta() { // TODO
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
        Solo s;
        print("¿Cuál es el nombre del grupo?");
        String pName = Util.readText();
        print("¿Sigue el artista activo? s/n");
        boolean pActive = ('s' == Util.readChar('s', 'n'));
        print("¿Cuándo debutó la banda?");
        LocalDate pDebut = Util.readDateYMD();
        print("¿Cuál es su nombre real?");
        String pRealName = Util.readText();

        print("¿Quieres añadir el sello??");
        if ('s' == Util.readChar('s', 'n')) {
            print("¿Cuál es el sello?");
            String pLabel = Util.readText();
            s = new Solo(pk(), pName, pLabel, pActive, pDebut, pRealName);
        } else
            s = new Solo(pk(), pName, pActive, pDebut, pRealName);
        return s;
    }

    private static Band createBand() {
        Band b;
        print("¿Cuál es el nombre del grupo?");
        String pName = Util.readText();
        print("¿Sigue el artista activo? s/n");
        boolean pActive = ('s' == Util.readChar('s', 'n'));
        print("¿Cuándo debutó la banda?");
        LocalDate pDebut = Util.readDateYMD();
        print("¿Quieres añadir el sello??");
        if ('s' == Util.readChar('s', 'n')) {
            print("¿Cuál es el sello?");
            String pLabel = Util.readText();
            print("¿Quieres añadir la descripción?");
            if ('s' == Util.readChar('s', 'n')) {
                String pDescription = Util.readText();
                b = new Band(pk(), pName, pLabel, pActive, pDebut, pDescription);
            } else {
                b = new Band(pk(), pName, pLabel, pActive, pDebut);
            }
        } else
            b = new Band(pk(), pName, pActive, pDebut);

        return b;
    }

    private static Album createAlbum() {
        print("¿Cuál es el nombre del album?");
        String pName = Util.readText();
        print("¿Cuándo se publico?");
        LocalDate pReleased = Util.readDateYMD();
        return new Album(pName, pReleased);
    }

    private static Concert creatConcert() {
        print("¿De qué artista fue el concierto?");
        String pName = Util.readText();
        int pAID = searchArtistByName(pName).getID();
        print("¿Cuándo es/fue el concierto?");
        LocalDate pDate = Util.readDateYMD();
        print("¿Dónde se celebró el concierto?");
        String pPlace = Util.readText();
        return new Concert(pk(), pAID, pDate, pPlace);
    }

    private static int pk() {
        // TODO: generator of primary keys.
        return 0;
    }

    /*
     * ObjectInputStream ois = null;
     * Artist a = null;
     * 
     * if (!f.exists()) {
     * try {
     * ois = new ObjectInputStream(new FileInputStream(f));
     * int length = Util.fileLength(f);
     * //Concert aux;
     * for (int i = 0; i < length; i++) {
     * a = (Artist) ois.readObject();
     * if (a.getID() == pID)
     * break;
     * }
     * 
     * } catch (FileNotFoundException fnfe) {
     * System.out.println("File not found.");
     * } catch (Exception e) {
     * System.out.
     * println("Well, something went wrong because files in java suck. What else can I say?"
     * );
     * }
     * try {
     * ois.close();
     * } catch (IOException e) {
     * System.out.println("Error closing the file.");
     * 
     */

    private static void print(Object obj) {
        System.out.print(obj);
    }

}
