# Refactor Homework  
Technical interview homework problem two  
Completed by Marcus Lull
---
## Problem
See below

## Process breakdown
1. Understand clearly the problem statement and ask clarifying questions if needed.
2. Prepare the README.md to outline resolution process
3. Explore assumptions
4. Technical design
5. Code
6. Test
7. Refactor of optimization
8. Test
9. Documentation
10. Zip and submit for review

## My interpretations and assumptions
* Data is collected on employees by branch
* Refactor code for readability and maintainability
* Do not change the method signature
* Java 10+ and Maven
* Rewrite `getBestProductivityForEmployee`
* Helpers are permitted but must use the `private` access modifier
* Cannot rely on the supplied data
* null safety and validation are paramount
* Use functional programming where possible (Streams, Optionals)
* Graded against an unknown test suite
* The original method passes the unknown tests
* Assumption: The client may want the ability to filter based on existing enums, or branch names in the future.

## Current code review
 * This code parses a data structure that contains employee data partitioned by branch and then by year. 
 * Data is searched for an employee name that matches the employee name that was passed as an argument.
 * On a match, it checks productivity and updates bestProductivity based on a comparison between current and previous max.
 * Employees may have multiple entries because of data partitioning
 * The current hierarchy is: String year > Branch branch > Employee employee > Integer productivity.
 * Null checks at each level
 * Employee matched based on name
 * Method returns null by default
 * Method returns an Integer max productivity only if an employee is found that matches the employeeName argument and has a productivity score
 * Current logic does not seem to rely on Branch.department, Employee.department, Employee.status.


## My technical choices
One of the requirements of this refactoring is readability. Java Streams make a great choice for this aspect as they are intuitively more easy to read in a step-by-step manner.  

Java Maps do not natively have access to the `.stream()` method like Java Collections do. However, adapting Maps to the Stream API can be done using the `keySet()`, `values()`, or `entrySet()` methods.  

For this particular problem it looks like we are only returning the best productivity score for a given employee. This productivity field is held by the Employee object which is contained in a List field of the Branch object. Branches are the values of the inner Map which is itself the value of the outer year Map. So, at least initially it looks like we can drill down to individual Branches with `Map.values()` and the key data will not be needed. From this point we may only require a `filter()` to collect all employee records where we can decide the max perhaps with a max function.  

As far as null handling and validation this can be handled with optionals and a combination of `if/isPresent()`, and `orElse()` functions.

Once I have achieved the initial functionality and come up with suitable tests I can refactor for optimization and maintainability.


## Lessons learned
* Initially I wanted to break this down into steps to emphasize the readability requirement but to do so require creating all the extra Lists as in my first impl. I decided that it really didn't make things easier to understand than the single stream method especially considering the extra memory used.
* Initially I thought I may want to set things up for the potential future use of the Department and Status as filter criteria but this could easily wind up being technical debt so, I abandoned this as YAGNI.

---

## IntelliJ Setup
1. Ensure you have the latest version of IntelliJ. Older versions are not compatible with the newest Java releases.
2. Select File->New->Project from existing sources
3. In the import modal, check the `Import from external model` radio button and select `Maven`
4. Go to File->Project Structure.
    1. If you do not see a Java 10+ SDK in the `project` tab, click the dropdown and install it.
    2. Select a language level of 10 or higher.
    3. Navigate to the `Modules` tab.
        1. Ensure the `/src/main/java` directory is marked as `Sources`
        2. Ensure the `/src/test/java` directory is marked as `Test`
        3. Ensure the `/src/test/resources` directory is marked as `Resources`
    3. Click apply and close the project structure modal.
5. Open the `Processor` java file. If `Integer` is highlighted in red, use the context menu to add `java.lang` to the compiler options.
6. Run the tests by right-clicking on the `src/test/java` directory in the project explorer and select `Run 'All Tests'`

## Manual Setup
1. Install the Java 10+ JDK. If you already have an older java version installed, we recommend using one of the following version managers.
    * MacOS/Linux: [jEnv](https://www.jenv.be/)
    * Windows: [jabba](https://github.com/shyiko/jabba)
2. Install [Maven](https://maven.apache.org/install.html)
3. Compile the sources in a new terminal window `mvn clean install -DskipTests`.
4. Run the tests. `mvn clean install`. The tests should pass

## Homework Overview
You are a software contractor and have been approached by a relatively small startup for work maintaining one of their internal utility applications. The company collects data on the productivity of its employees yearly. The company is organized into branches, where each branch has multiple employees. They want you to refactor a specific piece of code to make it more readable and maintainable without changing the way the code is used.

## Details
* You have been provided with a program written in Java 10+ using Maven for dependency management.
* You are to rewrite the `getBestProductivityForEmployee` method located in the `Processor` class. The method is too deeply nested, making it difficult to read. There are a variety of ways to solve this, the specifics are up to you.
* No other prior-existing files will be modified. However, you are allowed to create new tests if you wish.
* The method signature of `getBestProductivityForEmployee` must not change, but the method body is free game. Creating new helper methods is also allowed, as long as they remain private.
* You cannot rely on the integrity of the supplied analytics data. Values can be null, empty, or otherwise invalid.
* There is no documentation on the specifics of which edge cases are properly handled and how. You will need to determine the requirements using only the source code provided.
* You have been provided a unit test file and a sample JSON file. The test cases in this file are far from complete, only checking a handful of employees. When refactoring the method, make sure to pay attention to null safety and any other validation steps included, as they are not checked in the provided tests.
* The client has been reading up on the [Functional Programming Paradigm](https://www.geeksforgeeks.org/functional-programming-paradigm/#:~:text=Functional%20programming%20is%20a%20programming,is%20%E2%80%9Chow%20to%20solve%E2%80%9D.). Usage of this paradigm in your refactor is welcome, but not required.  (Hint, think [Optionals](https://www.geeksforgeeks.org/java-8-optional-class/) and [Streams](https://www.geeksforgeeks.org/stream-in-java/))
* To simulate a production environment, you will be graded based on how your program performs against a test suite you have not been provided with.
* The original implementation of the `getBestProductivityForEmployee` method passes these hidden tests, so as long as your refactored code stays consistent with the original API contract, they will as well.
## Deliverables
Your project files in a zip archive. Assuming all Maven and Java are already installed, your submission should be ready to compile and run immediately after unzipping. Files specific to your IDE are not needed. (files like .idea, .vscode, etc)
