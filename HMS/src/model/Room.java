package model;

public class Room {
    private int id;
    private String roomType;
    private double price;
    private String status; // "available" or "booked"

    // Default constructor
    public Room() {}

    // Parameterized constructor
    public Room(int id, String roomType, double price, String status) {
        this.id = id;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // ToString method for easy debugging
    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}