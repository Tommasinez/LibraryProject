package it.betacom.application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import it.betacom.model.Book;

public class GetBookListAndCreatePDF {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/library_db";
		String user = "root";
		String password = "";

		Book book = new Book();
		ArrayList<Book> bookList = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(url, user, password)) {
			String query = "SELECT * FROM book";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);

			while (result.next()) {
				book.setId(result.getInt("id"));
				book.setTitle(result.getString("title"));
				book.setAuthor(result.getString("author"));
				book.setGenre(result.getString("genre"));
				book.setPublisher(result.getString("publisher"));
				book.setPages(result.getInt("pages"));
				book.setPublicationYear(result.getInt("publication_year"));
				bookList.add(book);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		System.out.println(bookList);

		try {
			Document document = new Document();
			OutputStream output = new FileOutputStream("books.pdf");
			PdfWriter.getInstance(document, output);
			document.open();
			document.add(new Paragraph(bookList + "\n"));
			document.close();
			output.close();
		} catch (DocumentException | IOException exception) {
			exception.printStackTrace();
		}
	}
}
