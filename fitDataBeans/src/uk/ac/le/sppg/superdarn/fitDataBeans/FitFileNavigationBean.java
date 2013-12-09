/*
 * FitFileNavigationBean.java
 *
 * Created on 25 January 2005, 10:07
 */

package uk.ac.le.sppg.superdarn.fitDataBeans;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import uk.ac.le.sppg.superdarn.fitData.FitData;
import uk.ac.le.sppg.superdarn.fitData.FitFilesReader;

/**
 *
 * @author  nigel
 */
public class FitFileNavigationBean extends javax.swing.JPanel {
    
    private static final long serialVersionUID = 0x5253505047000029L;
    
    /* ImageFilter.java is a 1.4 example used by FileChooserDemo2.java. */
    public class FitFileFilter extends FileFilter {
        
        //Accept all directories and fit files.
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            
            if ( f.getName().endsWith(".fit") || f.getName().endsWith("fit.gz")) {
                return true;
            }
            return false;
        }
        
        //The description of this filter
        public String getDescription() {
            return "Fit files";
        }
        
    }
    /** Creates new form BeanForm */
    public FitFileNavigationBean() {
        initComponents();
        fitDisplay = new FitDataBean();
        
    }
    
    public FitFileNavigationBean(FitDataBean display) {
        initComponents();
        fitDisplay = display;
    }
    
    File[] fileList;
    FitDataBean fitDisplay;
    FitFilesReader reader;
    FitFileFilter fileFilter = new FitFileFilter();
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        fileChooser = new javax.swing.JFileChooser();
        navPanel = new javax.swing.JPanel();
        recordPanel = new javax.swing.JPanel();
        rewindStart = new javax.swing.JButton();
        stepForward = new javax.swing.JButton();
        searchPanel = new javax.swing.JPanel();
        searchTimeField = new javax.swing.JTextField();
        searchTimeButton = new javax.swing.JButton();
        filePanel = new javax.swing.JPanel();
        previousFileButton = new javax.swing.JButton();
        nextFileButton = new javax.swing.JButton();
        selectFilesButton = new javax.swing.JButton();

        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setMultiSelectionEnabled(true);

        setLayout(new java.awt.GridBagLayout());

        navPanel.setLayout(new java.awt.GridBagLayout());

        navPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        recordPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        rewindStart.setText("<<");
        rewindStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindStartActionPerformed(evt);
            }
        });

        recordPanel.add(rewindStart);

        stepForward.setText(">");
        stepForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepForwardActionPerformed(evt);
            }
        });

        recordPanel.add(stepForward);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        navPanel.add(recordPanel, gridBagConstraints);

        searchTimeField.setColumns(10);
        searchPanel.add(searchTimeField);

        searchTimeButton.setText("Locate time");
        searchPanel.add(searchTimeButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        navPanel.add(searchPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(navPanel, gridBagConstraints);

        filePanel.setLayout(new java.awt.GridBagLayout());

        previousFileButton.setText("Prev. file");
        previousFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousFileButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        filePanel.add(previousFileButton, gridBagConstraints);

        nextFileButton.setText("Next file");
        nextFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextFileButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        filePanel.add(nextFileButton, gridBagConstraints);

        selectFilesButton.setText("Select files...");
        selectFilesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFilesButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        filePanel.add(selectFilesButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        add(filePanel, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void rewindStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rewindStartActionPerformed
        try {
            if ( reader != null ) {
                reader.rewind();
                stepForward();
            }
        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, "IOException: "+e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_rewindStartActionPerformed
    
    private void nextFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextFileButtonActionPerformed
        if ( reader != null ) {
            try {
                reader.openNext();
                stepForward();
            } catch(IOException e) {
                JOptionPane.showMessageDialog(this, "IOException: "+e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }//GEN-LAST:event_nextFileButtonActionPerformed
    
    private void previousFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousFileButtonActionPerformed
        if ( reader != null ) {
            try {
                reader.openPrevious();
                stepForward();
            } catch(IOException e) {
                JOptionPane.showMessageDialog(this, "IOException: "+e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }//GEN-LAST:event_previousFileButtonActionPerformed
    
    private void stepForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepForwardActionPerformed
        stepForward();
    }//GEN-LAST:event_stepForwardActionPerformed
    
    private void selectFilesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectFilesButtonActionPerformed
        int returnVal = fileChooser.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            setFileList(files);
        }
    }//GEN-LAST:event_selectFilesButtonActionPerformed
    
    /**
     * Getter for property fileList.
     * @return Value of property fileList.
     */
    public File[] getFileList() {
        return this.fileList;
    }
    
    /**
     * Setter for property fileList.
     * @param fileList New value of property fileList.
     */
    public void setFileList(File[] fileList) {
        this.fileList = fileList;
        
        if ( fileList != null ) {
            if ( reader != null ) {
                try {
                    reader.close();
                } catch(IOException e) {
                    JOptionPane.showMessageDialog(this, "Error closing current file: "+e.getMessage(), "Close file error", JOptionPane.ERROR_MESSAGE);
                }
            }
            reader = new FitFilesReader(fileList);
            stepForward();
        }
    }
    
    public void setFileList(String[] fileList) {
        File[] files = new File[fileList.length];
        for(int i=0; i<files.length; i++) {
            files[i] = new File(fileList[i]);
        }
        
        setFileList(files);
    }
    /**
     * Getter for property fitDisplay.
     * @return Value of property fitDisplay.
     */
    public FitDataBean getFitDisplay() {
        return fitDisplay;
    }
    
    /**
     * Setter for property fitDisplay.
     * @param fitDisplay New value of property fitDisplay.
     */
    public void setFitDisplay(FitDataBean fitDisplay) {
        this.fitDisplay = fitDisplay;
    }
    
    /**
     * Getter for property reader.
     * @return Value of property reader.
     */
    public FitFilesReader getReader() {
        return reader;
    }
    
    /**
     * Setter for property reader.
     * @param reader New value of property reader.
     */
    public void setReader(FitFilesReader reader) {
        this.reader = reader;
    }
    
    public void stepForward() {
        if ( reader != null ) {
            try {
                final FitData fit = reader.next();
                if ( SwingUtilities.isEventDispatchThread() ) {
                    fitDisplay.setData(fit);
                }
                else {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            fitDisplay.setData(fit);
                        }
                    });

                }
            } catch(IOException e) {
                JOptionPane.showMessageDialog(this, "IOException: "+e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
            } catch(DataFormatException e) {
                JOptionPane.showMessageDialog(this, "DataFormatException: "+e.getMessage(), "DataFormatException", JOptionPane.ERROR_MESSAGE);
            }
        }        
    }    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JPanel filePanel;
    private javax.swing.JPanel navPanel;
    private javax.swing.JButton nextFileButton;
    private javax.swing.JButton previousFileButton;
    private javax.swing.JPanel recordPanel;
    private javax.swing.JButton rewindStart;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JButton searchTimeButton;
    private javax.swing.JTextField searchTimeField;
    private javax.swing.JButton selectFilesButton;
    private javax.swing.JButton stepForward;
    // End of variables declaration//GEN-END:variables
    
}
