package db;

import java.io.*;
import java.util.*;

import tools.*;
import obj.Artist;
import obj.Concert;

public abstract class ConcertsDatabase {
    /**
     * Do I have to add all the concerts in
     * an ArrayList. In that case, is
     * ArrayList a serializable class?
     * 
     * @see ArrayList
     */
    private static final String PATH = "concerts.dat";
    private static File f = new File(PATH);

    // Modules.

    /**
     * Add concerts to the database. Before adding anything,
     * the database is checked to be sure that the concert
     * itself exists, by calling the auxiliar method "isContained".
     * 
     * @see Concert
     */
    public static void addConcert(Concert pConcert) {
        try {
            if (f.exists())
                if (null == findConcert(pConcert)) {
                    MyObjectOutputStream moos = new MyObjectOutputStream();
                    moos.writeObject(pConcert);
                    moos.close();
                } else {
                    ObjectOutputStream oos = 
                        new ObjectOutputStream(new FileOutputStream(f, true));
                    oos.writeObject(pConcert);
                    oos.close();
                }
        } catch (Exception e) {
            print("Error during the storage.");
        }
    }

   

    /**
     * This module deletes an artist from the database
     * using its primary key. Horrible code, but there's
     * no other way of doin' it.
     * 
     * @param pID id of the artist to be deleted.
     */
    public static void deleteConcert(int pID) {
        if (!f.exists())
            print("There's no file.");
        else {
            // file is stored in an ArrayList.
            int size = Util.fileLength(f);
            ArrayList<Concert> fileCopy = new ArrayList<Concert> ();
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                for (int i = 0; i < size; i++)
                    fileCopy.add((Concert) ois.readObject());

                ois.close();
            } catch (Exception e) {
                print("Something went wrong.");
            }

            Optional <Concert> pConcert = fileCopy.stream()
				.filter(c -> c.getID() == pID)
				.findFirst();
		    
                pConcert.ifPresent(c -> fileCopy.remove(c));
            

            try {
                ObjectOutputStream oos = 
                    new ObjectOutputStream(new FileOutputStream(f));

                for (int i = 0; i < size - 1; i++) 
                    oos.writeObject(fileCopy.get(i));
                
                oos.close();
            } catch (Exception e) {
                print("There was an error.");
            }
        }
    }

    /**
     * The only way of obtaining concerts from the Database if
     * by using the ID of an artist.
     */
    public static ArrayList<Concert> getConcerts(Artist pArtist) {
        ObjectInputStream ois = null;
        ArrayList<Concert> ans = new ArrayList<Concert>();

        if (!f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                Concert aux;
                for (int i = 0; i < length; i++) {
                    aux = (Concert) ois.readObject();
                    if (aux.getAID() == pArtist.getID())
                        ans.add(aux);
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

    /**
     * ALl the concert with the same ID of the concert will be changed.
     * Theoretically, there's only one concert with that ID.
     * 
     * @param pConcert
     */
    public static void updateConcert(Concert pConcert) {
        if (f.exists()) {
            boolean done = false;

            FileOutputStream fos = null;
            ObjectOutputStream oos = null;
            FileInputStream fis = null;
            ObjectInputStream ois = null;

            File auxFile = new File("auxiliar.dat");
            try {
                // the auxiliar file to store data is created.
                fos = new FileOutputStream(auxFile);
                oos = new ObjectOutputStream(fos);
                // the file is opened to read the concerts.
                fis = new FileInputStream(f);
                ois = new ObjectInputStream(fis);

                Concert aux;
                do {
                    aux = (Concert) ois.readObject();
                    if (pConcert.getID() == aux.getID()) {
                        aux = pConcert;
                        done = true;
                    }

                    oos.writeObject(aux);

                } while (aux != null);
            }

            catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch (IOException e) {
                System.out.println(" ");
            } catch (ClassNotFoundException e) {
                System.out.println("Error en la lectura de datos.");
            }
            try {
                ois.close();
                fis.close();
                oos.close();
                fos.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar los ficheros");
            }

            if (!done)
                System.out.println("The changes weren't done correctly.");
            else {
                f.delete();
                auxFile.renameTo(f);
            }
        }
    }


    /**
     * Indicates if the artist is contained in the database.
     * @param pCode
     * @return the Artist indicating if the concert is already.
     * null in case of not finding anything.
     */
    public static Concert findConcert(Concert pConcert) {
        boolean exit = false;
        Concert ans = null;
        if (f.exists()) 
            try {
                int size = Util.fileLength(f);
                ObjectInputStream ois = 
                    new ObjectInputStream(new FileInputStream(f));
                for (int i = 0; i < size && !exit; i++) {
                    ans = ((Concert) ois.readObject());
                    exit = ((Concert) ois.readObject()).equals(pConcert);
                }
                        
                ois.close();
            } catch (Exception e) {
                System.out.println("Error 404 not found.");
                // in case of any error, the artist is returned anyway.
            }
        return ans;
    }


    public static int createID() {
        return Util.fileLength(f) + 1;
    }

    public static void print(Object obj) {
        System.out.print(obj);
    }
}
