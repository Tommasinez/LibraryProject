package it.betacom.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.betacom.model.Book;

public class GetItalianAuthorBookList {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/library_db";
		String user = "root";
		String password = "";
		
		Book newBook = new Book();
		ArrayList<Book> bookList = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "CALL get_italian_author_books()";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				newBook.setTitle(result.getString(1));
				bookList.add(newBook);
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		bookList.forEach(book -> {
			System.out.println(book.getTitle());
		});
	}
}
