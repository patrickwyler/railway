package ch.teko.railway.controllers;

import ch.teko.railway.dtos.*;
import ch.teko.railway.mappers.LineMapper;
import ch.teko.railway.services.LineService;
import ch.teko.railway.services.StationService;
import ch.teko.railway.services.TraceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for stationplan which is generated with http://visjs.org/docs/network/
 */
@Controller
@Getter
@RequestMapping("/plans")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationplanController {

    private static final String BLACK = "black";
    private static final String HEXAGON = "hexagon";
    private static final String DOT = "dot";
    private static final int SIZE_ROOT = 20;
    private static final int SIZE_STANDARD = 10;

    LineService lineService;
    StationService stationService;
    TraceService traceService;
    LineMapper lineMapper;
    ObjectMapper objectMapper;

    @Autowired
    public StationplanController(LineService lineService, StationService stationService, TraceService traceService, LineMapper lineMapper, ObjectMapper objectMapper) {
        this.lineService = lineService;
        this.stationService = stationService;
        this.traceService = traceService;
        this.lineMapper = lineMapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/stationplan")
    public String showStationPlan(final Model model) throws JsonProcessingException {
        populateStationplanDtos(model);

        return "/plans/stationplan";
    }

    // Populate data needed for generating the station plan with visjs javascript module
    private void populateStationplanDtos(Model model) throws JsonProcessingException {
        final List<LineDto> lines = getLineMapper().lineModelsToLineDtos(getLineService().getAllLines());

        final List<StationplanNodeDto> nodeDtos = new ArrayList<>();
        final List<StationplanEdgeDto> edgeDtos = new ArrayList<>();

        for (LineDto lineDto : lines) {
            List<LinePartDto> lineParts = lineDto.getLineParts();
            for (int i = 0; i < lineParts.size(); i++) {
                LinePartDto linePartDto = lineParts.get(i);
                if (linePartDto instanceof StationDto) {
                    final StationDto station = (StationDto) linePartDto;

                    nodeDtos.add(StationplanNodeDto.builder()
                            .id(station.getId())
                            .label(station.getName())
                            .size((station.isRootStation() ? SIZE_ROOT : SIZE_STANDARD))
                            .shape((station.isRootStation() ? HEXAGON : DOT))
                            .color(BLACK)
                            .build());
                } else {
                    edgeDtos.add(StationplanEdgeDto.builder()
                            .from(((StationDto) lineParts.get(i - 1)).getId())
                            .to(((StationDto) lineParts.get(i + 1)).getId())
                            .length(10)
                            .build());
                }
            }
        }

        model.addAttribute("nodesArray", getObjectMapper().writeValueAsString(nodeDtos.toArray()));
        model.addAttribute("edgesArray", getObjectMapper().writeValueAsString(edgeDtos.toArray()));
    }

}
