package yow.yourownway;

import java.util.Date;

/**
 * Created by felix on 18.05.17.
 */
public class EventProvider {

    public String id;
    public String title;
    public String description;
    public Date start;
    public Date end;

    public EventProvider(String id, String title, String description, Date start, Date end){
        this.title = title;
        this.id = id;
        this.description = description;
        this.start = start;
        this.end = end;
    }
}
