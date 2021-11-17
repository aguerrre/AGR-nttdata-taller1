package com.nttdata.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * NTTData - JDBC - Taller 1
 * 
 * @author aguerrre
 *
 */
public class NttDataJDBCMain {
	/**
	 * Método main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Conexión y consulta a la Base de Datos de Oracle creada con el script.
		stablishODBConnection();
	}

	/**
	 * Establece la conexión con la Base de Datos de Oracle
	 */
	private static void stablishODBConnection() {

		try {
			// Se establece el driver de conexión a BBDD.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// Se abre la conexión con la Base de Datos (URL / Usuario / Contraseña).
			final Connection dbConn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xepdb1", "sys as SYSDBA", "rootroot");
			
			// Realización de consulta.
			final Statement st = dbConn.createStatement();
			final String query = "SELECT B.TITLE AS TITULO, CONCAT(CONCAT(A.NAME, ' '), A.SURNAME) AS AUTOR, B.YEAR AS AÑO FROM NTTDATA_ORACLE_AUTHORS A JOIN NTTDATA_ORACLE_BOOKS B ON A.ID = B.ID_AUTHOR WHERE B.YEAR >= 1950";
			final ResultSet rs = st.executeQuery(query);
			
			StringBuilder info = new StringBuilder();
			// Iteración de resultados.
			while(rs.next()) {
				
				info.append("Libro: ");
				info.append(rs.getString("TITULO"));
				
				info.append(" | Autor: ");
				info.append(rs.getString("AUTOR"));
				
				info.append(" | Año Publicación: ");
				info.append(rs.getString("AÑO"));
				info.append("\n");
				
			}
			
			// Descripción resultados de la query.
			System.out.println("Libros con autor y año, escritos después de 1950:\n");
			System.out.println(info.toString());
			
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("[ERROR]: Error en la conexión a la Base de Datos.");
			e.printStackTrace();
		}
		
	}
}
