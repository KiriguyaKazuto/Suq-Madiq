package me.is103t4.corendonluggagesystem.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This class handles any interaction with the database. Any database
 * connections should be handled via this class ONLY
 *
 * @author Finn Bon
 */
public class DBHandler {

    public static final DBHandler INSTANCE = new DBHandler();

    private final String host = "sql11.freemysqlhosting.net";
    private final int port = 3306;
    private final String username = "sql11202531";
    private final String password = "MY6gDGml88";
    private final String db = "sql11202531";

    private Connection connection;
    public static final Logger LOGGER = Logger.getLogger(DBHandler.class.
	    getName());

    public void start() {
	open();
	create();
    }

    public Connection getConnection() {
	return connection;
    }

    public void create() {
	String query = "CREATE TABLE IF NOT EXISTS accounts ("
		+ "id INT UNIQUE KEY AUTO_INCREMENT,"
		+ "username VARCHAR(4) NOT NULL,"
		+ "code INT(4) NOT NULL,"
		+ "password VARCHAR(128) NOT NULL,"
		+ "email VARCHAR(128) NOT NULL,"
		+ "role INT(1) NOT NULL)";
	try (PreparedStatement preparedStatement = connection.
		prepareStatement(query)) {
	    PreparingStatement preparingStatement = new PreparingStatement(preparedStatement);

	    preparingStatement.ps.executeUpdate();
	} catch (SQLException ex) {
	    Logger.getLogger(DBHandler.class.getName()).
		    log(Level.SEVERE, null, ex);
	}
    }
    
    public void open() {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    
	    String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
	    connection = DriverManager.getConnection(url, username, password);
	} catch (SQLException | ClassNotFoundException ex) {
	    LOGGER.log(Level.SEVERE, null, ex);
	}
    }

    public static class PreparingStatement {

	private PreparedStatement ps;

	public PreparingStatement(PreparedStatement ps) {
	    this.ps = ps;
	}

	public PreparingStatement setString(int i, String msg) {
	    try {
		ps.setString(i, msg);
	    } catch (SQLException ex) {
		LOGGER.log(Level.SEVERE, null, ex);
	    }
	    return this;
	}

	public PreparingStatement setDate(int i, Date date) {
	    try {
		ps.setDate(i, date);
	    } catch (SQLException ex) {
		LOGGER.log(Level.SEVERE, null, ex);
	    }
	    return this;
	}

	public PreparingStatement setFloat(int i, float f) {
	    try {
		ps.setFloat(i, f);
	    } catch (SQLException ex) {
		LOGGER.log(Level.SEVERE, null, ex);
	    }
	    return this;
	}

	public PreparingStatement setInt(int i, int j) {
	    try {
		ps.setInt(i, j);
	    } catch (SQLException ex) {
		LOGGER.log(Level.SEVERE, null, ex);
	    }
	    return this;
	}

	public PreparingStatement setLong(int i, long l) {
	    try {
		ps.setLong(i, l);
	    } catch (SQLException ex) {
		LOGGER.log(Level.SEVERE, null, ex);
	    }
	    return this;
	}

    }

    public static interface PlaceholderSetter {

	void replace(PreparingStatement ps);
    }

}
