package database_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import database_system.exceptions.IllegalMemberException;
import entities.user_and_admin.User;

public abstract class AbstractDataBase<T>{

    public  ArrayList<T> list = new ArrayList<T>();
    public  ArrayList<String> nameList = new ArrayList<>();

    public AbstractDataBase(){

    }



    public void add(T obj) throws IllegalMemberException {

        list.add(obj);
    }


    public T getMemberByIndex(int index){
        
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

        // This method will return user
    public T getMember(String name) {
    
        return list.get(nameList.indexOf(name));
    }

    // This method removes user from user_list by its username
    public void remove(String name) {
        if (contains(name)) {
    
            list.remove(list.get(nameList.indexOf(name)));
        }
    
        System.out.println("There is no such object in the list");
    }
    
    public boolean contains(String name) {
        return nameList.contains(name) ? true : false;
    }
}