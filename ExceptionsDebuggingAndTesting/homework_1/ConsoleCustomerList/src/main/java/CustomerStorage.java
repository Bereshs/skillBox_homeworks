import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    private boolean isNoTokens(String[] tokens) {
        return tokens.length <= 1;
    }

    private boolean isCorrectEmail(String email) {

        return email.matches("[A-z0-9]+@[A-z0-9]+.[A-z0-9]+");
    }

    private boolean isCorrectPhoneNumber(String phoneNumber) {

        return phoneNumber.matches("\\+7[0-9]{10}");
    }

    public void addCustomer(String data) throws Exception {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        String[] components = data.split("\\s+");

        if (isNoTokens(components)) {
            throw new CustomerStorageException("Wrong add format command. received data: " + data);
        }
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];

        if (!isCorrectEmail(components[INDEX_EMAIL])) {
            throw new CustomerStorageException("Wrong e-mail received " + components[INDEX_EMAIL]);
        }

        if (!isCorrectPhoneNumber(components[INDEX_PHONE])) {
            throw new CustomerStorageException("Wrong phone number received " + components[INDEX_PHONE]);
        }

        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}