package javaskop.demo.homeautomation.core.appliance;

/**
 * Created by martin.ilievski on 11/19/2015.
 */
public interface Appliance {
    String getName();

    boolean isTurnedOn();

    void turnOn();

    void turnOff();
}
