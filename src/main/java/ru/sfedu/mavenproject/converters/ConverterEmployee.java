package ru.sfedu.mavenproject.converters;

import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.mavenproject.bean.Employee;

public class ConverterEmployee extends AbstractBeanField<Employee, Integer> {
    private static Logger log = LogManager.getLogger(ConverterEmployee.class);
    @Override
    protected Object convert(String s){
        Employee employee = new Employee();
        employee.setId(Long.parseLong(s));
        return employee;
    }

    public String convertToWrite(Object value){
        String res="";
        Employee employee = (Employee) value;
        res += employee.getId();
        return res;
    }
}
