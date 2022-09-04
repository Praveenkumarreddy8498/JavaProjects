package com.bookapp.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bookapp.bean.Book;
import com.bookapp.exception.*;

public class BookImpl implements BookInter {
	Connection connection;
	PreparedStatement pstatement;
	Statement statement;
	ResultSet resultset;

	@Override
	public void addBook(Book book) {
		pstatement = null;
		try {
			connection = ModelDAO.openConnection();
			pstatement = connection.prepareStatement("insert into bookdb values(?,?,?,?,?)");
			pstatement.setString(1, book.getTitle());
			pstatement.setString(2, book.getAuthor());
			pstatement.setString(3, book.getCategory());
			pstatement.setInt(4, book.getBookid());
			pstatement.setInt(5, book.getPrice());
			pstatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ModelDAO.closeConnection();
			try {
				if (pstatement != null)
					pstatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public boolean deleteBook(int bookid) throws BookNotFoundException {
		pstatement = null;
		try {
			connection = ModelDAO.openConnection();
			pstatement = connection.prepareStatement("delete from bookdb where bookid=?");
			pstatement.setInt(1, bookid);
			int res = pstatement.executeUpdate();
			if (res == 0)
				throw new BookNotFoundException();
			else
				System.out.println("book with id " + bookid + " is deleted");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ModelDAO.closeConnection();
			try {
				if (pstatement != null)
					pstatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	@Override
	public Book getBookById(int bookid) throws BookNotFoundException {
		Book book = new Book();
		statement = null;
		try {
			connection = ModelDAO.openConnection();
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultset = statement.executeQuery("select * from bookdb where bookid=+'" + bookid + "'");
			resultset.afterLast();
			if (resultset.previous() == false) {
				throw new BookNotFoundException();
			} else {
				do {
					book.setTitle(resultset.getString(1));
					book.setAuthor(resultset.getString(2));
					book.setCategory(resultset.getString(3));
					book.setBookid(resultset.getInt(4));
					book.setPrice(resultset.getInt(5));
				} while (resultset.previous());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ModelDAO.closeConnection();
			try {
				if (statement != null)
					statement.close();
				if (resultset != null)
					resultset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return book;
	}

	@Override
	public boolean updateBook(int bookid, int price) throws BookNotFoundException {
		pstatement = null;
		try {
			connection = ModelDAO.openConnection();
			pstatement = connection.prepareStatement("update bookdb set price=? where bookid=?");
			pstatement.setInt(1, price);
			pstatement.setInt(2, bookid);
			int res = pstatement.executeUpdate();
			if (res == 0)
				throw new BookNotFoundException("Book Not Found");
			else
				System.out.println("Book with id " + bookid + "is updated with price " + price);

		} catch (SQLException | BookNotFoundException e) {
			e.printStackTrace();
		} finally {
			ModelDAO.closeConnection();
			try {
				if (pstatement != null)
					pstatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> booklist = new ArrayList<>();
		pstatement = null;
		resultset = null;
		try {
			connection = ModelDAO.openConnection();
			pstatement = connection.prepareStatement("select * from bookdb");
			resultset = pstatement.executeQuery();
			while (resultset.next()) {
				Book book = new Book();

				book.setTitle(resultset.getString(1));
				book.setAuthor(resultset.getString(2));
				book.setCategory(resultset.getString(3));
				book.setBookid(resultset.getInt(4));
				book.setPrice(resultset.getInt(5));
				booklist.add(book);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ModelDAO.closeConnection();
			try {
				if (pstatement != null)
					pstatement.close();
				if (resultset != null)
					resultset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return booklist;
	}

	@Override
	public List<Book> getBookbyAuthor(String author) throws AuthorNotFoundException {
		List<Book> booklist = new ArrayList<>();
		pstatement = null;
		resultset = null;
		try {
			connection = ModelDAO.openConnection();
			pstatement = connection.prepareStatement("select * from bookdb where author=?");
			pstatement.setString(1, author);
			resultset = pstatement.executeQuery();
			if (resultset.next() == false) {
				throw new AuthorNotFoundException("Author Not Found");
			} else {
				do {
					Book book = new Book();
					book.setTitle(resultset.getString(1));
					book.setAuthor(resultset.getString(2));
					book.setCategory(resultset.getString(3));
					book.setBookid(resultset.getInt(4));
					book.setPrice(resultset.getInt(5));
					booklist.add(book);
				} while (resultset.next());

			}

		} catch (SQLException | AuthorNotFoundException e) {
			e.printStackTrace();
		} finally {
			ModelDAO.closeConnection();
			try {
				if (pstatement != null)
					pstatement.close();
				if (resultset != null)
					resultset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return booklist;
	}

	@Override
	public List<Book> getBookbycategory(String category) throws CategoryNotFoundException {
		List<Book> booklist = new ArrayList<>();
		pstatement = null;
		resultset = null;
		try {
			connection = ModelDAO.openConnection();
			pstatement = connection.prepareStatement("select * from bookdb where category=?");
			pstatement.setString(1, category);
			resultset = pstatement.executeQuery();
			if (resultset.next() == false) {
				throw new CategoryNotFoundException("category not found");
			} else {
				do {
					Book book = new Book();
					book.setTitle(resultset.getString(1));
					book.setAuthor(resultset.getString(2));
					book.setCategory(resultset.getString(3));
					book.setBookid(resultset.getInt(4));
					book.setPrice(resultset.getInt(5));
					booklist.add(book);
				} while (resultset.next());
			}

		} catch (SQLException | CategoryNotFoundException e) {
			e.printStackTrace();
		} finally {
			ModelDAO.closeConnection();
			try {
				if (pstatement != null)
					pstatement.close();
				if (resultset != null)
					resultset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return booklist;
	}

}
