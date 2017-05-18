package yow.yourownway;

/**
 * Created by felix on 18.05.17.
 */
public class CalendarProvider {

    public long id;
    public String displayName;
    public String accountName;
    public String ownerName;

    public CalendarProvider(long id, String dN, String aN, String oN){
        this.id = id;               // 0
        this.displayName = dN;      // 1
        this.accountName = aN;      // 2
        this.ownerName = oN;        // 3
    }
}
