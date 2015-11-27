package javaskop.demo.smarthome.core.sensor;

import rx.Observable;

/**
 * Created by martin.ilievski on 11/19/2015.
 */
public interface TemperatureSensor {

    Observable<SensorData> getData();

    String getName();

    boolean isEnabled();

    void enableSensor();

    void disableSensor();
}
