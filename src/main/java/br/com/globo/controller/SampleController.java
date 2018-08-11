package br.com.globo.controller;

import br.com.globo.components.AppConstants;
import br.com.globo.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static br.com.globo.components.AppConstants.HONEST_JASMINE_AJAX_PARAMETER;
import static br.com.globo.components.AppConstants.HONEST_SAMPLE_ATTRIBUTE;
import static br.com.globo.components.AppConstants.HONEST_SAMPLE_HEADER;
import static br.com.globo.components.UrlBuilder.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;


@Controller
public class SampleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    private SampleService sampleService;

    @GetMapping(REQUEST_PATH_SAMPLE)
    public ModelAndView sample(@RequestHeader(HONEST_SAMPLE_HEADER) String honestSampleHeader) {

        LOGGER.info("What was received in {}: {}", HONEST_SAMPLE_HEADER, honestSampleHeader);

        sampleService.doTheThing(() -> honestSampleHeader);

        ModelAndView view = new ModelAndView(VIEW_PATH_SAMPLE);
        view.addObject(HONEST_SAMPLE_ATTRIBUTE, honestSampleHeader);

        return view;
    }

    @GetMapping(REQUEST_PATH_JASMINE)
    public String jasmine() {

        return VIEW_PATH_JASMINE;
    }

    @PostMapping(path = REQUEST_PATH_JASMINE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> jasmine(@RequestParam(HONEST_JASMINE_AJAX_PARAMETER) Optional<String> sample) throws InterruptedException {

        var maybeEmptySample = sample.map(s -> s.isEmpty()? null : s);

        LOGGER.info("Looking for {}", maybeEmptySample);

        TimeUnit.SECONDS.sleep(2);

        return ok(new Object() {

            String message = "Pois é! Você estava procurando por " + maybeEmptySample.orElse("NADA") + " de fato!";

            public String getMessage() {

                return message;
            }
        });
    }
}