import java.util.HashMap;
import java.util.List;
import java.util.Map;
import operation.OperationHandler;
import operation.OrdersOperationHandler;
import operation.QueriesOperationHandler;
import operation.UpdateOperationHandler;
import service.FileReader;
import service.FileWriter;
import service.Parser;
import service.ReportService;
import strategy.OperationStrategy;
import strategy.OperationStrategyImpl;

public class Main {
    private static final String INPUT_FILE = "input.txt";
    private static final String OUTPUT_FILE = "output.txt";

    public static void main(String[] args) {
        Parser parser = new Parser();
        ReportService reportService = new ReportService();
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("u", new UpdateOperationHandler(parser));
        operationHandlerMap.put("q", new QueriesOperationHandler(reportService));
        operationHandlerMap.put("o", new OrdersOperationHandler());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        List<String> strings = FileReader.read(INPUT_FILE);
        strings.forEach(e -> {
            operationStrategy.get(String.valueOf(e.charAt(0))).apply(e);
        });

        FileWriter.write(OUTPUT_FILE, reportService.getReport());
    }
}
