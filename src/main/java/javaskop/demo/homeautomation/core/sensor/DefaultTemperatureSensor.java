package javaskop.demo.homeautomation.core.sensor;

import javaskop.demo.homeautomation.core.supplier.TemperatureSupplier;
import rx.Observable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by martin.ilievski on 11/19/2015.
 */
public class DefaultTemperatureSensor implements TemperatureSensor {

    private boolean state;
    private String sensorName;
    private TemperatureSupplier temperatureSupplier;

    public DefaultTemperatureSensor(String sensorName) {
        this.sensorName = sensorName;
        temperatureSupplier = new TemperatureSupplier();
    }

    @Override
    public Observable<SensorData> getData() {
        return Observable.create(subscriber -> {
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (subscriber.isUnsubscribed()) {
                        timer.cancel();
                        return;
                    }
                    Integer currentTemperature = temperatureSupplier.get();
                    subscriber.onNext(new SensorData(currentTemperature, sensorName));
                }
            }, 1000, 1000);
        });
    }

    @Override
    public String getName() {
        return sensorName;
    }

    @Override
    public boolean isEnabled() {
        return state;
    }

    @Override
    public void enableSensor() {
        this.state = true;
    }

    @Override
    public void disableSensor() {
        this.state = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultTemperatureSensor that = (DefaultTemperatureSensor) o;

        return sensorName.equals(that.sensorName);

    }

    @Override
    public int hashCode() {
        return sensorName.hashCode();
    }
}
