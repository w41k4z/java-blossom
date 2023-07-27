package javablossom.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * The {@code ClassHelper} class is used to provide some useful methods for java
 * class manipulation.
 */
public class ClassHelper {

    private Class<?> javaClass;

    /**
     * Constructor with the class type parameter.
     * 
     * @param javaClass the java class to be set.
     */
    public ClassHelper(Class<?> javaClass) {
        this.setJavaClass(javaClass);
    }

    /**
     * This method set the Class object
     * 
     * @param javaClass
     */
    public void setJavaClass(Class<?> javaClass) {
        this.javaClass = javaClass;
    }

    /**
     * Thie method returns the Class object
     * 
     * @return the Class object
     */
    public Class<?> getJavaClass() {
        return this.javaClass;
    }

    /**
     * This method returns all the methods annotated with the given annotation
     * class.
     * 
     * @param annotationClass the annotation class.
     * @return all the methods annotated with the given annotation class.
     */
    public Method[] getMethodByAnnotation(Class<? extends Annotation> annotationClass) {
        ArrayList<Method> methods = new ArrayList<>();
        for (Method method : this.getJavaClass().getMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                methods.add(method);
            }
        }
        return methods.toArray(new Method[methods.size()]);
    }

    /**
     * This method sets the field value for the given target object using its setter
     * 
     * @param object the object to set the field value from.
     * @param data   the parameter value for the field setter.
     * @param field  the field to set the value for.
     * 
     * @throws NoSuchMethodException     if the field has no setter (following the
     *                                   java naming convention)
     * @throws InvocationTargetException if the setter throws an exception
     * @throws IllegalAccessException    if the setter is not public or the field is
     *                                   final or static
     */
    public static void setObjectFieldValue(Object object, Field field, Object data)
            throws InvocationTargetException, IllegalAccessException {
        Object castedData = null;
        Class<?> fieldType = field.getType().isArray() ? field.getType().getComponentType()
                : field.getType();
        if (data == null || data.toString().trim().length() == 0
                || data.toString().trim().toLowerCase().equals("null")) {
            castedData = null;
        } else {
            // date type: java.sql.Date, java.sql.Time, java.sql.Timestamp
            if (java.util.Date.class.isAssignableFrom(fieldType)) {
                String[] dateFormats = DateHelper.getSupportedPatterns(field);
                for (int i = 0; i < dateFormats.length; i++) {
                    try {
                        castedData = DateHelper.format(fieldType, data.toString().trim(),
                                dateFormats[i]);
                        break;
                    } catch (ParseException e) {
                        if (i == dateFormats.length - 1) {
                            throw new IllegalArgumentException("The date format is not supported");
                        }
                    }
                }
            } else {
                try {
                    // Basic type : INTEGER, STRING, BOOLEAN, DOUBLE, FLOAT, LONG, SHORT, BYTE
                    castedData = fieldType.getConstructor(String.class).newInstance(data.toString());
                } catch (Exception e) {
                    // Object type
                    castedData = data;
                }
            }
        }
        try {
            Method setter = object.getClass().getMethod(StringHelper.toCamelCase("set", field.getName()),
                    field.getType());
            setter.invoke(object, castedData);
        } catch (NoSuchMethodException e) {
            field.setAccessible(true);
            field.set(object, data.getClass().cast(castedData));
        }

    }
}
