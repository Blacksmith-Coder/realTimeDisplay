/*
 * FitFileBrowser.java
 *
 * Created on 19 September 2007, 10:46
 */

package uk.ac.le.sppg.superdarn.fitDataViewers;

/**
 *
 * @author  nigel
 */
public class FitFileBrowser extends javax.swing.JFrame {
    
    /** Creates new form FitFileBrowser */
    public FitFileBrowser(String[] filenames) {
        initComponents();
        fitFileNavigation.setFileList(filenames);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        panel = new javax.swing.JPanel();
        fitBean = new uk.ac.le.sppg.superdarn.fitDataBeans.FitDataBean();
        fitFileNavigation = fitFileNavigation = new uk.ac.le.sppg.superdarn.fitDataBeans.FitFileNavigationBean(fitBean);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        panel.setLayout(new java.awt.BorderLayout());

        panel.add(fitBean, java.awt.BorderLayout.CENTER);

        panel.add(fitFileNavigation, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        final String[] strings = args;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FitFileBrowser(strings).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private uk.ac.le.sppg.superdarn.fitDataBeans.FitDataBean fitBean;
    private uk.ac.le.sppg.superdarn.fitDataBeans.FitFileNavigationBean fitFileNavigation;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
    
}
