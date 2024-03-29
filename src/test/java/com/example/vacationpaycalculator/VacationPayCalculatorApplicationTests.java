package com.example.vacationpaycalculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//365 в качестве avgSalary - для удобства восприятия.

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class VacationPayCalculatorApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    /*Чтоб тесты не были громоздкими, я объединил проверку корректных параметров с вычислением отпускных с 1 по 10 января.
    С 1 по 10 января не праздники только 9 и 10 число.
    ОЧЕНЬ ВАЖНОЕ УТОЧНЕНИЕ!!!
    Программа не учитывает праздник, расположенный в сб или вскр, если необходимо убрать такую логику
    в файле my_holiday.xml нужно убрать:
    <tns:MovingCondition substitute="SATURDAY" with="NEXT" weekday="MONDAY"/>
    <tns:MovingCondition substitute="SUNDAY" with="NEXT" weekday="MONDAY"/>
    Также в этот xml можно добавить любой другой праздник.
     */
    @Test
    public void validParametersWithVacFrom1stJanTo10thJan() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-01-10")
                        .param("avgSalary", "365"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("4.0"));
    }

    @Test
    public void missingParameter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("endDate", "2024-01-10")
                        .param("avgSalary", "365"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void invalidDateFormat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("startDate", "2024/01/01")
                        .param("endDate", "2024-01-10")
                        .param("avgSalary", "365"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void negativeSalary() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-01-10")
                        .param("avgSalary", "-365"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // В эти числа праздники отсутствуют
    @Test
    public void vacationFrom1stFebTo4thFeb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("startDate", "2024-02-01")
                        .param("endDate", "2024-02-04")
                        .param("avgSalary", "365"))
                .andExpect(status().isOk())
                .andExpect(content().string("4.0"));

    }

    @Test
    public void vacationFrom1stMarchTo12thMarch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("startDate", "2024-03-01")
                        .param("endDate", "2024-03-12")
                        .param("avgSalary", "365"))
                .andExpect(status().isOk())
                .andExpect(content().string("11.0"));
    }

    //vacationDayPayment округляется до 2х знаков после запятой
    @Test
    public void vacationFrom1stJanTo10thJan() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-01-10")
                        .param("avgSalary", "1000"))
                .andExpect(status().isOk())
                .andExpect(content().string("10.96"));
    }
}
