package com.djl.mq;

import lombok.Data;

/**
 * @author djl
 */
@Data
public class RemoteCommand {
    private Integer messageType;
    private Integer businessCode;
    private Message message;
}
