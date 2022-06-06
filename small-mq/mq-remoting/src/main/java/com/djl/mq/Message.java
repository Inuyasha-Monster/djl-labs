package com.djl.mq;

import lombok.Data;

import java.util.Map;

/**
 * @author djl
 */
@Data
public class Message {
    private Map<String, String> headers;
    private String body;
}
