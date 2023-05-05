package com.example.project;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@FeignClient(name = "IsDayOffClient", url = "https://isdayoff.ru")
public interface IsDayOffClient {

    @GetMapping("/{startDate}")
    boolean isDayOff(@PathVariable("startDate") LocalDate startDate);

    //boolean isDayOff(@PathVariable String startDate);
}
