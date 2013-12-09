/*
 * fitFileBrowser.java
 *
 * Created on 25 January 2005, 10:00
 */

package superdarn.fitDataViewers;

/**
 *
 * @author  nigel
 */
public class OldFitFileBrowser extends javax.swing.JDialog {
    
    private static final long serialVersionUID = 0x5253505047000028L;
    
    /** Creates new form fitFileBrowser */
    public OldFitFileBrowser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        fitFileNavigationBean.setFitDisplay(fitBean);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        fitBean = new fitDataViewers.FitBean();
        fitFileNavigationBean = new fitDataViewers.FitFileNavigationBean();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().add(fitBean, java.awt.BorderLayout.CENTER);

        getContentPane().add(fitFileNavigationBean, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new OldFitFileBrowser(new javax.swing.JFrame(), true).setVisible(true);
        
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private fitDataViewers.FitBean fitBean;
    private fitDataViewers.FitFileNavigationBean fitFileNavigationBean;
    // End of variables declaration//GEN-END:variables
    
}
