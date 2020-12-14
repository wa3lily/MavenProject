package ru.sfedu.mavenproject.bean.converters;

import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.bean.Meeting;

public class ConverterMeeting extends AbstractBeanField<Meeting, Integer> {
    private static Logger log = LogManager.getLogger(ConverterMeeting.class);
    @Override
    protected Object convert(String s){
        Meeting meeting;
        if (Long.parseLong(s)>-1) {
            meeting = new Meeting();
            meeting.setId(Long.parseLong(s));
        }else{
            meeting = null;
        }
        return meeting;
    }

    public String convertToWrite(Object value){
        String res="";
        Meeting meeting = (Meeting) value;
        try{
            res += meeting.getId();
        }catch (NullPointerException e){
            res += -1;
        }
        return res;
    }
}
