package javaskop.demo.smarthome.core.event.events;

import com.google.gson.Gson;
import javaskop.demo.smarthome.core.event.Event;

/**
 * Created by martin.ilievski on 11/19/2015.
 */
public class TemperatureChangedEvent implements Event {

    private Integer currentTemperature;
    private String fromSensor;

    public TemperatureChangedEvent(String fromSensor, Integer currentTemperature) {
        this.fromSensor = fromSensor;
        this.currentTemperature = currentTemperature;
    }

    @Override
    public Object getData() {
        return new Gson().toJson(this);
    }

    @Override
    public String getQueue() {
        return "temperatureChangedEvent";
    }
}
