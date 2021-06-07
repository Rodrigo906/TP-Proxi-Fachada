package ar.unrn.tp7.inciso1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaDao {

	private String url = "jdbc:mysql://localhost:3306/test";;
	private String user = "root";
	private String password = "";

	private Connection obtenerConexion() {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException("Error al establecer conexion", e);
		}

	}

	public Persona personaPorId(int id) {
		String sql = "select p.nombre " + "from personas p " + "where p.id = ?";
		try (Connection conn = obtenerConexion(); PreparedStatement statement = conn.prepareStatement(sql);) {
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();
			String nombrePersona = null;

			while (result.next()) {
				nombrePersona = result.getString(1);
			}

			return new Persona(id, nombrePersona, new ProxySet(url, user, password, id));

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
