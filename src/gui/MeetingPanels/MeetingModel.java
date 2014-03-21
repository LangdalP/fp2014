package gui.MeetingPanels;

import model.Attendee;
import model.Meeting;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

/**
 * Created by Christoffer on 20.03.14.
 *
 * Holdes adskilt fra modelImpl. Ved avbryt vil dette objektet kastes.
 * Ved lagre vil dette objektet bli sendt til server.
 */
public class MeetingModel extends Meeting  {
    private PropertyChangeSupport pcs;
    private String username;
    private int nrAttendees = 0;
    private boolean attending;


    public MeetingModel(Meeting m) {
        super(m.getMeetingID(), m.getMeetingTime(), m.getDuration(), m.getDescription(), m.getMeetngLocation(), m.getMeetingOwner(),
                m.getMapAttendees(), m.getGuestAmount(), m.getMeetingRoom());
        pcs = new PropertyChangeSupport(this);
    }




    public void setUsername(String username) {
        this.username = username;
    }

    public Attendee getUserAttende() {
        return getMapAttendees().get(username);
    }


    public int getNrAttendees() {
        return nrAttendees;
    }

    public boolean hasAlarm(){
        if (getMapAttendees().get(username) == null) return false;
        return getUserAttende().getHasAlarm();
    }

    public Meeting meeting(){
        System.out.println("atts: " + getMapAttendees().size());
        for (Attendee att : getMapAttendees().values()) addAttendee(att);
        System.out.println("MEETING ATTS:\n " + getMapAttendees().values());
//        System.out.println("desc: " + getDescription());
        Meeting m = new Meeting(getMeetingID(), getMeetingTime(), getDuration(), getDescription(),getMeetngLocation(),getMeetingOwner(),getMapAttendees(),getGuestAmount(),getMeetingRoom());
        System.out.println("SENDING MEETING: " + m);
        return m;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    public Boolean isAttending() {
        if (getUserAttende() == null || !getUserAttende().getHasResponded()) return null;
        if (getUserAttende().getHasResponded() && getUserAttende().getAttendeeStatus()) return true;
        if (getUserAttende().getHasResponded() && !getUserAttende().getAttendeeStatus()) return false;
        return false;
    }

    public void addOwnerToAttendees(){
        addAttendee(new Attendee(getMeetingOwner(), false, false, new Date(), false, new Date()));
    }
}
