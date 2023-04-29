
import java.util.*;

public class Main {

    private static final String STAFF_TXT = "data/staff.txt";

    public static void main(String[] args) {
        List<Employee> staff = Employee.loadStaffFromFile(STAFF_TXT);
        Employee employeeMaxSalary = findEmployeeWithHighestSalary(staff, 2017);
        System.out.println(employeeMaxSalary);
    }

    public static Employee findEmployeeWithHighestSalary(List<Employee> staff, int year) {

        int yearTimeStamp = year - 1900;

        Optional<Employee> optionalEmployee = staff.stream().filter(employee -> employee.getWorkStart().getYear() == yearTimeStamp)
                .max(Comparator.comparing(Employee::getSalary));

        return optionalEmployee.orElse(null);
    }
}