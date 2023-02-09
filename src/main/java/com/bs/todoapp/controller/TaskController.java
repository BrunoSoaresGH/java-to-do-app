/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bs.todoapp.controller;

import com.bs.todoapp.model.Task;
import com.bs.todoapp.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author Bruno Soares
 */
public class TaskController {
    
    public void save(Task task){
        String sql = "INSERT INTO tasks "
                + "(idProject, "
                + "name, "
                + "description, " 
                + "observations, "
                + "completed, "
                + "deadline, "
                + "createdAt, "
                + "updatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection c = null;
        PreparedStatement statement = null;
        
        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getObservations());
            statement.setBoolean(5, task.isCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Error saving task!");
        } finally {
            ConnectionFactory.closeConnection(c, statement);           
        }
    }
    
    public void update(Task task){
        String sql = "UPDATE tasks SET "
                + "idProject = ?, "
                + "name  = ?, "
                + "description = ?, "
                + "observations  = ?, "
                + "completed  = ?, "
                + "deadline  = ?, "
                + "createdAt  = ?, "
                + "updatedAt  = ?"
                + "WHERE id = ?";
         
        Connection c = null;
        PreparedStatement statement = null;
        
        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getObservations());
            statement.setBoolean(5, task.isCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            statement.execute();
        } catch(Exception e){
            throw new RuntimeException("Error updating task!");
        } finally {
            ConnectionFactory.closeConnection(c, statement);
        }
    }
    
    public void removeById(int taskId) throws SQLException {
    
        String sql = "DELETE FROM tasks WHERE id = ?";
    
        Connection c = null;
        PreparedStatement statement = null; 
        
        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting task!");
        } finally {
            ConnectionFactory.closeConnection(c, statement);
        }
    }
    
    public List<Task> getAll(int idProject){
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection c = null;
        PreparedStatement statement = null;
        ResultSet result = null; 
        
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
            statement.setInt(1, idProject);
            result = statement.executeQuery();
            
            while(result.next()){
                Task task = new Task();
                task.setId(result.getInt("id"));
                task.setIdProject(result.getInt("idProject"));
                task.setName(result.getString("name"));
                task.setDescription(result.getString("description"));
                task.setObservations(result.getString("observations"));
                task.setCompleted(result.getBoolean("completed"));
                task.setDeadline(result.getDate("deadline"));
                task.setCreatedAt(result.getDate("createdAt"));
                task.setUpdatedAt(result.getDate("updatedAt"));
                
                tasks.add(task);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting all tasks!");
        } finally {
            ConnectionFactory.closeConnection(c, statement, result);
        }      
        return tasks;
    }
    
}
