package com.example.vacationpaycalculator.service.impl;

import com.example.vacationpaycalculator.service.CalculatorService;
import de.jollyday.HolidayManager;
import de.jollyday.ManagerParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDate;

@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {

    private static final int DAYS_IN_YEAR = 365;
    private final HolidayManager holidayManager;

    public CalculatorServiceImpl() {
        URL url = getClass().getClassLoader().getResource("my_holiday.xml");
        this.holidayManager = HolidayManager.getInstance(ManagerParameters.create(url));
    }

    @Override
    public Double calculateVacationPay(LocalDate startDate, LocalDate endDate, Double avgSalary) {
        log.info("Начался процесс вычисления отпускных...");

        double vacationDayPayment = Math.round(avgSalary / DAYS_IN_YEAR * 100.0) / 100.0;
        double res = 0;
        System.out.println(holidayManager.getHolidays(2024));
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (!holidayManager.isHoliday(date)) {
                log.info("{} - не праздник. За него выплата будет!", date);
                res += vacationDayPayment;
            } else {
                log.info("{} - праздник. За него выплаты не будет!", date);
            }
        }

        log.info("Размер отпускных: {}.", res);
        return res;
    }
}