package Database.Class;

import java.io.*;
import java.util.*;

import Database.Tools.*;

public abstract class ConcertsDatabase {
    /**
     * Do I have to add all the concerts in
     * an ArrayList. In that case, is
     * ArrayList a serializable class?
     * 
     * @see ArrayList
     */
    private static final String PATH = "directory of the file itself";
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
            if (f.exists()) {
                if (!isContained(pConcert.getID())) {
                    MyObjectOutputStream moos = new MyObjectOutputStream();
                    moos.writeObject(pConcert);
                    moos.close();
                } else {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, true));
                    oos.writeObject(pConcert);
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


    /**The only way of obtaining concerts from the Database if
     * by using the ID of an artist.
     */
    public static ArrayList<Concert> getConcerts(int pAID) { 
        ObjectInputStream ois = null;
        ArrayList<Concert> ans = new ArrayList<Concert>();

        if (!f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                Concert aux;
                for (int i = 0; i < length; i++) {
                    aux = (Concert) ois.readObject();
                    if (aux.getAID() == pAID)
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


    /**ALl the concert with the same ID of the concert will be changed.
     * Theoretically, there's only one concert with that ID.
     * @param pConcert
     */
    public static void modifyConcert(Concert pConcert) {
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



    public static void delete(int pID) {
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
                    if (pID != aux.getID()) {
                        oos.writeObject(aux);                        
                        done = true;
                    }
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
}
