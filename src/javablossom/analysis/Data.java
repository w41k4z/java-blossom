package javablossom.analysis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Data {

    /**
     * This method is used to get the sum of a given array of any object.
     * 
     * @param obj         The array of object to get the sum from.
     * @param fieldGetter The getter method of the field to get the sum from or null
     *                    if `obj` is an array of number.
     * @return The sum of the given array of object.
     * @throws NoSuchMethodException     If the getter method is not found.
     * @throws IllegalAccessException    If the getter method is not accessible.
     * @throws InvocationTargetException If the getter method throws an exception.
     */
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
