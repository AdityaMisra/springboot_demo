package com.learn.demo.util;

import org.springframework.stereotype.Component;

/**
 * Created on 15/3/21, 9:54 PM
 * DemoUtil.java
 *
 * @author aditya.misra
 */


@Component
public class DemoUtil {
    public String someUtil() {
        return "This is a utility method";
    }
}
