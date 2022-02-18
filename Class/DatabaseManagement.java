package Database.Class;

import java.util.*;

import Database.Class.Databases.ArtistsDatabase;
import Database.Class.Databases.ConcertsDatabase;
import Database.Class.Objects.Album;
import Database.Class.Objects.Artist;
import Database.Class.Objects.Band;
import Database.Class.Objects.Concert;
import Database.Class.Objects.Solo;
import Database.Class.Objects.Song;
import Database.Tools.Util;
import java.time.LocalDate;

public abstract class DatabaseManagement {

    /**Adds an artist to the database, calling the 
     * private method createArtist.
     */
    public static void addArtist() {
        print("Band or solo artist? [b/s]");
        ArtistsDatabase.addArtist(createArtist(Util.readChar('b', 's')));
    }

    /**Updates the value of an artist in the database,
     * asking the user which artist to be deleted.
     */
    public static void updateArtist() {
        Artist pArtist = selectArtistFromDatabase();
        modifyArtist(pArtist);
        // once the artist has been modified, it's updated.
        ArtistsDatabase.update(pArtist);
    }

    /**Ask the user for an artist name, and then shows
     * all the results of the datbase.
     */
    public static void searchArtistByName() {
        print("Input the name of the artist: ");
            ArrayList <Artist> pArtist =
                ArtistsDatabase.searchArtistsByName(Util.readText());
        if (pArtist.isEmpty())
            print("No results where found.");
        else {
            print("Here's all the results:  ");
            for (Artist a : pArtist) 
                print(a + "\n");
        }
    }   

    /**Adds a concert to the database, given an artist. */
    public static void addConcert() {
        Artist pArtist = selectArtistFromDatabase();
        pArtist.addConcert(createConcert(pArtist));
        ArtistsDatabase.update(pArtist);
    }

    public static void deleteConcert() {
        
    }

    private static Artist selectArtistFromDatabase() {
        print("Input the name of the artist: ");
            String pName = Util.readText();
        ArrayList <Artist> pSearch = 
            ArtistsDatabase.searchArtistsByName(pName);
        if (pSearch.isEmpty()) 
            print("No artists where found");
        else {
            print("These artists where found:\n");
            for (Artist a : pSearch) 
                print((pSearch.indexOf(a) + 1) + ". " + a);

        print("\nWhich one do you want?");
            // the user selects which one from the list.
            int pIndex = Util.readInt(1, pSearch.size()) - 1;
            return pSearch.get(pIndex);
        }
        return null;        
    }


    /**Ask the user for aa genre, and then shows
     * all the results of the datbase.
     */
    public static void searchArtistByGenre() {
        print("Input the genre you want to search: ");
            ArrayList <Artist> pArtist =
                ArtistsDatabase.searchArtistsByGenre(Util.readText());
        if (pArtist.isEmpty())
            print("No results where found.");
        else {
            print("Here's all the results:  ");
            for (Artist a : pArtist) 
                print(a + "\n");
        }
    }

    /**Searches all the information of the songs 
     * with that title.
    */
    public static void searchSongs() {
        print("Input the title of the song: ");
            String pTitle = Util.readText();
        ArrayList <Artist> pSearch
            = ArtistsDatabase.searchSongsByTitle(pTitle);
        print("The following artists have at least one" +
        "song that matches with the title " + pTitle + ".\n");
        for (Artist a : pSearch) 
            print(a.getName());        
    }



    public static void sortAlbums() {
        print("Input the name of the artist you want to search: ");
            String pName =  Util.readText();
        // TODO call to the selector.
        print("How do you want to sort the albums?" +
        "\n1. By year." +
        "\n2. By name." +
        "\n3. By length.");
            int pOpt = Util.readInt(1, 3);


        ArrayList <Artist> pArtists = 
            ArtistsDatabase.searchArtistsByName(pName);
        print("These are all the artists found by name " + pName + ":\n");
        for (Artist a : pArtists) 
            print((pArtists.indexOf(a) + 1) + a.toString());

        print("Select which ones discography you want: ");
            int pIndex = Util.readInt(1, pArtists.size()) - 1;

        ArrayList <Album> pAlbums = 
            pArtists.get(pIndex).getAlbumsSorted(pOpt);

        print(pArtists.get(pIndex).getName() + 
        " has the following albums:\n");
        
        for (Album a : pAlbums) 
            print(a);
    }


    public static void getDebutAlbumOfAnArtist() {
        print("Which artist do you want to search: ");
            String pName = Util.readText();
        ArrayList <Artist> pArtists =
            ArtistsDatabase.searchArtistsByName(pName);

        for (Artist a : pArtists) 
            print((pArtists.indexOf(a) + 1) + a.toString());

        print("Select which ones discography you want: ");
            int pIndex = Util.readInt(1, pArtists.size()) - 1;

        print("The debut album of " + 
            pArtists.get(pIndex).getName() + " is " +
            pArtists.get(pIndex).getDebutAlbum().toString());
    }

