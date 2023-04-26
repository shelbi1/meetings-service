package com.example.project.init;

import com.example.project.dao.MeetingDAO;
import com.example.project.entity.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Component
public class DataInit implements ApplicationRunner {

    private MeetingDAO meetingDAO;

    @Autowired
    public DataInit(MeetingDAO meetingDAO) {
        this.meetingDAO = meetingDAO;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = meetingDAO.count();

        if (count == 0) {
            Meeting m1 = new Meeting();
            m1.setName("Conference");

            UUID creator1 = UUID.randomUUID();
            m1.setCreatedById(creator1);

            LocalDateTime startDate1 = LocalDateTime.now();
            m1.setStartDate(startDate1);

            LocalDateTime endDate1 = LocalDateTime.now().plusHours(3);
            m1.setEndDate(endDate1);

            Meeting m2 = new Meeting();
            m1.setName("Consultation");

            UUID creator2 = UUID.randomUUID();
            m1.setCreatedById(creator2);

            LocalDateTime startDate2 = LocalDateTime.now();
            m1.setStartDate(startDate2);

            LocalDateTime endDate2 = LocalDateTime.now().plusDays(1);
            m1.setEndDate(endDate2);

            meetingDAO.save(m1);
            meetingDAO.save(m2);
        }
    }

}
