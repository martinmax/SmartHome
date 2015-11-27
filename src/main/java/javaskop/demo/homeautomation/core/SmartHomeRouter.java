package javaskop.demo.homeautomation.core;

import javaskop.demo.homeautomation.core.appliance.AirConditioner;
import javaskop.demo.homeautomation.core.appliance.Heater;
import javaskop.demo.homeautomation.core.event.EventPublisher;
import javaskop.demo.homeautomation.core.event.events.*;
import javaskop.demo.homeautomation.core.exception.SmartHomeException;
import javaskop.demo.homeautomation.core.sensor.SensorData;
import javaskop.demo.homeautomation.core.sensor.TemperatureSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rx.Subscription;
import rx.subjects.PublishSubject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin.ilievski on 11/19/2015.
 */
@Component
public class SmartHomeRouter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmartHomeRouter.class);

    @Value("${home.smart.ac.limit:20}")
    private int acLimit;
    @Value("${home.smart.heat.limit:18}")
    private int heatLimit;
    @Value("${home.smart.average.buffer:10}")
    private int averageBuffer;
    @Value("${home.smart.current.buffer:5}")
    private int currentBuffer;
    @Value("${home.smart.switch.buffer:10}")
    private int switchBuffer;

    private PublishSubject<SensorData> temperatureSensorStream = PublishSubject.create();
    private Map<TemperatureSensor, Subscription> activeSensors = new HashMap<>();
    private Map<String, TemperatureSensor> availableSensors = new HashMap<>();
    private List<AirConditioner> airConditioners = new ArrayList<>();
    private List<Heater> heaters = new ArrayList<>();

    @Autowired
    private EventPublisher eventPublisher;

    public void addSensor(TemperatureSensor sensor) {
        if (availableSensors.get(sensor.getName()) != null) {
            throw new SmartHomeException("Sensor already exists.");
        }
        eventPublisher.publish(new NewSensorEvent(sensor.getName()));
        availableSensors.put(sensor.getName(), sensor);
        LOGGER.info("New sensor added to router {}", sensor.getName());
        this.enableSensor(sensor);
    }

    public void enableSensor(TemperatureSensor temperatureSensor) {
        if (activeSensors.containsKey(temperatureSensor)) {
            throw new SmartHomeException("Already enabled.");
        }
        temperatureSensor.enableSensor();
        activeSensors.put(temperatureSensor, temperatureSensor.getData().subscribe(temperatureSensorStream));
        availableSensors.put(temperatureSensor.getName(), temperatureSensor);
        eventPublisher.publish(new SensorStateEvent(temperatureSensor.getName(), temperatureSensor.isEnabled()));
        LOGGER.info("Sensor {} is enabled", temperatureSensor.getName());
    }

    public void disableSensor(TemperatureSensor temperatureSensor) {
        final Subscription sensorSubscription = activeSensors.remove(temperatureSensor);
        if (sensorSubscription == null) {
            throw new SmartHomeException("Sensor not enabled.");
        }
        temperatureSensor.disableSensor();
        sensorSubscription.unsubscribe();
        eventPublisher.publish(new SensorStateEvent(temperatureSensor.getName(), temperatureSensor.isEnabled()));
        LOGGER.info("Sensor {} is disabled", temperatureSensor.getName());
    }

    public void addHeater(Heater heater) {
        if (heaters.contains(heater)) {
            throw new SmartHomeException("Already  exists.");
        }
        eventPublisher.publish(new NewHeaterEvent(heater.getName()));
        heaters.add(heater);
        LOGGER.info("New heater added to router {}", heater.getName());
    }

    public void addAirConditioner(AirConditioner airCondition) {
        if (airConditioners.contains(airCondition)) {
            throw new IllegalStateException("Already  exists.");
        }
        eventPublisher.publish(new NewAirConditionEvent(airCondition.getName()));
        airConditioners.add(airCondition);
        LOGGER.info("New airCondition added to router {}", airCondition.getName());
    }


    public void initCurrentTemperature() {
        temperatureSensorStream.skip(currentBuffer).subscribe(sensorData -> {
            eventPublisher.publish(new TemperatureChangedEvent(sensorData.getName(), sensorData.getTemperature()));
            LOGGER.info("From sensor: " + sensorData.getName() + "\t Current temperature: " + String.valueOf(sensorData.getTemperature()));
        });

    }

    public void initAverageTemperature() {
        temperatureSensorStream.buffer(averageBuffer).subscribe(sensorDatas -> {
            double averageTemperature = sensorDatas.stream().mapToInt(i -> i.getTemperature()).average().getAsDouble();
            eventPublisher.publish(new AverageTemperatureEvent(averageTemperature));
            LOGGER.info("New average temperature {}", averageTemperature);
        });

    }

    public void initAppliances() {
        initAirConditioners();
        initHeaters();
    }

    private void initAirConditioners() {
        temperatureSensorStream.buffer(switchBuffer).map(t -> t.stream().mapToInt(i -> i.getTemperature()).average().getAsDouble())
                .subscribe(currentTemperature -> {
                    airConditioners.forEach(airCondition -> {
                        boolean acState = currentTemperature > acLimit;
                        if (acState && !airCondition.isTurnedOn()) {
                            airCondition.turnOn();
                            eventPublisher.publish(new AirConditionStateChangedEvent(airCondition.getName(), true));
                        } else if (!acState && airCondition.isTurnedOn()) {
                            airCondition.turnOff();
                            eventPublisher.publish(new AirConditionStateChangedEvent(airCondition.getName(), false));
                        }
                    });
                });
    }

    private void initHeaters() {
        temperatureSensorStream.buffer(switchBuffer).map(t -> t.stream().mapToInt(i -> i.getTemperature()).average().getAsDouble())
                .subscribe(currentTemperature -> {
                    heaters.forEach(heater -> {
                        boolean heaterState = currentTemperature < heatLimit;
                        if (heaterState && !heater.isTurnedOn()) {
                            heater.turnOn();
                            eventPublisher.publish(new HeaterStateChangedEvent(heater.getName(), true));
                        } else if (!heaterState && heater.isTurnedOn()) {
                            heater.turnOff();
                            eventPublisher.publish(new HeaterStateChangedEvent(heater.getName(), false));
                        }
                    });
                });
    }

    public Map<String, TemperatureSensor> getAvailableSensors() {
        return availableSensors;
    }

    public List<AirConditioner> getAirConditioners() {
        return airConditioners;
    }

    public List<Heater> getHeaters() {
        return heaters;
    }
}