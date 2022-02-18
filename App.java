package Database;

// import java.util.*;
import java.time.LocalDate;
import Database.Class.*;

public class App {

    public static void main(String[] args) {
        Band b = new Band(
            0,
            "Captain Beefheart & His Magic Band",
            "Bizarre Records",
            false,
            LocalDate.of(1966, 1, 1),
            "The most weird but awesome band of the 1960s.");

        b.addAlbum(new Album(
                "Trout Mask Replica",
                LocalDate.of(1969, 6, 15)));
        b.addAlbum(new Album(
                "Safe as Milk",
                LocalDate.of(1967, 3, 15)));
        b.addAlbum(new Album(
                "Clear Spot",
                LocalDate.of(1972, 9, 1)));
        b.addAlbum(new Album(
                "Clear Spot",
                LocalDate.of(1972, 9, 1)));
        b.addAlbum(new Album(
                "The A & M",
                LocalDate.of(1965, 5, 1)));

        System.out.println(b.getLabel() + " " + b.getName() + " " + b.getAlbumsFromYear(1970));
        
        // the values are technically able to be sorted.
        System.out.println(b.getDebutYear());
    }
}
