package com.bookapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.bookapp.bean.Book;
import com.bookapp.exception.*;

public class BookImpl implements BookInter {
	List<Book> bookList = new ArrayList<>();

	@Override
	public void addBook(Book book) {
		bookList.add(book);
	}

	@Override
	public List<Book> getAllBooks() {
		Collections.sort(bookList, (book1, book2) -> {
			return book1.getAuthor().compareToIgnoreCase(book2.getAuthor());

		});
		return bookList;

	}

	@Override
	public List<Book> getBookbyAuthor(String author) throws AuthorNotFoundException {
		List<Book> bookbyAuthor = new ArrayList<>();
		for (Book bk : bookList) {
			if (bk.getAuthor().equalsIgnoreCase(author))
				bookbyAuthor.add(bk);
			
		}
		if (bookbyAuthor.isEmpty())
			throw new AuthorNotFoundException("Author not found");
		else {
			Collections.sort(bookbyAuthor, (book1, book2) -> {
				return book1.getTitle().compareToIgnoreCase(book2.getTitle());

			});

		}
		return bookbyAuthor;

	}

	@Override
	public List<Book> getBookbycategory(String category) throws CategoryNotFoundException {
		List<Book> bookbyCategory = new ArrayList<>();
		for (Book bk : bookList) {
			if (bk.getCategory().equalsIgnoreCase(category))
				bookbyCategory.add(bk);

		}
		if (bookbyCategory.isEmpty())
			throw new CategoryNotFoundException("Category not found");
		else {
			Collections.sort(bookbyCategory, (book1, book2) -> {
				return book1.getTitle().compareToIgnoreCase(book2.getTitle());

			});

		}
		return bookbyCategory;

	}

}
