package ut.server.plugin.sample;

import org.junit.Test;
import server.plugin.sample.api.MyPluginComponent;
import server.plugin.sample.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest {
    @Test
    public void testMyName() {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent", component.getName());
    }
}