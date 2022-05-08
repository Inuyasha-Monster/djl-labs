package com.djlnet.config;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author djl
 * @create 2022/5/8 12:04
 */
@Data
public class UpdateConfigReq {
    @NotBlank
    private String configKey;

    @NotBlank
    private String newValue;
}
