package grvt.cloud.epam_web.controllers;

import grvt.cloud.epam_web.perfomance_counter.CounterLogic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(TriangleController.class);

    @RequestMapping(method = RequestMethod.GET)
    @Operation(summary = "Visitors")
    public String greetings(){
        JSONObject response = new JSONObject();
        response.put("visitors", CounterLogic.getMetric_visitors());
        return response.toString();
    }

}
