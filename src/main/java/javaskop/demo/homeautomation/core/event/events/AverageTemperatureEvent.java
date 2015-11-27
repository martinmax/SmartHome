package javaskop.demo.homeautomation.core.event.events;

import com.google.gson.Gson;
import javaskop.demo.homeautomation.core.event.Event;

/**
 * Created by martin.ilievski on 11/20/2015.
 */
public class AverageTemperatureEvent implements Event {
    private double averageTemperature;

    public AverageTemperatureEvent(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    @Override
    public Object getData() {
        return new Gson().toJson(this);
    }

    @Override
    public String getQueue() {
        return "averageTemperatureEvent";
    }
}
