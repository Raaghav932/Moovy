package org.springproject.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.tinylog.Logger;

/**
 *
 * @author postgresqltutorial.com
 */
public class DBHelper{

    private final String url = "jdbc:postgresql://localhost:5432/Moovy";
    private final String user = "postgres";
    private final String password = "monkey";

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
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
    
    
    public Posts getPostsByName(String name){
    	Connection conn = connect();
    	try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select p.name, p.price, p.description, p.picture from posts p, vehicles v where v.name = '"+name+"' and p.vehicle_id = v.vid");
			if (rs.next()) {
				Integer price = rs.getInt("price");
				String postName = rs.getString("name");
				String description = rs.getString("description");
				String picture = rs.getString("picture");
				Posts post = new Posts();
				post.setDescrption(description);
				post.setPrice(price);
				post.setPicture(picture);
				post.setName(postName);
				return post;
			}else {
				Logger.warn("No post found of name []",name);
			}
		} catch (SQLException e) {
			Logger.error(e);
		}
    	return null;
    }
}