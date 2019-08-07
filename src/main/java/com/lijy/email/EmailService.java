package com.lijy.email;

/**
 * @author lijy
 */
public class EmailService {

    // todo 实现send方法
    public void send(EmailMessage message) {
        System.out.println(message.getEmail());
        System.out.println(message.getSubject());
        System.out.println(message.getContent());
        System.out.println("==================");
    }
}
