package javaskop.demo.smarthome.webapp.controller;

import javaskop.demo.smarthome.core.SmartHomeRouter;
import javaskop.demo.smarthome.core.appliance.AirConditioner;
import javaskop.demo.smarthome.core.appliance.Heater;
import javaskop.demo.smarthome.core.sensor.DefaultTemperatureSensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by martin.ilievski on 11/25/2015.
 */
@Controller
public class ControlPanelController {
    @Autowired
    private SmartHomeRouter smartHomeRouter;

    @RequestMapping(value = "/control")
    String control() {
        return "controlPanel";
    }

    @RequestMapping(value = "/add/sensor", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    void addSensor(@RequestParam(value = "name") String name) {
        smartHomeRouter.addSensor(new DefaultTemperatureSensor(name));
    }

    @RequestMapping(value = "/add/ac", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    void addAC(@RequestParam(value = "name") String name) {
        smartHomeRouter.addAirConditioner(new AirConditioner(name));
    }

    @RequestMapping(value = "/add/heater", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    void addHeater(@RequestParam(value = "name") String name) {
        smartHomeRouter.addHeater(new Heater(name));
    }

    @RequestMapping(value = "/disable/sensor", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    void disableSensor(@RequestParam(value = "name") String name) {
        smartHomeRouter.disableSensor(smartHomeRouter.getAvailableSensors().get(name));
    }

    @RequestMapping(value = "/enable/sensor", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    void enableSensor(@RequestParam(value = "name") String name) {
        smartHomeRouter.enableSensor(smartHomeRouter.getAvailableSensors().get(name));
    }
}
