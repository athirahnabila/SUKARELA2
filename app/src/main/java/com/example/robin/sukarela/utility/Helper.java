package com.example.robin.sukarela.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static String getStringDateTime(Date date) {
        return SimpleDateFormat.getDateTimeInstance().format(date);
    }
}
