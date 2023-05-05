package com.example.project;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class IsDayOffService {

    private final IsDayOffClient isDayOffClient;

    public boolean isDayOff(LocalDateTime startDate) {

        LocalDate date = startDate.toLocalDate();
        return isDayOffClient.isDayOff(date);
        /*
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = startDate.format(myFormatObj);
        return isDayOff(formattedDate);
        */
    }

    /*
    public boolean isDayOff1(String date) {
        return isDayOffClient.isDayOff(date);
    }
    */

    public boolean checkTimeInterval(LocalDateTime startDate, LocalDateTime endDate) {

        boolean flag = false;
        LocalDate date = startDate.toLocalDate();
        flag = isDayOffClient.isDayOff(date);
        while (flag != true && date.isBefore(endDate.toLocalDate())) {
            date = date.plusDays(1);
            flag = isDayOffClient.isDayOff(date);
        }

        return flag;
    }

}
