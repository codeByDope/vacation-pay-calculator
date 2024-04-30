# Vacation Pay Calculator

## Описание
Приложение разработано на Java с использованием Spring Boot, библиотеки Jollyday для управления праздниками и JDK 11. Оно предоставляет API для расчета размера отпускных на основе даты начала и конца отпуска, а также средней зарплаты за год с использованием XML файла с праздниками(по логике праздники не оплачиваются).

## Использование
Для расчета отпускных отправьте GET запрос на эндпоинт /calculate с параметрами startDate, endDate и avgSalary.
Пример запроса:
```
http://localhost:8080/calculate?startDate=2024-01-01&endDate=2024-01-10&avgSalary=365
```
Получите ответ с размером отпускных:
```
4.0
```
**Важное замечание: программа не учитывает праздники, расположенные в субботу и воскресенье, она перенесет их на понедельник**

**vacationDayPayment округляется до 2-х знаков после запятой!**

## Настройки праздников
Для настройки праздников используется XML-файл my_holiday.xml, который должен содержать информацию о праздниках. Приложение использует библиотеку Jollyday для загрузки и управления праздниками из этого файла.

Пример записи праздника в файле my_holiday.xml:
```
<tns:Fixed month="JANUARY" day="1" descriptionPropertiesKey="NEW_YEAR">
    <tns:MovingCondition substitute="SATURDAY" with="NEXT" weekday="MONDAY"/>
    <tns:MovingCondition substitute="SUNDAY" with="NEXT" weekday="MONDAY"/>
</tns:Fixed>
```
Что касается замечания из предыдущего пункта:
```
Эти строки кода представляют собой условия для перемещения праздников, если они выпадают на определенные дни недели.

<tns:MovingCondition substitute="SATURDAY" with="NEXT" weekday="MONDAY"/> означает, что если праздник выпадает на субботу (SATURDAY), он должен быть перенесен на следующий понедельник (MONDAY).
<tns:MovingCondition substitute="SUNDAY" with="NEXT" weekday="MONDAY"/> означает, что если праздник выпадает на воскресенье (SUNDAY), он также должен быть перенесен на следующий понедельник (MONDAY).
```
Если хотите убрать логику учета праздников-выходных, то необходимо убрать:
```
<tns:MovingCondition substitute="SATURDAY" with="NEXT" weekday="MONDAY"/>
    <tns:MovingCondition substitute="SUNDAY" with="NEXT" weekday="MONDAY"/>
```
Также можно настроить перенос праздника, поменяв значение параметра weekday.
