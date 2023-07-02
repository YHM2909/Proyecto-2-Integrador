package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.models.Alumno;
import com.example.plataforma_cerebritos.utils.DatabaseUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class CredentialRepository {
    public Alumno authenticate(String usuario, String password) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM alumno WHERE usuario = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, usuario);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Alumno alumno = new Alumno(
                                resultSet.getInt("idAlumno"),
                                resultSet.getInt("idUniversidad"),
                                resultSet.getString("password"),
                                resultSet.getString("nombres"),
                                resultSet.getString("dni"),
                                resultSet.getString("correo"),
                                resultSet.getString("residencia"),
                                resultSet.getString("usuario")
                        );
                        return alumno;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

