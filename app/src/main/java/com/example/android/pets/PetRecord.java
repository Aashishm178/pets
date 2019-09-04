package com.example.android.pets;

import java.io.Serializable;

public class PetRecord implements Serializable {

    int ID_Databse;
    String Pet_Name_Database,Pet_Breed_Database,Pet_Height_Database,Pet_Gender_Database,Pet_Weight_Database;

    public PetRecord(int ID_Databse,String Pet_Gender_Database,String Pet_Weight_Database,String Pet_Name_Database,String Pet_Breed_Database,String Pet_Height_Database){
        this.ID_Databse = ID_Databse;
        this.Pet_Gender_Database = Pet_Gender_Database;
        this.Pet_Weight_Database = Pet_Weight_Database;
        this.Pet_Name_Database = Pet_Name_Database;
        this.Pet_Breed_Database = Pet_Breed_Database;
        this.Pet_Height_Database = Pet_Height_Database;
    }

    public PetRecord(){
        //Empty
    }

    public void setPet_Gender_Database(String pet_Gender_Database) {
        Pet_Gender_Database = pet_Gender_Database;
    }

    public void setPet_Weight_Database(String pet_Weight_Database) {
        Pet_Weight_Database = pet_Weight_Database;
    }

    public void setPet_Name_Database(String pet_Name_Database) {
        Pet_Name_Database = pet_Name_Database;
    }

    public void setPet_Breed_Database(String pet_Breed_Database) {
        Pet_Breed_Database = pet_Breed_Database;
    }

    public void setPet_Height_Database(String pet_Height_Database) {
        Pet_Height_Database = pet_Height_Database;
    }

    public void setID_Databse(int ID_Databse) {
        this.ID_Databse = ID_Databse;
    }

    public int getID_Databse()
    {
        return  ID_Databse;
    }

    public  String getPet_Gender_Database()
    {
        return Pet_Gender_Database;
    }

    public  String getPet_Weight_Database()
    {
        return  Pet_Weight_Database;
    }

    public String getPet_Name_Database()
    {
        return  Pet_Name_Database;
    }

    public String getPet_Breed_Database()
    {
        return Pet_Breed_Database;
    }

    public   String getPet_Height_Database()
    {
        return Pet_Height_Database;
    }
}
