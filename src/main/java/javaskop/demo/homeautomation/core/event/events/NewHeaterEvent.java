package javaskop.demo.homeautomation.core.event.events;

import com.google.gson.Gson;
import javaskop.demo.homeautomation.core.event.Event;

/**
 * Created by martin.ilievski on 11/24/2015.
 */
public class NewHeaterEvent implements Event {
    private String name;

    public NewHeaterEvent(String name) {
        this.name = name;
    }

    @Override
    public Object getData() {
        return new Gson().toJson(this);
    }

    @Override
    public String getQueue() {
        return "newHeaterEvent";
    }
}
