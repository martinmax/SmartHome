package javaskop.demo.smarthome.core.appliance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by martin.ilievski on 11/19/2015.
 */
public class Cooler implements Appliance {

    private Logger logger = LoggerFactory.getLogger(Cooler.class);
    private String name;
    private boolean isTurnedOn;

    public Cooler(String name) {
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
        logger.info("Turning on Cooler " + getName());
        isTurnedOn = true;
    }

    @Override
    public void turnOff() {
        logger.info("Turning off Cooler " + getName());
        isTurnedOn = false;
    }
}
