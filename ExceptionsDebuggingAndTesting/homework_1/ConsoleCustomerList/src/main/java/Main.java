import java.util.Scanner;
import org.apache.logging.log4j.*;

public class Main {
    private static final Logger LOGGER =
            LogManager.getLogger(Main.class);
    private static final String ADD_COMMAND = "add Василий Петров " +
            "vasily.petrov@gmail.com +79215637722";
    private static final String COMMAND_EXAMPLES = "\t" + ADD_COMMAND + "\n" +
            "\tlist\n\tcount\n\tremove Василий Петров";
    private static final String COMMAND_ERROR = "Wrong command! Available command examples: \n" +
            COMMAND_EXAMPLES;
    private static final String HELP_TEXT = "Command examples:\n" + COMMAND_EXAMPLES;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();

        while (true) {
            String command = scanner.nextLine();
            LOGGER.info("Entered command: "+ command);
            String[] tokens = command.split("\\s+", 2);

            switch (tokens[0]) {
                case "add":
                    try {
                        executor.addCustomer(tokens[1]);
                    } catch (Exception exception) {
                        LOGGER.error(exception.getMessage());
                    }
                    break;
                case "list":
                    executor.listCustomers();
                    break;
                case "remove":
                    executor.removeCustomer(tokens[1]);
                    break;
                case "count":
                    System.out.println("There are " + executor.getCount() + " customers");
                    break;
                case "help":
                    System.out.println(HELP_TEXT);
                    break;
                default:
                    System.out.println(COMMAND_ERROR);
                    break;
            }
        }
    }
}
