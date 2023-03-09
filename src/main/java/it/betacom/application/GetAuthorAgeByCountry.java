package it.betacom.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetAuthorAgeByCountry {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/library_db";
		String user = "root";
		String password = "";
		ArrayList<String[]> authorList = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "CALL get_author_age_by_country(?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, "Italy");
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				String[] newAuthor = new String[2];
				newAuthor[0] = result.getString("name");
				newAuthor[1] = Integer.toString(result.getInt("age"));
				authorList.add(newAuthor);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		authorList.forEach(author -> {
			System.out.print("Name: " + author[0] + " | ");
			System.out.println("Age: " + author[1]);
		});
	}
}
