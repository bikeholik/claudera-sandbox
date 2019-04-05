package com.github.bikeholik.cloudera.query;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

@RestController
@RequestMapping("v1/analytics")
@RequiredArgsConstructor
public class QueryController {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final DateFormatValidator validator;
    private final QueryProperties queryProperties;

    @GetMapping({"1", "max-temperature-per-device"})
    List<Map<String, Object>> getMaxTemperaturePerDevice() {
        return jdbcOperations.queryForList(queryProperties.getMaxTemperaturePerDevice(), Collections.<String, Object>emptyMap());
    }

    @GetMapping({"2", "number-of-points-per-device"})
    List<Map<String, Object>> getNumberOfPointsPerDevice() {
        return jdbcOperations.queryForList(queryProperties.getAmountOfPointsPerDevice(), Collections.<String, Object>emptyMap());
    }

    @GetMapping(value = {"3", "max-temperature-per-device"}, params = "date")
    List<Map<String, Object>> getMaxTemperaturePerDevice(@RequestParam("date") String dateAsString) {
        String date = validator.validateAndFormat(dateAsString);
        return jdbcOperations.queryForList(queryProperties.getMaxTemperaturePerDeviceOnGivenDay(), singletonMap("date", date));
    }
}
