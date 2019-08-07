package com.lijy;

import com.lijy.greeting.BirthdayGreetingService;
import com.lijy.greeting.BirthdayGreetingServiceImpl;
import org.junit.Test;

/**
 * @author lijy
 */
public class BirthdayGreetingServiceImplTest {
    @Test
    public void sendGreetingMessage() throws Exception {
        BirthdayGreetingService birthdayGreetingService = new BirthdayGreetingServiceImpl();
        birthdayGreetingService.sendGreetingMessage();
    }
}