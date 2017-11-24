import com.aikon.wht.dao.extend.TagExtendMapper;
import com.aikon.wht.entity.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author haitao.wang
 */
@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file://**/resources/spring/spring-beans.xml",
        "file://**/resources/spring/spring-mybatis.xml",
        "file://**/resources/spring/spring-shiro.xml"})
public class TxTest {

    @Autowired
    private DataSourceTransactionManager txManager;

    @Autowired
    private TagExtendMapper tagExtendMapper;

    private ApplicationContext context;


    @Before
    public void loadApplicationContext() {
    }

    @Test
    public void dirtyReadTest() {
        DefaultTransactionDefinition def1 = new DefaultTransactionDefinition();
        def1.setName("tx1");
        def1.setIsolationLevel(Isolation.READ_COMMITTED.value());
        def1.setPropagationBehavior(Propagation.REQUIRES_NEW.value());
        TransactionStatus status1 = txManager.getTransaction(def1);

        try {
            this.updateDescription("ttt",1);
            txRead(Isolation.READ_COMMITTED, Propagation.REQUIRES_NEW);
            throw new Exception("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
        } catch (Exception ex) {
            System.out.println(ex);
            if (!status1.isCompleted()) {
                txManager.rollback(status1);
            }
        }
        txRead(Isolation.READ_COMMITTED, Propagation.REQUIRES_NEW);
    }


    @Test
    public void nonRepeatableReadTest() {
        DefaultTransactionDefinition def2 = new DefaultTransactionDefinition();
        def2.setName("tx2");
        def2.setIsolationLevel(Isolation.REPEATABLE_READ.value());
//        def2.setPropagationBehavior(Propagation.REQUIRES_NEW.value());
        TransactionStatus status2 = txManager.getTransaction(def2);
        System.out.println(getTag().getDescription());

        txUpdate(Isolation.REPEATABLE_READ,Propagation.REQUIRES_NEW);

        System.out.println(getTag().getDescription());
        txManager.commit(status2);


    }

    @Test
    public void rollRollTest() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setName("tx1");
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_NEVER);
//        def1.setPropagationBehavior(propagation.value());
        TransactionStatus status1 = txManager.getTransaction(definition);

        try {
            this.updateDescription("ttt",1);
            txManager.commit(status1);
        } catch (Exception ex) {
            System.out.println(ex);
            if (!status1.isCompleted()) {
                txManager.rollback(status1);
            }
        }
    }

    @Test
    public void doInTxTest() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setName("tx1");
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        definition.setReadOnly(true);
        TransactionStatus status1 = txManager.getTransaction(definition);
        System.out.println(getTag().getDescription());
        this.updateDescription("iii",1);
        this.updateDescription("GGG",2);
        System.out.println(getTag().getDescription());
        txManager.rollback(status1);


    }

    public void updateDescription(String val,Integer id) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setDescription(val);
        tagExtendMapper.updateByPrimaryKeySelective(tag);
    }

    public void txUpdate(Isolation isolation, Propagation propagation) {
        DefaultTransactionDefinition def1 = new DefaultTransactionDefinition();
        def1.setName("tx1");
        def1.setIsolationLevel(isolation.value());
//        def1.setPropagationBehavior(propagation.value());
        TransactionStatus status1 = txManager.getTransaction(def1);

        try {
            this.updateDescription("ttt",1);
            txManager.commit(status1);
        } catch (Exception ex) {
            System.out.println(ex);
            if (!status1.isCompleted()) {
                txManager.rollback(status1);
            }
        }
    }

    public void txRead(Isolation isolation, Propagation propagation) {
        DefaultTransactionDefinition def2 = new DefaultTransactionDefinition();
        def2.setName("tx2");
        def2.setIsolationLevel(isolation.value());
        def2.setPropagationBehavior(propagation.value());
        TransactionStatus status2 = txManager.getTransaction(def2);
        System.out.println(getTag().getDescription());
        if (status2.isCompleted()) {
            txManager.commit(status2);
        }
    }

    public void testNested() {
        StaticNested staticNested = new StaticNested();
        Nested nested = new Nested();
    }

    public static void staticTestNested() {

    }

    public Tag getTag() {
        return tagExtendMapper.selectByPrimaryKey(1);
    }

    public static class StaticNested{

        public StaticNested() {
        }

        public static void main(String[] args) {

        }
    }

    public class Nested{

        public Nested() {
        }

        public void main(String[] args) {

        }
    }
}
