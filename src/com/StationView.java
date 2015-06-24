/****************************************************************
    * Author: Aleksei Larionov 
    * Assignment: 2b
    * Assignment task: Weather Station Simulation
    ****************************************************************/

package com;

/****************************************************************
    * The Station View class has a role of a Presenter in the MVP pattern
    * It contains methods only GUI elements and action listeners
    ****************************************************************/
// importing APIs for the tasks
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class StationView extends javax.swing.JFrame
{
    public StationView(Object parent)
    {
        initComponents();
        setVisible(true);        
    }

   
    // action initializing listener for all buttons 
    public void addSimulationListener(ActionListener SimulationListener)
    {
        startSimulationButton.addActionListener(SimulationListener);
        stopSimulationButton.addActionListener(SimulationListener);
        showReadingsInRangeButton.addActionListener(SimulationListener);
        showReadingsOutOfRangeButton.addActionListener(SimulationListener);
    }

   
    // setters and getters to communicate with the Controller class
    
    public JButton getShowReadingsInRangeButton() {
        return showReadingsInRangeButton;
    }

    public JButton getStopSimulationButton() {
        return stopSimulationButton;
    }

    public JButton getShowReadingsOutOfRangeButton() {
        return showReadingsOutOfRangeButton;
    }

    public JButton getStartSimulationButton() {
        return startSimulationButton;
    }

    public JTextArea getDisplayArea() {
        return displayArea;
    }

    public void appendDisplayAreaText(String appendText) {
        this.displayArea.append(appendText);
    }

    public void setDisplayAreaText(String text) {
        this.displayArea.setText(text);
    }

    public void setAirTempAverageTextField(String airTempAverageText) {
        this.airTempAverageTextField.setText(airTempAverageText);
    }

    public void setGroundTempAverageTextField(String groundTempAverageText) {
        this.groundTempAverageTextField.setText(groundTempAverageText);
    }

    public void setTimeTextField(int time) {
        if (time == 0)
            this.timeTextField.setText("");
        else
        {
            String timeText = String.valueOf(time);
            this.timeTextField.setText(timeText);
        }
    }

    public JTextField getFromTimeTextField() {
        return fromTimeTextField;
    }

    public JTextField getToTimeTextField() {
        return toTimeTextField;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        displayArea = new javax.swing.JTextArea();
        startSimulationButton = new javax.swing.JButton();
        airTempAverageTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fromTimeTextField = new javax.swing.JTextField();
        toTimeTextField = new javax.swing.JTextField();
        showReadingsOutOfRangeButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        stopSimulationButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        groundTempAverageTextField = new javax.swing.JTextField();
        showReadingsInRangeButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        timeTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        displayArea.setColumns(20);
        displayArea.setRows(5);
        jScrollPane1.setViewportView(displayArea);

        startSimulationButton.setText("Start Simulation");
        startSimulationButton.setActionCommand("");

        jLabel2.setText("Time range");

        showReadingsOutOfRangeButton.setText("Show Readings out of Range");

        jLabel4.setText("Start");

        jLabel5.setText("Stop");

        stopSimulationButton.setText("Stop Simulation");

        jLabel1.setText("Air Temperature Average");

        jLabel6.setText("Ground Temperature Average");

        showReadingsInRangeButton.setText("Show Readings in Range");

        jLabel3.setText("Seconds elapsed: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fromTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(toTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                                .addComponent(showReadingsOutOfRangeButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showReadingsInRangeButton)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(startSimulationButton)
                            .addComponent(jLabel3))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(timeTextField)
                            .addComponent(stopSimulationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(airTempAverageTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                            .addComponent(groundTempAverageTextField))
                        .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startSimulationButton)
                    .addComponent(airTempAverageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopSimulationButton)
                    .addComponent(jLabel1))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(groundTempAverageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(timeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fromTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showReadingsOutOfRangeButton)
                    .addComponent(showReadingsInRangeButton))
                .addContainerGap())
        );

        startSimulationButton.getAccessibleContext().setAccessibleName("");
        jLabel2.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField airTempAverageTextField;
    private javax.swing.JTextArea displayArea;
    private javax.swing.JTextField fromTimeTextField;
    private javax.swing.JTextField groundTempAverageTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton showReadingsInRangeButton;
    private javax.swing.JButton showReadingsOutOfRangeButton;
    private javax.swing.JButton startSimulationButton;
    private javax.swing.JButton stopSimulationButton;
    private javax.swing.JTextField timeTextField;
    private javax.swing.JTextField toTimeTextField;
    // End of variables declaration//GEN-END:variables
    } // end of StationView class