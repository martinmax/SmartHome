package javaskop.demo.smarthome.core.event.events;

import com.google.gson.Gson;
import javaskop.demo.smarthome.core.event.Event;

/**
 * Created by martin.ilievski on 11/20/2015.
 */
public class HeaterStateChangedEvent implements Event {
    private String name;
    private boolean state;

    public HeaterStateChangedEvent(String name, boolean state) {
        this.name = name;
        this.state = state;
    }

    @Override
    public Object getData() {
        return new Gson().toJson(this);
    }

    @Override
    public String getQueue() {
        return "heaterStateEvent";
    }
}
