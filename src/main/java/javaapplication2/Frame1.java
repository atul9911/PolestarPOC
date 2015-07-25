/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.awt.*;
import java.awt.event.*;
import java.io.File; 
import java.io.FileInputStream;
import java.io.IOException; 
import java.util.Vector; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import javax.swing.*; 
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.oxbow.swingbits.table.filter.*;
/**
 *
 * @author atul
 */
public class Frame1 extends javax.swing.JFrame {

    /**
     * Creates new form Frame1
     */
    static JTable table; 
    static JScrollPane scroll; 
    // header is Vector contains table Column 
    static Vector headers = new Vector(); 
    static Vector data = new Vector();
    // Model is used to construct 
    DefaultTableModel model = null; 
    // data is Vector contains Data from Excel File 
   // static Vector data = new Vector();
    static JButton jbClick; 
    static JFileChooser jChooser; 
    static int tableWidth = 0;
    static int tableHeight = 0; 
    
    public Frame1() {
            super("Import Excel To JTable");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      JPanel buttonPanel = new JPanel(); 
      buttonPanel.setBackground(Color.white); 
      jChooser = new JFileChooser(); 
      jbClick = new JButton("Select Excel File"); 
      buttonPanel.add(jbClick, BorderLayout.CENTER); 

        // Show Button Click Event 
          jbClick.addActionListener(new ActionListener() 
            { 
                     @Override public void actionPerformed(ActionEvent arg0) 
                              { 
                                     jChooser.showOpenDialog(null); 
                                     jChooser.setDialogTitle("Select only Excel workbooks");
                                     File file = jChooser.getSelectedFile();
                                    if(file==null)
                                      {
                                          JOptionPane.showMessageDialog(
                                          null, "Please select any Excel file",
                                          "Help",
                                          JOptionPane.INFORMATION_MESSAGE); 
                                          return;
                                        }
                                    else if(!file.getName().endsWith("xls"))
                                       { 
                                             JOptionPane.showMessageDialog(
                                             null, "Please select only Excel file.", 
                                            "Error",JOptionPane.ERROR_MESSAGE); 
                                       }
                                    else 
                                      { 
                                            fillData(file);
                                            model = new DefaultTableModel(data, headers); 
                                            tableWidth = model.getColumnCount() * 150;                             
                                            tableHeight = model.getRowCount() * 25; 
                                            table.setPreferredSize(new Dimension( tableWidth, tableHeight));
                                            table.setModel(model); 
                                            TableRowFilterSupport.forTable(table);
        
                                         } 
                              } 
            });
         table = new JTable(); 
         table.setAutoCreateRowSorter(true); 
         model = new DefaultTableModel(data, headers);
         table.setModel(model); 
         table.setBackground(Color.pink);
         table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
         table.setEnabled(false); 
         table.setRowHeight(25); 
         table.setRowMargin(4); 
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setShowGrid(true);
         tableWidth = model.getColumnCount() * 550;
         tableHeight = model.getRowCount() * 25;
         table.setPreferredSize(new Dimension( tableWidth, tableHeight)); 
         scroll = new JScrollPane(table);
         scroll.setBackground(Color.pink);
         scroll.setPreferredSize(new Dimension(300, 300)); 
         scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
         scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
         getContentPane().add(buttonPanel, BorderLayout.NORTH); 
         getContentPane().add(scroll, BorderLayout.CENTER); 
         setSize(600, 600); 
         setResizable(true);
         setVisible(true);
    }
    
    
    void fillData(File file) 
      { 
         int index=-1;
         HSSFWorkbook workbook = null; 
        try { 
               try { 
                       FileInputStream inputStream = new FileInputStream (file);
                        workbook = new HSSFWorkbook(inputStream);
                    } 
               catch (IOException ex) 
                    { 
                         Logger.getLogger(Frame1.class. getName()).log(Level.SEVERE, null, ex);
                     } 

                       String[] strs=new String[workbook.getNumberOfSheets()];
                      //get all sheet names from selected workbook
                        for (int i = 0; i < strs.length; i++) {
                             strs[i]= workbook.getSheetName(i); }
                        JFrame frame = new JFrame("Input Dialog");
                      
                        String selectedsheet = (String) JOptionPane.showInputDialog(
                           frame, "Which worksheet you want to import ?", "Select Worksheet",
                          JOptionPane.QUESTION_MESSAGE, null, strs, strs[0]);
                
                       if (selectedsheet!=null) {
                            for (int i = 0; i < strs.length; i++)
                              {
                                 if (workbook.getSheetName(i).equalsIgnoreCase(selectedsheet))
                                 index=i; }
                            HSSFSheet sheet = workbook.getSheetAt(index);
                            HSSFRow row=sheet.getRow(0);
                        
                           headers.clear();
                           //int value=row.getLastCellNum();
                           for (int i = 0; i < row.getLastCellNum(); i++)
                          {
                             HSSFCell cell1 = row.getCell(i);
                             headers.add(cell1.toString());
                             
                          }
                        
                          data.clear();
                          for (int j = 1; j < sheet.getLastRowNum() + 1; j++)
                          {
                             Vector d = new Vector();
                             row=sheet.getRow(j);
                             int noofrows=row.getLastCellNum();
                             for (int i = 0; i < noofrows; i++)
                             {    //To handle empty excel cells 
                                   HSSFCell cell=row.getCell(i,
                                   org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK );
                                   d.add(cell.toString());
                             }
                            d.add("\n");
                            data.add(d);
                          }
                     }
                    else { return; }
        }
      catch (Exception e) { e.printStackTrace(); } }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Frame1().setVisible(true);
//            }
//        });
new Frame1();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
