package base.service.appointment.task;

import base.service.appointment.dto.DateDto;
import base.service.appointment.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import base.service.appointment.utilit.DateUtility;

import java.util.*;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    AppointmentService service;
    @Autowired
    DateUtility dateUtility;

    @Scheduled(cron = "* 55 23 28-31 * *")
    public void generateAppointments() {
        HashMap<DateDto, Date> dateMap = dateUtility.getFirstLastDayOfNextMonth(new Date(), 1);
        service.generateAppointments(dateMap);
    }
}
