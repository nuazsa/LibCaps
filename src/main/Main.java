/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import developer.Developer;
import java.util.Random;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Azis
 */
public class Main extends javax.swing.JFrame {
    public Statement st;
    public ResultSet rs;
    String todayDate;
    // Connector Database   
    Connection conn = connector.ConnectionDatabases.openConnection();
    
    public Main() {
        initComponents();
        updateStatistics();
        updateDateTime();
        loadReport();
        updateStatusToOverdue();
        loadDataBook(false);
        loadDataStudent(false);
    }
    
    private void updateStatistics() {
        try {
            // Statement and result set using try-with-resources
            try (Statement stmt = conn.createStatement()) {
                // Query to get the sum of 'stok' from the 'books' table
                String stockSumQuery = "SELECT SUM(stock) FROM books";
                try (ResultSet sumResult = stmt.executeQuery(stockSumQuery)) {
                    if (sumResult.next()) {
                        int sumStock = sumResult.getInt(1);
                        lbl_dashTotalStock.setText(String.valueOf(sumStock));
                        lbl_catTotalStok.setText(String.valueOf(sumStock));
                    } else {
                        lbl_dashTotalStock.setText("0");
                        lbl_catTotalStok.setText("0");
                    }
                }

                // Query to get the count of records in the 'books' table
                String countQuery = "SELECT COUNT(*) FROM books";
                try (ResultSet countResult = stmt.executeQuery(countQuery)) {
                    if (countResult.next()) {
                        int bookCount = countResult.getInt(1);
                        lbl_catTotalTitles.setText(String.valueOf(bookCount));
                    } else {
                        lbl_catTotalTitles.setText("0");
                    }
                }
                
                // Query to get the count distinct records in the 'rack' column
                String rackCountQuery = "SELECT COUNT(DISTINCT rack) FROM books";
                try (ResultSet rackCountResult = stmt.executeQuery(rackCountQuery)) {
                    if (rackCountResult.next()) {
                        int rackCount = rackCountResult.getInt(1);
                        lbl_catTotalRack.setText(String.valueOf(rackCount));
                    } else {
                        lbl_catTotalRack.setText("0");
                    }
                }                
                
                // Query to get the count of records in the 'students' table
                String userCountQuery = "SELECT COUNT(*) FROM students";
                try (ResultSet userCountResult = stmt.executeQuery(userCountQuery)) {
                    if (userCountResult.next()) {
                        int studentCount = userCountResult.getInt(1);
                        lbl_dashTotalStudent.setText(String.valueOf(studentCount));
                        lbl_masTotalStudent.setText(String.valueOf(studentCount));
                    } else {
                        lbl_dashTotalStudent.setText("0");
                        lbl_masTotalStudent.setText("0");
                    }
                }
                
                // Query to get the count distinct records in the 'class' column
                String classCountQuery = "SELECT COUNT(DISTINCT class) FROM students";
                try (ResultSet classCountResult = stmt.executeQuery(classCountQuery)) {
                    if (classCountResult.next()) {
                        int classCount = classCountResult.getInt(1);
                        lbl_masTotalClass.setText(String.valueOf(classCount));
                    } else {
                        lbl_masTotalClass.setText("0");
                    }
                }

                // Query to get the count distinct records in the 'districk' column
                String districkCountQuery = "SELECT COUNT(DISTINCT districk) FROM students";
                try (ResultSet districkCountResult = stmt.executeQuery(districkCountQuery)) {
                    if (districkCountResult.next()) {
                        int districkCount = districkCountResult.getInt(1);
                        lbl_masTotalDistrick.setText(String.valueOf(districkCount));
                    } else {
                        lbl_masTotalDistrick.setText("0");
                    }
                }
                
                // Query to get the count of records in the 'borrowed' table
                String loanCountQuery = "SELECT COUNT(*) FROM borrowed";
                try (ResultSet userCountResult = stmt.executeQuery(loanCountQuery)) {
                    if (userCountResult.next()) {
                        int userCount = userCountResult.getInt(1);
                        lbl_dashTotalLoan.setText(String.valueOf(userCount));
                    } else {
                        lbl_dashTotalLoan.setText("0");
                        lbl_repActHeaCount.setText("0");
                    }
                }   
                
                // Query to get the count of records status = 'borrowed' in the 'borrowed' table
                String borrowCountQuery = "SELECT COUNT(*) FROM borrowed WHERE status = 'borrowed'";
                try (ResultSet borrowCountResult = stmt.executeQuery(borrowCountQuery)) {
                    if (borrowCountResult.next()) {
                        int borrowCount = borrowCountResult.getInt(1);
                        lbl_repActHeaCount.setText(String.valueOf(borrowCount));
                        lbl_dashActHeaCount.setText(String.valueOf(borrowCount));
                    } else {
                        lbl_repActHeaCount.setText("0");
                        lbl_dashActHeaCount.setText("0");
                    }
                } 
                
                // Query to get the count of records status = 'returned' in the 'borrowed' table
                String returnCountQuery = "SELECT COUNT(*) FROM borrowed WHERE status = 'returned'";
                try (ResultSet returnCountResult = stmt.executeQuery(returnCountQuery)) {
                    if (returnCountResult.next()) {
                        int returnCount = returnCountResult.getInt(1);
                        lbl_dashTotalReturn.setText(String.valueOf(returnCount));
                        lbl_repHisHeaCount.setText(String.valueOf(returnCount));
                    } else {
                        lbl_dashTotalReturn.setText("0");
                        lbl_repHisHeaCount.setText("0");
                    }
                }  
                
                // Query to get the count of records status = 'overdue' in the 'borrowed' table
                String overdueCountQuery = "SELECT COUNT(*) FROM borrowed WHERE status = 'overdue'";
                try (ResultSet overdueCountResult = stmt.executeQuery(overdueCountQuery)) {
                    if (overdueCountResult.next()) {
                        int overdueCount = overdueCountResult.getInt(1);
                        lbl_repDetHeaCount.setText(String.valueOf(overdueCount));
                        lbl_dashDetHeaCount.setText(String.valueOf(overdueCount));
                    } else {
                        lbl_repDetHeaCount.setText("0");
                        lbl_dashDetHeaCount.setText("0");
                    }
                } 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    private void updateDateTime() {
        try {
            Date currentDate = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);

            SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            String dayOfWeek = dayOfWeekFormat.format(currentDate);
            lbl_dashCalDay.setText(dayOfWeek);

            SimpleDateFormat dayOfMonthFormat = new SimpleDateFormat("dd", Locale.getDefault());
            String dayOfMonth = dayOfMonthFormat.format(currentDate);
            lbl_dashCalDate.setText(dayOfMonth);

            SimpleDateFormat monthNameFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
            String monthName = monthNameFormat.format(currentDate);
            lbl_dashCalMonth.setText(monthName);

            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
            String year = yearFormat.format(currentDate);
            lbl_dashCalYear.setText(year);

            if ("Sunday".equals(dayOfWeek)) {
                lbl_dashCalDay.setForeground(Color.RED);
            } else if ("Friday".equals(dayOfWeek)) {
                lbl_dashCalDay.setForeground(Color.GREEN);
            }

            // Today's date
            SimpleDateFormat dateLoanFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            todayDate = dateLoanFormat.format(currentDate);
            txt_traLoaDate.setText(todayDate);
            txt_traRetTodayDate.setText(todayDate);

            // Return Date (next 14 days)
            calendar.add(Calendar.DAY_OF_MONTH, 14);
            Date returnDate = calendar.getTime();
            String formattedReturnDate = dateLoanFormat.format(returnDate);
            txt_traLoaReturnDate.setText(formattedReturnDate);            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void setButtonBackgroundColors(Component selectedButton) {
        // Define the default color for buttons
        Color defaultColor = new Color(102, 102, 102);

        // Set the selected button's background color
        selectedButton.setBackground(new Color(114, 114, 114));

        // Reset the background color for all other buttons
        Component[] buttons = {btn_dashboard, btn_masterData, btn_catalog, btn_transaction, btn_report};
        for (Component button : buttons) {
            if (button != selectedButton) {
                button.setBackground(defaultColor);
            }
        }
    }
    
    private void setPanelVisibility(boolean dashboardVisible, boolean masterDataVisible, boolean catalogVisible, boolean  transactionVisible, boolean reportVisible) {
        pnl_dashboard.setVisible(dashboardVisible);
        pnl_masterData.setVisible(masterDataVisible);
        pnl_catalog.setVisible(catalogVisible);
        pnl_transaction.setVisible(transactionVisible);
        pnl_report.setVisible(reportVisible);
    }
    
    private void loadDataBook(boolean isSearch) {
        try {
            String orderOptions, fromOptions;

            if (rbt_catDesc.isSelected()) {
                orderOptions = " DESC";
            } else {
                orderOptions = " ASC";
            }

            if (cmb_catFromOptions.getSelectedItem().equals("Judul")) {
                fromOptions = "bookName";
                cmb_catFromOptions.hidePopup();
            } else if (cmb_catFromOptions.getSelectedItem().equals("Klasifikasi")) {
                fromOptions = "classification";
                cmb_catFromOptions.hidePopup();
            } else if (cmb_catFromOptions.getSelectedItem().equals("Tahun")) {
                fromOptions = "publishYear";
                cmb_catFromOptions.hidePopup();
            } else if (cmb_catFromOptions.getSelectedItem().equals("Rack")) {
                fromOptions = "rack";
                cmb_catFromOptions.hidePopup();
            } else {
                fromOptions = "name"; // Default to sorting by name
            }

            st = conn.createStatement();
            String query;

            if (isSearch) {
                query = "SELECT * FROM books WHERE bookName LIKE '%" + txt_catSearch.getText() + "%' ORDER BY " + fromOptions + orderOptions;
            } else {
                query = "SELECT * FROM books ORDER BY " + fromOptions + orderOptions;
            }

            rs = st.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Isbn");
            model.addColumn("Book Title");
            model.addColumn("Classification");
            model.addColumn("Rack");


            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("bookCode"),
                    rs.getString("bookName"),
                    rs.getString("classification"),
                    rs.getString("rack"),
                };
                model.addRow(data);
                cmb_traLoaBookName.addItem(rs.getString("bookName"));
            }

            tbl_catView.setModel(model);

            // Set the preferred column widths
            tbl_catView.getColumnModel().getColumn(0).setPreferredWidth(100); // Kolom Kode
            tbl_catView.getColumnModel().getColumn(1).setPreferredWidth(400); // Kolom Judul
            tbl_catView.getColumnModel().getColumn(2).setPreferredWidth(100); // Kolom Klasifikasi
            tbl_catView.getColumnModel().getColumn(3).setPreferredWidth(100); // Kolom Pengarang

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadDataStudent(boolean isSearch) {
        try {
            String orderOptions, fromOptions;

            if (rbt_masDesc.isSelected()) {
                orderOptions = " DESC";
            } else {
                orderOptions = " ASC";
            }

            if (cmb_masFromOptions.getSelectedItem().equals("Nama")) {
                fromOptions = "studentName";
                cmb_masFromOptions.hidePopup();
            } else if (cmb_masFromOptions.getSelectedItem().equals("Kelas")) {
                fromOptions = "class";
                cmb_masFromOptions.hidePopup();
            } else if (cmb_masFromOptions.getSelectedItem().equals("Tanggal")) {
                fromOptions = "date_of_birth";
                cmb_masFromOptions.hidePopup();
            } else {
                fromOptions = "studentId"; // Default to sorting by name
                cmb_masFromOptions.hidePopup();
            }

            st = conn.createStatement();
            String query;

            if (isSearch) {
                query = "SELECT * FROM students WHERE studentName LIKE '%" + txt_masSearch.getText() + "%' ORDER BY " + fromOptions + orderOptions;
            } else {
                query = "SELECT * FROM students ORDER BY " + fromOptions + orderOptions;
            }

            rs = st.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            
            model.addColumn("Nisn");
            model.addColumn("Full Name");
            model.addColumn("Gender"); // Correct the column name
            model.addColumn("Class");
            
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("nisn"),
                    rs.getString("studentName"),
                    rs.getString("gender"),
                    rs.getString("class"),                   
                };
                model.addRow(data);
                cmb_traLoaStudentName.addItem(rs.getString("studentName"));
            }

            tbl_masView.setModel(model);
            
            // Set the preferred column widths
            tbl_masView.getColumnModel().getColumn(0).setPreferredWidth(100); // Kolom Kode
            tbl_masView.getColumnModel().getColumn(1).setPreferredWidth(400); // Kolom Judul
            tbl_masView.getColumnModel().getColumn(2).setPreferredWidth(50); // Kolom Klasifikasi
            tbl_masView.getColumnModel().getColumn(3).setPreferredWidth(50); // Kolom Pengarang

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    

    private void clearCatFields() {
        txt_catDetCode.setText("");
        txt_catDetName.setText("");
        txt_catDetClassifications.setText("");        
        txt_catDetPublisher.setText("");        
        txt_catDetAuthor.setText("");     
        txt_catDetPagecount.setText("");     
        txt_catDetPublishyear.setText("");       
        txt_catDetStock.setText("");       
        txt_catDetRack.setText("");
        
        btn_catCreate.setText("Create");
    }

    private void clearMasFields() {
        txt_masDetNisn.setText("");
        txt_masDetName.setText("");
        bgr_genderOptions.clearSelection();
        txt_masDetClass.setText("");
        txt_masDetPlace.setText("");     
        txt_masDetDate.setText("");     
        cmb_masDetReligion.setSelectedItem("-- Pilih Agama --");         
        txt_masDetAddress.setText(""); 
        txt_masDetMother.setText("");
        txt_masDetFather.setText("");
        txt_masDetPhone.setText("");
        txt_masDetDistrick.setText("");
        
        btn_masDetCreate.setText("Create"); 
    }


    private void clear() {
        clearCatFields();
        clearMasFields();
    }

    private void populateTableReport(String status, String orderBy, JTable table, Color textColor) {
        try {
            st = conn.createStatement();
            String query = "SELECT borrowed.*, students.studentName, books.bookName FROM borrowed INNER JOIN books ON borrowed.bookCode = books.bookCode INNER JOIN students ON borrowed.nisn = students.nisn WHERE borrowed.status = ? ";
            
            // Check the orderBy parameter and add the ORDER BY clause accordingly
            if (orderBy.equals("deadlineDate") || orderBy.equals("returnedDate")) {
                query += "ORDER BY " + orderBy + " DESC";
            }
            
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, status);

            rs = preparedStatement.executeQuery();

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Serial");
            model.addColumn("Full Name");
            model.addColumn("Book Tiltle");
            model.addColumn("Deadline");
            model.addColumn("Status");

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] data = {
                    rs.getString("borrowId"),
                    rs.getString("studentName"),
                    rs.getString("bookName"),
                    status.equals("returned") ? rs.getString("returnedDate") : rs.getString("deadlineDate"),
                    rs.getString("status"),
                };
                model.addRow(data);
            }

            table.setModel(model);

            // Set the preferred column widths
            table.getColumnModel().getColumn(0).setPreferredWidth(7);   // Serial
            table.getColumnModel().getColumn(1).setPreferredWidth(250); // Nama Lengkap
            table.getColumnModel().getColumn(2).setPreferredWidth(250); // Judul Buku
            table.getColumnModel().getColumn(3).setPreferredWidth(20);  // Deadline/Pengembalian
            table.getColumnModel().getColumn(4).setPreferredWidth(10);  // Status

            table.getColumnModel().getColumn(4).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                Component component = renderer.getTableCellRendererComponent(table1, value, isSelected, hasFocus, row, column);
                if (column == 4) {
                    component.setForeground(textColor); // Set the text color
                }
                return component;
            });

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateTableDashboard(String status, JTable table, Color textColor) {
        try {
            st = conn.createStatement();
            String query = "SELECT borrowed.*, students.studentName FROM borrowed INNER JOIN students ON borrowed.nisn = students.nisn WHERE borrowed.status = ? ORDER BY deadlineDate DESC";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, status);

            rs = preparedStatement.executeQuery();

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Full Name");
            model.addColumn("Status");

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] data = {
                    rs.getString("studentName"),
                    rs.getString("status"),
                };
                model.addRow(data);
            }

            table.setModel(model);

            // Set the preferred column widths
            table.getColumnModel().getColumn(0).setPreferredWidth(250);   // Serial
            table.getColumnModel().getColumn(1).setPreferredWidth(10); // Nama Lengkap

