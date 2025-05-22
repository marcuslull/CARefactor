import model.Branch;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProcessorTest {
    private final Parser parser = new Parser();
    private final Processor processor = new Processor();

    @Test
    public void getBestProductivityForEmployee_basicTest() {
        Map<String, Map<String, Branch>> employeeData = parser.parseBranchesByYear("sample.json");

        Integer result;

        result = processor.getBestProductivityForEmployee("Joward Maximilian", employeeData);
        assertEquals(Integer.valueOf(83), result);

        result = processor.getBestProductivityForEmployee("Big Tom", employeeData);
        assertEquals(Integer.valueOf(98), result);

        result = processor.getBestProductivityForEmployee("7 Toe Bill", employeeData);
        assertEquals(Integer.valueOf(7), result);

    }

    @Test
    public void getBestProductivityForEmployee_comprehensiveTest() {


        Map<String, Map<String, Branch>> employeeData = parser.parseBranchesByYear("comprehensive_sample.json");

        Integer result;

        result = processor.getBestProductivityForEmployee("Alice Wonderland", employeeData);
        assertEquals(Integer.valueOf(85), result);

        result = processor.getBestProductivityForEmployee("Bob The Builder", employeeData);
        assertEquals(Integer.valueOf(65), result);

        assertNull(processor.getBestProductivityForEmployee("Charlie_ProductivityIsNull", employeeData));

        assertNull(processor.getBestProductivityForEmployee("David_ProductivityFieldMissing", employeeData));

        result = processor.getBestProductivityForEmployee("Eve_OtherEmployee", employeeData);
        assertEquals(Integer.valueOf(77), result);

        assertNull(processor.getBestProductivityForEmployee("NonExistent Employee", employeeData));
    }
}