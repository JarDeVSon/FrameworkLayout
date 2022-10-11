package tests;

import core.GalenTestBase;
import org.testng.annotations.Test;

public class LayoutTest extends GalenTestBase {

    @Test(dataProvider = "devices")
    public void testLayoutDispositivos(TestDevice device) throws Exception{
        load("");
        Thread.sleep(5000);

        checkLayout("src/test/java/specs", device.getTags());
    }
}
