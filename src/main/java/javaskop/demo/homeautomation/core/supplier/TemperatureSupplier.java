package javaskop.demo.homeautomation.core.supplier;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by martin.ilievski on 11/19/2015.
 */
public class TemperatureSupplier implements Supplier<Integer> {

    private static final Integer INITIAL_TEMP = 20;
    private static final Integer DELTA_TEMP = 1;
    private static final Integer MIN_TEMP = -5;
    private static final Integer MAX_TEMP = 30;
    private Random randomTemp = new Random();
    private int currentTemp = INITIAL_TEMP;

    @Override
    public Integer get() {
        int deltaTemp = randomTemp.nextInt(DELTA_TEMP + 1);
        int deltaTempSignum = Integer.signum(randomTemp.nextInt());

        int newTemp = currentTemp + deltaTemp * deltaTempSignum;
        if (newTemp <= MAX_TEMP && newTemp >= MIN_TEMP) {
            currentTemp = newTemp;
        }
        return currentTemp;
    }
}
