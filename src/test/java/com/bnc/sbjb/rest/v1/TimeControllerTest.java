package com.bnc.sbjb.rest.v1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bnc.sbjb.config.WebConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@Import({
    WebConfiguration.class,
})
@WebMvcTest(TimeController.class)
public class TimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getTime() throws Exception {
        mockMvc.perform(get("/1/time")).andExpect(status().isOk());
    }

    @Test
    public void getPrettyTime() throws Exception {
        mockMvc.perform(get("/1/time/pretty")).andExpect(status().isOk());
    }
}
