package com.example.vacationpaycalculator.service;

import java.time.LocalDate;

public interface CalculatorService {
    Double calculateVacationPay(LocalDate startDate, LocalDate endDate, Double avgSalary);
}
