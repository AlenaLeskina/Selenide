package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {

    public static String date(Integer days){

        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
