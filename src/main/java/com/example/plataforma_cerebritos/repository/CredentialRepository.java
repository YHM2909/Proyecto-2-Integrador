package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.utils.DatabaseUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CredentialRepository {
    public boolean authenticate(String usuario, String password) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT COUNT(*) FROM alumno WHERE usuario = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, usuario);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next() && resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
