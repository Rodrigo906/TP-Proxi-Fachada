package ar.unrn.tp7.inciso2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBC implements DBFacade {

	private Connection conn;

	@Override
	public void open() {
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "";
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new RuntimeException("Error al establecer conexion", e);
		}
	}

	@Override
	public List<Map<String, String>> queryResultAsAsociation(String sql) {
		this.open();
		Map<String, String> fila = new HashMap<String, String>();
		List<Map<String, String>> datos = new ArrayList<Map<String, String>>();
		PreparedStatement statement;
		try {

			statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			ResultSetMetaData resultMetaData = result.getMetaData(); // para obtener metadatos

			while (result.next()) {
				fila = new HashMap<String, String>();
				for (int i = 1; i <= resultMetaData.getColumnCount(); i++) {
					fila.put(resultMetaData.getColumnName(i), result.getString(i));
				}
				datos.add(fila);
			}

			result.close();
			this.close();
		} catch (SQLException e) {
			throw new RuntimeException("Error de consulta", e);
		}

		return datos;
	}

	@Override
	public List<String[]> queryResultAsArray(String sql) {
		this.open();
		List<String[]> datos = new ArrayList<String[]>();
		String fila[] = null;
		PreparedStatement statement;

		try {
			statement = conn.prepareStatement(sql);

			ResultSet result = statement.executeQuery();
			ResultSetMetaData resultMetaData = result.getMetaData();

			while (result.next()) {
				fila = new String[resultMetaData.getColumnCount()];
				for (int i = 0; i < resultMetaData.getColumnCount(); i++) {
					fila[i] = result.getString(i + 1);
				}
				datos.add(fila);
			}
			result.close();
			this.close();
		} catch (SQLException e) {
			throw new RuntimeException("Error de consulta", e);
		}
		return datos;
	}

	@Override
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException("Error al desconectar", e);
		}

	}

}
