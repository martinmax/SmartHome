package javaskop.demo.smarthome.core.appliance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by martin.ilievski on 11/19/2015.
 */
public class Heater implements Appliance {

    private Logger logger = LoggerFactory.getLogger(Heater.class);
    private String name;
    private boolean isTurnedOn;

    public Heater(String heaterName) {
        this.name = heaterName;
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
        logger.info("Turning on heater " + getName());
        isTurnedOn = true;
    }

    @Override
    public void turnOff() {
        logger.info("Turning off heater " + getName());
        isTurnedOn = false;
    }
}
