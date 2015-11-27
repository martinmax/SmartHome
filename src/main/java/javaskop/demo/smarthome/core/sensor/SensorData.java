package javaskop.demo.smarthome.core.sensor;

/**
 * Created by martin.ilievski on 11/20/2015.
 */
public class SensorData {
    private Integer temperature;
    private String name;

    public SensorData(Integer temperature, String name) {
        this.temperature = temperature;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getTemperature() {
        return temperature;
    }
}
