package operation;

import java.util.Comparator;
import model.Book;
import service.ReportService;
import storage.Storage;

public class QueriesOperationHandler implements OperationHandler {
    private static final String COMA = ",";
    private static final int INDEX_OF_PRICE = 2;
    private final ReportService reportService;

    public QueriesOperationHandler(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public void apply(String param) {
        String[] params = param.split(COMA);
        if (params.length == 2) {
            switch (params[1]) {
                case "best_bid":
                    addBookToReport("bid");
                    break;
                case "best_ask":
                    addBookToReport("ask");
                    break;
                default:
                    break;
            }
            return;
        }
        Storage.storage.stream()
                .filter(e -> e.getPrice().equals(Long.valueOf(params[INDEX_OF_PRICE])))
                .findFirst()
                .ifPresent(e -> reportService.addInfoToReport(e.getCount()));
    }

    private void addBookToReport(String type) {
        Storage.storage.stream()
                .filter(e -> e.getType().equals(type) && e.getCount() > 0)
                .sorted(type.equals("ask") ? Comparator.comparing(Book::getPrice)
                        : Comparator.comparing(Book::getPrice).reversed())
                .findFirst()
                .ifPresent(reportService::addBookToReport);
    }
}
