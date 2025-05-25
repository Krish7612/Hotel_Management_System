package dao;

import model.Booking;
import hms.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public void addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (guest_id, room_id, check_in_date, check_out_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, booking.getGuestId());
            ps.setInt(2, booking.getRoomId());
            ps.setDate(3, booking.getCheckIn());
            ps.setDate(4, booking.getCheckOut());
            ps.executeUpdate();

            // Update room status to booked
            RoomDAO roomDAO = new RoomDAO();
            roomDAO.updateRoomStatus(booking.getRoomId(), "booked");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("booking_id")); // Ensure column name matches DB schema
                booking.setGuestId(rs.getInt("guest_id"));
                booking.setRoomId(rs.getInt("room_id"));
                booking.setCheckIn(rs.getDate("check_in_date"));
                booking.setCheckOut(rs.getDate("check_out_date"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}
