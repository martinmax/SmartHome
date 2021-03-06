package javaskop.demo.smarthome.webapp;

import javaskop.demo.smarthome.SmartHomePackage;
import javaskop.demo.smarthome.core.SmartHomeRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = SmartHomePackage.class)
public class SmartHomeApplication implements CommandLineRunner {

    @Autowired
    private SmartHomeRouter smartHomeRouter;

    @Override
    public void run(String... strings) throws Exception {
        smartHomeRouter.initCurrentTemperature();
        smartHomeRouter.initAverageTemperature();
        smartHomeRouter.initAppliances();
    }


    public static void main(String[] args) {
        SpringApplication.run(SmartHomeApplication.class, args);
    }
}
