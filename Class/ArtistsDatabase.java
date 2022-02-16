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


    public static void deleteArtist(int pID) {
        
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


    

    public static void changeArtist(){
        print("¿Cuál es el nombre del artista que quieres modificar?");
            String pName = Util.readText();
        ObjectInputStream ois = null;
        Artist a = null;
        ArrayList <Artist> listado = new ArrayList <Artist> ();

        if (!f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                //Concert aux;
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
                    //Concert aux;
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

/**The only way of obtaining concerts from the Database if
     * by using the ID of an artist.
     * @returns null in case of not finding anything.
     */
    public static Artist getArtistByID(int pID) { 
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


    public static Artist searchArtistByName(String pName) { 
        ObjectInputStream ois = null;
        Artist a = null;

        if (!f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                //Concert aux;
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
            if ('s'  == Util.readChar('s', 'n')) {
                print("¿Cuál es el sello?");
                String pLabel = Util.readText();
                s = new Solo(pk(), pName, pLabel, pActive, pDebut, pRealName);
            }
            else 
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
            if ('s'  == Util.readChar('s', 'n')) {
                print("¿Cuál es el sello?");
                String pLabel = Util.readText();
                print("¿Quieres añadir la descripción?");
                    if ('s' == Util.readChar('s', 'n')) {
                        String pDescription = Util.readText();
                        b = new Band(pk(), pName, pLabel, pActive, pDebut, pDescription);
                    }
                    else {
                        b =  new Band(pk(), pName, pLabel, pActive, pDebut);
                    }
            }
            else 
                b = new Band(pk(), pName, pActive, pDebut);
            
            return b;
    }

    private static Album createAlbum(){
        print("¿Cuál es el nombre del album?");
            String pName = Util.readText();
        print("¿Cuándo se publico?");
            LocalDate pReleased = Util.readDateYMD();
        return new Album(pName, pReleased);
    }

    private static Concert creatConcert(){
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

    private static void print(Object obj) {
        System.out.print(obj);
    }

}
