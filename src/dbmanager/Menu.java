package dbmanager;

import tools.Util;

public abstract class Menu {

    public static void run() {
        System.out.println(
        "Welcome to the Artists Database!\n" +
        "Please select an option to search, add and change information!");

        int pOpt = 1;
        
        while (pOpt != -1) {
            System.out.println(text);
            pOpt = Util.readInt();
            switch (pOpt) {
                case 1:
                    DatabaseManagement.addArtist();
                    break;

                case 2:
                    DatabaseManagement.updateArtist();
                    break;

                case 3:
                    DatabaseManagement.addConcert();
                    break;

                case 4:
                    DatabaseManagement.searchArtistByName();
                    break;

                case 5:
                    DatabaseManagement.searchArtistByGenre();
                    break;

                case 6:
                    DatabaseManagement.searchSongs();
                    break;

                case 7:
                    DatabaseManagement.sortAlbums();
                    break;

                case 8:
                    DatabaseManagement.getDebutAlbumOfAnArtist();
                    break;

                case 9:
                    DatabaseManagement.showArtistWithMoreAlbums();
                break;

                case 10:
                    DatabaseManagement.concertsOfArtistSorted();

                break;

                case 11:
                    DatabaseManagement.showConcertsByYear();
                break;

                case 12:
                    DatabaseManagement.concertsOfArtist();
                break;

                case 13:
                    DatabaseManagement.futureConcertsOfArtist();
                break;

                case 14:
                    DatabaseManagement.recentArtists();
                break;

                default:
                    System.out.println("Invalid option. Try again.");
                break;
            }
        }
    }

    private static String text = 
    "\n1. Add an artist."+
    "\n2. Update an artist."+
    "\n3. Add a concert in the database."+
    "\n4. Search for an artist by name."+
    "\n5. Search artists by genre."+
    "\n6. Search songs by title."+
    "\n7. Show the discography of an artist sorted."+
    "\n8. Show the debut album of an artist."+
    "\n9. Show the artist with more albums."+
    "\n10. Show sorted concerts."+
    "\n11. Show all the concerts that an artist gave in a specific year."+
    "\n12. All the concerts that an artist gave."+
    "\n13. Search for future concert dates and venues from an artist."+
    "\n14. Consult the artists that have been consulted recently.\n"; 
}
