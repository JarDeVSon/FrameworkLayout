package core;

import com.galenframework.testng.GalenTestNgTestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class GalenTestBase extends GalenTestNgTestBase {
    public String ENV_URL = "";

    @Override
    public WebDriver createDriver(Object[] args){
        System.setProperty("webdriver.chrome.driver", "\"C:\\Users\\pesso\\chromedriver\\chromedriver.exe\"");
        WebDriver driver = null;
        if (args.length > 0){
            if (args[0] != null && args[0] instanceof TestDevice){
                TestDevice device = (TestDevice)args[0];
                driver = new ChromeDriver(device.getOptions());
                driver.manage().window().maximize();
            }
        }
        return driver;
    }
    @AfterMethod(alwaysRun = true)
    @Override
    public void quitDriver(){
        getDriver().quit();
    }

    @DataProvider(name = "devices")
    public Object [][] devices(){
        return new Object[][]{
                {new TestDevice("mobile",
                        new Emulation().getChromeOptions(
                                new MobileEmulation().getDeviceMetrics(
                                        new Metrics().getMetrics(320, 4500, 3.0))),
                        asList("mobile"))},
                {new TestDevice("tablet",
                        new Emulation().getChromeOptions(
                                new MobileEmulation().getDeviceMetrics(
                                        new Metrics().getMetrics(768, 2300, 3.0))),
                        asList("tablet"))},
                {new TestDevice("desktop",
                        new Emulation().getChromeOptions(
                                new MobileEmulation().getDeviceMetrics(
                                        new Metrics().getMetrics(1280, 2300, 3.0))),
                        asList("desktop"))},


        };
    }

    public static class Metrics {
        public Map<String, Object> getMetrics(Object width, Object height, Object pixelRatio){
            Map<String, Object> map = new HashMap<>();
            map.put("width", width);
            map.put("height", height);
            map.put("pixelRatio", pixelRatio);
            return map;
        }
    }
    public static class MobileEmulation{
        public Map<String, Object> getDeviceMetrics(Map<String,Object> deviceMetrics){
            Map<String, Object> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceMetrics", deviceMetrics);
            mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 8.0.0;" + "Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko" + "Chrome/67.0.3396.99 Mobile Safari/537.36");
            return mobileEmulation;
        }
    }
    public static class Emulation{
        public ChromeOptions getChromeOptions(Map<String,Object> mobileEmulation){
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            return chromeOptions;
        }
    }
    public static class TestDevice{
        private final String name;
        private final List<String> tags;
        private final ChromeOptions options;

        public TestDevice(String name, List<String> tags, ChromeOptions options) {
            this.name = name;
            this.tags = tags;
            this.options = options;
        }

        public String getName() {
            return name;
        }

        public List<String> getTags() {
            return tags;
        }

        public ChromeOptions getOptions() {
            return options;
        }
    }
}
