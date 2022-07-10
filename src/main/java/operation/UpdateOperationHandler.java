package operation;

import model.Book;
import service.Parser;
import storage.Storage;

public class UpdateOperationHandler implements OperationHandler {
    private final Parser parser;

    public UpdateOperationHandler(Parser parser) {
        this.parser = parser;
    }

    @Override
    public void apply(String param) {
        Book book = parser.apply(param);
        Book bookFromDb = Storage.storage.stream().filter(e -> e.getType().equals(book.getType())
                        && e.getPrice().equals(book.getPrice()))
                .findFirst()
                .orElse(null);
        if (bookFromDb == null) {
            Storage.storage.add(book);
            return;
        }
        bookFromDb.setCount(book.getCount() + bookFromDb.getCount());
    }
}
