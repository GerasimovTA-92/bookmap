package service;

import model.Book;

public class ReportService {
    private StringBuilder builder;

    public ReportService() {
        builder = new StringBuilder();
    }

    public void addBookToReport(Book book) {
        if (book == null) {
            return;
        }
        builder
                .append(book.getPrice())
                .append(",")
                .append(book.getCount())
                .append("\n");
    }

    public void addInfoToReport(Object str) {
        builder.append(str)
                .append("\n");
    }

    public String getReport() {
        return builder.toString();
    }
}
