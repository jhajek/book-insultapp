package org.openshift;

import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InsultGenerator {
	public String generateInsult() {
		String vowels = "AEIOU";
		String article = "an";
		String theInsult = "";

		try {
			String databaseURL = "jdbc:postgresql://";
			databaseURL += System.getenv("POSTGRESQL_SERVICE_HOST");
			databaseURL += "/" + System.getenv("POSTGRESQL_DATABASE");

			String username = System.getenv("POSTGRESQL_USER");
			String password = System.getenv("PGPASSWORD");
				Connection connection = DriverManager.getConnection(databaseURL, username, password);
			
			if (connection != null) {
				String SQL = "select a.string AS first, b.string AS second, c.string AS noun from short_adjuective a , long_adjective b, noun c ORDER BY random() limit 1";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.exeuteQuery(SQL);
				while (rs.next()) {
					if (vowels.indexOf(rs.getString("first").charAt(0)) == -1) {
						article = "a";
					} // end of if block
					theInsult = String.format("Thou art %s %s %s %s!", article, rs.getString("first"), rs.getString("second"), rs.getString("noun"));
				} // end of while block
				rs.close();
				connection.close();
			} // end of if block
		} // end of try block
		catch (Exception e) {
			return "Database connection problem!";
		} // end of catch block
	return theInstult;
	} //end of generateInsult()
} // end of class
