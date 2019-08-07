package com.lijy;

import com.lijy.employee.CsvEmployeeBuilder;
import com.lijy.employee.Employee;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author lijy
 */
public class CsvEmployeeBuilderTest {
    @Test
    public void build() throws Exception {
        CsvEmployeeBuilder employeeBuilder = new CsvEmployeeBuilder();
        Employee employee = employeeBuilder
                .headerLine("gender, last_name, first_name, job, date_of_birth, email")
                .dataLine("Man, Anb, Maryfg, manager, 1974/09/11, mary.ann@foobar.com")
                .build();
        Assert.assertTrue(employee.getFirstName().equals("Maryfg"));
        Assert.assertTrue(employee.getLastName().equals("Anb"));
        Assert.assertTrue(employee.getBirthDay().equals(
                LocalDate.parse("1974/09/11", DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
        Assert.assertTrue(employee.getEmail().equals("mary.ann@foobar.com"));
    }

}