package service;

import java.util.function.Function;
import model.Book;

public class Parser implements Function<String, Book> {
    private static final String COMA = ",";
    private static final int INDEX_OF_PRICE = 1;
    private static final int INDEX_OF_SIZE = 2;
    private static final int INDEX_OF_TYPE = 3;

    @Override
    public Book apply(String string) {
        String[] strings = string.split(COMA);
        Book book = new Book();
        book.setType(strings[INDEX_OF_TYPE]);
        book.setCount(Long.valueOf(strings[INDEX_OF_SIZE]));
        book.setPrice(Long.valueOf(strings[INDEX_OF_PRICE]));
        return book;
    }
}
