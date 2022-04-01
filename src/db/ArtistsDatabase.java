package db;

import java.io.*;
import java.util.*;

import tools.*;
import obj.*;

public abstract class ArtistsDatabase {
    /*** The ConcertsDatabase works in a very similar way.
     * @see ArrayList
     * @see ConcertsDatabase
     */
    private static final String PATH = "artists.dat";
    private static File f = new File(PATH);

     // Modules.

    /**
     * Add artists to the database. Before adding anything,
     * the database is checked to be sure that the artists
     * itself exists, by calling the auxiliar method "isContained".
     * @see Artist
     * @see Concert
     */

    public static void addArtist(Artist pArtist) {
        try {
            if (f.exists())
                if (null == findArtist(pArtist)) {
                    MyObjectOutputStream moos = new MyObjectOutputStream();
                    moos.writeObject(pArtist);
                    moos.close();
                } else {
                    ObjectOutputStream oos = 
                        new ObjectOutputStream(new FileOutputStream(f, true));
                    oos.writeObject(pArtist);
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
    public static void deleteArtist(int pID) {
        if (!f.exists())
            print("There's no file.");
        else {
            // file is stored in an ArrayList.
            int size = Util.fileLength(f);
            ArrayList<Artist> fileCopy = new ArrayList<Artist> ();
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                for (int i = 0; i < size; i++)
                    fileCopy.add((Artist) ois.readObject());

                ois.close();
            } catch (Exception e) {
                print("Something went wrong.");
            }

            Optional <Artist> pArtist = fileCopy.stream()
				.filter(a -> a.getID() == pID)
				.findFirst();
		    
                pArtist.ifPresent(a -> fileCopy.remove(a));
            

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

    public static Artist findArtistByID(int pID) {
        Artist ans = null;
        ObjectInputStream ois = null;

        if (f.exists())
            try {
                ois = 
                    new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                // concert aux;
                for (int i = 0; i < length; i++) {
                    ans = (Artist) ois.readObject();
                    if (ans.getID() == pID)
                        break;
                }
                ois.close();
            } catch (Exception e) {
                print("Something went wrong.");
            }
        return ans;
    }


    /**
     * Indicates if the artist is contained in the database.
     * @param pCode
     * @return the Artist indicating if the concert is already.
     * null in case of not finding anything.
     */
    public static Artist findArtist(Artist pArtist) {
        boolean exit = false;
        Artist ans = null;
        if (f.exists()) 
            try {
                int size = Util.fileLength(f);
                ObjectInputStream ois = 
                    new ObjectInputStream(new FileInputStream(f));
                for (int i = 0; i < size && !exit; i++) {
                    ans = ((Artist) ois.readObject());
                    exit = ((Artist) ois.readObject()).equals(pArtist);
                }
                        
                ois.close();
            } catch (Exception e) {
                System.out.println("Error 404 not found.");
                // in case of any error, the artist is returned anyway.
            }
        return ans;
    }
    


    /** Input the data of the artist to be deleted. 
     * @param pName
    */
    public static ArrayList <Artist> searchArtistsByName(String pName) {
        Artist a = null;
        ObjectInputStream ois = null;
        ArrayList<Artist> pSearch = new ArrayList<Artist>();

        if (f.exists())
            try {
                ois = 
                    new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                // concert aux;
                for (int i = 0; i < length; i++) {
                    a = (Artist) ois.readObject();
                    if (a.isKnownAs(pName) != null)
                    pSearch.add(a);
                }
                ois.close();
            } catch (Exception e) {
                print("Something went wrong.");
            }
        return pSearch;
    }

    /**Simply deletes and then adds an artist 
     * to the file. @param pArtist
     */
    public static void update(Artist pArtist) {
        deleteArtist(pArtist.getID());
        addArtist(pArtist);
    }


    /**Search all the artists that play that genre.
     * @param pGenre
    */
    public static ArrayList <Artist> searchArtistsByGenre(String pGenre) {
        Artist a = null;
        ObjectInputStream ois = null;
        ArrayList<Artist> pSearch = new ArrayList<Artist>();

        if (f.exists())
            try {
                ois = 
                    new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                // concert aux;
                for (int i = 0; i < length; i++) {
                    a = (Artist) ois.readObject();
                    if (a.playsGenre(pGenre) != null)
                    pSearch.add(a);
                }
                ois.close();
            } catch (Exception e) {
                print("Something went wrong.");
            }
        return pSearch;
    }

    
    /**Returns a list of all the artists that have a song
     * with that title. Then the information can be located.
     * @param pTitle
     */
    public static ArrayList <Artist> searchSongsByTitle(String pTitle) {
        ObjectInputStream ois = null;
        Artist a = null;
        ArrayList <Artist> pSearch = new ArrayList <Artist>();

        if (!f.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                // Concert aux;
                for (int i = 0; i < length; i++) {
                    a = (Artist) ois.readObject();
                    if (a.hasSongsWithName(pTitle) != null)
                        pSearch.add(a);
                }
                ois.close();
            } catch (FileNotFoundException fnfe) {
                System.out.println("File not found.");
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }

        return pSearch;
    }


    public static int getIDOfArtistWithMoreAlbums() {
        Artist a = null;
        int pID = 0, pNumAlb = 0;
        ObjectInputStream ois = null;

        if (f.exists())
            try {
                ois = 
                    new ObjectInputStream(new FileInputStream(f));
                int length = Util.fileLength(f);
                // concert aux;
                for (int i = 0; i < length; i++) {
                    a = (Artist) ois.readObject();
                    if (a.getNumberOfAlbums() > pNumAlb) {
                        pNumAlb = a.getNumberOfAlbums();
                        pID = a.getID();
                    }
                }
                ois.close();
            } catch (Exception e) {
                print("Something went wrong.");
            }
        return pID;
    }


    public static int createID() {
        return Util.fileLength(f);
    }    

    public static void print(Object obj) {
        System.out.print(obj);
    }
}
