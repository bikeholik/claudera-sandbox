package com.github.bikeholik.cloudera.query;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
class DateFormatValidator {
    private final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    @SneakyThrows
    String validateAndFormat(String dateAsString) {
        Date date = dateFormat.get().parse(dateAsString);
        return dateFormat.get().format(date);
    }
}
