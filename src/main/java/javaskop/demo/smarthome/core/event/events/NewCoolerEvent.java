package javaskop.demo.smarthome.core.event.events;

import com.google.gson.Gson;
import javaskop.demo.smarthome.core.event.Event;

/**
 * Created by martin.ilievski on 11/24/2015.
 */
public class NewCoolerEvent implements Event {

    private String name;

    public NewCoolerEvent(String name) {
        this.name = name;
    }

    @Override
    public Object getData() {
        return new Gson().toJson(this);
    }

    @Override
    public String getQueue() {
        return "newCoolerEvent";
    }
}
