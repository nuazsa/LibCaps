/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Azis
 */
public class preview extends javax.swing.JFrame {
    public Statement st;
    public ResultSet rs;
    // Connector Database   
    Connection conn = connector.ConnectionDatabases.openConnection();
    /**
     * Creates new form preview
     * @param Options
     */
    public preview(String Options) {
        initComponents();
        
        try {
            if (Options.equals("studentPreview")){
                loadStudentsPreview();
                lbl_preview.setText("STUDENTS PREVIEW");
            } else if (Options.equals("bookPreview")) {
                loadBooksPreview();
                lbl_preview.setText("BOOKS PREVIEW");
            } else if (Options.equals("reportPreview")) {
                loadReportPreview();
                lbl_preview.setText("FULL REPORT PREVIEW");
            } else if (Options.equals("activePreview")) {
                loadActivePreview();
                lbl_preview.setText("BORROW ACTIVE PREVIEW");
            } else if (Options.equals("timeoverPreview")) {
                loadTImeOverPreview();
                lbl_preview.setText("TIME OVER PREVIEW");
            } else if (Options.equals("historyPreview")) {
                loadHistoryPreview();
                lbl_preview.setText("HISOTRY PREVIEW");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadStudentsPreview() {
        try {
            st = conn.createStatement();
            String query = "SELECT * FROM students";

            rs = st.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            
            model.addColumn("Nisn");
            model.addColumn("Full Name");
            model.addColumn("Gender"); // Correct the column name
            model.addColumn("Class");
            model.addColumn("place_of_birth");
            model.addColumn("date_of_birth");
            model.addColumn("religion");
            model.addColumn("address");
            model.addColumn("districk");
            model.addColumn("father");
            model.addColumn("mother");
            model.addColumn("phone");
            
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("nisn"),
                    rs.getString("studentName"),
                    rs.getString("gender"),
                    rs.getString("romawiClass"),   
                    rs.getString("place_of_birth"),
                    rs.getString("date_of_birth"),
                    rs.getString("religion"),
                    rs.getString("address"), 
                    rs.getString("districk"),
                    rs.getString("father"),
                    rs.getString("mother"),
                    rs.getString("phone"),                    
                };
                model.addRow(data);
            }

            tbl_preview.setModel(model);
            
            // Set the preferred column widths
            tbl_preview.getColumnModel().getColumn(0).setPreferredWidth(200); // Kolom Kode
            tbl_preview.getColumnModel().getColumn(1).setPreferredWidth(400); // Kolom Judul
            tbl_preview.getColumnModel().getColumn(2).setPreferredWidth(50); // Kolom Klasifikasi
            tbl_preview.getColumnModel().getColumn(3).setPreferredWidth(50); // Kolom Pengarang
            tbl_preview.getColumnModel().getColumn(4).setPreferredWidth(200); // Kolom Kode
            tbl_preview.getColumnModel().getColumn(5).setPreferredWidth(200); // Kolom Judul
            tbl_preview.getColumnModel().getColumn(6).setPreferredWidth(200); // Kolom Klasifikasi
            tbl_preview.getColumnModel().getColumn(7).setPreferredWidth(400); // Kolom Pengarang
            tbl_preview.getColumnModel().getColumn(8).setPreferredWidth(100); // Kolom Kode
            tbl_preview.getColumnModel().getColumn(9).setPreferredWidth(200); // Kolom Judul
            tbl_preview.getColumnModel().getColumn(10).setPreferredWidth(200); // Kolom Klasifikasi
            tbl_preview.getColumnModel().getColumn(11).setPreferredWidth(200); // Kolom Pengarang
            
            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    private void loadBooksPreview() {
        try {
            st = conn.createStatement();
            String query = "SELECT * FROM books";

            rs = st.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            
            model.addColumn("bookCode");
            model.addColumn("bookName");
            model.addColumn("classification");
            model.addColumn("publisher");
            model.addColumn("author");
            model.addColumn("pageCount");
            model.addColumn("publishYear");
            model.addColumn("stock");
            model.addColumn("rack");
            
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("bookCode"),
                    rs.getString("bookName"),
                    rs.getString("classification"),
                    rs.getString("publisher"),   
                    rs.getString("author"),
                    rs.getString("pageCount"),
                    rs.getString("publishYear"),
                    rs.getString("stock"), 
                    rs.getString("rack")                 
                };
                model.addRow(data);
            }

            tbl_preview.setModel(model);
            
            // Set the preferred column widths
            tbl_preview.getColumnModel().getColumn(0).setPreferredWidth(100); // Kolom Kode
            tbl_preview.getColumnModel().getColumn(1).setPreferredWidth(300); // Kolom Judul
            tbl_preview.getColumnModel().getColumn(2).setPreferredWidth(100); // Kolom Klasifikasi
            tbl_preview.getColumnModel().getColumn(3).setPreferredWidth(100); // Kolom Pengarang
            tbl_preview.getColumnModel().getColumn(4).setPreferredWidth(100); // Kolom Kode
            tbl_preview.getColumnModel().getColumn(5).setPreferredWidth(50); // Kolom Judul
            tbl_preview.getColumnModel().getColumn(6).setPreferredWidth(100); // Kolom Klasifikasi
            tbl_preview.getColumnModel().getColumn(7).setPreferredWidth(50); // Kolom Pengarang
            tbl_preview.getColumnModel().getColumn(8).setPreferredWidth(50); // Kolom Kode
            
            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } 
    
    private void loadReportPreview() {
        try {
            st = conn.createStatement();
            String query = """
                           SELECT borrowed.*, students.studentName, books.BookName
                           FROM borrowed
                           INNER JOIN students ON borrowed.nisn = students.nisn
                           INNER JOIN books ON borrowed.bookCode = books.bookCode
                           ORDER BY takenDate ASC;""";

            rs = st.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            
            model.addColumn("Taken");
            model.addColumn("Full Name");
            model.addColumn("Book");
            model.addColumn("Deadline");
            model.addColumn("Return");
            model.addColumn("Status");
            model.addColumn("Serial");
            
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("takenDate"),
                    rs.getString("studentName"),
                    rs.getString("bookName"),   
                    rs.getString("deadlineDate"),
                    rs.getString("returnedDate"),
                    rs.getString("status"),
                    rs.getString("borrowId")             
                };
                model.addRow(data);
            }

            tbl_preview.setModel(model);
            
            // Set the preferred column widths
            tbl_preview.getColumnModel().getColumn(0).setPreferredWidth(100); // Kolom Kode
            tbl_preview.getColumnModel().getColumn(1).setPreferredWidth(300); // Kolom Judul
            tbl_preview.getColumnModel().getColumn(2).setPreferredWidth(300); // Kolom Klasifikasi
            tbl_preview.getColumnModel().getColumn(3).setPreferredWidth(100); // Kolom Pengarang
            tbl_preview.getColumnModel().getColumn(4).setPreferredWidth(100); // Kolom Kode
            

            tbl_preview.getColumnModel().getColumn(5).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 5) {
                Color textColor = Color.BLACK;
                String statusValue = tbl_preview.getValueAt(row, column).toString();
                if ( statusValue.equals("borrowed")) {
                    textColor = Color.GREEN;
                } else if ( statusValue.equals("overdue")) {
                    textColor = Color.RED;
                }
                component.setForeground(textColor);
            }
            return component;
            });
            tbl_preview.getColumnModel().getColumn(6).setPreferredWidth(100); // Kolom Klasifikasi
            
            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } 

    private void loadActivePreview() {
        try {
            st = conn.createStatement();
            String query = """
                           SELECT borrowed.*, students.studentName, books.BookName
                           FROM borrowed
                           INNER JOIN students ON borrowed.nisn = students.nisn
                           INNER JOIN books ON borrowed.bookCode = books.bookCode
                           WHERE status = 'borrowed' ORDER BY takenDate ASC;""";

            rs = st.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            
            model.addColumn("Full Name");
            model.addColumn("Book");
            model.addColumn("Taken");
            model.addColumn("Deadline");
            model.addColumn("Status");
            model.addColumn("Serial");
            
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("studentName"),
                    rs.getString("bookName"),   
                    rs.getString("takenDate"),
                    rs.getString("deadlineDate"),
                    rs.getString("status"),
                    rs.getString("borrowId")             
                };
                model.addRow(data);
            }

            tbl_preview.setModel(model);
            
            // Set the preferred column widths
            tbl_preview.getColumnModel().getColumn(0).setPreferredWidth(400); // Kolom Kode
            tbl_preview.getColumnModel().getColumn(1).setPreferredWidth(400); // Kolom Judul
            tbl_preview.getColumnModel().getColumn(2).setPreferredWidth(100); // Kolom Klasifikasi
            tbl_preview.getColumnModel().getColumn(3).setPreferredWidth(100); // Kolom Pengarang
            tbl_preview.getColumnModel().getColumn(4).setPreferredWidth(100); // Kolom Kode
            tbl_preview.getColumnModel().getColumn(5).setPreferredWidth(100); // Kolom Judul
            
            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }        
    
    private void loadTImeOverPreview() {
        try {
            st = conn.createStatement();
            String query = """
                           SELECT borrowed.*, students.studentName, books.BookName
                           FROM borrowed
                           INNER JOIN students ON borrowed.nisn = students.nisn
                           INNER JOIN books ON borrowed.bookCode = books.bookCode
                           WHERE status = 'overdue' ORDER BY takenDate ASC;""";

            rs = st.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            
            model.addColumn("Full Name");
            model.addColumn("Book");
            model.addColumn("Taken");
            model.addColumn("Deadline");
            model.addColumn("Status");
            model.addColumn("Serial");
            
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("studentName"),
                    rs.getString("bookName"),   
                    rs.getString("takenDate"),
                    rs.getString("deadlineDate"),
                    rs.getString("status"),
                    rs.getString("borrowId")             
                };
                model.addRow(data);
            }

            tbl_preview.setModel(model);
            
            // Set the preferred column widths
            tbl_preview.getColumnModel().getColumn(0).setPreferredWidth(400); // Kolom Kode
            tbl_preview.getColumnModel().getColumn(1).setPreferredWidth(400); // Kolom Judul
            tbl_preview.getColumnModel().getColumn(2).setPreferredWidth(100); // Kolom Klasifikasi
            tbl_preview.getColumnModel().getColumn(3).setPreferredWidth(100); // Kolom Pengarang
            tbl_preview.getColumnModel().getColumn(4).setPreferredWidth(100); // Kolom Kode
            tbl_preview.getColumnModel().getColumn(5).setPreferredWidth(100); // Kolom Judul
            
            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } 
    
    private void loadHistoryPreview() {
        try {
            st = conn.createStatement();
            String query = """
                           SELECT borrowed.*, students.studentName, books.BookName
                           FROM borrowed
                           INNER JOIN students ON borrowed.nisn = students.nisn
                           INNER JOIN books ON borrowed.bookCode = books.bookCode
                           WHERE status = 'returned' ORDER BY takenDate ASC;""";

            rs = st.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            
            model.addColumn("Full Name");
            model.addColumn("Book");
            model.addColumn("Taken");
            model.addColumn("Deadline");
            model.addColumn("Return");
            model.addColumn("Serial");
            model.addColumn("Fine");
            
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("studentName"),
                    rs.getString("bookName"),   
                    rs.getString("takenDate"),
                    rs.getString("deadlineDate"),
                    rs.getString("returnedDate"),
                    rs.getString("borrowId")   ,
                    rs.getString("fine")              
                };
                model.addRow(data);
            }

            tbl_preview.setModel(model);
            
            // Set the preferred column widths
            tbl_preview.getColumnModel().getColumn(0).setPreferredWidth(400); // Kolom Kode
            tbl_preview.getColumnModel().getColumn(1).setPreferredWidth(400); // Kolom Judul
            tbl_preview.getColumnModel().getColumn(2).setPreferredWidth(100); // Kolom Klasifikasi
            tbl_preview.getColumnModel().getColumn(3).setPreferredWidth(100); // Kolom Pengarang
            tbl_preview.getColumnModel().getColumn(4).setPreferredWidth(100); // Kolom Kode
            
            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }  
    
    public void openFile(String file){
        try{
            File path = new File(file);
            Desktop.getDesktop().open(path);
        }catch(IOException ioe){
            System.out.println(ioe);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbl_preview = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_preview = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        lbl_preview.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_preview.setForeground(new java.awt.Color(102, 102, 102));
        lbl_preview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_preview.setText("PREVIEW");

        tbl_preview.setBackground(new java.awt.Color(204, 204, 204));
        tbl_preview.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_preview.setEnabled(false);
        jScrollPane1.setViewportView(tbl_preview);

        jButton1.setText(".xlsx");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(lbl_preview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbl_preview)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Workbook wb = null; // Declare wb here
        
        try{
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if(saveFile != null){
                saveFile = new File(saveFile.toString()+".xlsx");
                wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("customer");

                Row rowCol = sheet.createRow(0);
                for(int i=0;i<tbl_preview.getColumnCount();i++){
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tbl_preview.getColumnName(i));
                }

                for(int j=0;j<tbl_preview.getRowCount();j++){
                    Row row = sheet.createRow(j+1);
                    for(int k=0;k<tbl_preview.getColumnCount();k++){
                        Cell cell = row.createCell(k);
                        if(tbl_preview.getValueAt(j, k)!=null){
                            cell.setCellValue(tbl_preview.getValueAt(j, k).toString());
                        }
                    }
                }
                try (FileOutputStream out = new FileOutputStream(new File(saveFile.toString()))) {
                    wb.write(out);
                }
                openFile(saveFile.toString());
            } else {
                JOptionPane.showMessageDialog(null,"Error al generar archivo");
            }
        } catch(FileNotFoundException e){
            System.out.println(e);
        } catch(IOException io){
            System.out.println(io);
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(preview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(preview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(preview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(preview.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new preview("").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_preview;
    private javax.swing.JTable tbl_preview;
    // End of variables declaration//GEN-END:variables
}
