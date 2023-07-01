package com.example.plataforma_cerebritos.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/simulacion_evaluacion";
    private static final String DB_USERNAME = "root123";
    private static final String DB_PASSWORD = "FFmm123456--//";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}