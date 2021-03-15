package nl.altindag.psc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.phantomjs.PhantomJSDriverService.PHANTOMJS_CLI_ARGS;
import static org.openqa.selenium.phantomjs.PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY;

public class App {

    private static final String DEFAULT_PHANTOM_CLI_COMMANDS[] = {"--webdriver-loglevel=NONE"};

    /**
     * @param args      0. URL
     *                  1. HTML element by class name of the product status indicator
     *                  2. Text to contain in the HTML element when the product is available
     *                  3. Absolute path to phantom js driver
     *                  4. Retry after n amount of time in seconds
     */
    public static void main(String[] args) throws InterruptedException {
        boolean isProductAvailable = false;

        String url = args[0];
        String stockInfoClassName = args[1];
        String stockInfoElementText = args[2];
        int timeoutInSeconds = Integer.parseInt(args[4]);
        String phantomJsDriverPath = args[3];

        PhantomJSDriver driver = createPhantomJSDriver(phantomJsDriverPath, DEFAULT_PHANTOM_CLI_COMMANDS);

        do {
            driver.get(url);

            Document doc = Jsoup.parse(driver.getPageSource());
            String stockInfo = doc.getElementsByClass(stockInfoClassName).text();
            if (stockInfo.contains(stockInfoElementText)) {
                System.out.println(LocalTime.now() + " + Product is available");
                isProductAvailable = true;
            } else {
                System.err.println(LocalTime.now() + " - Product is not available");
            }

            if (!isProductAvailable) {
                System.out.print(LocalTime.now() + " % Waiting for " + timeoutInSeconds + " seconds before retrying");
                for (int i = 0; i < timeoutInSeconds; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.print(".");
                }
                System.out.println();
            }

        } while (!isProductAvailable);

        driver.quit();
    }

    private static PhantomJSDriver createPhantomJSDriver(String driverPath, String... cliArguments) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PHANTOMJS_CLI_ARGS, cliArguments);
        desiredCapabilities.setCapability(PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverPath);
        return new PhantomJSDriver(desiredCapabilities);
    }

}
