package com.bookapp.main;

import com.bookapp.dao.BookImpl;

import com.bookapp.dao.BookInter;
import com.bookapp.exception.*;
import java.util.*;
import com.bookapp.bean.Book;

public class Client {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BookInter bookInter = new BookImpl();
		Book book = new Book();
		System.out.println("Enter your choice\n" + "1.Add a Book\n" + "2.Delete a book\n" + "3.Update a book\n"
				+ "4.Get book by Bookid\n" + "5.Get book by Author\n" + "6.Get book by Category\n" + "7.Get all books");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			System.out.println("Enter no of books to add");
			int num = sc.nextInt();
			for (int i = 0; i < num; i++) {
				System.out.println("Enter in order -->Title , author , category , id , price");
				sc.nextLine();

				try {
					String title = sc.nextLine();
					book.setTitle(title);
					String author = sc.nextLine();
					book.setAuthor(author);
					String category = sc.nextLine();
					book.setCategory(category);
					int id = sc.nextInt();
					book.setBookid(id);
					int price = sc.nextInt();
					book.setPrice(price);
					bookInter.addBook(book);
					System.out.println("Book Added");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;

		case 2:
			try {
				System.out.println("Enter Bookid to delete");
				int bookId = sc.nextInt();
				boolean res = bookInter.deleteBook(bookId);
				System.out.println(res);
			} catch (BookNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				System.out.println("To update a book,enter Bookid,price");
				int bookId = sc.nextInt();
				int price = sc.nextInt();
				boolean res = bookInter.updateBook(bookId, price);
				System.out.println(res);
			} catch (BookNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			try {
				System.out.println("Search Book Based on Bookid");
				int bookId = sc.nextInt();
				System.out.println(bookInter.getBookById(bookId));

			} catch (BookNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 5:
			try {
				System.out.println("Enter Author Name");
				sc.nextLine();
				String author = sc.nextLine();
				List<Book> booklist = bookInter.getBookbyAuthor(author);
				booklist.forEach(System.out::println);

			} catch (AuthorNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 6:
			try {
				System.out.println("Enter Category");
				sc.nextLine();
				String category = sc.nextLine();
				List<Book> booklist = bookInter.getBookbycategory(category);
				booklist.forEach(System.out::println);

			} catch (CategoryNotFoundException e) {
				e.printStackTrace();
			}

			break;
		case 7:
			System.out.println("getting all the books...");
			List<Book> booklist = bookInter.getAllBooks();
			Collections.sort(booklist, (Book b1, Book b2) -> {
				return b1.getTitle().compareTo(b2.getTitle());
			});
			booklist.forEach(System.out::println);

			sc.close();
			break;

		default:
			System.out.println("wrong choice");
			break;

		}

	}

}
