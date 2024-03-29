package com.lijy.employee;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

/**
 * 使用CSV格式的数据构建Employee对象. 调用方式{@code new CsvEmployeeBuilder().headerLine(headerLine).dataLine(dataLine).build()}，
 * 其中headerLine为CSV表头数据，dataLine为当前要处理的数据行，利用{@code Employee}字段上的{@code Column}注解来映射数据与字段的关系，
 *
 * @author lijy
 */
public class CsvEmployeeBuilder implements EmployeeBuilder {

    private static final String CSV_SPLITER = "\\s*,\\s*";

    private String[] headers;
    private String[] data;

    public CsvEmployeeBuilder headerLine(String headerLine) {
        this.headers = csvDataSplit(headerLine);
        return this;
    }

    public CsvEmployeeBuilder dataLine(String dataLine) {
        this.data = csvDataSplit(dataLine);
        return this;
    }

    @Override
    public Employee build() {
        if (Objects.isNull(headers)) {
            throw new IllegalStateException("Call headerLine before");
        }
        if (Objects.isNull(data)) {
            throw new IllegalStateException("Call dataLine before");
        }

        Employee employee = new Employee();
        for (int i = 0; i < headers.length; i++) {
            String header = headers[i];
            String value = data[i];
            setValue(employee, header, value);
        }
        return employee;
    }

    private void setValue(Employee employee, String header, String value) {
        Arrays.stream(employee.getClass().getDeclaredFields())
                .filter(field -> columnMatch(field, header))
                .findFirst()
                .ifPresent(field -> setValueInternal(employee, field, value));
    }

    private boolean columnMatch(Field field, String header) {
        Column column = field.getAnnotation(Column.class);
        return Objects.nonNull(column) && column.value().equals(header);
    }

    private void setValueInternal(Employee employee, Field field, String value) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), Employee.class);
            Column column = field.getAnnotation(Column.class);
            String format = column.format();
            Object arg = parse(value, format, field.getType());
            pd.getWriteMethod().invoke(employee, arg);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            // todo 记录异常日志
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T parse(String value, String format, Class<T> target) {
        if (target.isAssignableFrom(String.class)) {
            return (T) value;
        } else if (target.isAssignableFrom(LocalDate.class)) {
            return (T) LocalDate.parse(value, DateTimeFormatter.ofPattern(format));
        } else {
            // todo 支持更多的类型
            throw new NotImplementedException();
        }
    }

    private String[] csvDataSplit(String line) {
        return line.split(CSV_SPLITER);
    }
}
