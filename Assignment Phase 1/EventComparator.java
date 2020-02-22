import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
    @Override
    // public int compare(Event e1, Event e2) {
    // int result = 0;
    // if (e1.getTime() > e2.getTime()) {
    // result = 1;
    // } else if (e1.getTime() < e2.getTime()) {
    // result = -1;
    // } else if (e1.getId() > e2.getId()) {
    // result = 1;
    // } else if (e1.getId() < e2.getId()) {
    // result = -1;
    // } else if (e1.getId() == e2.getId()) {
    // if (e1.getEventType() > e2.getEventType()) {
    // result = 1;
    // } else {
    // result = -1;
    // }
    // } else {
    // result = 0;
    // }
    // return result;
    // }
    public int compare(Event e1, Event e2) {
        if (e1.getTime() >= e2.getTime()) {
            if (e1.getTime() == e2.getTime()) {
                if (e1.getId() >= e2.getId()) {
                    if (e1.getId() == e2.getId()) {
                        if (e1.getEventType() > e2.getEventType()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }
}