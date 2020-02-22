package cs2030.simulator;

import java.util.Comparator;

/**
 * The EventComparator class to create a comparison criteria for events.
 */
class EventComparator implements Comparator<Event> {
    /**
     * Manages the sorting order of the priority queue. The strings of actions will
     * be stored in a priority queue and sorted based on the customer ID, time and
     * what action is being perfomed.
     * 
     * @param e1 Event 1
     * @param e2 Event 2
     */
    @Override
    public int compare(Event e1, Event e2) {
        int result = 0;
        if (e1.isAfter(e2)) {
            result = 1;
        } else if (!e1.isAfter(e2)) {
            result = -1;
        } else if (e1.hasBiggerId(e2)) {
            result = 1;
        } else if (!e1.hasBiggerId(e2)) {
            result = -1;
        } else if (e1.hasHigherType(e2)) {
            result = 1;
        } else if (!e1.hasHigherType(e2)) {
            result = -1;
        } else {
            result = 0;
        }
        return result;
    }
}
