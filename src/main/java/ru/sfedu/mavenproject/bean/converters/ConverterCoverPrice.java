package ru.sfedu.mavenproject.bean.converters;

import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.bean.CoverPrice;

import java.util.ArrayList;
import java.util.List;

public class ConverterCoverPrice extends AbstractBeanField<CoverPrice, Integer> {
    private static Logger log = LogManager.getLogger(ConverterCoverPrice.class);

    @Override
    protected Object convert(String s){
        String indexString;
        indexString = s.substring(1, s.length() - 1);
        String[] unparsedIndexList = indexString.split(",");
        List<CoverPrice> indexEmployeeList = new ArrayList<>();
        for (String strIndex : unparsedIndexList) {
            if (!strIndex.isEmpty()) {
                CoverPrice coverPrice = new CoverPrice();
                coverPrice.setId(Long.parseLong(strIndex));
                indexEmployeeList.add(coverPrice);
            }
        }
        return indexEmployeeList;
    }

    public String convertToWrite(Object value){
        List<CoverPrice> coverPrices = (List<CoverPrice>) value;
        StringBuilder builder = new StringBuilder("[");
        if (coverPrices.size() > 0) {
            for (CoverPrice coverPrice : coverPrices) {
                builder.append(coverPrice.getId());
                builder.append(",");
            }

            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append("]");
        //log.debug(builder.toString());
        return builder.toString();
    }
}
