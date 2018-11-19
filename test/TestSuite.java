
//JUnit 4 test suite
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.risk.helper.CommonTest;
import com.risk.helper.GetArmiesByTradingTest;
import com.risk.model.*;

@RunWith(Suite.class)
@SuiteClasses({ MapTest.class, CountryTest.class, ContinentTest.class,GameTest.class,PlayerTest.class,CommonTest.class,GetArmiesByTradingTest.class})

/**
 * This is the Test Suite class
 */
public class TestSuite {

}