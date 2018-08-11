package br.com.globo.controller.aspects;

import br.com.globo.MainApplication;
import br.com.globo.components.AppConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static br.com.globo.components.AppConstants.HONEST_SAMPLE_HEADER;
import static br.com.globo.components.UrlBuilder.REQUEST_PATH_SAMPLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Teste do proxy")
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerAspectTest {

    @SpyBean
    private SampleControllerAspect sampleControllerAspect;
    @Autowired
    private MockMvc mockMvc;

    private String honestSampleHeaderWithStandardEncoding = "{  \"honest-parameter\": \"Por quÃª? Ã\u0089 por que o porquÃª das coisas sÃ£o estudadas. Estudo porque Ã© importante\" }";
    private String honestSampleHeaderConverted = "{  \"honest-parameter\": \"Por quê? É por que o porquê das coisas são estudadas. Estudo porque é importante\" }";

    @Test
    @DisplayName("Deve codificar o header como UTF-8 para ficar legível")
    public void a() throws Throwable {

        ResultActions resultActions = mockMvc.perform(get(REQUEST_PATH_SAMPLE)
            .header(HONEST_SAMPLE_HEADER, honestSampleHeaderWithStandardEncoding))
            .andDo(print());

        MvcResult result = resultActions.andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        verify(sampleControllerAspect).aroundWallMethod(any(), anyString());

        assertThat(result.getModelAndView().getModelMap())
            .hasSize(1).containsKey(AppConstants.HONEST_SAMPLE_ATTRIBUTE)
            .extracting(m -> m.get(AppConstants.HONEST_SAMPLE_ATTRIBUTE))
            .containsExactly(honestSampleHeaderConverted);
    }
}