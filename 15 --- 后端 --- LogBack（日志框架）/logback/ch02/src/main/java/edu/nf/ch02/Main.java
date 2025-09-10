package edu.nf.ch02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.trace("trace:{}", 1);
        logger.debug("debug:{}", 2);
        logger.info("info:{}", 3);
        logger.warn("warn:{}", 4);
        logger.error("error:{}", 5);
    }
}
