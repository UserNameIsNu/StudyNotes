package edu.nf.ch03;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.trace("trace:{}", 1);
        log.debug("debug:{}", 2);
        log.info("info:{}", 3);
        log.warn("warn:{}", 4);
        log.error("error:{}", 5);
    }
}
