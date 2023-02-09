/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bs.todoapp.controller;

import com.bs.todoapp.model.Project;
import com.bs.todoapp.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bruno
 */
public class ProjectController {
    
    public void save(Project project){
        String sql = "INSERT INTO projects "
                + "(name, "
                + "description, "
                + "createdAt, "
                + "updatedAt) "
                + "VALUES (?, ?, ?, ?)";
        
        Connection c = null; 
        PreparedStatement statement = null;
        
        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.execute();
        } catch (Exception e){
            throw new RuntimeException("Error saving project!");
        } finally {
            ConnectionFactory.closeConnection(c, statement);
        }
    }
    
    public void update(Project project) {
        String sql = "UPDATE projects SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";
        
        Connection c = null; 
        PreparedStatement statement = null; 
        
        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Error updating project!");
        } finally {
            ConnectionFactory.closeConnection(c, statement);
        }        
    }
    
    public void removeById(int projectId) {
        String sql = "DELETE FROM projects WHERE id = ?"; 
        
        Connection c = null;
        PreparedStatement statement = null;
        
        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
            statement.setInt(1, projectId);
            statement.execute();
        } catch (Exception e){
            throw new RuntimeException("Error removing project!");
        } finally {
            ConnectionFactory.closeConnection(c, statement);
        }
    }
    
    public List<Project> getAll(){
        String sql = "SELECT * FROM projects";
        
        Connection c = null;
        PreparedStatement statement = null; 
        ResultSet result = null; 
        
        List<Project> projects = new ArrayList<Project>();
        
        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);         
            result = statement.executeQuery();
            
            while(result.next()){
                Project project = new Project();
                
                project.setId(result.getInt("id"));
                project.setName(result.getString("name"));
                project.setDescription(result.getString("description"));
                project.setCreatedAt(result.getDate("createdAt"));
                project.setUpdatedAt(result.getDate("updatedAt"));
                
                projects.add(project);
            }
        } catch (Exception e){
            throw new RuntimeException("Error getting all projects!");
        } finally {
            ConnectionFactory.closeConnection(c, statement, result);
        }
        
        return projects;
    }
    
}
