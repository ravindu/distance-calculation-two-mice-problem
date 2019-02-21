                                                              READ ME
                                                         Important facts


This is Suggested solution for the Two-Mice problem. This project is created using Spring and Junit as a maven project.

Requirements
- Java 8 and Maven should have installed in your machine.

Commands
- mvn clean install to build and run the test case

Best practices and improvement can be done
- Try to apply Single responsibility principle.
- Moved various logic implementations to methods to improve the readability.
- Moved common, solid functionalities to a Utility class.
- Here I should have implemented a strategy pattern to calculate the distance based on different scenarios. But couldn’t implement it because of time constraint.

Assumptions
- If the small mice approximately closer to 0, then I assumed that small mice can’t travel anymore. In that moment Big mice need care of cover the rest of the distance

