package in.techaddicts.bluenix.Model.EventBus;

import in.techaddicts.bluenix.Model.Room;

import java.util.List;

public class BarberDoneEvent {

    private List<Room> barberList;

    public BarberDoneEvent(List<Room> barberList) {
        this.barberList = barberList;
    }

    public List<Room> getBarberList() {
        return barberList;
    }

    public void setBarberList(List<Room> barberList) {
        this.barberList = barberList;
    }
}
