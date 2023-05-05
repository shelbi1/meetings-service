package com.example.project;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IsDayOffService {

    private final IsDayOffClient isDayOffClient;

    public ResponseEntity<String> isDayOff(LocalDate startDate) {
        return isDayOffClient.isDayOff(startDate);
    }

    public boolean checkTimeInterval(LocalDateTime startDate, LocalDateTime endDate) {

        boolean flag = false;
        LocalDate date = startDate.toLocalDate();
        LocalDate localEndDate = endDate.toLocalDate();
        //flag = isDayOffClient.isDayOff(date);
        while (flag != true && date.isBefore(localEndDate)) {
            date = date.plusDays(1);
            //flag = isDayOffClient.isDayOff(date);
        }

        return flag;
    }



}
