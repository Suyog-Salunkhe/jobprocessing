package com.vdx.jobprocessing.impl;

import com.vdx.jobprocessing.service.AbstractJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VdxJobImpl extends AbstractJob {

    private final Logger logger = LoggerFactory.getLogger(VdxJobImpl.class);

    @Override
    public void run() {
        System.out.println("VDX Job run - successful");
        logger.info("Done");
    }

    @Override
    public String getId() {
        return null;
    }
}
