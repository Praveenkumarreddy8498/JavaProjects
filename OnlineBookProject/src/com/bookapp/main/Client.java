package com.bookapp.main;

import com.bookapp.bean.*;
import com.bookapp.exception.*;
import com.bookapp.service.*;

import java.util.List;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		BookInter bookInter = new BookImpl();
		Book book = new Book("Art of war", "sun tzu", "war", 1, 150);
		Book book1 = new Book("As a Man Thinketh ", "James allen", "Philisophy", 2, 120);
		bookInter.addBook(book);
		bookInter.addBook(book1);

		Scanner sc = new Scanner(System.in);

		Book book2 = new Book();

		System.out.println("Enter no of books to add");
		int num = sc.nextInt();
		for (int i = 0; i < num; i++) {
			try {
				System.out.println("Enter in order -->Title , author , category , id , price");
				sc.nextLine();
				String title = sc.nextLine();
				book2.setTitle(title);
				String author = sc.nextLine();
				book2.setAuthor(author);
				String category = sc.nextLine();
				book2.setCategory(category);
				int id = sc.nextInt();
				book2.setBookid(id);
				int price = sc.nextInt();
				book2.setPrice(price);
				bookInter.addBook(book2);
				System.out.println("book added");

			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		System.out.println(
				"Enter your choice\n" + "1.Get Book by Author\n" + "2.Get Book by Cagegory\n" + "3.Get all Books\n");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			try {
				System.out.println("search book based on Author");
				sc.nextLine();
				String auth = sc.nextLine();
				System.out.println(bookInter.getBookbyAuthor(auth));
			} catch (AuthorNotFoundException e) {
				System.out.println(e.getMessage());
			}

			break;

		case 2:
			try {
				System.out.println("search book based on category");
				sc.nextLine();
				String ctgry = sc.nextLine();
				List<Book> bookList=bookInter.getBookbycategory(ctgry);
				bookList.forEach(System.out::println);
			} catch (CategoryNotFoundException e) {
				System.out.println(e.getMessage());
			}

			break;
		case 3:
			List<Book> bookList=bookInter.getAllBooks();
			bookList.forEach(System.out::println);
			break;

		default:
			System.out.println("Wrong Choice");
			break;
		}

		sc.close();

	}

}
