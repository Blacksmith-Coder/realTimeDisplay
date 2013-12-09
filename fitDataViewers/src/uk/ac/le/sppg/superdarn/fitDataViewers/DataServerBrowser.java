/*
 * NetFitDataBrowser.java
 *
 * Created on 19 September 2007, 16:56
 */

package uk.ac.le.sppg.superdarn.fitDataViewers;

/**
 *
 * @author  nigel
 */
public class DataServerBrowser extends javax.swing.JFrame {
    
    /** Creates new form NetFitDataBrowser */
    public DataServerBrowser(String server) {
        initComponents();
        netServerBean.setServer( server );
        netServerBean.setFitBean( fitDataBean );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        fitDataBean = new uk.ac.le.sppg.superdarn.fitDataBeans.FitDataBean();
        netServerBean = new uk.ac.le.sppg.superdarn.fitDataBeans.NetFitacfServerBean();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setLayout(new java.awt.BorderLayout());
        panel.add(fitDataBean, java.awt.BorderLayout.CENTER);
        panel.add(netServerBean, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        if ( args.length != 1 ) {
            System.err.print("DataServerBrowser requires 1 argument,");
            System.err.println(" the server on which the realtime data servlet is running ");
            System.exit(1);
        }
        
        final String server = args[0];
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataServerBrowser(server).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private uk.ac.le.sppg.superdarn.fitDataBeans.FitDataBean fitDataBean;
    private uk.ac.le.sppg.superdarn.fitDataBeans.NetFitacfServerBean netServerBean;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
    
}
