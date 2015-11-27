package javaskop.demo.homeautomation.core.appliance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by martin.ilievski on 11/19/2015.
 */
public class AirConditioner implements Appliance {

    private Logger logger = LoggerFactory.getLogger(AirConditioner.class);
    private String name;
    private boolean isTurnedOn;

    public AirConditioner(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    @Override
    public void turnOn() {
        logger.info("Turning on AirConditioner " + getName());
        isTurnedOn = true;
    }

    @Override
    public void turnOff() {
        logger.info("Turning off AirConditioner " + getName());
        isTurnedOn = false;
    }
}
