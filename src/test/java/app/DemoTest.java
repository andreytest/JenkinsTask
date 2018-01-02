package app;


import org.junit.Assert;
import org.junit.Test;

public class DemoTest {
    @Test
    public void checkForPass(){
        Assert.assertEquals("Test for jenkins", "Test for jenkins1");
    }

    @Test
    public void checkForFail(){
        Assert.assertNotEquals("Test for jenkins","");
    }

}