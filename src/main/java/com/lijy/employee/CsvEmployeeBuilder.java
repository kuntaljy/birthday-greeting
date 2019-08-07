package com.lijy.employee;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author lijy
 */
public class CsvEmployeeBuilder implements EmployeeBuilder {

    private String[] headers;
    private String[] data;

    public CsvEmployeeBuilder headerLine(String headerLine) {
        this.headers = csvDataSplit(headerLine);
        return this;
    }

    public EmployeeBuilder dataLine(String dataLine) {
        this.data = csvDataSplit(dataLine);
        return this;
    }

    @Override
    public Employee build() {
        Employee employee = new Employee();
        for (int i = 0; i < headers.length; i++) {
            String header = headers[i];
            String value = data[i];
            setValue(employee, header, value);
        }
        return employee;
    }

    private static void setValue(Employee employee, String header, String value) {
        Arrays.stream(employee.getClass().getDeclaredFields())
                .filter(field -> columnMatch(field, header))
                .findFirst()
                .ifPresent(field -> setValueInternal(employee, field, value));
    }

    private static boolean columnMatch(Field field, String header) {
        Column column = field.getAnnotation(Column.class);
        return Objects.nonNull(column) && column.value().equals(header);
    }

    private static void setValueInternal(Employee employee, Field field, String value) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), Employee.class);
            Column column = field.getAnnotation(Column.class);
            String format = column.format();
            Object arg = parse(value, format, field.getType());
            pd.getWriteMethod().invoke(employee, arg);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T parse(String value, String format, Class<T> target){
        if(target.isAssignableFrom(String.class)){
            return (T)value;
        }else if(target.isAssignableFrom(LocalDate.class)){
            return (T)LocalDate.parse(value, DateTimeFormatter.ofPattern(format));
        }else {
            throw new NotImplementedException();
        }
    }

    private static String[] csvDataSplit(String line) {
        return line.split("\\s*,\\s*");
    }
}
