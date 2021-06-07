package ar.unrn.tp7.inciso2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {

		List<String[]> listaArreglos = new ArrayList<String[]>();
		List<Map<String, String>> listaMap = new ArrayList<Map<String, String>>();

		System.out.println("CONSULTA LISTA DE ARRAYS \n");
		DBFacade fachada = new JDBC();
		listaArreglos = fachada.queryResultAsArray(
				"select p.nombre,t.numero from personas p JOIN telefonos t ON (p.id = t.idpersona)");
		mostrarListadoArray(listaArreglos);
		System.out.println();

		System.out.println("CONSULTA LISTA DE MAPS\n");
		listaMap = fachada.queryResultAsAsociation(
				"select p.nombre,t.numero from personas p JOIN telefonos t ON (p.id = t.idpersona) ");
		mostrarListadoMap(listaMap);
	}

	public static void mostrarListadoArray(List<String[]> lista) {
		String fila[] = null;
		for (int i = 0; i < lista.size(); i++) {
			fila = lista.get(i);
			for (int t = 0; t < fila.length; t++) {
				System.out.print(fila[t] + " ");
			}
			System.out.println();
		}
	}

	public static void mostrarListadoMap(List<Map<String, String>> listaMap) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < listaMap.size(); i++) {
			map = listaMap.get(i);
			map.forEach((k, v) -> System.out.println(k + " " + v));
			System.out.println();
		}

	}

}
