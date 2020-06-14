import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class DemoTest {

    @Test
    public void test() {
        Assertions.assertEquals(0, 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void mockito() {
        List<Integer> mock = Mockito.mock(List.class);
        mock.add(0);
        Mockito.verify(mock).add(0);
    }

}
