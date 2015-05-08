/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

/**
 *
 * @author roygalet
 */
public class Car {
    private int uniqueID;
    private String userName;
    private String make;
    private String model;
    private int year;
    
    public Car(int newID, String newUserName, String newMake, String newModel, int newYear){
        this.uniqueID = newID;
        this.userName = newUserName;
        this.make = newMake;
        this.model = newModel;
        this.year = newYear;
    }
    
    public int getID(){
        return this.uniqueID;
    }
    
    public String getUserName(){
        return this.userName;
    }
    
    public String getMake(){
        return this.make;
    }
    
    public String getModel(){
        return this.model;
    }
    
    public int getYear(){
        return this.year;
    }
    
    public void setID(int newID){
        this.uniqueID = newID;
    }
    
    public void setUserName(String newUserName){
        this.userName = newUserName;
    }
    
    public void setMake(String newMake){
        this.make = newMake;
    }
    
    public void setModel(String newModel){
        this.model = newModel;
    }
}
