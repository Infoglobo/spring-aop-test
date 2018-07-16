package br.com.globo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class SampleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleService.class);

    public void doTheThing(Supplier<String> supplier) {

        LOGGER.info("Wow! I received the following: {}", supplier.get());
    }
}