/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

/**
 *
 * @author atul
 */
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.oxbow.swingbits.table.filter.TableRowFilterSupport;


public class TableFilterTest implements Runnable {

    
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater( new TableFilterTest() );
        
    }


    @Override
    public void run() {
        
        JFrame f = new JFrame("Swing Table Filter Test");
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        f.setPreferredSize( new Dimension( 1000, 600 ));
        
        JPanel p = (JPanel) f.getContentPane();
        p.setBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5));
        final JTable table = buildTable();
        p.add( new JScrollPane( table ));
        
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
    }
    
    private JTable buildTable() {
        JTable table = TableRowFilterSupport.forTable(new JTable()).actions(true).searchable(true).useTableRenderers(true).apply();
        table.setModel( new DefaultTableModel(data, colNames) );
        table.getColumnModel().getColumn(0).setCellRenderer(new TestRenderer());
        return table;
    }
    
    private static final int ITEM_COUNT = 2000;

    public static Object[] colNames = { "A123", "B123", "C123" };

    public static Object[][] sample = {

        { "aaa444", 123.2, "ccc333" },
        {    null,  88888888,    null },
        { "aaa333", 12344, "ccc222" },
        { "aaa333", 67456.34534, "ccc111" },
        { "aaa111", 78427.33, "ccc444" }

    };

    public static Object[][] data = new Object[ITEM_COUNT][sample[0].length];

    static {

        for( int i = 0; i<ITEM_COUNT; i+=sample.length ) {
            for( int j = 0; j<sample.length; j+=1 ) {
                data[i+j] = sample[j];
            }
        }

    }
    
    @SuppressWarnings("serial")
    static class TestRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, 
                int row, int column) {
        
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setText( getText() + "  ********" );
            return this;
        }
        
    }
    
    
}
