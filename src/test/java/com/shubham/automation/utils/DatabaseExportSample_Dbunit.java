package com.shubham.automation.utils;

import java.io.FileOutputStream;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import com.mysql.jdbc.Connection;

public class DatabaseExportSample_Dbunit {
//	final static String _url  = "jdbc:mysql://172.28.146.165:3306/sakurai";
  final static String _url  = "jdbc:mysql://172.28.146.165:3306/sakurai_automation?zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8";
	public static void main(String[] args) throws Exception
    {
        // database connection
        Class driverClass = Class.forName("com.mysql.jdbc.Driver");
        Connection jdbcConnection = (Connection) DriverManager.getConnection(
                _url, "automation_user", "automationpass");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN , "[ORDER]");
       // connection.getConfig().setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, "\"?\"");
        // partial database export
//        QueryDataSet partialDataSet = new QueryDataSet(connection);
//        System.out.println("hi");
//        partialDataSet.addTable("questions", "SELECT question_text from questions");
//       // partialDataSet.addTable("questions");
//        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("partial.sql"));

//         full database export
        IDataSet fullDataSet = connection.createDataSet();
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
//        
        // dependent tables database export: export table X and all tables that
        // have a PK which is a FK on X, in the right order for insertion
//        String[] depTableNames = 
//          TablesDependencyHelper.getAllDependentTables( connection, "X" );
//        IDataSet depDataset = connection.createDataSet( depTableNames );
//        FlatXmlDataSet.write(depDataset, new FileOutputStream("dependents.xml"));          
        
    }
}
