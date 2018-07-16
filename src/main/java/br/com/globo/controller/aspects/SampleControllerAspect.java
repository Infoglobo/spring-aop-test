package br.com.globo.controller.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static br.com.globo.support.Encodes.encodeFromHeaderParameter;

@Aspect
@Component
public class SampleControllerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleControllerAspect.class);

    @Around("execution(* br.com.globo.controller.SampleController.sample(String)) && args(honestSampleHeader)")
    public ModelAndView aroundWallMethod(ProceedingJoinPoint proceedingJoinPoint, String honestSampleHeader) throws Throwable {

        LOGGER.debug("What was intercepted: {}", honestSampleHeader);

        return (ModelAndView) proceedingJoinPoint.proceed(List.of(encodeFromHeaderParameter(honestSampleHeader)).toArray());
    }
}