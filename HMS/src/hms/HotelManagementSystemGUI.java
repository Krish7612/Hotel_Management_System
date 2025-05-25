package hms;

import dao.BookingDAO;
import dao.GuestDAO;
import dao.RoomDAO;
import model.Booking;
import model.Guest;
import model.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.List;

public class HotelManagementSystemGUI extends JFrame {
    private final GuestDAO guestDAO = new GuestDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final BookingDAO bookingDAO = new BookingDAO();

    private final DefaultTableModel guestTableModel;
    private final DefaultTableModel roomTableModel;
    private final DefaultTableModel bookingTableModel;

    public HotelManagementSystemGUI() {
        setTitle("Hotel Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Guests tab
        JPanel guestPanel = new JPanel(new BorderLayout());
        guestTableModel = new DefaultTableModel(new Object[]{"Guest ID", "Name", "Contact Info"}, 0);
        JTable guestTable = new JTable(guestTableModel);
        guestPanel.add(new JScrollPane(guestTable), BorderLayout.CENTER);
        guestPanel.add(createGuestForm(), BorderLayout.SOUTH);
        tabbedPane.addTab("Guests", guestPanel);

        // Rooms tab
        JPanel roomPanel = new JPanel(new BorderLayout());
        roomTableModel = new DefaultTableModel(new Object[]{"Room ID", "Room Type", "Price", "Status"}, 0);
        JTable roomTable = new JTable(roomTableModel);
        roomPanel.add(new JScrollPane(roomTable), BorderLayout.CENTER);
        roomPanel.add(createRoomForm(), BorderLayout.SOUTH);
        tabbedPane.addTab("Rooms", roomPanel);

        // Bookings tab
        JPanel bookingPanel = new JPanel(new BorderLayout());
        bookingTableModel = new DefaultTableModel(new Object[]{"Booking ID", "Guest ID", "Room ID", "Check-In", "Check-Out"}, 0);
        JTable bookingTable = new JTable(bookingTableModel);
        bookingPanel.add(new JScrollPane(bookingTable), BorderLayout.CENTER);
        bookingPanel.add(createBookingForm(), BorderLayout.SOUTH);
        tabbedPane.addTab("Bookings", bookingPanel);

        add(tabbedPane);

        // Load initial data
        loadGuests();
        loadRooms();
        loadBookings();
    }

    private JPanel createGuestForm() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField nameField = new JTextField(15);
        JTextField contactField = new JTextField(15);
        JButton addGuestButton = new JButton("Add Guest");

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Contact Info:"));
        panel.add(contactField);
        panel.add(addGuestButton);

        addGuestButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String contact = contactField.getText().trim();
            if (name.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both name and contact info.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Guest guest = new Guest();
            guest.setName(name);
            guest.setContactInfo(contact);
            guestDAO.addGuest(guest);
            loadGuests();
            nameField.setText("");
            contactField.setText("");
            JOptionPane.showMessageDialog(this, "Guest added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }

    private JPanel createRoomForm() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField typeField = new JTextField(10);
        JTextField priceField = new JTextField(7);
        JButton addRoomButton = new JButton("Add Room");

        panel.add(new JLabel("Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(addRoomButton);

        addRoomButton.addActionListener(e -> {
            String type = typeField.getText().trim();
            String priceText = priceField.getText().trim();
            if (type.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both type and price.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double price;
            try {
                price = Double.parseDouble(priceText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for price.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Room room = new Room();
            room.setRoomType(type);
            room.setPrice(price);
            roomDAO.addRoom(room);
            loadRooms();
            typeField.setText("");
            priceField.setText("");
            JOptionPane.showMessageDialog(this, "Room added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }

    private JPanel createBookingForm() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField guestIdField = new JTextField(5);
        JTextField roomIdField = new JTextField(5);
        JTextField checkInField = new JTextField(10);
        JTextField checkOutField = new JTextField(10);
        JButton bookButton = new JButton("Book Room");

        panel.add(new JLabel("Guest ID:"));
        panel.add(guestIdField);
        panel.add(new JLabel("Room ID:"));
        panel.add(roomIdField);
        panel.add(new JLabel("Check-In (yyyy-mm-dd):"));
        panel.add(checkInField);
        panel.add(new JLabel("Check-Out (yyyy-mm-dd):"));
        panel.add(checkOutField);
        panel.add(bookButton);

        bookButton.addActionListener((ActionEvent e) -> {
            String guestIdText = guestIdField.getText().trim();
            String roomIdText = roomIdField.getText().trim();
            String checkInText = checkInField.getText().trim();
            String checkOutText = checkOutField.getText().trim();

            if (guestIdText.isEmpty() || roomIdText.isEmpty() || checkInText.isEmpty() || checkOutText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all booking details.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int guestId;
            int roomId;
            Date checkInDate;
            Date checkOutDate;
            try {
                guestId = Integer.parseInt(guestIdText);
                roomId = Integer.parseInt(roomIdText);
                checkInDate = Date.valueOf(checkInText);
                checkOutDate = Date.valueOf(checkOutText);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check availability
            List<Room> availableRooms = roomDAO.getAvailableRooms();
            boolean available = availableRooms.stream().anyMatch(r -> r.getId() == roomId);
            if (!available) {
                JOptionPane.showMessageDialog(this, "Room is not available.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Booking booking = new Booking();
            booking.setGuestId(guestId);
            booking.setRoomId(roomId);
            booking.setCheckIn(checkInDate);
            booking.setCheckOut(checkOutDate);

            bookingDAO.addBooking(booking);
            loadBookings();
            loadRooms(); // Update rooms status in UI

            guestIdField.setText("");
            roomIdField.setText("");
            checkInField.setText("");
            checkOutField.setText("");

            JOptionPane.showMessageDialog(this, "Booking successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }

    private void loadGuests() {
        guestTableModel.setRowCount(0);
        List<Guest> guests = guestDAO.getAllGuests();
        for (Guest g : guests) {
            guestTableModel.addRow(new Object[]{g.getId(), g.getName(), g.getContactInfo()});
        }
    }

    private void loadRooms() {
        roomTableModel.setRowCount(0);
        List<Room> rooms = roomDAO.getAllRooms();
        for (Room r : rooms) {
            roomTableModel.addRow(new Object[]{r.getId(), r.getRoomType(), r.getPrice(), r.getStatus()});
        }
    }

    private void loadBookings() {
        bookingTableModel.setRowCount(0);
        List<Booking> bookings = bookingDAO.getAllBookings();
        for (Booking b : bookings) {
            bookingTableModel.addRow(new Object[]{
                    b.getId(), b.getGuestId(), b.getRoomId(),
                    b.getCheckIn(), b.getCheckOut()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HotelManagementSystemGUI app = new HotelManagementSystemGUI();
            app.setVisible(true);
        });
    }
}