            table.getColumnModel().getColumn(1).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                Component component = renderer.getTableCellRendererComponent(table1, value, isSelected, hasFocus, row, column);
                if (column == 1) {
                    component.setForeground(textColor); // Set the text color
                }
                return component;
            });

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStatusToOverdue() {
        try {
            String query = "UPDATE borrowed SET status = 'overdue' WHERE status = 'borrowed' AND deadlineDate < ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, todayDate);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Status updated to overdue for overdue records.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadReport();
            } else {
                JOptionPane.showMessageDialog(null, "No records needed updating.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Call the function for each table
    
    private void dashBorrow() {
        populateTableDashboard("borrowed", tbl_dashActive, Color.GREEN);
    }

    private void dashOverdue() {
        populateTableDashboard("overdue", tbl_dashDetained, Color.RED);
    }
    
    private void repBorrow() {
        populateTableReport("borrowed","deadlineDate", tbl_repActive, Color.GREEN);
    }

    private void repOverdue() {
        populateTableReport("overdue", "deadlineDate",tbl_repDetained, Color.RED);
    }

    private void repHistory() {
        populateTableReport("returned", "returnedDate",tbl_repHistory, Color.BLACK);
    }
    
    private void loadReport(){
        dashBorrow();
        dashOverdue();
        repBorrow();
        repOverdue();
        repHistory();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgr_transactionOptions = new javax.swing.ButtonGroup();
        bgr_filterOptions = new javax.swing.ButtonGroup();
        bgr_genderOptions = new javax.swing.ButtonGroup();
        bgr_classOptions = new javax.swing.ButtonGroup();
        jPanel = new javax.swing.JPanel();
        pnl_header = new java.awt.Panel();
        lbl_institution = new javax.swing.JLabel();
        pnl_menu = new java.awt.Panel();
        lbl_username = new javax.swing.JLabel();
        lbl_status = new javax.swing.JLabel();
        lbl_developer = new javax.swing.JLabel();
        ic_user = new javax.swing.JLabel();
        btn_dashboard = new javax.swing.JPanel();
        lbl_dashboard = new javax.swing.JLabel();
        btn_masterData = new javax.swing.JPanel();
        lbl_materData = new javax.swing.JLabel();
        btn_catalog = new javax.swing.JPanel();
        lbl_catalog = new javax.swing.JLabel();
        btn_transaction = new javax.swing.JPanel();
        lbl_transaction = new javax.swing.JLabel();
        btn_report = new javax.swing.JPanel();
        lbl_report = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        pnl_display = new java.awt.Panel();
        pnl_dashboard = new javax.swing.JPanel();
        lbl_dashDashboard = new javax.swing.JLabel();
        lbl_dashControl = new javax.swing.JLabel();
        pnl_dashStudent = new javax.swing.JPanel();
        lbl_dashTotalStudent = new javax.swing.JLabel();
        ic_dashStudent = new javax.swing.JLabel();
        lbl_dashStudent = new javax.swing.JLabel();
        pnl_dashStock = new javax.swing.JPanel();
        lbl_dashTotalStock = new javax.swing.JLabel();
        ic_dashStock = new javax.swing.JLabel();
        lbl_dashStok = new javax.swing.JLabel();
        pnl_dashLoan = new javax.swing.JPanel();
        lbl_dashTotalLoan = new javax.swing.JLabel();
        ic_dashLoan = new javax.swing.JLabel();
        lbl_dashLoan = new javax.swing.JLabel();
        pnl_dashReturn = new javax.swing.JPanel();
        lbl_dashTotalReturn = new javax.swing.JLabel();
        ic_dashReturn = new javax.swing.JLabel();
        lbl_dashReturn = new javax.swing.JLabel();
        pnl_dashCalendar = new javax.swing.JPanel();
        lbl_dashCalDay = new javax.swing.JLabel();
        lbl_dashCalDate = new javax.swing.JLabel();
        ic_dashCalCalendar = new javax.swing.JLabel();
        pnl_dashCalSecond = new javax.swing.JPanel();
        lbl_dashCalMonth = new javax.swing.JLabel();
        lbl_dashCalYear = new javax.swing.JLabel();
        pnl_dashData = new javax.swing.JPanel();
        pnl_dashDatHeader = new javax.swing.JPanel();
        lbl_dashDatHeader = new javax.swing.JLabel();
        btn_dashDatBook = new javax.swing.JPanel();
        lbl_dashDatBook = new javax.swing.JLabel();
        ic_dashDatBook = new javax.swing.JLabel();
        btn_dashDatStudent = new javax.swing.JPanel();
        lbl_dashDatStudent = new javax.swing.JLabel();
        ic_dashDatStudent = new javax.swing.JLabel();
        pnl_dashTransaction = new javax.swing.JPanel();
        pnl_dashTraHeader = new javax.swing.JPanel();
        lbl_dashTraHeader = new javax.swing.JLabel();
        btn_dashTraLoan = new javax.swing.JPanel();
        lbl_dashTraLoan = new javax.swing.JLabel();
        ic_dashTraLoan = new javax.swing.JLabel();
        btn_dashTraRetrun = new javax.swing.JPanel();
        lbl_dashTraRetrun = new javax.swing.JLabel();
        ic_dashTraRetrun = new javax.swing.JLabel();
        btn_dashTraReport = new javax.swing.JPanel();
        lbl_dashTraReport = new javax.swing.JLabel();
        ic_dashTraReport = new javax.swing.JLabel();
        pnl_dashPrint = new javax.swing.JPanel();
        pnl_dashPriHeader = new javax.swing.JPanel();
        ic_dashPriHeader = new javax.swing.JLabel();
        btn_dashPriBook = new javax.swing.JPanel();
        lbl_dashPriBook = new javax.swing.JLabel();
        ic_dashPriBook = new javax.swing.JLabel();
        btn_dashPriStudent = new javax.swing.JPanel();
        lbl_dashPriStudent = new javax.swing.JLabel();
        ic_dashPriStudent = new javax.swing.JLabel();
        pnl_dashActive = new javax.swing.JPanel();
        pnl_dashActHeader = new javax.swing.JPanel();
        lbl_dashActHeader = new javax.swing.JLabel();
        pnl_dashActHeaCount = new javax.swing.JPanel();
        lbl_dashActHeaCount = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_dashActive = new javax.swing.JTable();
        pnl_dashDetained = new javax.swing.JPanel();
        pnl_dashDetHeader = new javax.swing.JPanel();
        lbl_dashDetHeader = new javax.swing.JLabel();
        pnl_dashDetHeaCount = new javax.swing.JPanel();
        lbl_dashDetHeaCount = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_dashDetained = new javax.swing.JTable();
        pnl_masterData = new javax.swing.JPanel();
        lbl_masterData = new javax.swing.JLabel();
        lbl_masControlStudent = new javax.swing.JLabel();
        lbl_masSearch = new javax.swing.JLabel();
        txt_masSearch = new javax.swing.JTextField();
        lbl_masFilters = new javax.swing.JLabel();
        rbt_masAsc = new javax.swing.JRadioButton();
        rbt_masDesc = new javax.swing.JRadioButton();
        cmb_masFromOptions = new javax.swing.JComboBox<>();
        scr_masView = new javax.swing.JScrollPane();
        tbl_masView = new javax.swing.JTable();
        pnl_masStudent = new javax.swing.JPanel();
        lbl_masTotalStudent = new javax.swing.JLabel();
        lbl_masStudent = new javax.swing.JLabel();
        pnl_masDistrick = new javax.swing.JPanel();
        lbl_masTotalDistrick = new javax.swing.JLabel();
        lbl_masDistrick = new javax.swing.JLabel();
        pnl_masClass = new javax.swing.JPanel();
        lbl_masTotalClass = new javax.swing.JLabel();
        lbl_masClass = new javax.swing.JLabel();
        pnl_masDetail = new javax.swing.JPanel();
        pnl_masDetHeader = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        lbl_masDetNisn = new javax.swing.JLabel();
        txt_masDetNisn = new javax.swing.JTextField();
        lbl_masDetNis = new javax.swing.JLabel();
        txt_masDetNis = new javax.swing.JTextField();
        lbl_masDetName = new javax.swing.JLabel();
        txt_masDetName = new javax.swing.JTextField();
        lbl_masDetGender = new javax.swing.JLabel();
        rbt_masDetGenderMale = new javax.swing.JRadioButton();
        rbt_masDetGenderFemale = new javax.swing.JRadioButton();
        lbl_masDetClass = new javax.swing.JLabel();
        txt_masDetClass = new javax.swing.JTextField();
        lbl_masDetPlace = new javax.swing.JLabel();
        txt_masDetPlace = new javax.swing.JTextField();
        lbl_masDetDate = new javax.swing.JLabel();
        txt_masDetDate = new javax.swing.JTextField();
        lbl_masDetReligion = new javax.swing.JLabel();
        cmb_masDetReligion = new javax.swing.JComboBox<>();
        lbl_masDetAddress = new javax.swing.JLabel();
        txt_masDetAddress = new javax.swing.JTextField();
        lbl_masDetMother = new javax.swing.JLabel();
        txt_masDetMother = new javax.swing.JTextField();
        lbl_masDetFather = new javax.swing.JLabel();
        txt_masDetFather = new javax.swing.JTextField();
        lbl_masDetPhone = new javax.swing.JLabel();
        txt_masDetPhone = new javax.swing.JTextField();
        lbl_masDetDistrick = new javax.swing.JLabel();
        txt_masDetDistrick = new javax.swing.JTextField();
        btn_masDetCreate = new javax.swing.JButton();
        btn_masDetDelete = new javax.swing.JButton();
        btn_masDetClear = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        pnl_catalog = new javax.swing.JPanel();
        lbl_bookCatalog = new javax.swing.JLabel();
        lbl_catControlCatalog = new javax.swing.JLabel();
        lbl_catSearch = new javax.swing.JLabel();
        txt_catSearch = new javax.swing.JTextField();
        lbl_catFilters = new javax.swing.JLabel();
        rbt_catAsc = new javax.swing.JRadioButton();
        rbt_catDesc = new javax.swing.JRadioButton();
        cmb_catFromOptions = new javax.swing.JComboBox<>();
        scr_catView = new javax.swing.JScrollPane();
        tbl_catView = new javax.swing.JTable();
        pnl_catStok = new javax.swing.JPanel();
        lbl_catTotalStok = new javax.swing.JLabel();
        lbl_catStok = new javax.swing.JLabel();
        pnl_catTitles = new javax.swing.JPanel();
        lbl_catTotalTitles = new javax.swing.JLabel();
        lbl_catTitle = new javax.swing.JLabel();
        pnl_catRack = new javax.swing.JPanel();
        lbl_catTotalRack = new javax.swing.JLabel();
        lbl_catRack = new javax.swing.JLabel();
        pnl_catDetail = new javax.swing.JPanel();
        pnl_catDetHeader = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lbl_catDetCode = new javax.swing.JLabel();
        txt_catDetCode = new javax.swing.JTextField();
        lbl_catDetName = new javax.swing.JLabel();
        txt_catDetName = new javax.swing.JTextField();
        lbl_catDetClassifications = new javax.swing.JLabel();
        txt_catDetClassifications = new javax.swing.JTextField();
        lbl_catDetAuthor = new javax.swing.JLabel();
        txt_catDetAuthor = new javax.swing.JTextField();
        lbl_catDetPublisher = new javax.swing.JLabel();
        txt_catDetPublisher = new javax.swing.JTextField();
        lbl_catDetPublishyear = new javax.swing.JLabel();
        txt_catDetPublishyear = new javax.swing.JTextField();
        lbl_catDetPagecount = new javax.swing.JLabel();
        txt_catDetPagecount = new javax.swing.JTextField();
        lbl_catDetStock = new javax.swing.JLabel();
        txt_catDetStock = new javax.swing.JTextField();
        lbl_catDetRack = new javax.swing.JLabel();
        txt_catDetRack = new javax.swing.JTextField();
        btn_catCreate = new javax.swing.JButton();
        btn_catDelete = new javax.swing.JButton();
        btn_catClear = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        pnl_transaction = new javax.swing.JPanel();
        lbl_tra = new javax.swing.JLabel();
        lbl_traControl = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        rbt_traLoaning = new javax.swing.JRadioButton();
        rbt_traReturning = new javax.swing.JRadioButton();
        pnl_traLoan = new javax.swing.JPanel();
        pnl_traLoaHeader = new javax.swing.JPanel();
        lbl_formTransaction = new javax.swing.JLabel();
        lbl_traLoaSerial = new javax.swing.JLabel();
        txt_traLoaSerial = new javax.swing.JTextField();
        btn_traLoaGenerate = new javax.swing.JButton();
        lbl_traLoaStudentName = new javax.swing.JLabel();
        cmb_traLoaStudentName = new javax.swing.JComboBox<>();
        txt_traLoaStudentName = new javax.swing.JTextField();
        lbl_traLoaBookName = new javax.swing.JLabel();
        cmb_traLoaBookName = new javax.swing.JComboBox<>();
        txt_traLoaBookName = new javax.swing.JTextField();
        lbl_traLoaLoanDate = new javax.swing.JLabel();
        lbl_traLoaDate = new javax.swing.JLabel();
        txt_traLoaDate = new javax.swing.JTextField();
        lbl_traLoaReturnDate = new javax.swing.JLabel();
        txt_traLoaReturnDate = new javax.swing.JTextField();
        btn_traLoaSend = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        pnl_traReturn = new javax.swing.JPanel();
        pnl_traRetHeader = new javax.swing.JPanel();
        lbl_formTransaction1 = new javax.swing.JLabel();
        lbl_traRetSerial = new javax.swing.JLabel();
        txt_traRetSerial = new javax.swing.JTextField();
        btn_traRetSerial = new javax.swing.JButton();
        lbl_traRetStudentName = new javax.swing.JLabel();
        txt_traRetStudentName = new javax.swing.JTextField();
        lbl_traRetBookName = new javax.swing.JLabel();
        txt_traRetBookName = new javax.swing.JTextField();
        lbl_traRetDate = new javax.swing.JLabel();
        lbl_traRetReturnDate = new javax.swing.JLabel();
        txt_traRetReturnDate = new javax.swing.JTextField();
        lbl_traRetTodayDate = new javax.swing.JLabel();
        txt_traRetTodayDate = new javax.swing.JTextField();
        btn_traRetReturn = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        pnl_report = new javax.swing.JPanel();
        lbl_rep = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        pnl_repActive = new javax.swing.JPanel();
        pnl_repActHeader = new javax.swing.JPanel();
        lbl_repActHeader = new javax.swing.JLabel();
        pnl_repActHeaCount = new javax.swing.JPanel();
        lbl_repActHeaCount = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_repActive = new javax.swing.JTable();
        pnl_repHistory = new javax.swing.JPanel();
        pnl_repHisHeader = new javax.swing.JPanel();
        lbl_repHisHeader = new javax.swing.JLabel();
        pnl_repHisHeaCount = new javax.swing.JPanel();
        lbl_repHisHeaCount = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_repHistory = new javax.swing.JTable();
        pnl_repDetained = new javax.swing.JPanel();
        pnl_repDetHeader = new javax.swing.JPanel();
        lbl_repDetHeader = new javax.swing.JLabel();
        pnl_repDetHeaCount = new javax.swing.JPanel();
        lbl_repDetHeaCount = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_repDetained = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_dashDatBook3 = new javax.swing.JPanel();
        lbl_dashDatBook3 = new javax.swing.JLabel();
        ic_dashDatBook3 = new javax.swing.JLabel();
        btn_dashDatBook4 = new javax.swing.JPanel();
        lbl_dashDatBook4 = new javax.swing.JLabel();
        ic_dashDatBook4 = new javax.swing.JLabel();
        btn_dashDatBook5 = new javax.swing.JPanel();
        lbl_dashDatBook5 = new javax.swing.JLabel();
        ic_dashDatBook5 = new javax.swing.JLabel();
        btn_dashDatBook6 = new javax.swing.JPanel();
        lbl_dashDatBook6 = new javax.swing.JLabel();
        ic_dashDatBook6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel.setBackground(new java.awt.Color(153, 153, 153));
        jPanel.setForeground(new java.awt.Color(153, 255, 255));

        pnl_header.setBackground(new java.awt.Color(153, 153, 255));

        lbl_institution.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        lbl_institution.setForeground(new java.awt.Color(255, 255, 255));
        lbl_institution.setText("INSTITUTION NAME");

        javax.swing.GroupLayout pnl_headerLayout = new javax.swing.GroupLayout(pnl_header);
        pnl_header.setLayout(pnl_headerLayout);
        pnl_headerLayout.setHorizontalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_institution, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_headerLayout.setVerticalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_institution)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_menu.setBackground(new java.awt.Color(102, 102, 102));

        lbl_username.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbl_username.setForeground(new java.awt.Color(255, 255, 255));
        lbl_username.setText("Nur Azis Saputra");
        lbl_username.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lbl_status.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_status.setForeground(new java.awt.Color(204, 204, 204));
        lbl_status.setText("Administrator");

        lbl_developer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_developer.setForeground(new java.awt.Color(255, 255, 255));
        lbl_developer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/headphones.png"))); // NOI18N
        lbl_developer.setText(" Info Developer");
        lbl_developer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_developer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_developerMouseClicked(evt);
            }
        });

        ic_user.setForeground(new java.awt.Color(255, 255, 255));
        ic_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N

        btn_dashboard.setBackground(new java.awt.Color(114, 114, 114));
        btn_dashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashboardMouseClicked(evt);
            }
        });

        lbl_dashboard.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_dashboard.setForeground(new java.awt.Color(204, 204, 204));
        lbl_dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home.png"))); // NOI18N
        lbl_dashboard.setText(" Dashboard");
        lbl_dashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout btn_dashboardLayout = new javax.swing.GroupLayout(btn_dashboard);
        btn_dashboard.setLayout(btn_dashboardLayout);
        btn_dashboardLayout.setHorizontalGroup(
            btn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_dashboardLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lbl_dashboard)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_dashboardLayout.setVerticalGroup(
            btn_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        btn_masterData.setBackground(new java.awt.Color(102, 102, 102));
        btn_masterData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_masterData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_masterDataMouseClicked(evt);
            }
        });

        lbl_materData.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_materData.setForeground(new java.awt.Color(204, 204, 204));
        lbl_materData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/category.png"))); // NOI18N
        lbl_materData.setText("  Master Data");
        lbl_materData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout btn_masterDataLayout = new javax.swing.GroupLayout(btn_masterData);
        btn_masterData.setLayout(btn_masterDataLayout);
        btn_masterDataLayout.setHorizontalGroup(
            btn_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_masterDataLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lbl_materData)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_masterDataLayout.setVerticalGroup(
            btn_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_materData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        btn_catalog.setBackground(new java.awt.Color(102, 102, 102));
        btn_catalog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_catalog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_catalogMouseClicked(evt);
            }
        });

        lbl_catalog.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_catalog.setForeground(new java.awt.Color(204, 204, 204));
        lbl_catalog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/document.png"))); // NOI18N
        lbl_catalog.setText(" Catalog");
        lbl_catalog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout btn_catalogLayout = new javax.swing.GroupLayout(btn_catalog);
        btn_catalog.setLayout(btn_catalogLayout);
        btn_catalogLayout.setHorizontalGroup(
            btn_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_catalogLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_catalog)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_catalogLayout.setVerticalGroup(
            btn_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_catalog, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        btn_transaction.setBackground(new java.awt.Color(102, 102, 102));
        btn_transaction.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_transaction.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_transactionMouseClicked(evt);
            }
        });

        lbl_transaction.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_transaction.setForeground(new java.awt.Color(204, 204, 204));
        lbl_transaction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/transfer.png"))); // NOI18N
        lbl_transaction.setText(" Transactions");
        lbl_transaction.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout btn_transactionLayout = new javax.swing.GroupLayout(btn_transaction);
        btn_transaction.setLayout(btn_transactionLayout);
        btn_transactionLayout.setHorizontalGroup(
            btn_transactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_transactionLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_transaction, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_transactionLayout.setVerticalGroup(
            btn_transactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_transaction, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        btn_report.setBackground(new java.awt.Color(102, 102, 102));
        btn_report.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_report.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_reportMouseClicked(evt);
            }
        });

        lbl_report.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_report.setForeground(new java.awt.Color(204, 204, 204));
        lbl_report.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/documents-folder.png"))); // NOI18N
        lbl_report.setText(" Report");
        lbl_report.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout btn_reportLayout = new javax.swing.GroupLayout(btn_report);
        btn_report.setLayout(btn_reportLayout);
        btn_reportLayout.setHorizontalGroup(
            btn_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_reportLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lbl_report)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_reportLayout.setVerticalGroup(
            btn_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_report, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnl_menuLayout = new javax.swing.GroupLayout(pnl_menu);
        pnl_menu.setLayout(pnl_menuLayout);
        pnl_menuLayout.setHorizontalGroup(
            pnl_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_masterData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_catalog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_transaction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_report, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_menuLayout.createSequentialGroup()
                .addGroup(pnl_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_menuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ic_user)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_username)
                            .addComponent(lbl_status)))
                    .addGroup(pnl_menuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_menuLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(pnl_menuLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lbl_developer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_menuLayout.setVerticalGroup(
            pnl_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_menuLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnl_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ic_user)
                    .addGroup(pnl_menuLayout.createSequentialGroup()
                        .addComponent(lbl_username)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_status, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btn_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_masterData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_catalog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_transaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_report, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_developer)
                .addContainerGap())
        );

        pnl_display.setBackground(new java.awt.Color(204, 204, 204));
        pnl_display.setLayout(new java.awt.CardLayout());

        pnl_dashboard.setBackground(new java.awt.Color(220, 220, 220));

        lbl_dashDashboard.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_dashDashboard.setForeground(new java.awt.Color(102, 102, 102));
        lbl_dashDashboard.setText("Dashboard");

        lbl_dashControl.setForeground(new java.awt.Color(102, 102, 102));
        lbl_dashControl.setText("/ Control Panel");

        pnl_dashStudent.setBackground(new java.awt.Color(239, 239, 239));
        pnl_dashStudent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        lbl_dashTotalStudent.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_dashTotalStudent.setForeground(new java.awt.Color(102, 102, 102));
        lbl_dashTotalStudent.setText("126");

        ic_dashStudent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/users.png"))); // NOI18N

        lbl_dashStudent.setForeground(new java.awt.Color(102, 102, 102));
        lbl_dashStudent.setText("Students");

        javax.swing.GroupLayout pnl_dashStudentLayout = new javax.swing.GroupLayout(pnl_dashStudent);
        pnl_dashStudent.setLayout(pnl_dashStudentLayout);
        pnl_dashStudentLayout.setHorizontalGroup(
            pnl_dashStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashStudentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashStudent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(pnl_dashStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_dashTotalStudent, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_dashStudent, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnl_dashStudentLayout.setVerticalGroup(
            pnl_dashStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashStudentLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbl_dashTotalStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_dashStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(ic_dashStudent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnl_dashStock.setBackground(new java.awt.Color(239, 239, 239));
        pnl_dashStock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        lbl_dashTotalStock.setBackground(new java.awt.Color(102, 102, 102));
        lbl_dashTotalStock.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_dashTotalStock.setForeground(new java.awt.Color(102, 102, 102));
        lbl_dashTotalStock.setText("126");

        ic_dashStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/open-book-outline-variant-inside-a-circle.png"))); // NOI18N

        lbl_dashStok.setForeground(new java.awt.Color(102, 102, 102));
        lbl_dashStok.setText("Books");

        javax.swing.GroupLayout pnl_dashStockLayout = new javax.swing.GroupLayout(pnl_dashStock);
        pnl_dashStock.setLayout(pnl_dashStockLayout);
        pnl_dashStockLayout.setHorizontalGroup(
            pnl_dashStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dashStockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashStock, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(pnl_dashStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_dashTotalStock, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_dashStok, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnl_dashStockLayout.setVerticalGroup(
            pnl_dashStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashStockLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbl_dashTotalStock, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_dashStok)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(ic_dashStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnl_dashLoan.setBackground(new java.awt.Color(239, 239, 239));
        pnl_dashLoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        lbl_dashTotalLoan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_dashTotalLoan.setForeground(new java.awt.Color(102, 102, 102));
        lbl_dashTotalLoan.setText("126");

        ic_dashLoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/seo-outline-symbol-in-a-circle.png"))); // NOI18N

        lbl_dashLoan.setForeground(new java.awt.Color(102, 102, 102));
        lbl_dashLoan.setText("Borrow");

        javax.swing.GroupLayout pnl_dashLoanLayout = new javax.swing.GroupLayout(pnl_dashLoan);
        pnl_dashLoan.setLayout(pnl_dashLoanLayout);
        pnl_dashLoanLayout.setHorizontalGroup(
            pnl_dashLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dashLoanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashLoan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(pnl_dashLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_dashTotalLoan, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_dashLoan, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnl_dashLoanLayout.setVerticalGroup(
            pnl_dashLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashLoanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashTotalLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_dashLoan)
                .addGap(10, 10, 10))
            .addGroup(pnl_dashLoanLayout.createSequentialGroup()
                .addComponent(ic_dashLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnl_dashReturn.setBackground(new java.awt.Color(239, 239, 239));
        pnl_dashReturn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        lbl_dashTotalReturn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_dashTotalReturn.setForeground(new java.awt.Color(102, 102, 102));
        lbl_dashTotalReturn.setText("126");

        ic_dashReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-in-a-circle-pointing-left.png"))); // NOI18N

        lbl_dashReturn.setForeground(new java.awt.Color(102, 102, 102));
        lbl_dashReturn.setText("Return");

        javax.swing.GroupLayout pnl_dashReturnLayout = new javax.swing.GroupLayout(pnl_dashReturn);
        pnl_dashReturn.setLayout(pnl_dashReturnLayout);
        pnl_dashReturnLayout.setHorizontalGroup(
            pnl_dashReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dashReturnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashReturn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(pnl_dashReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_dashTotalReturn, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_dashReturn, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnl_dashReturnLayout.setVerticalGroup(
            pnl_dashReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashReturnLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(lbl_dashTotalReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_dashReturn)
                .addGap(10, 10, 10))
            .addGroup(pnl_dashReturnLayout.createSequentialGroup()
                .addComponent(ic_dashReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnl_dashCalendar.setBackground(new java.awt.Color(239, 239, 239));
        pnl_dashCalendar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        lbl_dashCalDay.setBackground(new java.awt.Color(204, 0, 0));
        lbl_dashCalDay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_dashCalDay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashCalDay.setText("Wednesday");

        lbl_dashCalDate.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        lbl_dashCalDate.setText("21");

        ic_dashCalCalendar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ic_dashCalCalendar.setForeground(new java.awt.Color(255, 255, 255));
        ic_dashCalCalendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/calendar.png"))); // NOI18N

        pnl_dashCalSecond.setBackground(new java.awt.Color(153, 153, 153));

        lbl_dashCalMonth.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_dashCalMonth.setForeground(new java.awt.Color(255, 255, 255));
        lbl_dashCalMonth.setText("August");

        lbl_dashCalYear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_dashCalYear.setForeground(new java.awt.Color(255, 255, 255));
        lbl_dashCalYear.setText("2023");

        javax.swing.GroupLayout pnl_dashCalSecondLayout = new javax.swing.GroupLayout(pnl_dashCalSecond);
        pnl_dashCalSecond.setLayout(pnl_dashCalSecondLayout);
        pnl_dashCalSecondLayout.setHorizontalGroup(
            pnl_dashCalSecondLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashCalSecondLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(pnl_dashCalSecondLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_dashCalYear, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_dashCalMonth, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnl_dashCalSecondLayout.setVerticalGroup(
            pnl_dashCalSecondLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashCalSecondLayout.createSequentialGroup()
                .addComponent(lbl_dashCalMonth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashCalYear)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_dashCalendarLayout = new javax.swing.GroupLayout(pnl_dashCalendar);
        pnl_dashCalendar.setLayout(pnl_dashCalendarLayout);
        pnl_dashCalendarLayout.setHorizontalGroup(
            pnl_dashCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashCalendarLayout.createSequentialGroup()
                .addGroup(pnl_dashCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dashCalendarLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(ic_dashCalCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(lbl_dashCalDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_dashCalDay, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(pnl_dashCalSecond, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnl_dashCalendarLayout.setVerticalGroup(
            pnl_dashCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashCalendarLayout.createSequentialGroup()
                .addComponent(lbl_dashCalDay, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(pnl_dashCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_dashCalDate)
                    .addComponent(ic_dashCalCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(pnl_dashCalSecond, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnl_dashData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        pnl_dashDatHeader.setBackground(new java.awt.Color(153, 153, 255));

        lbl_dashDatHeader.setBackground(new java.awt.Color(102, 102, 102));
        lbl_dashDatHeader.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_dashDatHeader.setForeground(new java.awt.Color(255, 255, 255));
        lbl_dashDatHeader.setText("Books & Students");

        javax.swing.GroupLayout pnl_dashDatHeaderLayout = new javax.swing.GroupLayout(pnl_dashDatHeader);
        pnl_dashDatHeader.setLayout(pnl_dashDatHeaderLayout);
        pnl_dashDatHeaderLayout.setHorizontalGroup(
            pnl_dashDatHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashDatHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_dashDatHeader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_dashDatHeaderLayout.setVerticalGroup(
            pnl_dashDatHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashDatHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        btn_dashDatBook.setBackground(new java.awt.Color(224, 224, 224));
        btn_dashDatBook.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btn_dashDatBook.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashDatBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashDatBookMouseClicked(evt);
            }
        });

        lbl_dashDatBook.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dashDatBook.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashDatBook.setText("Books");

        ic_dashDatBook.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ic_dashDatBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bookshelf.png"))); // NOI18N

        javax.swing.GroupLayout btn_dashDatBookLayout = new javax.swing.GroupLayout(btn_dashDatBook);
        btn_dashDatBook.setLayout(btn_dashDatBookLayout);
        btn_dashDatBookLayout.setHorizontalGroup(
            btn_dashDatBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashDatBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ic_dashDatBook, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
        );
        btn_dashDatBookLayout.setVerticalGroup(
            btn_dashDatBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashDatBookLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashDatBook, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashDatBook)
                .addContainerGap())
        );

        btn_dashDatStudent.setBackground(new java.awt.Color(224, 224, 224));
        btn_dashDatStudent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btn_dashDatStudent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashDatStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashDatStudentMouseClicked(evt);
            }
        });

        lbl_dashDatStudent.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dashDatStudent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashDatStudent.setText("Students");

        ic_dashDatStudent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ic_dashDatStudent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/people.png"))); // NOI18N

        javax.swing.GroupLayout btn_dashDatStudentLayout = new javax.swing.GroupLayout(btn_dashDatStudent);
        btn_dashDatStudent.setLayout(btn_dashDatStudentLayout);
        btn_dashDatStudentLayout.setHorizontalGroup(
            btn_dashDatStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashDatStudent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ic_dashDatStudent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
        );
        btn_dashDatStudentLayout.setVerticalGroup(
            btn_dashDatStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashDatStudentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashDatStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashDatStudent)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_dashDataLayout = new javax.swing.GroupLayout(pnl_dashData);
        pnl_dashData.setLayout(pnl_dashDataLayout);
        pnl_dashDataLayout.setHorizontalGroup(
            pnl_dashDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_dashDatHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_dashDataLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btn_dashDatBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_dashDatStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_dashDataLayout.setVerticalGroup(
            pnl_dashDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashDataLayout.createSequentialGroup()
                .addComponent(pnl_dashDatHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_dashDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_dashDatBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_dashDatStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_dashTransaction.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        pnl_dashTraHeader.setBackground(new java.awt.Color(153, 153, 255));

        lbl_dashTraHeader.setBackground(new java.awt.Color(102, 102, 102));
        lbl_dashTraHeader.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_dashTraHeader.setForeground(new java.awt.Color(255, 255, 255));
        lbl_dashTraHeader.setText("Transaction");
        lbl_dashTraHeader.setToolTipText("");

        javax.swing.GroupLayout pnl_dashTraHeaderLayout = new javax.swing.GroupLayout(pnl_dashTraHeader);
        pnl_dashTraHeader.setLayout(pnl_dashTraHeaderLayout);
        pnl_dashTraHeaderLayout.setHorizontalGroup(
            pnl_dashTraHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashTraHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_dashTraHeader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_dashTraHeaderLayout.setVerticalGroup(
            pnl_dashTraHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashTraHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        btn_dashTraLoan.setBackground(new java.awt.Color(224, 224, 224));
        btn_dashTraLoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btn_dashTraLoan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashTraLoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashTraLoanMouseClicked(evt);
            }
        });

        lbl_dashTraLoan.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dashTraLoan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashTraLoan.setText("Borrow");

        ic_dashTraLoan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ic_dashTraLoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/book.png"))); // NOI18N

        javax.swing.GroupLayout btn_dashTraLoanLayout = new javax.swing.GroupLayout(btn_dashTraLoan);
        btn_dashTraLoan.setLayout(btn_dashTraLoanLayout);
        btn_dashTraLoanLayout.setHorizontalGroup(
            btn_dashTraLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashTraLoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ic_dashTraLoan, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
        );
        btn_dashTraLoanLayout.setVerticalGroup(
            btn_dashTraLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashTraLoanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashTraLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashTraLoan)
                .addContainerGap())
        );

        btn_dashTraRetrun.setBackground(new java.awt.Color(224, 224, 224));
        btn_dashTraRetrun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btn_dashTraRetrun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashTraRetrun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashTraRetrunMouseClicked(evt);
            }
        });

        lbl_dashTraRetrun.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dashTraRetrun.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashTraRetrun.setText("Return");

        ic_dashTraRetrun.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ic_dashTraRetrun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/books.png"))); // NOI18N

        javax.swing.GroupLayout btn_dashTraRetrunLayout = new javax.swing.GroupLayout(btn_dashTraRetrun);
        btn_dashTraRetrun.setLayout(btn_dashTraRetrunLayout);
        btn_dashTraRetrunLayout.setHorizontalGroup(
            btn_dashTraRetrunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashTraRetrun, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
            .addComponent(ic_dashTraRetrun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btn_dashTraRetrunLayout.setVerticalGroup(
            btn_dashTraRetrunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashTraRetrunLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashTraRetrun, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashTraRetrun)
                .addContainerGap())
        );

        btn_dashTraReport.setBackground(new java.awt.Color(224, 224, 224));
        btn_dashTraReport.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btn_dashTraReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashTraReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashTraReportMouseClicked(evt);
            }
        });

        lbl_dashTraReport.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dashTraReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashTraReport.setText("Report");

        ic_dashTraReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ic_dashTraReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/report.png"))); // NOI18N

        javax.swing.GroupLayout btn_dashTraReportLayout = new javax.swing.GroupLayout(btn_dashTraReport);
        btn_dashTraReport.setLayout(btn_dashTraReportLayout);
        btn_dashTraReportLayout.setHorizontalGroup(
            btn_dashTraReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashTraReport, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
            .addComponent(ic_dashTraReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btn_dashTraReportLayout.setVerticalGroup(
            btn_dashTraReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashTraReportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashTraReport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashTraReport)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_dashTransactionLayout = new javax.swing.GroupLayout(pnl_dashTransaction);
        pnl_dashTransaction.setLayout(pnl_dashTransactionLayout);
        pnl_dashTransactionLayout.setHorizontalGroup(
            pnl_dashTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_dashTraHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_dashTransactionLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btn_dashTraLoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_dashTraRetrun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_dashTraReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_dashTransactionLayout.setVerticalGroup(
            pnl_dashTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashTransactionLayout.createSequentialGroup()
                .addComponent(pnl_dashTraHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_dashTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_dashTraLoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_dashTraRetrun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_dashTraReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_dashPrint.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        pnl_dashPriHeader.setBackground(new java.awt.Color(153, 153, 255));

        ic_dashPriHeader.setBackground(new java.awt.Color(102, 102, 102));
        ic_dashPriHeader.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ic_dashPriHeader.setForeground(new java.awt.Color(255, 255, 255));
        ic_dashPriHeader.setText("Print");

        javax.swing.GroupLayout pnl_dashPriHeaderLayout = new javax.swing.GroupLayout(pnl_dashPriHeader);
        pnl_dashPriHeader.setLayout(pnl_dashPriHeaderLayout);
        pnl_dashPriHeaderLayout.setHorizontalGroup(
            pnl_dashPriHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashPriHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashPriHeader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_dashPriHeaderLayout.setVerticalGroup(
            pnl_dashPriHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ic_dashPriHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        btn_dashPriBook.setBackground(new java.awt.Color(224, 224, 224));
        btn_dashPriBook.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btn_dashPriBook.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashPriBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashPriBookMouseClicked(evt);
            }
        });

        lbl_dashPriBook.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dashPriBook.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashPriBook.setText("Books");

        ic_dashPriBook.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ic_dashPriBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/printer.png"))); // NOI18N

        javax.swing.GroupLayout btn_dashPriBookLayout = new javax.swing.GroupLayout(btn_dashPriBook);
        btn_dashPriBook.setLayout(btn_dashPriBookLayout);
        btn_dashPriBookLayout.setHorizontalGroup(
            btn_dashPriBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashPriBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ic_dashPriBook, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
        );
        btn_dashPriBookLayout.setVerticalGroup(
            btn_dashPriBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashPriBookLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashPriBook, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashPriBook)
                .addContainerGap())
        );

        btn_dashPriStudent.setBackground(new java.awt.Color(224, 224, 224));
        btn_dashPriStudent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btn_dashPriStudent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashPriStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashPriStudentMouseClicked(evt);
            }
        });

        lbl_dashPriStudent.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dashPriStudent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashPriStudent.setText("Students");

        ic_dashPriStudent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ic_dashPriStudent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/printer (1).png"))); // NOI18N

        javax.swing.GroupLayout btn_dashPriStudentLayout = new javax.swing.GroupLayout(btn_dashPriStudent);
        btn_dashPriStudent.setLayout(btn_dashPriStudentLayout);
        btn_dashPriStudentLayout.setHorizontalGroup(
            btn_dashPriStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashPriStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ic_dashPriStudent, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
        );
        btn_dashPriStudentLayout.setVerticalGroup(
            btn_dashPriStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashPriStudentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashPriStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashPriStudent)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_dashPrintLayout = new javax.swing.GroupLayout(pnl_dashPrint);
        pnl_dashPrint.setLayout(pnl_dashPrintLayout);
        pnl_dashPrintLayout.setHorizontalGroup(
            pnl_dashPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_dashPriHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_dashPrintLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btn_dashPriBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_dashPriStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_dashPrintLayout.setVerticalGroup(
            pnl_dashPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashPrintLayout.createSequentialGroup()
                .addComponent(pnl_dashPriHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_dashPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_dashPriBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_dashPriStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnl_dashActive.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        pnl_dashActHeader.setBackground(new java.awt.Color(153, 153, 255));

        lbl_dashActHeader.setBackground(new java.awt.Color(102, 102, 102));
        lbl_dashActHeader.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_dashActHeader.setForeground(new java.awt.Color(255, 255, 255));
        lbl_dashActHeader.setText("Borrower");
        lbl_dashActHeader.setToolTipText("");

        pnl_dashActHeaCount.setBackground(new java.awt.Color(204, 204, 255));

        lbl_dashActHeaCount.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_dashActHeaCount.setForeground(new java.awt.Color(0, 153, 51));
        lbl_dashActHeaCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashActHeaCount.setText("11");

        javax.swing.GroupLayout pnl_dashActHeaCountLayout = new javax.swing.GroupLayout(pnl_dashActHeaCount);
        pnl_dashActHeaCount.setLayout(pnl_dashActHeaCountLayout);
        pnl_dashActHeaCountLayout.setHorizontalGroup(
            pnl_dashActHeaCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashActHeaCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );
        pnl_dashActHeaCountLayout.setVerticalGroup(
            pnl_dashActHeaCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashActHeaCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnl_dashActHeaderLayout = new javax.swing.GroupLayout(pnl_dashActHeader);
        pnl_dashActHeader.setLayout(pnl_dashActHeaderLayout);
        pnl_dashActHeaderLayout.setHorizontalGroup(
            pnl_dashActHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashActHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_dashActHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnl_dashActHeaCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_dashActHeaderLayout.setVerticalGroup(
            pnl_dashActHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashActHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dashActHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dashActHeaCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbl_dashActive.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_dashActive.setEnabled(false);
        jScrollPane5.setViewportView(tbl_dashActive);

        javax.swing.GroupLayout pnl_dashActiveLayout = new javax.swing.GroupLayout(pnl_dashActive);
        pnl_dashActive.setLayout(pnl_dashActiveLayout);
        pnl_dashActiveLayout.setHorizontalGroup(
            pnl_dashActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_dashActHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_dashActiveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_dashActiveLayout.setVerticalGroup(
            pnl_dashActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashActiveLayout.createSequentialGroup()
                .addComponent(pnl_dashActHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_dashDetained.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        pnl_dashDetHeader.setBackground(new java.awt.Color(153, 153, 255));

        lbl_dashDetHeader.setBackground(new java.awt.Color(102, 102, 102));
        lbl_dashDetHeader.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_dashDetHeader.setForeground(new java.awt.Color(255, 255, 255));
        lbl_dashDetHeader.setText("Overdue ");
        lbl_dashDetHeader.setToolTipText("");

        pnl_dashDetHeaCount.setBackground(new java.awt.Color(204, 204, 255));

        lbl_dashDetHeaCount.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_dashDetHeaCount.setForeground(new java.awt.Color(204, 0, 0));
        lbl_dashDetHeaCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashDetHeaCount.setText("11");

        javax.swing.GroupLayout pnl_dashDetHeaCountLayout = new javax.swing.GroupLayout(pnl_dashDetHeaCount);
        pnl_dashDetHeaCount.setLayout(pnl_dashDetHeaCountLayout);
        pnl_dashDetHeaCountLayout.setHorizontalGroup(
            pnl_dashDetHeaCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashDetHeaCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );
        pnl_dashDetHeaCountLayout.setVerticalGroup(
            pnl_dashDetHeaCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashDetHeaCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnl_dashDetHeaderLayout = new javax.swing.GroupLayout(pnl_dashDetHeader);
        pnl_dashDetHeader.setLayout(pnl_dashDetHeaderLayout);
        pnl_dashDetHeaderLayout.setHorizontalGroup(
            pnl_dashDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashDetHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_dashDetHeader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnl_dashDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_dashDetHeaderLayout.createSequentialGroup()
                    .addContainerGap(453, Short.MAX_VALUE)
                    .addComponent(pnl_dashDetHeaCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        pnl_dashDetHeaderLayout.setVerticalGroup(
            pnl_dashDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashDetHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
            .addGroup(pnl_dashDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_dashDetHeaderLayout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(pnl_dashDetHeaCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(5, 5, 5)))
        );

        tbl_dashDetained.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tbl_dashDetained);

        javax.swing.GroupLayout pnl_dashDetainedLayout = new javax.swing.GroupLayout(pnl_dashDetained);
        pnl_dashDetained.setLayout(pnl_dashDetainedLayout);
        pnl_dashDetainedLayout.setHorizontalGroup(
            pnl_dashDetainedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_dashDetHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_dashDetainedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        pnl_dashDetainedLayout.setVerticalGroup(
            pnl_dashDetainedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashDetainedLayout.createSequentialGroup()
                .addComponent(pnl_dashDetHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_dashboardLayout = new javax.swing.GroupLayout(pnl_dashboard);
        pnl_dashboard.setLayout(pnl_dashboardLayout);
        pnl_dashboardLayout.setHorizontalGroup(
            pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dashboardLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnl_dashTransaction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnl_dashboardLayout.createSequentialGroup()
                                .addComponent(lbl_dashDashboard)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_dashControl)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(pnl_dashPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_dashData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnl_dashDetained, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_dashActive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10))
                    .addGroup(pnl_dashboardLayout.createSequentialGroup()
                        .addComponent(pnl_dashStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(pnl_dashStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_dashLoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_dashReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 197, Short.MAX_VALUE)
                        .addComponent(pnl_dashCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_dashboardLayout.setVerticalGroup(
            pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dashboardLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnl_dashCalendar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_dashStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_dashStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_dashLoan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_dashReturn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_dashDashboard)
                    .addComponent(lbl_dashControl))
                .addGap(18, 18, 18)
                .addGroup(pnl_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_dashboardLayout.createSequentialGroup()
                        .addComponent(pnl_dashData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl_dashTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl_dashPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_dashboardLayout.createSequentialGroup()
                        .addComponent(pnl_dashActive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl_dashDetained, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pnl_display.add(pnl_dashboard, "card2");

        pnl_masterData.setBackground(new java.awt.Color(220, 220, 220));

        lbl_masterData.setBackground(new java.awt.Color(102, 102, 102));
        lbl_masterData.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_masterData.setForeground(new java.awt.Color(102, 102, 102));
        lbl_masterData.setText("Master Data");

        lbl_masControlStudent.setForeground(new java.awt.Color(102, 102, 102));
        lbl_masControlStudent.setText("/ Control Student");

        lbl_masSearch.setBackground(new java.awt.Color(102, 102, 102));
        lbl_masSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_masSearch.setForeground(new java.awt.Color(102, 102, 102));
        lbl_masSearch.setText("Search :");

        txt_masSearch.setToolTipText("");
        txt_masSearch.setName("-- Masukkan Judul Buku --"); // NOI18N
        txt_masSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_masSearchKeyPressed(evt);
            }
        });

        lbl_masFilters.setBackground(new java.awt.Color(102, 102, 102));
        lbl_masFilters.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_masFilters.setForeground(new java.awt.Color(102, 102, 102));
        lbl_masFilters.setText("Filter :");

        bgr_filterOptions.add(rbt_masAsc);
        rbt_masAsc.setText("Asc");
        rbt_masAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_masAscActionPerformed(evt);
            }
        });

        bgr_filterOptions.add(rbt_masDesc);
        rbt_masDesc.setText("Desc");
        rbt_masDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_masAscActionPerformed(evt);
            }
        });

        cmb_masFromOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "Nama", "Kelas", "Tanggal" }));
        cmb_masFromOptions.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_masFromOptionsItemStateChanged(evt);
            }
        });

        scr_masView.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tbl_masView.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));
        tbl_masView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        tbl_masView.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_masView.setSelectionBackground(new java.awt.Color(153, 153, 255));
        tbl_masView.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbl_masView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_masViewMouseClicked(evt);
            }
        });
        scr_masView.setViewportView(tbl_masView);

        pnl_masStudent.setBackground(new java.awt.Color(239, 239, 239));
        pnl_masStudent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        lbl_masTotalStudent.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_masTotalStudent.setForeground(new java.awt.Color(102, 102, 102));
        lbl_masTotalStudent.setText("126");

        lbl_masStudent.setForeground(new java.awt.Color(102, 102, 102));
        lbl_masStudent.setText("Students :");

        javax.swing.GroupLayout pnl_masStudentLayout = new javax.swing.GroupLayout(pnl_masStudent);
        pnl_masStudent.setLayout(pnl_masStudentLayout);
        pnl_masStudentLayout.setHorizontalGroup(
            pnl_masStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_masStudentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_masStudent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(lbl_masTotalStudent)
                .addContainerGap())
        );
        pnl_masStudentLayout.setVerticalGroup(
            pnl_masStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_masTotalStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_masStudentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_masStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_masDistrick.setBackground(new java.awt.Color(239, 239, 239));
        pnl_masDistrick.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        lbl_masTotalDistrick.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_masTotalDistrick.setForeground(new java.awt.Color(102, 102, 102));
        lbl_masTotalDistrick.setText("126");

        lbl_masDistrick.setForeground(new java.awt.Color(102, 102, 102));
        lbl_masDistrick.setText("Kelurahan");

        javax.swing.GroupLayout pnl_masDistrickLayout = new javax.swing.GroupLayout(pnl_masDistrick);
        pnl_masDistrick.setLayout(pnl_masDistrickLayout);
        pnl_masDistrickLayout.setHorizontalGroup(
            pnl_masDistrickLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_masDistrickLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_masDistrick)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(lbl_masTotalDistrick)
                .addContainerGap())
        );
        pnl_masDistrickLayout.setVerticalGroup(
            pnl_masDistrickLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_masTotalDistrick, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lbl_masDistrick, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnl_masClass.setBackground(new java.awt.Color(239, 239, 239));
        pnl_masClass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        lbl_masTotalClass.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_masTotalClass.setForeground(new java.awt.Color(102, 102, 102));
        lbl_masTotalClass.setText("126");

        lbl_masClass.setForeground(new java.awt.Color(102, 102, 102));
        lbl_masClass.setText("Class :");

        javax.swing.GroupLayout pnl_masClassLayout = new javax.swing.GroupLayout(pnl_masClass);
        pnl_masClass.setLayout(pnl_masClassLayout);
        pnl_masClassLayout.setHorizontalGroup(
            pnl_masClassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_masClassLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_masClass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(lbl_masTotalClass)
                .addContainerGap())
        );
        pnl_masClassLayout.setVerticalGroup(
            pnl_masClassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_masTotalClass, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lbl_masClass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnl_masDetail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        pnl_masDetHeader.setBackground(new java.awt.Color(153, 153, 255));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("DETAIL SISWA");

        javax.swing.GroupLayout pnl_masDetHeaderLayout = new javax.swing.GroupLayout(pnl_masDetHeader);
        pnl_masDetHeader.setLayout(pnl_masDetHeaderLayout);
        pnl_masDetHeaderLayout.setHorizontalGroup(
            pnl_masDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnl_masDetHeaderLayout.setVerticalGroup(
            pnl_masDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        lbl_masDetNisn.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetNisn.setText("NISN");

        txt_masDetNisn.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_masDetNis.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetNis.setText("SN");

        txt_masDetNis.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_masDetName.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetName.setText("Full Name");

        txt_masDetName.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_masDetGender.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetGender.setText("Gender");

        bgr_genderOptions.add(rbt_masDetGenderMale);
        rbt_masDetGenderMale.setText("L");

        bgr_genderOptions.add(rbt_masDetGenderFemale);
        rbt_masDetGenderFemale.setText("P");

        lbl_masDetClass.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetClass.setText("Class Room");

        txt_masDetClass.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_masDetPlace.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetPlace.setText("Place Of Birth");

        txt_masDetPlace.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_masDetDate.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetDate.setText("Date Of Birth");

        txt_masDetDate.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_masDetReligion.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetReligion.setText("Religion");

        cmb_masDetReligion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- PIlih Agama --", "Islam", "Kristen", "Katholik" }));

        lbl_masDetAddress.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetAddress.setText("Address");

        txt_masDetAddress.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_masDetMother.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetMother.setText("Mother");

        txt_masDetMother.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_masDetFather.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetFather.setText("Father");

        txt_masDetFather.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_masDetPhone.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetPhone.setText("Phone");

        txt_masDetPhone.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_masDetDistrick.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_masDetDistrick.setText("Districk");

        txt_masDetDistrick.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        btn_masDetCreate.setBackground(new java.awt.Color(0, 204, 0));
        btn_masDetCreate.setForeground(java.awt.Color.white);
        btn_masDetCreate.setText("Create");
        btn_masDetCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_masDetCreateActionPerformed(evt);
            }
        });

        btn_masDetDelete.setBackground(new java.awt.Color(204, 0, 0));
        btn_masDetDelete.setForeground(java.awt.Color.white);
        btn_masDetDelete.setText("Delete");
        btn_masDetDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_masDetDeleteActionPerformed(evt);
            }
        });

        btn_masDetClear.setBackground(new java.awt.Color(255, 204, 0));
        btn_masDetClear.setForeground(java.awt.Color.white);
        btn_masDetClear.setText("Clear");
        btn_masDetClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_masDetClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_masDetailLayout = new javax.swing.GroupLayout(pnl_masDetail);
        pnl_masDetail.setLayout(pnl_masDetailLayout);
        pnl_masDetailLayout.setHorizontalGroup(
            pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_masDetHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_masDetailLayout.createSequentialGroup()
                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_masDetailLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_masDetPlace, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_masDetReligion, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_masDetAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_masDetMother, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_masDetClass, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_masDetGender, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_masDetName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_masDetNisn, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_masDetailLayout.createSequentialGroup()
                                .addComponent(txt_masDetMother, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addComponent(lbl_masDetFather)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_masDetFather, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_masDetName)
                            .addGroup(pnl_masDetailLayout.createSequentialGroup()
                                .addComponent(btn_masDetCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_masDetDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_masDetClear))
                            .addComponent(txt_masDetAddress)
                            .addGroup(pnl_masDetailLayout.createSequentialGroup()
                                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmb_masDetReligion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnl_masDetailLayout.createSequentialGroup()
                                        .addComponent(txt_masDetNisn, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbl_masDetNis)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_masDetNis, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnl_masDetailLayout.createSequentialGroup()
                                        .addComponent(rbt_masDetGenderMale)
                                        .addGap(12, 12, 12)
                                        .addComponent(rbt_masDetGenderFemale))
                                    .addGroup(pnl_masDetailLayout.createSequentialGroup()
                                        .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txt_masDetClass, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_masDetPlace, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(lbl_masDetDate)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_masDetDate, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pnl_masDetailLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_masDetPhone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_masDetPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(lbl_masDetDistrick)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_masDetDistrick, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_masDetailLayout.setVerticalGroup(
            pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_masDetailLayout.createSequentialGroup()
                .addComponent(pnl_masDetHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_masDetNisn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_masDetNisn)
                    .addComponent(txt_masDetNis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_masDetNis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_masDetName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_masDetName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_masDetGender)
                    .addComponent(rbt_masDetGenderMale)
                    .addComponent(rbt_masDetGenderFemale))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_masDetClass)
                    .addComponent(txt_masDetClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_masDetPlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_masDetPlace)
                    .addComponent(txt_masDetDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_masDetDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_masDetReligion)
                    .addComponent(cmb_masDetReligion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_masDetAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_masDetAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_masDetMother, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_masDetMother)
                    .addComponent(txt_masDetFather, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_masDetFather))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_masDetDistrick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_masDetDistrick))
                    .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_masDetPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_masDetPhone)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(pnl_masDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_masDetCreate)
                    .addComponent(btn_masDetDelete)
                    .addComponent(btn_masDetClear))
                .addContainerGap())
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/magnifying-glass.png"))); // NOI18N

        javax.swing.GroupLayout pnl_masterDataLayout = new javax.swing.GroupLayout(pnl_masterData);
        pnl_masterData.setLayout(pnl_masterDataLayout);
        pnl_masterDataLayout.setHorizontalGroup(
            pnl_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_masterDataLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(pnl_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_masterDataLayout.createSequentialGroup()
                        .addGroup(pnl_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_masterDataLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lbl_masSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_masSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_masFilters)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbt_masAsc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbt_masDesc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmb_masFromOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(scr_masView, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnl_masDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_masterDataLayout.createSequentialGroup()
                                .addComponent(pnl_masStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnl_masClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnl_masDistrick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnl_masterDataLayout.createSequentialGroup()
                        .addComponent(lbl_masterData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_masControlStudent)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_masterDataLayout.setVerticalGroup(
            pnl_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_masterDataLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_masterData)
                    .addComponent(lbl_masControlStudent))
                .addGap(42, 42, 42)
                .addGroup(pnl_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_masterDataLayout.createSequentialGroup()
                        .addGroup(pnl_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnl_masClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnl_masStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnl_masDistrick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(pnl_masterDataLayout.createSequentialGroup()
                        .addGroup(pnl_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_masSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_masFilters, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_masSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rbt_masAsc)
                                .addComponent(rbt_masDesc)
                                .addComponent(cmb_masFromOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)))
                .addGroup(pnl_masterDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_masterDataLayout.createSequentialGroup()
                        .addComponent(pnl_masDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 96, Short.MAX_VALUE))
                    .addComponent(scr_masView, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnl_display.add(pnl_masterData, "card3");

        pnl_catalog.setBackground(new java.awt.Color(220, 220, 220));

        lbl_bookCatalog.setBackground(new java.awt.Color(102, 102, 102));
        lbl_bookCatalog.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_bookCatalog.setForeground(new java.awt.Color(102, 102, 102));
        lbl_bookCatalog.setText("Catalogue");

        lbl_catControlCatalog.setForeground(new java.awt.Color(102, 102, 102));
        lbl_catControlCatalog.setText("/ Control Book");

        lbl_catSearch.setBackground(new java.awt.Color(102, 102, 102));
        lbl_catSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_catSearch.setForeground(new java.awt.Color(102, 102, 102));
        lbl_catSearch.setText("Search :");

        txt_catSearch.setToolTipText("");
        txt_catSearch.setName("-- Masukkan Judul Buku --"); // NOI18N
        txt_catSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_catSearchKeyPressed(evt);
            }
        });

        lbl_catFilters.setBackground(new java.awt.Color(102, 102, 102));
        lbl_catFilters.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_catFilters.setForeground(new java.awt.Color(102, 102, 102));
        lbl_catFilters.setText("Filter :");

        bgr_filterOptions.add(rbt_catAsc);
        rbt_catAsc.setText("Asc");
        rbt_catAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_catAscActionPerformed(evt);
            }
        });

        bgr_filterOptions.add(rbt_catDesc);
        rbt_catDesc.setText("Desc");
        rbt_catDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_catAscActionPerformed(evt);
            }
        });

        cmb_catFromOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Judul", "Klasifikasi", "Tahun", "Rack" }));
        cmb_catFromOptions.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_catFromOptionsItemStateChanged(evt);
            }
        });

        scr_catView.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tbl_catView.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));
        tbl_catView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_catView.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_catView.setSelectionBackground(new java.awt.Color(153, 153, 255));
        tbl_catView.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbl_catView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_catViewMouseClicked(evt);
            }
        });
        scr_catView.setViewportView(tbl_catView);

        pnl_catStok.setBackground(new java.awt.Color(239, 239, 239));
        pnl_catStok.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        lbl_catTotalStok.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_catTotalStok.setForeground(new java.awt.Color(102, 102, 102));
        lbl_catTotalStok.setText("126");

        lbl_catStok.setForeground(new java.awt.Color(102, 102, 102));
        lbl_catStok.setText("Books :");

        javax.swing.GroupLayout pnl_catStokLayout = new javax.swing.GroupLayout(pnl_catStok);
        pnl_catStok.setLayout(pnl_catStokLayout);
        pnl_catStokLayout.setHorizontalGroup(
            pnl_catStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_catStokLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_catStok)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(lbl_catTotalStok)
                .addContainerGap())
        );
        pnl_catStokLayout.setVerticalGroup(
            pnl_catStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_catTotalStok, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lbl_catStok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnl_catTitles.setBackground(new java.awt.Color(239, 239, 239));
        pnl_catTitles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        lbl_catTotalTitles.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_catTotalTitles.setForeground(new java.awt.Color(102, 102, 102));
        lbl_catTotalTitles.setText("126");

        lbl_catTitle.setForeground(new java.awt.Color(102, 102, 102));
        lbl_catTitle.setText("Title :");

        javax.swing.GroupLayout pnl_catTitlesLayout = new javax.swing.GroupLayout(pnl_catTitles);
        pnl_catTitles.setLayout(pnl_catTitlesLayout);
        pnl_catTitlesLayout.setHorizontalGroup(
            pnl_catTitlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_catTitlesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_catTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(lbl_catTotalTitles)
                .addContainerGap())
        );
        pnl_catTitlesLayout.setVerticalGroup(
            pnl_catTitlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_catTotalTitles, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lbl_catTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnl_catRack.setBackground(new java.awt.Color(239, 239, 239));
        pnl_catRack.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        lbl_catTotalRack.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_catTotalRack.setForeground(new java.awt.Color(102, 102, 102));
        lbl_catTotalRack.setText("126");

        lbl_catRack.setForeground(new java.awt.Color(102, 102, 102));
        lbl_catRack.setText("Rack :");

        javax.swing.GroupLayout pnl_catRackLayout = new javax.swing.GroupLayout(pnl_catRack);
        pnl_catRack.setLayout(pnl_catRackLayout);
        pnl_catRackLayout.setHorizontalGroup(
            pnl_catRackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_catRackLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_catRack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(lbl_catTotalRack)
                .addContainerGap())
        );
        pnl_catRackLayout.setVerticalGroup(
            pnl_catRackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_catTotalRack, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lbl_catRack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnl_catDetail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        pnl_catDetHeader.setBackground(new java.awt.Color(153, 153, 255));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("DETAIL BUKU");

        javax.swing.GroupLayout pnl_catDetHeaderLayout = new javax.swing.GroupLayout(pnl_catDetHeader);
        pnl_catDetHeader.setLayout(pnl_catDetHeaderLayout);
        pnl_catDetHeaderLayout.setHorizontalGroup(
            pnl_catDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnl_catDetHeaderLayout.setVerticalGroup(
            pnl_catDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        lbl_catDetCode.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_catDetCode.setText("Kode Buku");

        txt_catDetCode.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_catDetName.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_catDetName.setText("Judul Buku");

        txt_catDetName.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_catDetClassifications.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_catDetClassifications.setText("Klasifikasi");

        txt_catDetClassifications.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_catDetAuthor.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_catDetAuthor.setText("Pengarang");

        txt_catDetAuthor.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_catDetPublisher.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_catDetPublisher.setText("Penerbit");

        txt_catDetPublisher.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_catDetPublishyear.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_catDetPublishyear.setText("Tahun Terbit");

        txt_catDetPublishyear.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_catDetPagecount.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_catDetPagecount.setText("Jumlah Halaman");

        txt_catDetPagecount.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_catDetStock.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_catDetStock.setText("Stok");

        txt_catDetStock.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        lbl_catDetRack.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lbl_catDetRack.setText("Rak");

        txt_catDetRack.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N

        btn_catCreate.setBackground(new java.awt.Color(0, 204, 0));
        btn_catCreate.setForeground(java.awt.Color.white);
        btn_catCreate.setText("Create");
        btn_catCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_catCreateActionPerformed(evt);
            }
        });

        btn_catDelete.setBackground(new java.awt.Color(204, 0, 0));
        btn_catDelete.setForeground(java.awt.Color.white);
        btn_catDelete.setText("Delete");
        btn_catDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_catDeleteActionPerformed(evt);
            }
        });

        btn_catClear.setBackground(new java.awt.Color(255, 204, 0));
        btn_catClear.setForeground(java.awt.Color.white);
        btn_catClear.setText("Clear");
        btn_catClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_catClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_catDetailLayout = new javax.swing.GroupLayout(pnl_catDetail);
        pnl_catDetail.setLayout(pnl_catDetailLayout);
        pnl_catDetailLayout.setHorizontalGroup(
            pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_catDetHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_catDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_catDetRack)
                    .addComponent(lbl_catDetStock)
                    .addComponent(lbl_catDetPublishyear)
                    .addComponent(lbl_catDetPublisher)
                    .addComponent(lbl_catDetAuthor)
                    .addComponent(lbl_catDetClassifications)
                    .addComponent(lbl_catDetName)
                    .addComponent(lbl_catDetCode)
                    .addComponent(lbl_catDetPagecount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_catDetName, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_catDetClassifications, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_catDetAuthor, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_catDetPublisher, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_catDetPublishyear, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_catDetPagecount, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_catDetailLayout.createSequentialGroup()
                        .addComponent(btn_catCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_catDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(btn_catClear))
                    .addComponent(txt_catDetRack, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_catDetStock, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_catDetCode))
                .addContainerGap())
        );
        pnl_catDetailLayout.setVerticalGroup(
            pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_catDetailLayout.createSequentialGroup()
                .addComponent(pnl_catDetHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_catDetCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_catDetCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_catDetName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_catDetName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_catDetClassifications, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_catDetClassifications))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_catDetAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_catDetAuthor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_catDetPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_catDetPublisher))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_catDetPublishyear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_catDetPublishyear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_catDetPagecount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_catDetPagecount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_catDetStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_catDetStock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_catDetRack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_catDetRack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_catDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_catCreate)
                    .addComponent(btn_catDelete)
                    .addComponent(btn_catClear))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/magnifying-glass.png"))); // NOI18N

        javax.swing.GroupLayout pnl_catalogLayout = new javax.swing.GroupLayout(pnl_catalog);
        pnl_catalog.setLayout(pnl_catalogLayout);
        pnl_catalogLayout.setHorizontalGroup(
            pnl_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_catalogLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(pnl_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scr_catView, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                    .addGroup(pnl_catalogLayout.createSequentialGroup()
                        .addGroup(pnl_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_catalogLayout.createSequentialGroup()
                                .addComponent(lbl_bookCatalog)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_catControlCatalog)
                                .addGap(18, 18, Short.MAX_VALUE))
                            .addGroup(pnl_catalogLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lbl_catSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_catSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_catFilters)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbt_catAsc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbt_catDesc)
                                .addGap(8, 8, 8)))
                        .addComponent(cmb_catFromOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnl_catDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_catalogLayout.createSequentialGroup()
                        .addComponent(pnl_catStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl_catTitles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl_catRack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_catalogLayout.setVerticalGroup(
            pnl_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_catalogLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_bookCatalog)
                    .addComponent(lbl_catControlCatalog))
                .addGap(42, 42, 42)
                .addGroup(pnl_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_catalogLayout.createSequentialGroup()
                        .addGroup(pnl_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(pnl_catTitles, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pnl_catRack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pnl_catStok, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_catSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_catFilters, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_catSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rbt_catAsc)
                                .addComponent(rbt_catDesc)
                                .addComponent(cmb_catFromOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_catalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_catalogLayout.createSequentialGroup()
                                .addComponent(pnl_catDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(scr_catView)))
                    .addGroup(pnl_catalogLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnl_display.add(pnl_catalog, "card3");

        pnl_transaction.setBackground(new java.awt.Color(220, 220, 220));

        lbl_tra.setBackground(new java.awt.Color(102, 102, 102));
        lbl_tra.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_tra.setForeground(new java.awt.Color(102, 102, 102));
        lbl_tra.setText("Transactions");

        lbl_traControl.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traControl.setText("Control Transaction");

        jSeparator5.setForeground(new java.awt.Color(102, 102, 102));
        jSeparator5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        bgr_transactionOptions.add(rbt_traLoaning);
        rbt_traLoaning.setForeground(new java.awt.Color(102, 102, 102));
        rbt_traLoaning.setText("Borrowing");
        rbt_traLoaning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_traLoaningActionPerformed(evt);
            }
        });

        bgr_transactionOptions.add(rbt_traReturning);
        rbt_traReturning.setForeground(new java.awt.Color(102, 102, 102));
        rbt_traReturning.setText("Returning");
        rbt_traReturning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_traLoaningActionPerformed(evt);
            }
        });

        pnl_traLoan.setBackground(new java.awt.Color(229, 229, 229));
        pnl_traLoan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        pnl_traLoaHeader.setBackground(new java.awt.Color(204, 204, 204));

        lbl_formTransaction.setBackground(new java.awt.Color(102, 102, 102));
        lbl_formTransaction.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_formTransaction.setForeground(new java.awt.Color(102, 102, 102));
        lbl_formTransaction.setText("Borrowing Transactions");

        javax.swing.GroupLayout pnl_traLoaHeaderLayout = new javax.swing.GroupLayout(pnl_traLoaHeader);
        pnl_traLoaHeader.setLayout(pnl_traLoaHeaderLayout);
        pnl_traLoaHeaderLayout.setHorizontalGroup(
            pnl_traLoaHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_traLoaHeaderLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_formTransaction)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_traLoaHeaderLayout.setVerticalGroup(
            pnl_traLoaHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_formTransaction, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        lbl_traLoaSerial.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traLoaSerial.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traLoaSerial.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traLoaSerial.setText("Serial");

        txt_traLoaSerial.setText("- Generate Code -");
        txt_traLoaSerial.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txt_traLoaSerial.setRequestFocusEnabled(false);

        btn_traLoaGenerate.setBackground(new java.awt.Color(153, 153, 255));
        btn_traLoaGenerate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_traLoaGenerate.setForeground(new java.awt.Color(255, 255, 255));
        btn_traLoaGenerate.setText("Generate");
        btn_traLoaGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_traLoaGenerateActionPerformed(evt);
            }
        });

        lbl_traLoaStudentName.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traLoaStudentName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traLoaStudentName.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traLoaStudentName.setText("Name Student");

        cmb_traLoaStudentName.setForeground(new java.awt.Color(153, 153, 153));
        cmb_traLoaStudentName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Choose -" }));
        cmb_traLoaStudentName.setBorder(null);
        cmb_traLoaStudentName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cmb_traLoaStudentName.setFocusable(false);
        cmb_traLoaStudentName.setLightWeightPopupEnabled(false);
        cmb_traLoaStudentName.setName(""); // NOI18N
        cmb_traLoaStudentName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmb_traLoaStudentNamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        txt_traLoaStudentName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_traLoaStudentNameKeyPressed(evt);
            }
        });

        lbl_traLoaBookName.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traLoaBookName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traLoaBookName.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traLoaBookName.setText("Book TItle");

        cmb_traLoaBookName.setForeground(new java.awt.Color(153, 153, 153));
        cmb_traLoaBookName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Choose -" }));
        cmb_traLoaBookName.setFocusable(false);
        cmb_traLoaBookName.setLightWeightPopupEnabled(false);
        cmb_traLoaBookName.setMinimumSize(new java.awt.Dimension(138, 20));
        cmb_traLoaBookName.setRequestFocusEnabled(false);
        cmb_traLoaBookName.setVerifyInputWhenFocusTarget(false);
        cmb_traLoaBookName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmb_traLoaBookNamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        txt_traLoaBookName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_traLoaBookNameKeyPressed(evt);
            }
        });

        lbl_traLoaLoanDate.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traLoaLoanDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traLoaLoanDate.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traLoaLoanDate.setText("Date");

        lbl_traLoaDate.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traLoaDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traLoaDate.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traLoaDate.setText("Borrowing");

        txt_traLoaDate.setEnabled(false);
        txt_traLoaDate.setRequestFocusEnabled(false);

        lbl_traLoaReturnDate.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traLoaReturnDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traLoaReturnDate.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traLoaReturnDate.setText("Returning");

        txt_traLoaReturnDate.setEnabled(false);
        txt_traLoaReturnDate.setRequestFocusEnabled(false);

        btn_traLoaSend.setBackground(new java.awt.Color(102, 255, 102));
        btn_traLoaSend.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_traLoaSend.setForeground(new java.awt.Color(255, 255, 255));
        btn_traLoaSend.setText("Send");
        btn_traLoaSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_traLoaSendActionPerformed(evt);
            }
        });

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnl_traLoanLayout = new javax.swing.GroupLayout(pnl_traLoan);
        pnl_traLoan.setLayout(pnl_traLoanLayout);
        pnl_traLoanLayout.setHorizontalGroup(
            pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_traLoaHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_traLoanLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_traLoaSerial)
                    .addComponent(lbl_traLoaBookName)
                    .addComponent(lbl_traLoaStudentName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_traLoanLayout.createSequentialGroup()
                        .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnl_traLoanLayout.createSequentialGroup()
                                .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cmb_traLoaBookName, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmb_traLoaStudentName, 0, 309, Short.MAX_VALUE)
                                    .addComponent(txt_traLoaSerial))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_traLoaGenerate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_traLoaStudentName)
                                    .addComponent(txt_traLoaBookName)))
                            .addComponent(jSeparator3)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_traLoanLayout.createSequentialGroup()
                                .addComponent(lbl_traLoaDate)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(87, 87, 87))
                    .addGroup(pnl_traLoanLayout.createSequentialGroup()
                        .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_traLoaDate, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_traLoanLayout.createSequentialGroup()
                                .addComponent(lbl_traLoaReturnDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_traLoaSend, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_traLoaReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_traLoanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_traLoaLoanDate)
                .addGap(282, 282, 282))
        );
        pnl_traLoanLayout.setVerticalGroup(
            pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_traLoanLayout.createSequentialGroup()
                .addComponent(pnl_traLoaHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_traLoaSerial)
                    .addComponent(txt_traLoaSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_traLoaGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_traLoaStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_traLoaStudentName)
                    .addComponent(txt_traLoaStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_traLoaBookName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_traLoaBookName)
                    .addComponent(txt_traLoaBookName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(lbl_traLoaLoanDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_traLoaDate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_traLoaDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_traLoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_traLoaReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_traLoaReturnDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_traLoaSend)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnl_traReturn.setBackground(new java.awt.Color(229, 229, 229));

        pnl_traRetHeader.setBackground(new java.awt.Color(204, 204, 204));

        lbl_formTransaction1.setBackground(new java.awt.Color(102, 102, 102));
        lbl_formTransaction1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_formTransaction1.setForeground(new java.awt.Color(102, 102, 102));
        lbl_formTransaction1.setText("Returning Transactions");

        javax.swing.GroupLayout pnl_traRetHeaderLayout = new javax.swing.GroupLayout(pnl_traRetHeader);
        pnl_traRetHeader.setLayout(pnl_traRetHeaderLayout);
        pnl_traRetHeaderLayout.setHorizontalGroup(
            pnl_traRetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_traRetHeaderLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_formTransaction1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_traRetHeaderLayout.setVerticalGroup(
            pnl_traRetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_formTransaction1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        lbl_traRetSerial.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traRetSerial.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traRetSerial.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traRetSerial.setText("Serial");

        btn_traRetSerial.setBackground(new java.awt.Color(153, 153, 255));
        btn_traRetSerial.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_traRetSerial.setForeground(new java.awt.Color(255, 255, 255));
        btn_traRetSerial.setText("Check");
        btn_traRetSerial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_traRetSerialActionPerformed(evt);
            }
        });

        lbl_traRetStudentName.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traRetStudentName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traRetStudentName.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traRetStudentName.setText("Student Name");

        txt_traRetStudentName.setEnabled(false);
        txt_traRetStudentName.setRequestFocusEnabled(false);

        lbl_traRetBookName.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traRetBookName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traRetBookName.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traRetBookName.setText("Book Name");

        txt_traRetBookName.setEnabled(false);
        txt_traRetBookName.setRequestFocusEnabled(false);

        lbl_traRetDate.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traRetDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traRetDate.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traRetDate.setText("Date");

        lbl_traRetReturnDate.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traRetReturnDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traRetReturnDate.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traRetReturnDate.setText("Returning");

        txt_traRetReturnDate.setEnabled(false);
        txt_traRetReturnDate.setRequestFocusEnabled(false);

        lbl_traRetTodayDate.setBackground(new java.awt.Color(102, 102, 102));
        lbl_traRetTodayDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_traRetTodayDate.setForeground(new java.awt.Color(102, 102, 102));
        lbl_traRetTodayDate.setText("Today");

        txt_traRetTodayDate.setEnabled(false);
        txt_traRetTodayDate.setRequestFocusEnabled(false);

        btn_traRetReturn.setBackground(new java.awt.Color(102, 255, 102));
        btn_traRetReturn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_traRetReturn.setForeground(new java.awt.Color(255, 255, 255));
        btn_traRetReturn.setText("Send");
        btn_traRetReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_traRetReturnActionPerformed(evt);
            }
        });

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnl_traReturnLayout = new javax.swing.GroupLayout(pnl_traReturn);
        pnl_traReturn.setLayout(pnl_traReturnLayout);
        pnl_traReturnLayout.setHorizontalGroup(
            pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_traRetHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_traReturnLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(lbl_traRetSerial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_traReturnLayout.createSequentialGroup()
                        .addComponent(txt_traRetSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_traRetSerial))
                    .addGroup(pnl_traReturnLayout.createSequentialGroup()
                        .addGroup(pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_traReturnLayout.createSequentialGroup()
                                .addGroup(pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbl_traRetBookName)
                                    .addComponent(lbl_traRetStudentName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txt_traRetStudentName, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_traRetBookName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(43, 43, 43))
            .addGroup(pnl_traReturnLayout.createSequentialGroup()
                .addGroup(pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_traReturnLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnl_traReturnLayout.createSequentialGroup()
                                    .addComponent(lbl_traRetReturnDate)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt_traRetReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_traRetTodayDate)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt_traRetTodayDate, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnl_traReturnLayout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(btn_traRetReturn))
                    .addGroup(pnl_traReturnLayout.createSequentialGroup()
                        .addGap(337, 337, 337)
                        .addComponent(lbl_traRetDate)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_traReturnLayout.setVerticalGroup(
            pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_traReturnLayout.createSequentialGroup()
                .addComponent(pnl_traRetHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_traRetSerial)
                    .addComponent(txt_traRetSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_traRetSerial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_traRetStudentName)
                    .addComponent(txt_traRetStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_traRetBookName)
                    .addComponent(txt_traRetBookName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_traRetDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_traReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_traRetTodayDate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_traRetTodayDate)
                    .addComponent(txt_traRetReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_traRetReturnDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_traRetReturn)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_transactionLayout = new javax.swing.GroupLayout(pnl_transaction);
        pnl_transaction.setLayout(pnl_transactionLayout);
        pnl_transactionLayout.setHorizontalGroup(
            pnl_transactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_transactionLayout.createSequentialGroup()
                .addGroup(pnl_transactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_transactionLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(pnl_transactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnl_traLoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_transactionLayout.createSequentialGroup()
                                .addComponent(rbt_traLoaning)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbt_traReturning))))
                    .addGroup(pnl_transactionLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(pnl_transactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_transactionLayout.createSequentialGroup()
                                .addComponent(lbl_tra)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_traControl)))))
                .addContainerGap(411, Short.MAX_VALUE))
            .addGroup(pnl_transactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_transactionLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(pnl_traReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(346, Short.MAX_VALUE)))
        );
        pnl_transactionLayout.setVerticalGroup(
            pnl_transactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_transactionLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_transactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_tra)
                    .addComponent(lbl_traControl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_transactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbt_traLoaning)
                    .addComponent(rbt_traReturning))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_traLoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
            .addGroup(pnl_transactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_transactionLayout.createSequentialGroup()
                    .addGap(111, 111, 111)
                    .addComponent(pnl_traReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(93, Short.MAX_VALUE)))
        );

        pnl_display.add(pnl_transaction, "card3");

        pnl_report.setBackground(new java.awt.Color(220, 220, 220));

        lbl_rep.setBackground(new java.awt.Color(102, 102, 102));
        lbl_rep.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_rep.setForeground(new java.awt.Color(102, 102, 102));
        lbl_rep.setText("Report");

        jSeparator6.setForeground(new java.awt.Color(102, 102, 102));
        jSeparator6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        pnl_repActive.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        pnl_repActHeader.setBackground(new java.awt.Color(153, 153, 255));

        lbl_repActHeader.setBackground(new java.awt.Color(102, 102, 102));
        lbl_repActHeader.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_repActHeader.setForeground(new java.awt.Color(255, 255, 255));
        lbl_repActHeader.setText("Borrowing");
        lbl_repActHeader.setToolTipText("");

        pnl_repActHeaCount.setBackground(new java.awt.Color(204, 204, 255));

        lbl_repActHeaCount.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_repActHeaCount.setForeground(new java.awt.Color(0, 153, 51));
        lbl_repActHeaCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_repActHeaCount.setText("11");

        javax.swing.GroupLayout pnl_repActHeaCountLayout = new javax.swing.GroupLayout(pnl_repActHeaCount);
        pnl_repActHeaCount.setLayout(pnl_repActHeaCountLayout);
        pnl_repActHeaCountLayout.setHorizontalGroup(
            pnl_repActHeaCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_repActHeaCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );
        pnl_repActHeaCountLayout.setVerticalGroup(
            pnl_repActHeaCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_repActHeaCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnl_repActHeaderLayout = new javax.swing.GroupLayout(pnl_repActHeader);
        pnl_repActHeader.setLayout(pnl_repActHeaderLayout);
        pnl_repActHeaderLayout.setHorizontalGroup(
            pnl_repActHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_repActHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_repActHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnl_repActHeaCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_repActHeaderLayout.setVerticalGroup(
            pnl_repActHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_repActHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_repActHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_repActHeaCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbl_repActive.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_repActive.setEnabled(false);
        jScrollPane2.setViewportView(tbl_repActive);

        javax.swing.GroupLayout pnl_repActiveLayout = new javax.swing.GroupLayout(pnl_repActive);
        pnl_repActive.setLayout(pnl_repActiveLayout);
        pnl_repActiveLayout.setHorizontalGroup(
            pnl_repActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_repActHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_repActiveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        pnl_repActiveLayout.setVerticalGroup(
            pnl_repActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_repActiveLayout.createSequentialGroup()
                .addComponent(pnl_repActHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_repHistory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        pnl_repHisHeader.setBackground(new java.awt.Color(153, 153, 255));

        lbl_repHisHeader.setBackground(new java.awt.Color(102, 102, 102));
        lbl_repHisHeader.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_repHisHeader.setForeground(new java.awt.Color(255, 255, 255));
        lbl_repHisHeader.setText("History");
        lbl_repHisHeader.setToolTipText("");

        pnl_repHisHeaCount.setBackground(new java.awt.Color(204, 204, 255));

        lbl_repHisHeaCount.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_repHisHeaCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_repHisHeaCount.setText("11");

        javax.swing.GroupLayout pnl_repHisHeaCountLayout = new javax.swing.GroupLayout(pnl_repHisHeaCount);
        pnl_repHisHeaCount.setLayout(pnl_repHisHeaCountLayout);
        pnl_repHisHeaCountLayout.setHorizontalGroup(
            pnl_repHisHeaCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_repHisHeaCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );
        pnl_repHisHeaCountLayout.setVerticalGroup(
            pnl_repHisHeaCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_repHisHeaCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnl_repHisHeaderLayout = new javax.swing.GroupLayout(pnl_repHisHeader);
        pnl_repHisHeader.setLayout(pnl_repHisHeaderLayout);
        pnl_repHisHeaderLayout.setHorizontalGroup(
            pnl_repHisHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_repHisHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_repHisHeader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnl_repHisHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_repHisHeaderLayout.createSequentialGroup()
                    .addContainerGap(502, Short.MAX_VALUE)
                    .addComponent(pnl_repHisHeaCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        pnl_repHisHeaderLayout.setVerticalGroup(
            pnl_repHisHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_repHisHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
            .addGroup(pnl_repHisHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_repHisHeaderLayout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(pnl_repHisHeaCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(5, 5, 5)))
        );

        tbl_repHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_repHistory.setEnabled(false);
        jScrollPane3.setViewportView(tbl_repHistory);

        javax.swing.GroupLayout pnl_repHistoryLayout = new javax.swing.GroupLayout(pnl_repHistory);
        pnl_repHistory.setLayout(pnl_repHistoryLayout);
        pnl_repHistoryLayout.setHorizontalGroup(
            pnl_repHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_repHisHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_repHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_repHistoryLayout.setVerticalGroup(
            pnl_repHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_repHistoryLayout.createSequentialGroup()
                .addComponent(pnl_repHisHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_repDetained.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        pnl_repDetHeader.setBackground(new java.awt.Color(153, 153, 255));

        lbl_repDetHeader.setBackground(new java.awt.Color(102, 102, 102));
        lbl_repDetHeader.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_repDetHeader.setForeground(new java.awt.Color(255, 255, 255));
        lbl_repDetHeader.setText("Time Over");
        lbl_repDetHeader.setToolTipText("");

        pnl_repDetHeaCount.setBackground(new java.awt.Color(204, 204, 255));

        lbl_repDetHeaCount.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_repDetHeaCount.setForeground(new java.awt.Color(204, 0, 0));
        lbl_repDetHeaCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_repDetHeaCount.setText("11");

        javax.swing.GroupLayout pnl_repDetHeaCountLayout = new javax.swing.GroupLayout(pnl_repDetHeaCount);
        pnl_repDetHeaCount.setLayout(pnl_repDetHeaCountLayout);
        pnl_repDetHeaCountLayout.setHorizontalGroup(
            pnl_repDetHeaCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_repDetHeaCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );
        pnl_repDetHeaCountLayout.setVerticalGroup(
            pnl_repDetHeaCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_repDetHeaCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnl_repDetHeaderLayout = new javax.swing.GroupLayout(pnl_repDetHeader);
        pnl_repDetHeader.setLayout(pnl_repDetHeaderLayout);
        pnl_repDetHeaderLayout.setHorizontalGroup(
            pnl_repDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_repDetHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_repDetHeader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnl_repDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_repDetHeaderLayout.createSequentialGroup()
                    .addContainerGap(453, Short.MAX_VALUE)
                    .addComponent(pnl_repDetHeaCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        pnl_repDetHeaderLayout.setVerticalGroup(
            pnl_repDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_repDetHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
            .addGroup(pnl_repDetHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_repDetHeaderLayout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(pnl_repDetHeaCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(5, 5, 5)))
        );

        tbl_repDetained.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_repDetained.setEnabled(false);
        jScrollPane4.setViewportView(tbl_repDetained);

        javax.swing.GroupLayout pnl_repDetainedLayout = new javax.swing.GroupLayout(pnl_repDetained);
        pnl_repDetained.setLayout(pnl_repDetainedLayout);
        pnl_repDetainedLayout.setHorizontalGroup(
            pnl_repDetainedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_repDetHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_repDetainedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        pnl_repDetainedLayout.setVerticalGroup(
            pnl_repDetainedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_repDetainedLayout.createSequentialGroup()
                .addComponent(pnl_repDetHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("/ View Detail");

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        btn_dashDatBook3.setBackground(new java.awt.Color(224, 224, 224));
        btn_dashDatBook3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btn_dashDatBook3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashDatBook3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashDatBook3MouseClicked(evt);
            }
        });

        lbl_dashDatBook3.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dashDatBook3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashDatBook3.setText("Full Report");

        ic_dashDatBook3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ic_dashDatBook3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/test.png"))); // NOI18N

        javax.swing.GroupLayout btn_dashDatBook3Layout = new javax.swing.GroupLayout(btn_dashDatBook3);
        btn_dashDatBook3.setLayout(btn_dashDatBook3Layout);
        btn_dashDatBook3Layout.setHorizontalGroup(
            btn_dashDatBook3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashDatBook3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ic_dashDatBook3, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );
        btn_dashDatBook3Layout.setVerticalGroup(
            btn_dashDatBook3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashDatBook3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashDatBook3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashDatBook3)
                .addContainerGap())
        );

        btn_dashDatBook4.setBackground(new java.awt.Color(224, 224, 224));
        btn_dashDatBook4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btn_dashDatBook4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashDatBook4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashDatBook4MouseClicked(evt);
            }
        });

        lbl_dashDatBook4.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dashDatBook4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashDatBook4.setText("Active");

        ic_dashDatBook4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ic_dashDatBook4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/checklist.png"))); // NOI18N

        javax.swing.GroupLayout btn_dashDatBook4Layout = new javax.swing.GroupLayout(btn_dashDatBook4);
        btn_dashDatBook4.setLayout(btn_dashDatBook4Layout);
        btn_dashDatBook4Layout.setHorizontalGroup(
            btn_dashDatBook4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashDatBook4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ic_dashDatBook4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btn_dashDatBook4Layout.setVerticalGroup(
            btn_dashDatBook4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashDatBook4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashDatBook4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashDatBook4)
                .addContainerGap())
        );

        btn_dashDatBook5.setBackground(new java.awt.Color(224, 224, 224));
        btn_dashDatBook5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btn_dashDatBook5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashDatBook5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashDatBook5MouseClicked(evt);
            }
        });

        lbl_dashDatBook5.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dashDatBook5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashDatBook5.setText("Overdue ");

        ic_dashDatBook5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ic_dashDatBook5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/notes.png"))); // NOI18N

        javax.swing.GroupLayout btn_dashDatBook5Layout = new javax.swing.GroupLayout(btn_dashDatBook5);
        btn_dashDatBook5.setLayout(btn_dashDatBook5Layout);
        btn_dashDatBook5Layout.setHorizontalGroup(
            btn_dashDatBook5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashDatBook5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ic_dashDatBook5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btn_dashDatBook5Layout.setVerticalGroup(
            btn_dashDatBook5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashDatBook5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashDatBook5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashDatBook5)
                .addContainerGap())
        );

        btn_dashDatBook6.setBackground(new java.awt.Color(224, 224, 224));
        btn_dashDatBook6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btn_dashDatBook6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dashDatBook6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_dashDatBook6MouseClicked(evt);
            }
        });

        lbl_dashDatBook6.setForeground(new java.awt.Color(51, 51, 51));
        lbl_dashDatBook6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_dashDatBook6.setText("History");

        ic_dashDatBook6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ic_dashDatBook6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/writing.png"))); // NOI18N

        javax.swing.GroupLayout btn_dashDatBook6Layout = new javax.swing.GroupLayout(btn_dashDatBook6);
        btn_dashDatBook6.setLayout(btn_dashDatBook6Layout);
        btn_dashDatBook6Layout.setHorizontalGroup(
            btn_dashDatBook6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_dashDatBook6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ic_dashDatBook6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btn_dashDatBook6Layout.setVerticalGroup(
            btn_dashDatBook6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_dashDatBook6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ic_dashDatBook6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_dashDatBook6)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Print");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_dashDatBook3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_dashDatBook4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_dashDatBook5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_dashDatBook6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_dashDatBook3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_dashDatBook4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_dashDatBook5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_dashDatBook6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_reportLayout = new javax.swing.GroupLayout(pnl_report);
        pnl_report.setLayout(pnl_reportLayout);
        pnl_reportLayout.setHorizontalGroup(
            pnl_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_reportLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnl_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_reportLayout.createSequentialGroup()
                        .addGroup(pnl_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_reportLayout.createSequentialGroup()
                                .addComponent(lbl_rep)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 654, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_reportLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnl_repDetained, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_repActive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_repHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(44, 44, 44))
        );
        pnl_reportLayout.setVerticalGroup(
            pnl_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_reportLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnl_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_rep)
                    .addComponent(jLabel10))
                .addGap(11, 11, 11)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnl_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_reportLayout.createSequentialGroup()
                        .addComponent(pnl_repActive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_repDetained, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_repHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnl_display.add(pnl_report, "card3");

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addComponent(pnl_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(pnl_display, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addComponent(pnl_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(pnl_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(pnl_display, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        getContentPane().add(jPanel);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_developerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_developerMouseClicked
        // TODO add your handling code here:
        try {
            Developer dev = new Developer();
            dev.setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_lbl_developerMouseClicked

    private void btn_dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashboardMouseClicked
        // Call the method with the selected button as an argument
        setButtonBackgroundColors(btn_dashboard);
        
        // Set the appropriate panel visibility when btn_dashboard is clicked
        setPanelVisibility(true, false, false, false, false);
    }//GEN-LAST:event_btn_dashboardMouseClicked

    private void btn_masterDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_masterDataMouseClicked
        setButtonBackgroundColors(btn_masterData);
        setPanelVisibility(false, true, false, false, false);
    }//GEN-LAST:event_btn_masterDataMouseClicked

    private void btn_catalogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_catalogMouseClicked
        setButtonBackgroundColors(btn_catalog);
        setPanelVisibility(false,false, true, false, false);
    }//GEN-LAST:event_btn_catalogMouseClicked

    private void btn_transactionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_transactionMouseClicked
        setButtonBackgroundColors(btn_transaction);
        setPanelVisibility(false,false, false, true, false);
        rbt_traLoaning.setSelected(true);
        pnl_traLoan.setVisible(true);
        pnl_traReturn.setVisible(false);
    }//GEN-LAST:event_btn_transactionMouseClicked

    private void btn_reportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_reportMouseClicked
        setButtonBackgroundColors(btn_report);
        setPanelVisibility(false,false, false, false, true);
    }//GEN-LAST:event_btn_reportMouseClicked

    private void btn_dashDatBookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashDatBookMouseClicked
        setButtonBackgroundColors(btn_catalog);
        setPanelVisibility(false, false, true, false, false);
    }//GEN-LAST:event_btn_dashDatBookMouseClicked

    private void btn_dashDatStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashDatStudentMouseClicked
        setButtonBackgroundColors(btn_masterData);
        setPanelVisibility(false,true, false, false, false);
    }//GEN-LAST:event_btn_dashDatStudentMouseClicked

    private void btn_dashTraLoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashTraLoanMouseClicked
        setButtonBackgroundColors(btn_transaction);
        setPanelVisibility(false,false, false, true, false);
        rbt_traLoaning.setSelected(true);
        pnl_traLoan.setVisible(true);
        pnl_traReturn.setVisible(false);
    }//GEN-LAST:event_btn_dashTraLoanMouseClicked

    private void btn_dashTraRetrunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashTraRetrunMouseClicked
        setButtonBackgroundColors(btn_transaction);
        setPanelVisibility(false,false, false, true, false);
        rbt_traReturning.setSelected(true);
        pnl_traLoan.setVisible(false);
        pnl_traReturn.setVisible(true);
    }//GEN-LAST:event_btn_dashTraRetrunMouseClicked

    private void btn_dashTraReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashTraReportMouseClicked
        setButtonBackgroundColors(btn_report);
        setPanelVisibility(false,false, false, false, true);
    }//GEN-LAST:event_btn_dashTraReportMouseClicked

    private void btn_dashPriBookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashPriBookMouseClicked
        try {
            String Options = "bookPreview";
            preview prev = new preview(Options);
            prev.setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_dashPriBookMouseClicked

    private void btn_dashPriStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashPriStudentMouseClicked
        try {
            String Options = "studentPreview";
            preview prev = new preview(Options);
            prev.setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_dashPriStudentMouseClicked

    private void tbl_catViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_catViewMouseClicked
        try {
            String isbn = tbl_catView.getValueAt(tbl_catView.getSelectedRow(), 0).toString();
            
            String query = "SELECT * FROM books WHERE bookCode = ?";
 
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, isbn); // Wrap the wildcard outside of the quotes
            
            rs = preparedStatement.executeQuery();
            
            if (rs.next()){
                txt_catDetCode.setText(rs.getString("bookCode"));
                txt_catDetName.setText(rs.getString("bookName"));
                txt_catDetClassifications.setText(rs.getString("classification"));
                txt_catDetPublisher.setText(rs.getString("publisher"));
                txt_catDetPagecount.setText(rs.getString( "pageCount"));
                txt_catDetAuthor.setText(rs.getString("author"));
                txt_catDetPublishyear.setText(rs.getString("publishYear"));
                txt_catDetStock.setText(rs.getString("stock"));
                txt_catDetRack.setText(rs.getString("rack"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        btn_catCreate.setText("Update");
    }//GEN-LAST:event_tbl_catViewMouseClicked

    private void tbl_masViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_masViewMouseClicked
        try {
            String nisn = tbl_masView.getValueAt(tbl_masView.getSelectedRow(), 0).toString();

            String query = "SELECT * FROM students WHERE nisn = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, nisn); // Wrap the wildcard outside of the quotes

            rs = preparedStatement.executeQuery(); 
            if (rs.next()) {
                txt_masDetNisn.setText(rs.getString("Nisn"));
                txt_masDetName.setText(rs.getString("StudentName"));
                String selectedGender = rs.getString("gender");
                    if (selectedGender.equals("L")) {
                        rbt_masDetGenderMale.setSelected(true);
                    } else if (selectedGender.equals("P")) {
                        rbt_masDetGenderFemale.setSelected(true);
                    }      
                txt_masDetClass.setText(rs.getString("class"));
                txt_masDetPlace.setText(rs.getString("place_of_birth"));
                txt_masDetDate.setText(rs.getString("date_of_birth"));
                cmb_masDetReligion.setSelectedItem(rs.getString("religion"));
                txt_masDetAddress.setText(rs.getString("address"));
                txt_masDetMother.setText(rs.getString("mother"));
                txt_masDetFather.setText(rs.getString("father"));
                txt_masDetPhone.setText(rs.getString("phone"));
                txt_masDetDistrick.setText(rs.getString("districk"));
        
                btn_masDetCreate.setText("Update");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tbl_masViewMouseClicked

    private void btn_catCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_catCreateActionPerformed
        try {
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database.", "Koneksi Gagal", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String bookCode = txt_catDetCode.getText();
            String name = txt_catDetName.getText();
            String classification = txt_catDetClassifications.getText();
            String publisher = txt_catDetPublisher.getText();
            String author = txt_catDetAuthor.getText();
            String pageCount = txt_catDetPagecount.getText();
            String publishYear = txt_catDetPublishyear.getText();
            String stock = txt_catDetStock.getText();
            String rack = txt_catDetRack.getText();

            if (bookCode.isEmpty() || name.isEmpty() || classification.isEmpty() || publisher.isEmpty() || 
                author.isEmpty() || pageCount.isEmpty() || publishYear.isEmpty() || stock.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Data cannot be empty", "Data Validation", JOptionPane.INFORMATION_MESSAGE);

                return;
            }

            PreparedStatement preparedStatement;
            if (btn_catCreate.getText().equals("Create")) {
                String cek = "SELECT * FROM books WHERE bookCode = ?";
                preparedStatement = conn.prepareStatement(cek);
                preparedStatement.setString(1, bookCode);
                rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Parent Code Already Exists", "Data Validation", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String sql = "INSERT INTO books (`bookCode`, `bookName`, `classification`, `publisher`, `author`, `pageCount`, `publishYear`, `stock`, `rack`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, bookCode);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, classification);
                    preparedStatement.setString(4, publisher);
                    preparedStatement.setString(5, author);
                    preparedStatement.setString(6, pageCount);
                    preparedStatement.setString(7, publishYear);
                    preparedStatement.setString(8, stock);
                    preparedStatement.setString(9, rack);

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Successfully Added", "Data Validation", JOptionPane.INFORMATION_MESSAGE);
                    loadDataBook(false);
                    clear();
                }
            } else {
                String update = "UPDATE books SET bookName = ?, classification = ?, publisher = ?, author = ?, pageCount = ?, publishYear = ?, stock = ?, rack = ? WHERE bookCode = ?";
                preparedStatement = conn.prepareStatement(update);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, classification);
                preparedStatement.setString(3, publisher);
                preparedStatement.setString(4, author);
                preparedStatement.setString(5, pageCount);
                preparedStatement.setString(6, publishYear);
                preparedStatement.setString(7, stock);
                preparedStatement.setString(8, rack);
                preparedStatement.setString(9, bookCode);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data successfully updated", "Data Validation", JOptionPane.INFORMATION_MESSAGE);
                loadDataBook(false);
                clear();
            }
            updateStatistics();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_catCreateActionPerformed

    private void btn_masDetCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_masDetCreateActionPerformed
        try {
            st = conn.createStatement();
            
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Unable to connect to the database.", "Connection Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedGender = "";
            if (rbt_masDetGenderMale.isSelected()) {
                selectedGender = "L";
            } else if (rbt_masDetGenderFemale.isSelected()) {
                selectedGender = "P";
            }
            
            String nisn = txt_masDetNisn.getText();
            String name = txt_masDetName.getText();
            String classRoom = txt_masDetClass.getText();
            String placeOfBirth = txt_masDetPlace.getText();
            String dateOfBirth = txt_masDetDate.getText();
            String religion = cmb_masDetReligion.getSelectedItem().toString();
            String address = txt_masDetAddress.getText();
            String districk = txt_masDetDistrick.getText();
            String father = txt_masDetFather.getText();
            String mother = txt_masDetMother.getText();
            String phone = txt_masDetPhone.getText();

            if (nisn.isEmpty() || name.isEmpty() || selectedGender.isEmpty() || classRoom.isEmpty() || placeOfBirth.isEmpty() || 
                dateOfBirth.isEmpty() || religion.equalsIgnoreCase("-- Pilih Agama --") || address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Data cannot be empty", "Data Validation", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            PreparedStatement preparedStatement;
            if (btn_masDetCreate.getText().equals("Create")) {
                String cek = "SELECT * FROM students WHERE nisn = ?";
                preparedStatement = conn.prepareStatement(cek);
                preparedStatement.setString(1, nisn);
                rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "NISN already exists", "Data Validation", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String sql = "INSERT INTO students (`nisn`, `studentName`, `gender`, `class`, `place_of_birth`, `date_of_birth`, `religion`, `address`, `districk`, `father`, `mother`, `phone`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, nisn);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, selectedGender);
                    preparedStatement.setString(4, classRoom);
                    preparedStatement.setString(5, placeOfBirth);
                    preparedStatement.setString(6, dateOfBirth);
                    preparedStatement.setString(7, religion);
                    preparedStatement.setString(8, address);
                    preparedStatement.setString(9, districk);
                    preparedStatement.setString(10, father);
                    preparedStatement.setString(11, mother);
                    preparedStatement.setString(12, phone);

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Successfully Added", "Data Validation", JOptionPane.INFORMATION_MESSAGE);                  
                    clear();
                }
            } else {
                String update = "UPDATE students SET studentName = ?, gender = ?, class = ?, place_of_birth = ?, date_of_birth = ?, religion = ?, address = ?, districk = ?, father = ?, mother = ?, phone = ? WHERE nisn = ?";
                preparedStatement = conn.prepareStatement(update);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, selectedGender);
                preparedStatement.setString(3, classRoom);
                preparedStatement.setString(4, placeOfBirth);
                preparedStatement.setString(5, dateOfBirth);
                preparedStatement.setString(6, religion);
                preparedStatement.setString(7, address);
                preparedStatement.setString(8, districk);
                preparedStatement.setString(9, father);
                preparedStatement.setString(10, mother);
                preparedStatement.setString(11, phone);
                preparedStatement.setString(12, nisn);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data successfully updated", "Data Validation", JOptionPane.INFORMATION_MESSAGE);
                clear();
            }
            updateStatistics();
            preparedStatement.close();
            loadDataStudent(false); //buka fungsi
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_masDetCreateActionPerformed

    private void btn_catDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_catDeleteActionPerformed
        if (txt_catDetCode.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Select the Data to be Deleted");
        } else {
            int jawab = JOptionPane.showConfirmDialog(null, "This Data Will Be Deleted, Continue?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (jawab == JOptionPane.YES_OPTION) {
                try {
                    if (conn == null) {
                        JOptionPane.showMessageDialog(null, "Unable to connect to the database.", "Connection Failed", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String bookCode = txt_catDetCode.getText();
                    String sql = "DELETE FROM books WHERE bookCode = ?";
                    try (PreparedStatement pr = conn.prepareStatement(sql)) {
                        pr.setString(1, bookCode);
                        
                        pr.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Successfully Deleted");
                        loadDataBook(false);
                        clear();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btn_catDeleteActionPerformed

    private void btn_masDetDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_masDetDeleteActionPerformed
        if (txt_masDetNisn.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Select the Data to be Deleted");
        } else {
            int jawab = JOptionPane.showConfirmDialog(null, "This Data Will Be Deleted, Continue?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (jawab == JOptionPane.YES_OPTION) {
                try {
                    if (conn == null) {
                        JOptionPane.showMessageDialog(null, "Unable to connect to the database.", "Connection Failed", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String nisn = txt_masDetNisn.getText();
                    String sql = "DELETE FROM students WHERE nisn = ?";
                    try (PreparedStatement pr = conn.prepareStatement(sql)) {
                        pr.setString(1, nisn);
                        
                        pr.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Successfully Deleted");
                        loadDataStudent(false);
                        clear();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btn_masDetDeleteActionPerformed

    private void btn_catClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_catClearActionPerformed
        clear();
    }//GEN-LAST:event_btn_catClearActionPerformed

    private void btn_masDetClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_masDetClearActionPerformed
        clear();
    }//GEN-LAST:event_btn_masDetClearActionPerformed

    private void txt_catSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_catSearchKeyPressed
        loadDataBook(true);
    }//GEN-LAST:event_txt_catSearchKeyPressed

    private void txt_masSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_masSearchKeyPressed
        loadDataStudent(true);
    }//GEN-LAST:event_txt_masSearchKeyPressed

    private void rbt_catAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt_catAscActionPerformed
        loadDataBook(false);
    }//GEN-LAST:event_rbt_catAscActionPerformed

    private void rbt_masAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt_masAscActionPerformed
        loadDataStudent(false);
    }//GEN-LAST:event_rbt_masAscActionPerformed

    private void cmb_catFromOptionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_catFromOptionsItemStateChanged
        loadDataBook(false);
    }//GEN-LAST:event_cmb_catFromOptionsItemStateChanged

    private void cmb_masFromOptionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_masFromOptionsItemStateChanged
        loadDataStudent(false);
    }//GEN-LAST:event_cmb_masFromOptionsItemStateChanged

    private void btn_traLoaGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_traLoaGenerateActionPerformed
        int codeLength = 6; // Panjang kode acak
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < codeLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            code.append(randomChar);
        }

        String generatedCode = code.toString();
        txt_traLoaSerial.setText(generatedCode);
    }//GEN-LAST:event_btn_traLoaGenerateActionPerformed

    private void btn_traLoaSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_traLoaSendActionPerformed
        try {
            if (conn == null) {
                    JOptionPane.showMessageDialog(null, "Unable to connect to the database.", "Connection Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String serial = txt_traLoaSerial.getText();
            String StudentName = cmb_traLoaStudentName.getSelectedItem().toString();
            String StudentNisn = txt_traLoaStudentName.getText();
            String bookName = cmb_traLoaBookName.getSelectedItem().toString();
            String bookCode = txt_traLoaBookName.getText();
            String loanDate = txt_traLoaDate.getText();
            String deadlineDate = txt_traLoaReturnDate.getText();

            if (serial.equals("- Generate Code -") || StudentNisn.isEmpty() || bookCode.isEmpty() || loanDate.isEmpty() || deadlineDate.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Data cannot be empty", "Data Validation", JOptionPane.INFORMATION_MESSAGE); 
                return;
            }

            PreparedStatement preparedStatement;
            String cek = "SELECT * FROM borrowed WHERE borrowId = ?";
            preparedStatement = conn.prepareStatement(cek);
            preparedStatement.setString(1, serial);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Serial Number Already Exists", "Data Validation", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int answer = JOptionPane.showConfirmDialog(null,"Serial : " + serial + "\nStudent : " + StudentName + "\nBook : " + bookName, "Data Validation", JOptionPane.YES_NO_OPTION);
                if (answer == 0){
                String sql = "INSERT INTO `borrowed`(`borrowId`, `nisn`, `BookCode`, `takenDate`, `deadlineDate`, `status`) VALUES (?, ?, ?, ?, ?, ?)";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, serial);
                preparedStatement.setString(2, StudentNisn);
                preparedStatement.setString(3, bookCode);
                preparedStatement.setString(4, loanDate);
                preparedStatement.setString(5, deadlineDate);
                preparedStatement.setString(6, "borrowed");

                preparedStatement.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Book successfully borrowed", "Success", JOptionPane.INFORMATION_MESSAGE);   
                }
                
                txt_traLoaSerial.setText("- Generate Code -");
                cmb_traLoaStudentName.setSelectedItem("- Choose -");
                txt_traLoaStudentName.setText("");
                cmb_traLoaBookName.setSelectedItem("- Choose -");
                txt_traLoaBookName.setText("");
            }
            updateStatistics();
            loadReport();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_traLoaSendActionPerformed

    private void txt_traLoaStudentNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_traLoaStudentNameKeyPressed
        try {
            st = conn.createStatement();
            String selectedStudentNisn = txt_traLoaStudentName.getText();
            String query = "SELECT `studentName` FROM students WHERE nisn LIKE ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%" + selectedStudentNisn + "%"); // Wrap the wildcard outside of the quotes

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String studetName = rs.getString("studentName");
                cmb_traLoaStudentName.setSelectedItem(studetName);
            } else {
                // Handle case where no matching record was found
                cmb_traLoaStudentName.setSelectedItem(""); // Clear the combo box if no match is found
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txt_traLoaStudentNameKeyPressed

    private void txt_traLoaBookNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_traLoaBookNameKeyPressed
        try {
            st = conn.createStatement();
            String selectedBookCode = txt_traLoaBookName.getText();
            String query = "SELECT `bookName` FROM books WHERE bookCode LIKE ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%" + selectedBookCode + "%"); // Wrap the wildcard outside of the quotes

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String bookName = rs.getString("bookName");
                cmb_traLoaBookName.setSelectedItem(bookName);
            } else {
                // Handle case where no matching record was found
                cmb_traLoaBookName.setSelectedItem(""); // Clear the combo box if no match is found
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txt_traLoaBookNameKeyPressed

    private void cmb_traLoaStudentNamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmb_traLoaStudentNamePopupMenuWillBecomeInvisible
        try {
            st = conn.createStatement();
            String selectedStudentName = cmb_traLoaStudentName.getSelectedItem().toString();
            String query = "SELECT `nisn` FROM students WHERE studentName = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, selectedStudentName);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String nisn = rs.getString("nisn");
                txt_traLoaStudentName.setText(nisn);
            } else {
                // Handle case where no matching record was found
                txt_traLoaStudentName.setText("");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmb_traLoaStudentNamePopupMenuWillBecomeInvisible

    private void cmb_traLoaBookNamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmb_traLoaBookNamePopupMenuWillBecomeInvisible
        try {
            st = conn.createStatement();
            String selectedBookName = cmb_traLoaBookName.getSelectedItem().toString();
            String query = "SELECT `bookCode` FROM books WHERE bookName = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, selectedBookName);

            rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                String isbn = rs.getString("bookCode");
                txt_traLoaBookName.setText(isbn);
            } else {
                // Handle case where no matching record was found
                txt_traLoaBookName.setText("");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmb_traLoaBookNamePopupMenuWillBecomeInvisible

    private void rbt_traLoaningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt_traLoaningActionPerformed
        if (rbt_traLoaning.isSelected()) {
            pnl_traLoan.setVisible(true);
            pnl_traReturn.setVisible(false);
        }
        if (rbt_traReturning.isSelected()){
            pnl_traLoan.setVisible(false);
            pnl_traReturn.setVisible(true);
        }
    }//GEN-LAST:event_rbt_traLoaningActionPerformed

    private void btn_traRetSerialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_traRetSerialActionPerformed
        try {
            String serial = txt_traRetSerial.getText();
            
            String query = """
                           SELECT borrowed.*, students.studentName, books.BookName
                           FROM borrowed
                           INNER JOIN students ON borrowed.nisn = students.nisn
                           INNER JOIN books ON borrowed.bookCode = books.bookCode
                           WHERE borrowId = ? ;""";
            
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, serial);

            rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                String status = rs.getString("status");
                if (status.equals("returned")) {
                    JOptionPane.showMessageDialog(null, "Book has been returned", "Data Validation", JOptionPane.INFORMATION_MESSAGE);
                    txt_traRetSerial.setText("");
                } else {
                    // Tampilkan informasi buku jika belum dikembalikan.
                    txt_traRetStudentName.setText(rs.getString("studentName"));
                    txt_traRetBookName.setText(rs.getString("BookName"));
                    txt_traRetReturnDate.setText(rs.getString("deadlineDate"));
                }
            } else {
                JOptionPane.showMessageDialog(null, "Serial Number Not Available", "Data Validation", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_traRetSerialActionPerformed

    private void btn_traRetReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_traRetReturnActionPerformed
        try {
            String serial = txt_traRetSerial.getText();
            String query = "SELECT * FROM borrowed WHERE borrowId = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, serial);
            ResultSet resultSet = preparedStatement.executeQuery();

            int fineAmount = 0; // Deklarasikan fineAmount di tingkat yang lebih tinggi

            if (resultSet.next()) {
                int answer = JOptionPane.showConfirmDialog(null, "This Data Will Be Return, Continue?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (answer == 0) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Date today = sdf.parse(todayDate);
                        Date deadline = sdf.parse(resultSet.getString("deadlineDate"));

                        long diffInMillies = Math.abs(today.getTime() - deadline.getTime());
                        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                        long finePerDay = 1000;

                        fineAmount = (int) (diff * finePerDay); // Assuming finePerDay is the amount of fine per day

                        int answerFine = JOptionPane.showConfirmDialog(null, "Fine Amount: Rp. " + fineAmount + " IDR", "Fine Calculation", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (answerFine == 1) {
                            return;
                        }

                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(null, "Date Parsing Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    String updateQuery = "UPDATE `borrowed` SET `returnedDate`=?, `status`=?, `fine`=? WHERE borrowId = ?";
                    try (PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, todayDate);
                        updateStatement.setString(2, "returned");
                        updateStatement.setString(3, String.valueOf(fineAmount)); // Menggunakan setString untuk fineAmount
                        updateStatement.setString(4, serial);
                        updateStatement.executeUpdate();
                        // Close resources
                    }
                    resultSet.close();
                    preparedStatement.close();

                    // Reset UI elements
                    txt_traRetSerial.setText("");
                    txt_traRetStudentName.setText("");
                    txt_traRetBookName.setText("");
                    txt_traRetReturnDate.setText("");

                    updateStatistics();
                    loadReport();

                    JOptionPane.showMessageDialog(null, "Record Updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Serial Number Not Available", "Data Validation", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_traRetReturnActionPerformed

    private void btn_dashDatBook3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashDatBook3MouseClicked
        try {
            String Options = "reportPreview";
            preview prev = new preview(Options);
            prev.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_dashDatBook3MouseClicked

    private void btn_dashDatBook4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashDatBook4MouseClicked
        try {
            String Options = "activePreview";
            preview prev = new preview(Options);
            prev.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_dashDatBook4MouseClicked

    private void btn_dashDatBook5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashDatBook5MouseClicked
        try {
            String Options = "timeoverPreview";
            preview prev = new preview(Options);
            prev.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_dashDatBook5MouseClicked

    private void btn_dashDatBook6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_dashDatBook6MouseClicked
        try {
            String Options = "historyPreview";
            preview prev = new preview(Options);
            prev.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_dashDatBook6MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgr_classOptions;
    private javax.swing.ButtonGroup bgr_filterOptions;
    private javax.swing.ButtonGroup bgr_genderOptions;
    private javax.swing.ButtonGroup bgr_transactionOptions;
    private javax.swing.JButton btn_catClear;
    private javax.swing.JButton btn_catCreate;
    private javax.swing.JButton btn_catDelete;
    private javax.swing.JPanel btn_catalog;
    private javax.swing.JPanel btn_dashDatBook;
    private javax.swing.JPanel btn_dashDatBook3;
    private javax.swing.JPanel btn_dashDatBook4;
    private javax.swing.JPanel btn_dashDatBook5;
    private javax.swing.JPanel btn_dashDatBook6;
    private javax.swing.JPanel btn_dashDatStudent;
    private javax.swing.JPanel btn_dashPriBook;
    private javax.swing.JPanel btn_dashPriStudent;
    private javax.swing.JPanel btn_dashTraLoan;
    private javax.swing.JPanel btn_dashTraReport;
    private javax.swing.JPanel btn_dashTraRetrun;
    private javax.swing.JPanel btn_dashboard;
    private javax.swing.JButton btn_masDetClear;
    private javax.swing.JButton btn_masDetCreate;
    private javax.swing.JButton btn_masDetDelete;
    private javax.swing.JPanel btn_masterData;
    private javax.swing.JPanel btn_report;
    private javax.swing.JButton btn_traLoaGenerate;
    private javax.swing.JButton btn_traLoaSend;
    private javax.swing.JButton btn_traRetReturn;
    private javax.swing.JButton btn_traRetSerial;
    private javax.swing.JPanel btn_transaction;
    private javax.swing.JComboBox<String> cmb_catFromOptions;
    private javax.swing.JComboBox<String> cmb_masDetReligion;
    private javax.swing.JComboBox<String> cmb_masFromOptions;
    private javax.swing.JComboBox<String> cmb_traLoaBookName;
    private javax.swing.JComboBox<String> cmb_traLoaStudentName;
    private javax.swing.JLabel ic_dashCalCalendar;
    private javax.swing.JLabel ic_dashDatBook;
    private javax.swing.JLabel ic_dashDatBook3;
    private javax.swing.JLabel ic_dashDatBook4;
    private javax.swing.JLabel ic_dashDatBook5;
    private javax.swing.JLabel ic_dashDatBook6;
    private javax.swing.JLabel ic_dashDatStudent;
    private javax.swing.JLabel ic_dashLoan;
    private javax.swing.JLabel ic_dashPriBook;
    private javax.swing.JLabel ic_dashPriHeader;
    private javax.swing.JLabel ic_dashPriStudent;
    private javax.swing.JLabel ic_dashReturn;
    private javax.swing.JLabel ic_dashStock;
    private javax.swing.JLabel ic_dashStudent;
    private javax.swing.JLabel ic_dashTraLoan;
    private javax.swing.JLabel ic_dashTraReport;
    private javax.swing.JLabel ic_dashTraRetrun;
    private javax.swing.JLabel ic_user;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JLabel lbl_bookCatalog;
    private javax.swing.JLabel lbl_catControlCatalog;
    private javax.swing.JLabel lbl_catDetAuthor;
    private javax.swing.JLabel lbl_catDetClassifications;
    private javax.swing.JLabel lbl_catDetCode;
    private javax.swing.JLabel lbl_catDetName;
    private javax.swing.JLabel lbl_catDetPagecount;
    private javax.swing.JLabel lbl_catDetPublisher;
    private javax.swing.JLabel lbl_catDetPublishyear;
    private javax.swing.JLabel lbl_catDetRack;
    private javax.swing.JLabel lbl_catDetStock;
    private javax.swing.JLabel lbl_catFilters;
    private javax.swing.JLabel lbl_catRack;
    private javax.swing.JLabel lbl_catSearch;
    private javax.swing.JLabel lbl_catStok;
    private javax.swing.JLabel lbl_catTitle;
    private javax.swing.JLabel lbl_catTotalRack;
    private javax.swing.JLabel lbl_catTotalStok;
    private javax.swing.JLabel lbl_catTotalTitles;
    private javax.swing.JLabel lbl_catalog;
    private javax.swing.JLabel lbl_dashActHeaCount;
    private javax.swing.JLabel lbl_dashActHeader;
    private javax.swing.JLabel lbl_dashCalDate;
    private javax.swing.JLabel lbl_dashCalDay;
    private javax.swing.JLabel lbl_dashCalMonth;
    private javax.swing.JLabel lbl_dashCalYear;
    private javax.swing.JLabel lbl_dashControl;
    private javax.swing.JLabel lbl_dashDashboard;
    private javax.swing.JLabel lbl_dashDatBook;
    private javax.swing.JLabel lbl_dashDatBook3;
    private javax.swing.JLabel lbl_dashDatBook4;
    private javax.swing.JLabel lbl_dashDatBook5;
    private javax.swing.JLabel lbl_dashDatBook6;
    private javax.swing.JLabel lbl_dashDatHeader;
    private javax.swing.JLabel lbl_dashDatStudent;
    private javax.swing.JLabel lbl_dashDetHeaCount;
    private javax.swing.JLabel lbl_dashDetHeader;
    private javax.swing.JLabel lbl_dashLoan;
    private javax.swing.JLabel lbl_dashPriBook;
    private javax.swing.JLabel lbl_dashPriStudent;
    private javax.swing.JLabel lbl_dashReturn;
    private javax.swing.JLabel lbl_dashStok;
    private javax.swing.JLabel lbl_dashStudent;
    private javax.swing.JLabel lbl_dashTotalLoan;
    private javax.swing.JLabel lbl_dashTotalReturn;
    private javax.swing.JLabel lbl_dashTotalStock;
    private javax.swing.JLabel lbl_dashTotalStudent;
    private javax.swing.JLabel lbl_dashTraHeader;
    private javax.swing.JLabel lbl_dashTraLoan;
    private javax.swing.JLabel lbl_dashTraReport;
    private javax.swing.JLabel lbl_dashTraRetrun;
    private javax.swing.JLabel lbl_dashboard;
    private javax.swing.JLabel lbl_developer;
    private javax.swing.JLabel lbl_formTransaction;
    private javax.swing.JLabel lbl_formTransaction1;
    private javax.swing.JLabel lbl_institution;
    private javax.swing.JLabel lbl_masClass;
    private javax.swing.JLabel lbl_masControlStudent;
    private javax.swing.JLabel lbl_masDetAddress;
    private javax.swing.JLabel lbl_masDetClass;
    private javax.swing.JLabel lbl_masDetDate;
    private javax.swing.JLabel lbl_masDetDistrick;
    private javax.swing.JLabel lbl_masDetFather;
    private javax.swing.JLabel lbl_masDetGender;
    private javax.swing.JLabel lbl_masDetMother;
    private javax.swing.JLabel lbl_masDetName;
    private javax.swing.JLabel lbl_masDetNis;
    private javax.swing.JLabel lbl_masDetNisn;
    private javax.swing.JLabel lbl_masDetPhone;
    private javax.swing.JLabel lbl_masDetPlace;
    private javax.swing.JLabel lbl_masDetReligion;
    private javax.swing.JLabel lbl_masDistrick;
    private javax.swing.JLabel lbl_masFilters;
    private javax.swing.JLabel lbl_masSearch;
    private javax.swing.JLabel lbl_masStudent;
    private javax.swing.JLabel lbl_masTotalClass;
    private javax.swing.JLabel lbl_masTotalDistrick;
    private javax.swing.JLabel lbl_masTotalStudent;
    private javax.swing.JLabel lbl_masterData;
    private javax.swing.JLabel lbl_materData;
    private javax.swing.JLabel lbl_rep;
    private javax.swing.JLabel lbl_repActHeaCount;
    private javax.swing.JLabel lbl_repActHeader;
    private javax.swing.JLabel lbl_repDetHeaCount;
    private javax.swing.JLabel lbl_repDetHeader;
    private javax.swing.JLabel lbl_repHisHeaCount;
    private javax.swing.JLabel lbl_repHisHeader;
    private javax.swing.JLabel lbl_report;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_tra;
    private javax.swing.JLabel lbl_traControl;
    private javax.swing.JLabel lbl_traLoaBookName;
    private javax.swing.JLabel lbl_traLoaDate;
    private javax.swing.JLabel lbl_traLoaLoanDate;
    private javax.swing.JLabel lbl_traLoaReturnDate;
    private javax.swing.JLabel lbl_traLoaSerial;
    private javax.swing.JLabel lbl_traLoaStudentName;
    private javax.swing.JLabel lbl_traRetBookName;
    private javax.swing.JLabel lbl_traRetDate;
    private javax.swing.JLabel lbl_traRetReturnDate;
    private javax.swing.JLabel lbl_traRetSerial;
    private javax.swing.JLabel lbl_traRetStudentName;
    private javax.swing.JLabel lbl_traRetTodayDate;
    private javax.swing.JLabel lbl_transaction;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_catDetHeader;
    private javax.swing.JPanel pnl_catDetail;
    private javax.swing.JPanel pnl_catRack;
    private javax.swing.JPanel pnl_catStok;
    private javax.swing.JPanel pnl_catTitles;
    private javax.swing.JPanel pnl_catalog;
    private javax.swing.JPanel pnl_dashActHeaCount;
    private javax.swing.JPanel pnl_dashActHeader;
    private javax.swing.JPanel pnl_dashActive;
    private javax.swing.JPanel pnl_dashCalSecond;
    private javax.swing.JPanel pnl_dashCalendar;
    private javax.swing.JPanel pnl_dashDatHeader;
    private javax.swing.JPanel pnl_dashData;
    private javax.swing.JPanel pnl_dashDetHeaCount;
    private javax.swing.JPanel pnl_dashDetHeader;
    private javax.swing.JPanel pnl_dashDetained;
    private javax.swing.JPanel pnl_dashLoan;
    private javax.swing.JPanel pnl_dashPriHeader;
    private javax.swing.JPanel pnl_dashPrint;
    private javax.swing.JPanel pnl_dashReturn;
    private javax.swing.JPanel pnl_dashStock;
    private javax.swing.JPanel pnl_dashStudent;
    private javax.swing.JPanel pnl_dashTraHeader;
    private javax.swing.JPanel pnl_dashTransaction;
    private javax.swing.JPanel pnl_dashboard;
    private java.awt.Panel pnl_display;
    private java.awt.Panel pnl_header;
    private javax.swing.JPanel pnl_masClass;
    private javax.swing.JPanel pnl_masDetHeader;
    private javax.swing.JPanel pnl_masDetail;
    private javax.swing.JPanel pnl_masDistrick;
    private javax.swing.JPanel pnl_masStudent;
    private javax.swing.JPanel pnl_masterData;
    private java.awt.Panel pnl_menu;
    private javax.swing.JPanel pnl_repActHeaCount;
    private javax.swing.JPanel pnl_repActHeader;
    private javax.swing.JPanel pnl_repActive;
    private javax.swing.JPanel pnl_repDetHeaCount;
    private javax.swing.JPanel pnl_repDetHeader;
    private javax.swing.JPanel pnl_repDetained;
    private javax.swing.JPanel pnl_repHisHeaCount;
    private javax.swing.JPanel pnl_repHisHeader;
    private javax.swing.JPanel pnl_repHistory;
    private javax.swing.JPanel pnl_report;
    private javax.swing.JPanel pnl_traLoaHeader;
    private javax.swing.JPanel pnl_traLoan;
    private javax.swing.JPanel pnl_traRetHeader;
    private javax.swing.JPanel pnl_traReturn;
    private javax.swing.JPanel pnl_transaction;
    private javax.swing.JRadioButton rbt_catAsc;
    private javax.swing.JRadioButton rbt_catDesc;
    private javax.swing.JRadioButton rbt_masAsc;
    private javax.swing.JRadioButton rbt_masDesc;
    private javax.swing.JRadioButton rbt_masDetGenderFemale;
    private javax.swing.JRadioButton rbt_masDetGenderMale;
    private javax.swing.JRadioButton rbt_traLoaning;
    private javax.swing.JRadioButton rbt_traReturning;
    private javax.swing.JScrollPane scr_catView;
    private javax.swing.JScrollPane scr_masView;
    private javax.swing.JTable tbl_catView;
    private javax.swing.JTable tbl_dashActive;
    private javax.swing.JTable tbl_dashDetained;
    private javax.swing.JTable tbl_masView;
    private javax.swing.JTable tbl_repActive;
    private javax.swing.JTable tbl_repDetained;
    private javax.swing.JTable tbl_repHistory;
    private javax.swing.JTextField txt_catDetAuthor;
    private javax.swing.JTextField txt_catDetClassifications;
    private javax.swing.JTextField txt_catDetCode;
    private javax.swing.JTextField txt_catDetName;
    private javax.swing.JTextField txt_catDetPagecount;
    private javax.swing.JTextField txt_catDetPublisher;
    private javax.swing.JTextField txt_catDetPublishyear;
    private javax.swing.JTextField txt_catDetRack;
    private javax.swing.JTextField txt_catDetStock;
    private javax.swing.JTextField txt_catSearch;
    private javax.swing.JTextField txt_masDetAddress;
    private javax.swing.JTextField txt_masDetClass;
    private javax.swing.JTextField txt_masDetDate;
    private javax.swing.JTextField txt_masDetDistrick;
    private javax.swing.JTextField txt_masDetFather;
    private javax.swing.JTextField txt_masDetMother;
    private javax.swing.JTextField txt_masDetName;
    private javax.swing.JTextField txt_masDetNis;
    private javax.swing.JTextField txt_masDetNisn;
    private javax.swing.JTextField txt_masDetPhone;
    private javax.swing.JTextField txt_masDetPlace;
    private javax.swing.JTextField txt_masSearch;
    private javax.swing.JTextField txt_traLoaBookName;
    private javax.swing.JTextField txt_traLoaDate;
    private javax.swing.JTextField txt_traLoaReturnDate;
    private javax.swing.JTextField txt_traLoaSerial;
    private javax.swing.JTextField txt_traLoaStudentName;
    private javax.swing.JTextField txt_traRetBookName;
    private javax.swing.JTextField txt_traRetReturnDate;
    private javax.swing.JTextField txt_traRetSerial;
    private javax.swing.JTextField txt_traRetStudentName;
    private javax.swing.JTextField txt_traRetTodayDate;
    // End of variables declaration//GEN-END:variables
}
