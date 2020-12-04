package ru.sfedu.mavenproject.converters;

import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.bean.Order;

public class ConverterOrder extends AbstractBeanField<Order, Integer> {
    private static Logger log = LogManager.getLogger(ConverterOrder.class);
    @Override
    protected Object convert(String s){
        Order order = new Order();
        order.setId(Long.parseLong(s));
        return order;
    }

    public String convertToWrite(Object value){
        String res="";
        Order order = (Order) value;
        res += order.getId();
        return res;
    }
}
