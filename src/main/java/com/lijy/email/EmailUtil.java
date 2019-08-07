package com.lijy.email;

/**
 * @author lijy
 */
public final class EmailUtil {

    private EmailUtil() {
    }

    // todo 实现send方法
    public static void send(EmailMessage message) {
        System.out.println(message.getEmail());
        System.out.println(message.getSubject());
        System.out.println(message.getContent());
        System.out.println("==================");
    }
}
