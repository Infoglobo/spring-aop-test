package br.com.globo.controller;

import br.com.globo.components.AppConstants;
import br.com.globo.components.UrlBuilder;
import br.com.globo.controller.aspects.SampleControllerAspect;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static br.com.globo.components.AppConstants.HONEST_JASMINE_AJAX_PARAMETER;
import static br.com.globo.components.AppConstants.HONEST_SAMPLE_HEADER;
import static br.com.globo.components.UrlBuilder.REQUEST_PATH_JASMINE;
import static br.com.globo.components.UrlBuilder.REQUEST_PATH_SAMPLE;
import static br.com.globo.components.UrlBuilder.VIEW_PATH_JASMINE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String standardMessage = "Pois é! Você estava procurando por #PARAM# de fato!";

    @Test
    @DisplayName("Deve retornar página de exemplo do Jasmine com eventos no HTML quando acessado " + REQUEST_PATH_JASMINE)
    public void a() throws Exception {

        MvcResult result = mockMvc.perform(get(REQUEST_PATH_JASMINE))
            .andDo(print())
            .andExpect(view().name(VIEW_PATH_JASMINE))
            .andReturn();

        Document document = Jsoup.parse(result.getResponse().getContentAsString());

        assertThat(document.select("form").attr("onsubmit"))
            .isEqualTo("sampleJasmineController.search(event)");

        assertThat(document.select("input[name='sampleJasmine']").attr("onfocus"))
            .isEqualTo("sampleJasmineController.cleanMessage(event)");
    }

    @Test
    @DisplayName("Deve informar que está procurando nada quando consulta vazia")
    public void b() throws Exception {

        String messageToAssert = standardMessage.replace("#PARAM#", "NADA");

        mockMvc.perform(post(REQUEST_PATH_JASMINE))
            .andExpect(jsonPath("$.message", is(messageToAssert)));
    }

    @Test
    @DisplayName("Deve informar que está procurando pela própria mensagem quando consulta possuir algo")
    public void c() throws Exception {

        String param = "LAMBE SAL";
        String messageToAssert = standardMessage.replace("#PARAM#", param);

        mockMvc.perform(post(REQUEST_PATH_JASMINE)
            .param(HONEST_JASMINE_AJAX_PARAMETER, param))
            .andExpect(jsonPath("$.message", is(messageToAssert)));
    }

    @Test
    @DisplayName("Deve retornar 406 pois apenas application/json é suportado")
    public void d() throws Exception {

        mockMvc.perform(post(REQUEST_PATH_JASMINE).accept("text/*"))
            .andExpect(status().isNotAcceptable());

        mockMvc.perform(post(REQUEST_PATH_JASMINE).accept(APPLICATION_XML_VALUE))
            .andExpect(status().isNotAcceptable());
    }
}
