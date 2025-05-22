import model.Branch;
import model.Employee;

import java.util.*;

public class Processor {
    /**
     * Will return the most productive that the employee has ever been
     *
     * @param employeeName the name of the employee to analyse
     * @param annualData   all historical data we have across all branches
     * @return the most productivity or null.
     */
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
        List<Branch> flattenedBranchData = annualData.values().stream().flatMap(inner -> inner.values().stream()).toList();

        // another stream of streams. We need all employee data
        List<Employee> flattenedEmployeeData = flattenedBranchData.stream().flatMap(branch -> branch.getEmployees().stream()).toList();

        // now we can begin to filter on employeeName
        List<Employee> matchingEmployeeData = flattenedEmployeeData.stream().filter(employee -> employee.getName().equals(employeeName)).toList();

        // for all the matching employee data lets get the productivity Integer and then aggregate the max score
        Optional<Integer> optionalEmployeeMaxScore = matchingEmployeeData.stream().map(Employee::getProductivity).max(Integer::compareTo);

        // its possible we will have no employee match in that case return null
        return optionalEmployeeMaxScore.orElse(null);
    }
}
