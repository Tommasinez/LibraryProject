package it.betacom.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.betacom.model.Book;

public class CreateBook {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/library_db";
		String user = "root";
		String password = "";

		Book newBook = new Book();
		newBook.setTitle("Odi 2");
		newBook.setAuthor("Manzoni");
		newBook.setGenre("Romanzi");
		newBook.setPublisher("Rizzoli");
		newBook.setPages(267);
		newBook.setPublicationYear(1821);

		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "CALL create_book(?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, newBook.getTitle());
			statement.setString(2, newBook.getAuthor());
			statement.setString(3, newBook.getGenre());
			statement.setString(4, newBook.getPublisher());
			statement.setInt(5, newBook.getPages());
			statement.setInt(6, newBook.getPublicationYear());
			statement.executeUpdate();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "SELECT title, author.name, genre.category, publisher.name, pages FROM book "
					+ "JOIN author ON author.id = book.author "
					+ "JOIN genre ON genre.id = book.genre "
					+ "JOIN publisher ON publisher.id = book.publisher;";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			while (result.next()) {
				System.out.println("Book found!");
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
}
