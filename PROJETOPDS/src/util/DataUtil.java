/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sandr
 */
public class DataUtil {
    public static String dateToString(long mili){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mili);
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        return formatador.format(calendar.getTime());
    }
    
    public static Calendar stringToCalendar(String string) throws ParseException{
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        Date dateSimples = formatador.parse(string);
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(dateSimples);
        return dataCalendar;
    }
    
    public static Calendar dateToCalendar(Date data){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(data.getTime());
        return calendar;
    }
}
