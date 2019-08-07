package com.lijy.greeting;

import com.lijy.email.EmailService;
import com.lijy.employee.CsvEmployeeBuilder;
import com.lijy.employee.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author lijy
 */
public class BirthdayGreetingServiceImpl implements BirthdayGreetingService {

    @Override
    public void sendGreetingMessage() throws URISyntaxException, IOException {
        String filePath = "/employee_record.txt";
        URI fileUri = this.getClass().getResource(filePath).toURI();

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(fileUri))) {
            CsvEmployeeBuilder employeeBuilder = new CsvEmployeeBuilder();
            employeeBuilder.headerLine(bufferedReader.readLine());
            EmailService emailService = new EmailService();

            bufferedReader.lines()
                    .map(csvline -> employeeBuilder.dataLine(csvline).build())
                    .filter(Employee::isMyBirthDayToday)
                    .map(BirthdayGreetingMessage::new)
                    .forEach(emailService::send);
        }
    }
}
