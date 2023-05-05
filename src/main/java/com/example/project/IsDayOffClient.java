package com.example.project;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@FeignClient(name = "IsDayOffClient", url = "https://isdayoff.ru")
public interface IsDayOffClient {

    @GetMapping("/{startDate}")
    ResponseEntity<String> isDayOff(@PathVariable("startDate")
                 @DateTimeFormat(pattern = "yyyy-MM-dd")
                 final LocalDate startDate);
}