    /**Add a concert to an artist. */
   


    public static void showArtistWithMoreAlbums() {
        Artist ans = ArtistsDatabase.
            findArtistByID(
                ArtistsDatabase.getIDOfMoreAlbums());
        print(ans);
    }


    public static void concertsOfArtistSorted() {
        Artist pArtist = selectArtistFromDatabase();
        print("How do you want to order the concerts?" +
            "\n1. By date." +
            "\n2. By place." +
            "\n3. By price.");
            int pOpt = Util.readInt(1, 3);

        ArrayList <Concert> pConcerts = 
            pArtist.sortConcertsBy(pOpt);

        for (Concert c : pConcerts) 
            print(c.toString());
    }


    public static void showConcertsByYear() {
        print("Input the year you want to search by: ");
            int pYear = Util.readInt(0, LocalDate.now().getYear());
        Artist pArtist = selectArtistFromDatabase();
        ArrayList <Concert> pConcerts = 
            pArtist.getConcertsFromYear(pYear);

        for (Concert c : pConcerts) 
            print(c.toString());
    }

    public static void showConcertsByPlace() {

    }

    /**This module creates new artists (of both types)
     * with all the information about them inside.
     * @param pOpt
     */
    private static Artist createArtist(char pOpt) {
        Artist pArtist = null;
        String pLabel = null;

        int pID = ArtistsDatabase.createID();
        print("Name of the artist: ");
            String pName = Util.readText();
        print("Is the artist still active? [y/n] ");
            boolean pActive = ('s' == Util.readChar('y', 'n'));
        print("Debut of the artist: (Y-M-D) ");
            LocalDate pDebut = Util.readDateYMD();
        print("Do you want to add the label? [y/n] ");
            if ('s' == Util.readChar('s', 'n')) {
                print("Input the label: ");
                    pLabel = Util.readText();
            }

        switch (pOpt) {
            // creating a band.
            case 'b':
                if (pLabel != null) {
                    print("Input the description: ");
                        String pDescription = Util.readText();
                    pArtist = 
                        new Band(pID, pName, pLabel, 
                        pActive, pDebut, pDescription);
                } else
                    pArtist = 
                        new Band(pID, pName, 
                        pActive, pDebut);
                
                break;
            // creating a solitaire artist.
            case 's':
                print("Real name: ");
                    String pRealName = Util.readText();
                if (pLabel != null)
                    pArtist = 
                        new Solo(pID, pName, pLabel, 
                        pActive, pDebut, pRealName);
                else
                    pArtist = 
                        new Solo(pID, pName, pActive, 
                        pDebut, pRealName);
                break;
        }
        return pArtist;
    }
    /**Module that creates a new album asking to
     * the user all the information.
     */
    private static Album createAlbum() {
        Album rAlbum;
        print("Input the artist of the album: ");
            String pName = Util.readText();
        print("Input the releasing date of the album: ");
            LocalDate pReleased = Util.readDateYMD();

        rAlbum = new Album(pName, pReleased);

        do {
            print("Next song of the album " + pName + ":\n");
                rAlbum.addSong(createSong());
            print("Add more songs to " + pName + "? [y/n]");
        } while ('y' == Util.readChar('y', 'n'));

        return rAlbum;
    }
    /**@return a new song, asking the user all 
     * the information stored in it.
     */
    private static Song createSong() {
        print("Input the title of the song: s");
            String pTitle = Util.readText();
        print("Input the track number in the album: ");
            int pNumber = Util.readInt();
        print("Input the length of the track (in seconds): ");
            int pLength = Util.readInt();

        return new Song(pTitle, pNumber, pLength);
    }
    /**Creates a new concert given some information.
     * @param pArtist is obligatory 
     * @return a new concert.
     */
    private static Concert createConcert(Artist pArtist) {
        String pDescription = null; 
        print("Input the date of the conert: ");
            LocalDate pDate = Util.readDateYMD();
        print("Where was the concert held? ");
            String pPlace = Util.readText();
            
        print("Do you want to add a price? [y/n] ");
                if ('y' == Util.readChar('y', 'n')) {
                    print("Enter the price of the concert: ");
                        int pPrice = Util.readInt(5, 1000);
                    print("Enter the description: ");
                        pDescription = Util.readText();
                    return new Concert(
                        ConcertsDatabase.createID(),
                        pArtist.getID(), pDate, pPlace, pDescription, pPrice);
                }

        return new Concert(
            ConcertsDatabase.createID(),
            pArtist.getID(), pDate, pPlace);
    }

