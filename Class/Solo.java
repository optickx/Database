package Database.Class;

import java.time.LocalDate;

public class Solo extends Artist {

    private String realName;

    public Solo(
            int pID, String pName, boolean pActive,
            LocalDate pDebut, String pRealName) {
        super(pID, pName, pActive, pDebut);
        realName = pRealName;

    }

    public Solo(
            int pID, String pName, String pLabel,
            boolean pActive, LocalDate pDebut, String pRealName) {
        super(pID, pName, pLabel, pActive, pDebut);

        realName = pRealName;
    }

    public void setRealName(String pRealName) {
        realName = pRealName;
    }

    public String getRealName() {
        return realName;
    }


}
