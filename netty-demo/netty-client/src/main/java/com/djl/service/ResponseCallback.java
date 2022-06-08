package com.djl.service;

/**
 * @author djl
 */
@FunctionalInterface
public interface ResponseCallback {

    /**
     * onComplete
     *
     * @param o
     */
    void onResponse(Object o);
}
