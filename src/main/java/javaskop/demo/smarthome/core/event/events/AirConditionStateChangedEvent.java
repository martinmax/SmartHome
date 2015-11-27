package javaskop.demo.smarthome.core.event.events;

import com.google.gson.Gson;
import javaskop.demo.smarthome.core.event.Event;

/**
 * Created by martin.ilievski on 11/20/2015.
 */
public class AirConditionStateChangedEvent implements Event {
    private boolean state;
    private String name;

    public AirConditionStateChangedEvent(String name, boolean state) {
        this.name = name;
        this.state = state;
    }

    @Override
    public Object getData() {
        return new Gson().toJson(this);
    }

    @Override
    public String getQueue() {
        return "airConditionStateEvent";
    }
}
