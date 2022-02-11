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
            LocalDate.of(1966, 1, 1));

        b.addAlbum(
            new Album(
                "Trout Mask Replica",
                LocalDate.of(1969, 6, 15)));
        
        
        System.out.println(b.getLabel() + " " + b.getName() + " " + b.getAlbumsFromYear(1969));
        
        
    }
}
