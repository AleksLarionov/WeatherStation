/****************************************************************
    * Author: Aleksei Larionov 
    * Assignment: 2b
    * Assignment task: Weather Station Simulation
    ****************************************************************/

package com;

/****************************************************************
    * The Weather Station class has a role of a Model in the MVP pattern
    * It contains methods only business logic, methods to connect with database and select
    * records from the DB. 
    ****************************************************************/

// importing APIs for the assignment tasks
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JOptionPane;

// declaring Weather Station class
public class WeatherStation 
{
    // initializing parameters of the class
    private Scanner input;
    private final String dbURL = "jdbc:derby:SensorData;create=true;user=admin;password=password";
    private final String dbDriverName = "org.apache.derby.jdbc.EmbeddedDriver";
    private final String sqlDelimiter = ";";
    private Connection dbConnection = null;
    private Statement sqlStatement = null;
    private ArrayList<String> sqlCommandList;

    // connect with the DB
    public void doConnect() 
    {
        getSql();
        openDBConnection();
        executeSqlCreate();        
    }
        
    /****************************************************************
    * Function name: openDBConnection
    * Purpose      : set the DB connection
    ****************************************************************/    
    private void openDBConnection()
    {
        deleteFolder(new File("SensorData"));        
        try
        {
            Class.forName(dbDriverName);
            //Get a connection
            dbConnection = DriverManager.getConnection(dbURL); 
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Could not connect to database.");
            e.printStackTrace();
        }
    }// end of openDBConnectionmethod

