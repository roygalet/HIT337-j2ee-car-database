/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author roygalet
 */
public interface Catalog {
    
    public Car getCar(int ID) throws SQLException;
    
    public List<Car> getAllCars() throws Exception;
    
    public List<Car> getAllCarsForUser(String userName) throws Exception;
    
    public int addCar(String userName, String make, String model, int year) throws Exception;
    
    public void updateCar(int id, String userName, String model, String make, int year) throws Exception;
    
    public void removeCar(int id) throws Exception;
}
