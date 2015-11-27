package javaskop.demo.homeautomation.core.event.events;

import com.google.gson.Gson;
import javaskop.demo.homeautomation.core.event.Event;

/**
 * Created by martin.ilievski on 11/24/2015.
 */
public class NewSensorEvent implements Event {

    private String name;

    public NewSensorEvent(String name) {
        this.name = name;
    }

    @Override
    public Object getData() {
        return new Gson().toJson(this);
    }

    @Override
    public String getQueue() {
        return "newSensorEvent";
    }
}
