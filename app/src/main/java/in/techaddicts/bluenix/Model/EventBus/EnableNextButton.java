package in.techaddicts.bluenix.Model.EventBus;

import in.techaddicts.bluenix.Model.Room;
import in.techaddicts.bluenix.Model.Studio;

public class EnableNextButton {

    private int step;
    private Room room;
    private Studio salon;
    private  int timeSlot;

    public EnableNextButton(int step, int timeSlot) {
        this.step = step;
        this.timeSlot = timeSlot;
    }

    public EnableNextButton(int step, Room room) {
        this.step = step;
        this.room = room;
    }

    public EnableNextButton(int step, Studio salon) {
        this.step = step;
        this.salon = salon;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setSalon(Studio salon) {
        this.salon = salon;
    }

    public Studio getSalon() {
        return salon;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }
}