    /****************************************************************
    * Function name: executeSqlCreate
    * Purpose      : execution of sql queries 
    ****************************************************************/    
    private void executeSqlCreate()
    {
        try
        {
            sqlStatement = dbConnection.createStatement();
            
            for (String sqlCommand : sqlCommandList)
                sqlStatement.addBatch(sqlCommand);           

            sqlStatement.executeBatch();
            sqlStatement.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }  // end of executeSqlCreate method  

    /****************************************************************
    * Function name: executeSqlInsert
    * Purpose      : adding records into DB tables
    ****************************************************************/    
    public void executeSqlInsert(String tableName, int id, int time, int temperature)
    {
        try
        {
            sqlStatement = dbConnection.createStatement();
            
            sqlStatement.addBatch("INSERT INTO " + tableName 
                    + " VALUES (" 
                    + id + ", "
                    + time + ", " 
                    + temperature + ")");           

            sqlStatement.executeBatch();
            sqlStatement.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    } // end of method executeSqlInsert   
    
    /****************************************************************
    * Function name: deleteFolder
    * Purpose      : deleting folder with data
    ****************************************************************/ 
    private void deleteFolder(File folder) 
    {
        File[] files = folder.listFiles();
        if(files!=null) 
            for(File f: files) 
                if(f.isDirectory()) 
                    deleteFolder(f);
                else 
                    f.delete();
        folder.delete();
    }    

    /****************************************************************
    * Function name: selectSensorData
    * Purpose      : executing queries to select records in and out of range
    ****************************************************************/    
    public String selectSensorData(String tableName)
    {
        String output = "";
        try
        {
            sqlStatement = dbConnection.createStatement();
            ResultSet results = sqlStatement.executeQuery("select * from " + tableName + " order by 2, 3");
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names                
                output = output.concat(rsmd.getColumnLabel(i)+"\t");  
            }

            output = output.concat("\n----------------------------------------------------------------------------\n");
            // open cycle 
            while(results.next())
            {
                int id = results.getInt(1);
                int time = results.getInt(2);
                int temperature = results.getInt(3);
                output = output.concat(id + "\t" + time + "\t" + temperature + "\n");
            } // end of cycle
                        
            results.close();
            sqlStatement.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
        
        return output;
    }  // end of selectSensorData method  
    
    /****************************************************************
    * Function name: calculateAverageValue
    * Purpose      : calculating average values 
    ****************************************************************/    
    public String calculateAverageValue(String tableName)
    {
        double totalTemp = 0;
        int numTemp = 0;
        
        try
        {
            sqlStatement = dbConnection.createStatement();
            ResultSet results = sqlStatement.executeQuery("select * from " + tableName);

            int temperature;
            while(results.next())
            {
                temperature = results.getInt(3);
                totalTemp += temperature;
                numTemp += 1;
            }           
            
            results.close();
            sqlStatement.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
        return Double.toString(Math.round(totalTemp/numTemp));
    } // end of calculateAverageValue
    
    /****************************************************************
    * Function name: closeDBConnection
    * Purpose      : closing DB connection
    ****************************************************************/    
    private void closeDBConnection()
    {
        try
        {
            if (sqlStatement != null)
            {
                sqlStatement.close();
            }
            if (dbConnection != null)
            {
                //DriverManager.getConnection(dbURL + ";shutdown=true");
                dbConnection.close();
            }           
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Could not disconnect from database.");
            e.printStackTrace();
        }
    } // end of closeDBConnection method
    
    /****************************************************************
    * Function name: getSql
    * Purpose      : open and read data from DB
    ****************************************************************/    
    private void getSql()
    {
        openSqlFile();
        readSql(); 
        closeSqlFile();    
    }
    
    /****************************************************************
    * Function name: openSqlFile
    * Inputs       : none
    * Outputs      : none
    * Purpose      : open Input File
    ****************************************************************/    
    private void openSqlFile()
    {
        File file = new File("data.sql");
        try
        {
            input = new Scanner(file);
        } 
        catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "SQL file could not be opened.");
            System.exit(1);
        }
    } // end method openSqlFile  
    
    /****************************************************************
    * Function name: readSql
    * Inputs       : none
    * Outputs      : none
    * Purpose      : read SQL from input file
    ****************************************************************/
    private void readSql()
    {
        String line;        
        sqlCommandList = new ArrayList<>();   
        input.useDelimiter(sqlDelimiter);        
        
        try // read records from file using Scanner object
        {
            while (input.hasNext()) // while there are more lines to read
            {
                line = input.next().trim();                
                sqlCommandList.add(line);
            } // end while            
        } // end try
        catch (NoSuchElementException elementException)
        {
            JOptionPane.showMessageDialog(null, "SQL file improperly formed.");
            input.close();
            System.exit(1);
        } // end catch
        catch (IllegalStateException stateException)
        {
            JOptionPane.showMessageDialog(null, "Error reading from SQL file.");
            System.exit(1);
        } // end catch
    } // end method readSql

    /****************************************************************
    * Function name: closeSqlFile
    * Inputs       : none
    * Outputs      : none
    * Purpose      : close Input File
    ****************************************************************/
    private void closeSqlFile()
    {
        if (input != null) // if the file is actually open
            input.close(); // close file
    } // end method closeSqlFile

    /****************************************************************
    * Function name: getRangeReadings
    * Inputs       : none
    * Outputs      : none
    * Purpose      : select readings from range
    ****************************************************************/    
    public String getRangeReadings(String fromTimeString,String toTimeString, String tableName, RangeType rangeType)
    {
        String output = "";
        int fromTime = Integer.parseInt(fromTimeString);
        int toTime = Integer.parseInt(toTimeString);
        
        // exception throw
        try
        {
            sqlStatement = dbConnection.createStatement();
            ResultSet results = sqlStatement.executeQuery("select * from " + tableName + " order by 2, 3");
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names                
                output = output.concat(rsmd.getColumnLabel(i)+"\t");  
            }

            output = output.concat("\n----------------------------------------------------------------------------\n");

            // start a cycle to get all records
            while(results.next())
            {
                int id = results.getInt(1);
                int time = results.getInt(2);
                int temperature = results.getInt(3);
                if (rangeType == RangeType.IN_RANGE)
                {
                    if ( time >= fromTime && time <= toTime)
                        output = output.concat(id + "\t" + time + "\t" + temperature + "\n");
                }
                else if (rangeType == RangeType.OUT_OF_RANGE)
                {
                    if ( time < fromTime || time > toTime)
                        output = output.concat(id + "\t" + time + "\t" + temperature + "\n");
                }                
            }
            
            results.close();
            sqlStatement.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
        return output;
    } // end of getRangeReadings       
} // end of Weather Station class