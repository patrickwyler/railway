package ch.teko.railway.controllers;

import ch.teko.railway.dtos.LineDto;
import ch.teko.railway.mappers.LineMapper;
import ch.teko.railway.services.LineService;
import ch.teko.railway.services.StationService;
import ch.teko.railway.services.TraceService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for line functions
 */
@Controller
@RequestMapping("/lines")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LineController {

    LineService lineService;
    StationService stationService;
    TraceService traceService;
    LineMapper lineMapper;

    @Autowired
    public LineController(LineService lineService, StationService stationService, TraceService traceService, LineMapper lineMapper) {
        this.lineService = lineService;
        this.stationService = stationService;
        this.traceService = traceService;
        this.lineMapper = lineMapper;
    }

    @GetMapping("/all")
    public String showAll(final Model model) {
        final List<LineDto> lines = getLineMapper().lineModelsToLineDtos(getLineService().getAllLines());
        model.addAttribute("lines", lines);
        return "lines/all";
    }

    @GetMapping("/create")
    public String createLineForm(final Model model) {
        final LineDto lineDto = LineDto.builder().build();
        model.addAttribute("form", lineDto);
        model.addAttribute("stations", stationService.getAllStations());
        model.addAttribute("traces", traceService.getAllTraces());
        return "lines/create";
    }

    @PostMapping("/create")
    public String createLineSubmit(@ModelAttribute final LineDto lineDto) {
        getLineService().createLine(getLineMapper().lineDtoToLineModel(lineDto));
        return "redirect:/lines/all";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        final LineDto lineDto = getLineMapper().lineModelToLineDto(getLineService().getLineById(id));

        model.addAttribute("form", lineDto);

        return "lines/edit";
    }

    @PostMapping("/update/{id}")
    public String updateLine(@PathVariable("id") long id, @Valid LineDto lineDto, BindingResult result) {
        if (result.hasErrors()) {
            lineDto.setId(id);
            return "lines/edit";
        }

        getLineService().updateLine(getLineMapper().lineDtoToLineModel(lineDto));

        return "redirect:/lines/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteLine(@PathVariable("id") long id) {
        getLineService().deleteLineById(id);
        return "redirect:/lines/all";
    }
}