    /**This method loops the void that changes the 
     * attributes of an artist. @param pArtist
     */
    private static void modifyArtist(Artist pArtist) {
        do {
            changeValuesOfAnArtist(pArtist);
            print("\n\nDo you want to make more changes to "
            + pArtist.getName() + "? [y/n]");
        } while ('y' == Util.readChar('y', 'n'));
    }
    /**Ask about the information of all the attributes 
     * of an artist, depending on it's type.
     */
    private static void changeValuesOfAnArtist(Artist pArtist) {
        print("\n" + pArtist.toString() + "\n");
        int pOpt = 1;
        print("Which information do you want to change?" +
            "\n1. Name." +
            "\n2. Activity." +
            "\n3. Debut date." +
            "\n4. Label." +
            "\n5. Add pseudonym." +
            "\n6. Delete pseudonym." +
            "\n7. Add genre." +
            "\n8. Delete genre." +
            "\n9. Add album." +
            "\n10. Delete album." +
            "\n11. Add a concert." +
            "\n12. Delete a concert.");
        if (pArtist instanceof Band) 
            print("\n11. Band special information.");
        else if (pArtist instanceof Solo)
            print("\n11. Real name.");
        pOpt = Util.readInt(1, 12);

        print("\n");

        if (pOpt < 13) 
            switch (pOpt) {
                case 1:
                    print("Input " + pArtist.getName() + " 's new name: ");
                        pArtist.setName(Util.readText());
                    break;

                case 2:
                    print("Is the artist still active? [y/n] ");
                        pArtist.setActive('s' == Util.readChar('y', 'n'));
                        break;

                case 3:
                    print("What's the debut date? ");
                        pArtist.setDebut(Util.readDateYMD());
                    break;

                case 4:
                    print("Input the discographic label: ");
                        pArtist.setLabel(Util.readText());
                    break;

                case 5:
                    print("Input the pseudonym you want to add: ");
                        pArtist.addPseudonym(Util.readText());
                    break;

                case 6:
                    print("These are " + pArtist.getName() + " pseudonyms.\n"
                    + pArtist.getPseudonyms() +
                    "\nWhich one do you want to remove? ");
                        pArtist.deletePseudonym(Util.readText());
                    break;

                case 7:
                    print("Input the genre you want to add: ");
                        pArtist.addGenres(Util.readText());
                    break;

                case 8:
                    print("These are " + pArtist.getName() + "'s genres.\n"
                    + pArtist.getGenres() +
                    "\nWhich one do you want to delete? ");
                        pArtist.deleteGenre(Util.readText());
                    break;

                case 9:
                    pArtist.addAlbum(createAlbum());
                    break;

                case 10:
                    print("These are all the albums:\n"
                    + pArtist.getAlbumsInfo() +
                    "\nWhich one do you want to delete? ");
                        pArtist.deleteAlbum(Util.readText());
                break;

                case 11:
                    pArtist.addConcert(createConcert(pArtist));
                break;

                case 12:
                    String concertsInfo = "";
                    ArrayList <Concert> pConcerts = 
                        pArtist.getConcerts();
                    for (Concert c : pConcerts) 
                        concertsInfo += pConcerts.indexOf(c) + ". " + c.toString() + "\n";
                    print("These are all the concerts of " +
                    pArtist.getName() + ":\n" + concertsInfo + 
                    "\nWhich one do you want to delete?");
                        int pIndex = Util.readInt(1, pConcerts.size()) - 1;
                    pArtist.deleteConcert(pConcerts.get(pIndex));
                break;
            }
        else 
            if (pArtist instanceof Band) {
                print("1. Add member." +
                "\n2. Remove member." +
                "\n3. Change description.");
                    pOpt = Util.readInt(1, 3);

                switch(pOpt) {
                    case 1:
                        print("Which member do you want to add?");
                        ((Band) pArtist).addMember(Util.readText());
                        break;

                    case 2:
                        print("These are all the band members:\n" +
                        ((Band) pArtist).getMembersNames() +
                        "\nWho do you want to delete?");
                        ((Band) pArtist).deleteMember(Util.readText());
                        break;

                    case 3:
                        print("This is the description of the band:\n" + 
                        ((Band) pArtist).getDescription() + "\n Input the new one.");
                            ((Band) pArtist).setDescription(Util.readText());
                        break;
                }
            }
            else if (pArtist instanceof Solo) {
                print("Input the real name of " + pArtist.getName() + ": ");
                    ((Solo) pArtist).setRealName(Util.readText());
            }
    }


    private static void print(Object obj) {
        System.out.println(obj);
    }
}
