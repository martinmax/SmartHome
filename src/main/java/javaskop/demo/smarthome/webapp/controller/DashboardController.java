package javaskop.demo.smarthome.webapp.controller;

import javaskop.demo.smarthome.core.SmartHomeRouter;
import javaskop.demo.smarthome.core.appliance.AirConditioner;
import javaskop.demo.smarthome.core.appliance.Heater;
import javaskop.demo.smarthome.core.sensor.TemperatureSensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

/**
 * Created by martin.ilievski on 11/25/2015.
 */
@Controller
public class DashboardController {

    @Autowired
    private SmartHomeRouter smartHomeRouter;

    @ModelAttribute("availableSensors")
    public Collection<TemperatureSensor> availableSensors() {
        return smartHomeRouter.getAvailableSensors().values();
    }

    @ModelAttribute("availableAirconditioners")
    public Collection<AirConditioner> availableAirconditioners() {
        return smartHomeRouter.getAirConditioners();
    }

    @ModelAttribute("availableHeaters")
    public Collection<Heater> availableHeaters() {
        return smartHomeRouter.getHeaters();
    }


    @RequestMapping(path = "/")
    String dashboard() {
        return "dashboard";
    }
}
