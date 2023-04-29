import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Main {

    public static final String STAFF_TXT = "data/staff.txt";

    public static void main(String[] args) {
        List<Employee> staff = Employee.loadStaffFromFile(STAFF_TXT);
        System.out.println(staff);
    }

    public static void sortBySalaryAndAlphabet(List<Employee> staff) {

        Collections.sort(staff, (employee1, employee2) -> {
            if (Objects.equals(employee1.getSalary(), employee2.getSalary())) {
                return employee1.getName().compareTo(employee2.getName());
            }

            return employee1.getSalary().compareTo(employee2.getSalary());
        });

    }
}