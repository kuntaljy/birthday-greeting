package com.lijy.greeting;

import com.lijy.email.EmailMessage;
import com.lijy.employee.Employee;

/**
 * @author lijy
 */
public class BirthdayGreetingMessage implements EmailMessage {

    // todo 可以从配置文件获取
    private static final String DEFAULT_SUBJECT = "Happy birthday!";
    // todo 可以动配置文件读取，并使用占位符替换Employee中对应的字段
    private static final String DEFAULT_CONTENT_FORMAT = "Happy birthday, dear %s";

    private String email;
    private String subject;
    private String content;

    BirthdayGreetingMessage(Employee employee) {
        this.email = employee.getEmail();
        this.subject = DEFAULT_SUBJECT;
        this.content = String.format(DEFAULT_CONTENT_FORMAT, employee.getFirstName());
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getSubject() {
        return this.subject;
    }

    @Override
    public String getContent() {
        return this.content;
    }
}
