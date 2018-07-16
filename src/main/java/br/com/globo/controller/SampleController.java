package br.com.globo.controller;

import br.com.globo.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.ModelAndView;

import static br.com.globo.components.AppConstants.HONEST_SAMPLE_ATTRIBUTE;
import static br.com.globo.components.AppConstants.HONEST_SAMPLE_HEADER;
import static br.com.globo.components.UrlBuilder.REQUEST_PATH_SAMPLE;
import static br.com.globo.components.UrlBuilder.VIEW_PATH_SAMPLE;


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
}