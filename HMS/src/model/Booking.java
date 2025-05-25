package model;

import java.sql.Date;

public class Booking {
    private int id;
    private int guestId;
    private int roomId;
    private Date checkIn;
    private Date checkOut;

    // Default constructor
    public Booking() {}

    // Parameterized constructor
    public Booking(int id, int guestId, int roomId, Date checkIn, Date checkOut) {
        this.id = id;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public Date getCheckIn() { return checkIn; }
    public void setCheckIn(Date checkIn) { this.checkIn = checkIn; }

    public Date getCheckOut() { return checkOut; }
    public void setCheckOut(Date checkOut) { this.checkOut = checkOut; }

    // ToString method for easy representation
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", guestId=" + guestId +
                ", roomId=" + roomId +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}