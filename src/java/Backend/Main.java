/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author roygalet
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, Exception{
//        Connection myConnection = DriverManager.getConnection("jdbc:derby://localhost:1527/Assignment1; create=true", "roy", "password");
//        Statement selectStatement = myConnection.createStatement();
//        selectStatement.execute("SELECT * FROM CARS");
//        ResultSet myResults = selectStatement.getResultSet();
//        while (myResults.next()) {            
//            System.out.println(myResults.getString("Make"));
//        }
//        
        Catalog myBackend = new CarBackend("jdbc:derby://localhost:1527/Assignment1; create=true", "roy", "password");
//        Catalog myBackend = new CarBackend("jdbc:mysql://244715.spinetail.cdu.edu.au:3306/s244715_HIT337?zeroDateTimeBehavior=convertToNull", "s244715_roygalet", "password");
        System.out.println(myBackend.addCar("Marco", "Toyota", "FJ Cruiser", 2015));
//        myBackend.updateCar(6, "Ford", "Ranger", 2015);
//        myBackend.removeCar(6);
        List<Car> myList = myBackend.getAllCars();
//        List<Car> myList = myBackend.getAllCarsForUser("Roy");
        for(int index=0; index<myList.size(); index++){
            System.out.println(myList.get(index).getID() + "\t" + myList.get(index).getUserName() + "\t" + myList.get(index).getMake() + "\t" + myList.get(index).getModel() + "\t" + myList.get(index).getYear());
        }
    }
}
