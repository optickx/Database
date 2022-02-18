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
                if (!isContained(pConcert)) {
                    MyObjectOutputStream moos = new MyObjectOutputStream();
                    moos.writeObject(pConcert);
                    moos.close();
                } else {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, true));
                    oos.writeObject(pConcert);
                    oos.close();
                }
            } else {
                // TODO: Create a new file to add the values.
            }
        } catch (Exception e) {
            System.out.println("There was some sort of error.");
        }
    }

    /**
     * Private and auxiliar method to add a concert to the database.
     * 
     * @param pCode
     * @return boolean indicating if the concert is already.
     */
    public static boolean isContained(Concert pConcert) {
        boolean a = false;
        if (f.exists()) {
            int length = Util.fileLength(f);
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                for (int i = 0; i < length && !a; i++)
                    if (((Concert) ois.readObject()).getID() == pConcert.getID())
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
    public static void deleteConcert(Concert pConcert) {
        // TODO.: CHECK SECURITY
        char sino = 's';

        if (!f.exists()) {
            System.out.println("No hay conciertos para borrar.");
        } else {
            // Volcado del fichero a un ArrayList
            int cont = Util.fileLength(f);
            ArrayList<Concert> concerts = new ArrayList<Concert>(cont);
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                fis = new FileInputStream(f);
                ois = new ObjectInputStream(fis);
                for (int i = 0; i < cont; i++) {
                    concerts.add(i, (Concert) ois.readObject());
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
            for (int i = 0; i < concerts.size(); i++) {
                if (concerts.get(i).getID() == pConcert.getID()) {
                   System.out.println(concerts.get(i));
                    System.out.println("¿Está seguro de que desea borrar este concierto? [s/n]");
                    sino = Util.readChar('s', 'n');
                    if (sino == 's') {
                        concerts.remove(i);
                        System.out.println("Concierto borrado correctamente");
                        break;
                    }
                }
            }
            if (sino == 0) {
                System.out.println("No se ha podido borrar ya que no se ha encontrado");
            } else if (sino == 2) {
                System.out.println("Operación de borrado abortada");
            } else {
                // Se han realizado cambios, por lo que volcamos el ArrayList modificado en el
                // fichero (machacándolo)
                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    for (int i = 0; i < cont - 1; i++) {
                        oos.writeObject(concerts.get(i));
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

    

    public static int createID() {
        return 0;
        // TODO
    }
}
