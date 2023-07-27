package javablossom.analysis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Data {

    public static double sum(Object[] obj, String fieldGetter)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (obj == null || obj.length == 0) {
            return 0;
        }
        double sum = 0;
        if (Number.class.isAssignableFrom(obj.getClass().getComponentType())) {
            Method doubleValue = Number.class.getMethod("doubleValue");
            for (int i = 0; i < obj.length; i++) {
                Double value = (Double) doubleValue.invoke(obj[i]);
                sum += value == null ? 0 : value;
            }
        } else {
            Method getter = Number.class.getMethod(fieldGetter);
            Double[] values = new Double[obj.length];
            for (int i = 0; i < obj.length; i++) {
                Double value = (Double) getter.invoke(obj[i]);
                values[i] = value == null ? 0 : value;
            }
            sum = sum(values, null);
        }
        return sum;
    }
}
