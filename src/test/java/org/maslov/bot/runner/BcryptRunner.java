package org.maslov.bot.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class BcryptRunner {

    public static String PASSWORD = "admin";

    public static void main(String[] args) {
        var encoder = new BCryptPasswordEncoder();
        var encoded = encoder.encode(PASSWORD);
        log.info("password: {} encoded: {}", PASSWORD, encoded);
    }
}
