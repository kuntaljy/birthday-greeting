package com.lijy.greeting;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author lijy
 */
public interface BirthdayGreetingService {
    void sendGreetingMessage() throws URISyntaxException, IOException;
}
