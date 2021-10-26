package com.vdx.jobprocessing.service;

public interface Job {

    void run();

    String getId();

    boolean initialize();
}
