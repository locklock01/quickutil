/**
 * 邮件发送工具
 *
 * @class MailUtil
 * @author 0.5
 */
package com.quickutil.platform;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MailPoolUtil {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MailPoolUtil.class);

    private static Map<String, MailUtil> sessionMap = new HashMap<>();

    public MailPoolUtil() {
    }

    public MailPoolUtil(Properties mailProperties) {
        Enumeration<?> keys = mailProperties.propertyNames();
        List<String> keyList = new ArrayList<>();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            key = key.split("\\.")[0];
            if (!keyList.contains(key)) {
                keyList.add(key);
            }
        }
        for (String key : keyList) {
            try {
                String host = mailProperties.getProperty(key + ".host");
                String port = mailProperties.getProperty(key + ".port");
                String username = mailProperties.getProperty(key + ".username");
                String password = mailProperties.getProperty(key + ".password");
                boolean isSSL = Boolean.parseBoolean(mailProperties.getProperty(key + ".isSSL"));
                sessionMap.put(key, new MailUtil(host, port, username, password, isSSL));
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
    }

    public void add(String key, String host, String port, String username, String password, boolean isSSL) {
        sessionMap.put(key, new MailUtil(host, port, username, password, isSSL));
    }

    public MailUtil get(String key) {
        return sessionMap.get(key);
    }

    public MailUtil remove(String key) {
        return sessionMap.remove(key);
    }
}
