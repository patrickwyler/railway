package ch.teko.railway.controllers;

import ch.teko.railway.dtos.TraceDto;
import ch.teko.railway.mappers.TraceMapper;
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
 * Controller for trace functions
 */
@Controller
@RequestMapping("/traces")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraceController {

    TraceService traceService;
    TraceMapper traceMapper;

    @Autowired
    public TraceController(TraceService traceService, TraceMapper traceMapper) {
        this.traceService = traceService;
        this.traceMapper = traceMapper;
    }

    @GetMapping("/all")
    public String showAll(final Model model) {
        final List<TraceDto> traces = getTraceMapper().traceModelsToTraceDtos(getTraceService().getAllTraces());
        model.addAttribute("traces", traces);
        return "traces/all";
    }

    @GetMapping("/create")
    public String createTraceForm(final Model model) {
        final TraceDto traceDto = TraceDto.builder().build();

        model.addAttribute("form", traceDto);
        model.addAttribute("traces", getTraceMapper().traceModelsToTraceDtos(getTraceService().getAllTraces()));

        return "traces/create";
    }

    @PostMapping("/create")
    public String createTraceSubmit(@ModelAttribute final TraceDto traceDto) {
        getTraceService().createTrace(getTraceMapper().traceDtoToTraceModel(traceDto));
        return "redirect:/traces/all";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        final TraceDto traceDto = getTraceMapper().traceModelToTraceDto(getTraceService().getTraceById(id));

        model.addAttribute("form", traceDto);

        return "traces/edit";
    }

    @PostMapping("/update/{id}")
    public String updateTrace(@PathVariable("id") long id, @Valid TraceDto traceDto, BindingResult result) {
        if (result.hasErrors()) {
            traceDto.setId(id);
            return "traces/edit";
        }

        getTraceService().updateTrace(getTraceMapper().traceDtoToTraceModel(traceDto));

        return "redirect:/traces/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrace(@PathVariable("id") long id) {
        getTraceService().deleteTraceById(id);
        return "redirect:/traces/all";
    }
}
