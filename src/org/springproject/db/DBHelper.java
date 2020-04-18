package org.springproject.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.tinylog.Logger;
public class DBHelper{

    private final String url = "jdbc:postgresql://localhost:5432/Moovy";
    private final String user = "postgres";
    private final String password = "monkey";

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            Logger.info("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            Logger.error(e);
        }

        return conn;
    }
    public String getVehiclesById(int id){
    	Connection conn = connect();
    	try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT name FROM public.vehicles v WHERE vid = "+id);
			if (rs.next()) {
				String vname = rs.getString("name");
				return vname;
			}else {
				Logger.warn("No vehicle found of ID []",id);
			}
		} catch (SQLException e) {
			Logger.error(e);
		}
    	return null;
    }
    
    
    public ArrayList<Posts> getPostsByName(String name){
    	Logger.info("getPostsByName method called");
    	Connection conn = connect();
    	ArrayList<Posts> posts = new ArrayList<Posts>();
    	try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select p.name, p.price, p.description, p.picture from posts p, vehicles v where v.name = '"+name+"' and p.vehicle_id = v.vid");
			while (rs.next()) {
				Integer price = rs.getInt("price");
				String postName = rs.getString("name");
				String description = rs.getString("description");
				String picture = rs.getString("picture");
				
				Posts post = new Posts();
				post.setDescrption(description);
				post.setPrice(price);
				post.setPicture(picture);
				post.setName(postName);
				
				posts.add(post);
			}
			return posts;
		} 
    	catch (SQLException e) {
			Logger.error(e);
		}
    	Logger.warn("No results found");
    	return posts;
    }
    
    public int createPost(Posts newPost) {
    	final String sqlInsert = "INSERT INTO public.posts(\n" + 
    			"	name, price, vehicle_id, description, picture)\n" + 
    			"	VALUES (?, ?, ?, ?, ?)";
    	Connection  conn = connect();
    	try {
    		PreparedStatement ps = conn.prepareStatement(sqlInsert);
    		ps.setString(1, newPost.getName());
    		ps.setInt(2, newPost.getPrice());
    		ps.setInt(3, newPost.getVehicle_fk());
    		ps.setString(4, newPost.getDescrption());
    		ps.setString(5, newPost.getPicture());
    		int row = ps.executeUpdate();
    		Logger.info(row);
    		return(newPost.getId());
    	}catch(SQLException e) {
    		Logger.error(e);
    		return 0;
    	}catch(Exception e) {
    		Logger.error(e);
    		return 0;
    	}
    }
    public int getVehiclefk(String name) {
    	final String sql = "SELECT vid FROM public.vehicles where name = (?)";
    	Connection  conn = connect();
    	try {
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, name);
    		 ResultSet rs = ps.executeQuery();
    		 while (rs.next()) {
    			 return(rs.getInt("vid"));
    		 }
    		}catch(SQLException e) {
    			Logger.error(e);
    			return (Integer) null;
    		}catch(Exception e){
    			Logger.error(e);
    			return (Integer) null;
    		}
    	Logger.warn("something went wrong");
		return (Integer) null;
    }
}














