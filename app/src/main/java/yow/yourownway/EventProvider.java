package yow.yourownway;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by felix on 18.05.17.
 */
public class EventProvider implements Serializable {

    public int id;
    public String title;
    public String description;
    public Date start;
    public Date end;

    public EventProvider(int id, String title, String description, Date start, Date end){
        this.title = title;
        this.id = id;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    private void writeObject(ObjectOutputStream o)
            throws IOException {

        o.writeObject(id);
        o.writeObject(title);
        o.writeObject(description);
        o.writeObject(start);
        o.writeObject(end);
    }

    private void readObject(ObjectInputStream o)
            throws IOException, ClassNotFoundException {

        id = (Integer) o.readObject();
        title = (String) o.readObject();
        description = (String) o.readObject();
        start = (Date) o.readObject();
        end = (Date) o.readObject();
    }
}
