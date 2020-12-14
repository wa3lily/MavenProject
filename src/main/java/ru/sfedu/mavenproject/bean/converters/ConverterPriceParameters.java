package ru.sfedu.mavenproject.bean.converters;

import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.bean.PriceParameters;

public class ConverterPriceParameters extends AbstractBeanField<PriceParameters, Integer> {
    private static Logger log = LogManager.getLogger(ConverterPriceParameters.class);
    @Override
    protected Object convert(String s){
        PriceParameters priceParameters = new PriceParameters();
        priceParameters.setId(Long.parseLong(s));
        return priceParameters;
    }

    public String convertToWrite(Object value){
        String res="";
        PriceParameters priceParameters = (PriceParameters) value;
        res += priceParameters.getId();
        return res;
    }
}
