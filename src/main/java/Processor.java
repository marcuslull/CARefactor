import model.Branch;
import model.Employee;

import java.util.*;

public class Processor {
//    /**
//     * Will return the most productive that the employee has ever been
//     *
//     * @param employeeName the name of the employee to analyse
//     * @param annualData   all historical data we have across all branches
//     * @return the most productivity or null.
//     */
//    public final Integer getBestProductivityForEmployee(String employeeName, Map<String, Map<String, Branch>> annualData) {
//        Integer bestProductivity = null;
//        if (annualData != null) {
//            for (String year : annualData.keySet()) {
//                Map<String, Branch> branches = annualData.get(year);
//                if (branches != null) {
//                    for (Branch branch : branches.values()) {
//                        if (branch != null && branch.getEmployees() != null) {
//                            for (Employee employee : branch.getEmployees()) {
//                                if (employee != null && employee.getName() != null && employee.getName().equals(employeeName)) {
//                                    if (bestProductivity == null || (employee.getProductivity() != null && employee.getProductivity() > bestProductivity)) {
//                                        bestProductivity = employee.getProductivity();
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        return bestProductivity;
//    }

    /**
     * Will return the most productive that the employee has ever been
     *
     * @param employeeName the name of the employee to analyse
     * @param annualData   all historical data we have across all branches
     * @return the most productivity or null.
     */
    public final Integer getBestProductivityForEmployee(String employeeName, Map<String, Map<String, Branch>> annualData) {

        // short circuit null check for method arguments
        if (annualData == null || annualData.isEmpty() || employeeName == null || employeeName.isEmpty()) return null;

        // streaming multiple streams requires flatMap, and we need to extract all inner values
        return annualData.values().stream() //values only from the top Map
                .filter(Objects::nonNull) // dont process null years
                .flatMap(inner -> inner.values().stream()) // stream of Branch streams need to be flattened
                .filter(branch -> Objects.nonNull(branch) &&
                        branch.getEmployees() != null) // dont process null branches or employee fields
                .flatMap(branch -> branch.getEmployees().stream()) // stream of employee streams need to be flattened
                .filter(employee -> Objects.nonNull(employee) &&
                        employee.getName() != null && employee.getName().equals(employeeName)) // filter for nulls and match on name
                .map(Employee::getProductivity) // get the productivity Integer
                .filter(Objects::nonNull) // dont process null Integers
                .max(Integer::compareTo).orElse(null); // return the max Integer or null
    }
}
