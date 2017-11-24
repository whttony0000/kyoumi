import org.apache.log4j.PropertyConfigurator;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author haitao.wang
 */
public class JUnit4ClassRunner extends SpringJUnit4ClassRunner {

    static {
        try {
            InputStream is = new FileInputStream("log4j.properties");
            PropertyConfigurator.configure( is );
        }
        catch( Exception ex ) {
            System.err.println( "Cannot Initialize log4j" );
        }
    }

    public JUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}
