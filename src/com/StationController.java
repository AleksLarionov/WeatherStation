/****************************************************************
    * Author: Aleksei Larionov 
    * Assignment: 2b
    * Assignment task: Weather Station Simulation
    ****************************************************************/
package com;

/****************************************************************
    * The StationController class has a role of a Presenter in the MVP pattern
    * It creates instances of the Model and the View and call methods from these classes  
    ****************************************************************/

// declaring APIs 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class StationController 
{
    // initilazing Model and View
    private final StationView view;
    private final WeatherStation station;
    
    public StationController()
    {        
        // constructor 
        this.view = new StationView(this);
        this.station = new WeatherStation();     
        this.view.addSimulationListener(new SimulationListener(this.station, this.view));
    } // end of StationController       
    
    // declaring action listeners
    class SimulationListener implements ActionListener
    {
        private final WeatherStation station;
        private final StationView view;
        private boolean continueSimulation;
        private boolean databaseCreated;
        private int numRecords = 0;
       
        public SimulationListener(WeatherStation station, StationView view) 
        {
            continueSimulation = false;
            databaseCreated = false;
            this.station = station;
            this.view = view;
            numRecords = 0;
        }
        
        
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == this.view.getStartSimulationButton())
                startSimulation();
            else if (e.getSource() == this.view.getStopSimulationButton())
                continueSimulation = false;
            else if (e.getSource() == this.view.getShowReadingsInRangeButton())
                showRangeReadings(RangeType.IN_RANGE);
            else if (e.getSource() == this.view.getShowReadingsOutOfRangeButton())
                showRangeReadings(RangeType.OUT_OF_RANGE);
        } 
        
        
        // method showRangeReadings to select all needed readings 
        private void showRangeReadings(RangeType rangeType)
        {
            if (continueSimulation)
            {
                JOptionPane.showMessageDialog(null, "Please, stop the simulation first.");
                return;
            }
            String allData = "AIR data";
            allData += "\n" + station.getRangeReadings(view.getFromTimeTextField().getText(), 
                    view.getToTimeTextField().getText(), "AIR_TEMPERATURE", rangeType);
            allData += "\n";
            allData += "GROUND data";
            allData += "\n" + station.getRangeReadings(view.getFromTimeTextField().getText(), 
                    view.getToTimeTextField().getText(), "GROUND_TEMPERATURE", rangeType);

            view.setDisplayAreaText(allData);

            view.setAirTempAverageTextField("");
            view.setGroundTempAverageTextField("");
            view.setTimeTextField(0);                    
        }
        
        // method to start simulation
        private void startSimulation()
        {
            continueSimulation = true;
            if (! databaseCreated)
            {
                station.doConnect();
                databaseCreated = true;
            }
            
            Thread worker = new Thread()
            {
                @Override
                public void run() 
                {
                    String allData;
                    int temp;
                    do 
                    {                    
                        numRecords++;
                        temp = (int) (Math.random() * 100 + 1);
                        station.executeSqlInsert("GROUND_TEMPERATURE", numRecords, numRecords, temp);
                        temp = (int) (Math.random() * 100 + 1);
                        station.executeSqlInsert("AIR_TEMPERATURE", numRecords, numRecords, temp);
                        allData = "AIR data";
                        allData += "\n" + station.selectSensorData("AIR_TEMPERATURE");
                        allData += "\n";
                        allData += "GROUND data";
                        allData += "\n" + station.selectSensorData("GROUND_TEMPERATURE");
                        view.setDisplayAreaText(allData);
                        
                        view.setAirTempAverageTextField(station.calculateAverageValue("AIR_TEMPERATURE"));
                        view.setGroundTempAverageTextField(station.calculateAverageValue("GROUND_TEMPERATURE"));
                        view.setTimeTextField(numRecords);
                        try 
                        {
                            
                            sleep(1000);
                        } 
                        catch (InterruptedException ex) 
                        {
                            Logger.getLogger(StationController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (!continueSimulation)
                            stop();
                    } while (true);
                }
            };
            worker.start();            
        } // end of startSimulation
    } // end of SimulationListener       
} // end of StationController 