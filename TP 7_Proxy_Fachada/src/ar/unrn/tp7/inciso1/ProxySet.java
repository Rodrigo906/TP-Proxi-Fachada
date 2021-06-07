package ar.unrn.tp7.inciso1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ProxySet implements Set<Telefono> {

	private String url;
	private String user;
	private String password;
	private int idPersona;

	public ProxySet(String url, String user, String password, int idPersona) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.idPersona = idPersona;
	}

	private Connection obtenerConexion() {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException("Error al establecer conexion", e);
		}
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		String sql = "SELECT t.numero " + "FROM telefonos t " + "WHERE t.idpersona = ?";

		try (Connection conn = obtenerConexion(); PreparedStatement statement = conn.prepareStatement(sql);) {
			statement.setInt(1, this.idPersona);

			ResultSet result = statement.executeQuery();

			int i = 0;
			while (result.next()) {
				a[i] = (T) new Telefono(result.getString(1));
				i++;
			}
			result.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return a;
	}

	@Override
	public int size() {

		String sql = "SELECT COUNT(t.numero) " + "FROM telefonos t " + "WHERE t.idpersona = ?";
		int size = 0;
		try (Connection conn = obtenerConexion(); PreparedStatement statement = conn.prepareStatement(sql);) {
			statement.setInt(1, this.idPersona);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				size = result.getInt(1);
			}

			result.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return size;

	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Telefono> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Telefono e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Telefono> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
