package ru.sfedu.mavenproject.converters;

import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.bean.CoverPrice;

public class ConverterCoverPrice extends AbstractBeanField<CoverPrice, Integer> {
    private static Logger log = LogManager.getLogger(ConverterCoverPrice.class);
    @Override
    protected Object convert(String s){
        CoverPrice coverPrice = new CoverPrice();
        coverPrice.setId(Long.parseLong(s));
        return coverPrice;
    }

    public String convertToWrite(Object value){
        String res="";
        CoverPrice coverPrice = (CoverPrice) value;
        res += coverPrice.getId();
        return res;
    }
}
