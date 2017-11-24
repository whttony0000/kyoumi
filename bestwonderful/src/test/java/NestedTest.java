/**
 * @author haitao.wang
 */
public class NestedTest {

    public void nestedTest() {
        TxTest txTest = new TxTest();
        TxTest.Nested nested = txTest.new Nested();
        TxTest.StaticNested staticNested = new TxTest.StaticNested();
    }
}
