package base.service.appointment.utilit;

import base.service.appointment.dto.DateDto;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DateUtility {

    public Date randomDateMonth(Date startDate, Date endDate) {
        Date randomDate = new Date(ThreadLocalRandom.current()
                .nextLong(startDate.getTime(), endDate.getTime()));
        return randomDate;
    }

    public Date getNextMonth(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        return calendar.getTime();
    }

    public HashMap<DateDto, Date> getFirstLastDayOfMonth(Date date) {
        HashMap<DateDto, Date> dates = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        dates.put(DateDto.FIRST_DAY, calendar.getTime());
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        dates.put(DateDto.LAST_DAY, calendar.getTime());
        return dates;
    }

    public HashMap<DateDto, Date> getFirstLastDayOfNextMonth(Date date, int amount) {
        Date nextMonth = getNextMonth(date, amount);
        return getFirstLastDayOfMonth(nextMonth);
    }

    public String getFormattedDate(SimpleDateFormat dateFormat, Date date){
        return dateFormat.format(date);
    }
}
