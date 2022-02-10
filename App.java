package ArtistsDatabase;

// import java.util.*;
import java.time.LocalDate;
import ArtistsDatabase.Class.Band;

public class App {

    public static void main(String[] args) {
        Band b = new Band(
            0,
            "Captain Beefheart & His Magic Band",
            "Bizarre Records",
            false,
            LocalDate.of(1966, 1, 1));
        System.out.println(b.getLabel());
    }
}
