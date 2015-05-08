/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roygalet
 */
public class CarBackend implements Catalog {

    private String connectionString;
    private String userName;
    private String password;
    private Connection myConnection;
    private PreparedStatement selectCar;
    private PreparedStatement insertNewCar;
    private PreparedStatement updateCarByID;
    private PreparedStatement deleteCar;
    private PreparedStatement selectAllCars;
    private PreparedStatement selectCarsByUser;
    private List<Car> carList;

    public CarBackend(String connectionString, String userName, String password) {
        this.connectionString = connectionString;
        this.userName = userName;
        this.password = password;

        try {
            myConnection = DriverManager.getConnection(connectionString, userName, password);
        } catch (SQLException ex) {
            Logger.getLogger(CarBackend.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Car getCar(int ID) throws SQLException {
        if (selectCar == null) {
            try {
                this.selectCar = myConnection.prepareStatement("SELECT * FROM Cars WHERE ID=?");
            } catch (SQLException ex) {
                Logger.getLogger(CarBackend.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        selectCar.setInt(1, ID);
        selectCar.execute();
        ResultSet myResults = selectCar.getResultSet();
        if (myResults.next()) {
            return new Car(myResults.getInt("ID"), myResults.getString("UserName"), myResults.getString("Make"), myResults.getString("Model"), myResults.getInt("ProductionYear"));
        } else {
            return null;
        }
    }

    @Override
    public List<Car> getAllCars() throws Exception {
        if(selectAllCars==null){
            selectAllCars = myConnection.prepareStatement("SELECT * FROM Cars");
        }
        selectAllCars.execute();
        ResultSet myResults = selectAllCars.getResultSet();
        carList = new ArrayList<Car>();
        while(myResults.next()){
            carList.add(new Car(myResults.getInt(1), myResults.getString(2), myResults.getString(3),myResults.getString(4), myResults.getInt(5)));
        }
        return carList;
    }

    @Override
    public List<Car> getAllCarsForUser(String userName) throws Exception {
        if(selectCarsByUser==null){
            selectCarsByUser = myConnection.prepareStatement("SELECT * FROM Cars WHERE UserName=?");
        }
        selectCarsByUser.setString(1, userName);
        selectCarsByUser.execute();
        ResultSet myResults = selectCarsByUser.getResultSet();
        carList = new ArrayList<Car>();
        while(myResults.next()){
            carList.add(new Car(myResults.getInt(1), myResults.getString(2), myResults.getString(3),myResults.getString(4), myResults.getInt(5)));
        }
        return carList;
    }

    @Override
    public int addCar(String userName, String make, String model, int year) throws Exception {
        if (insertNewCar == null) {
            try {
                this.insertNewCar = myConnection.prepareStatement("INSERT INTO Cars(UserName,Make,Model,ProductionYear) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            } catch (SQLException ex) {
                Logger.getLogger(CarBackend.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        insertNewCar.setString(1, userName);
        insertNewCar.setString(2, make);
        insertNewCar.setString(3, model);
        insertNewCar.setInt(4, year);
        insertNewCar.execute();
        ResultSet newRow = insertNewCar.getGeneratedKeys();
        newRow.next();
        return newRow.getInt(1);
    }

    @Override
    public void updateCar(int id, String userName, String model, String make, int year) throws Exception {
        if (updateCarByID == null) {
            try {
                this.updateCarByID = myConnection.prepareStatement("UPDATE Cars SET UserName=?, Make=?, Model=?, ProductionYear=? WHERE ID=?");
            } catch (SQLException ex) {
                Logger.getLogger(CarBackend.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        updateCarByID.setString(1, userName);
        updateCarByID.setString(2, make);
        updateCarByID.setString(3, model);
        updateCarByID.setInt(4, year);
        updateCarByID.setInt(5, id);
        updateCarByID.execute();
    }

    @Override
    public void removeCar(int id) throws Exception {
        if (deleteCar == null) {
            try {
                this.deleteCar = myConnection.prepareStatement("DELETE FROM Cars WHERE ID=?");
            } catch (SQLException ex) {
                Logger.getLogger(CarBackend.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        deleteCar.setInt(1, id);
        deleteCar.execute();
    }

}
