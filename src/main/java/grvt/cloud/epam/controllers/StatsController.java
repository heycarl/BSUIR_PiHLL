package grvt.cloud.epam.controllers;

import grvt.cloud.epam.perfomance_counter.CounterLogic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/api/v0/statistics",
        produces = "application/json"
)
@Tag(name = "Statistics", description = "Gets service usage info")
public class StatsController {
    @RequestMapping(method = RequestMethod.GET)
    @Operation(summary = "Visitors")
    public String StatsEndpoint(){
        JSONObject response = new JSONObject();
        response.put("visitors", CounterLogic.getMetricVisitors());
        return response.toString();
    }
}
