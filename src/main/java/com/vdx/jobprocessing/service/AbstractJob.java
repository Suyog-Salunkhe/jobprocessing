package com.vdx.jobprocessing.service;

public abstract class AbstractJob implements Job {

    @Override
    public boolean initialize() {
        //if implementor class has multiple parameters to be initialized
        return true;
    }
}
