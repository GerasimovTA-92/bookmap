package operation;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import model.Book;
import storage.Storage;

public class OrdersOperationHandler implements OperationHandler {
    private static final String COMA = ",";
    private static final int INDEX_OF_COUNT = 2;
    private static final int INDEX_OF_TYPE = 1;

    @Override
    public void apply(String param) {
        String[] params = param.split(COMA);
        if (params[INDEX_OF_TYPE].equals("buy")) {
            removeSize("ask", Long.valueOf(params[INDEX_OF_COUNT]));
            return;
        }
        removeSize("bid", Long.valueOf(params[INDEX_OF_COUNT]));
    }

    private void removeSize(String type, Long count) {
        List<Book> books = Storage.storage.stream()
                .filter(e -> e.getType().equals(type))
                .sorted(type.equals("bid") ? Comparator.comparing(Book::getPrice).reversed()
                        : Comparator.comparing(Book::getPrice))
                        .collect(Collectors.toList());
        for (var book : books) {
            if (book.getCount() < count) {
                count -= book.getCount();
                book.setCount(0L);
                continue;
            }
            book.setCount(book.getCount() - count);
        }
    }
}
