package com.example.vacationpaycalculator.controller;

import com.example.vacationpaycalculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class MainController {
    private final CalculatorService calculatorService;

    @GetMapping("/calculate")
    public Double calculateVacationPay(
            @RequestParam @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam @NotNull @Positive Double avgSalary
    ) {
        log.info("Получен запрос на расчет отпускных со следующими параметрами:\n" +
                "{} - дата начала отпуска, {} - дата конца отпуска, " +
                "{} - ср. зп за 12 месяцев", startDate, endDate, avgSalary);

        return calculatorService.calculateVacationPay(startDate, endDate, avgSalary);
    }
}
