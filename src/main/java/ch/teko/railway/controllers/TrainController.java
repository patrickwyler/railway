package ch.teko.railway.controllers;

import ch.teko.railway.dtos.TrainDto;
import ch.teko.railway.mappers.TrainMapper;
import ch.teko.railway.services.TrainService;
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
 * Controller for trains functions
 */
@Controller
@RequestMapping("/trains")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainController {

    TrainService trainService;
    TrainMapper trainMapper;

    @Autowired
    public TrainController(TrainService trainService, TrainMapper trainMapper) {
        this.trainService = trainService;
        this.trainMapper = trainMapper;
    }

    @GetMapping("/all")
    public String showAll(final Model model) {
        final List<TrainDto> trains = getTrainMapper().trainModelsToTrainDtos(getTrainService().getAllTrains());
        model.addAttribute("trains", trains);
        return "trains/all";
    }

    @GetMapping("/create")
    public String createTrainForm(final Model model) {
        final TrainDto trainDto = TrainDto.builder().build();

        model.addAttribute("form", trainDto);
        model.addAttribute("trains", getTrainMapper().trainModelsToTrainDtos(getTrainService().getAllTrains()));

        return "trains/create";
    }

    @PostMapping("/create")
    public String createTrainSubmit(@ModelAttribute final TrainDto trainDto) {
        getTrainService().createTrain(getTrainMapper().trainDtoToTrainModel(trainDto));
        return "redirect:/trains/all";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        final TrainDto trainDto = getTrainMapper().trainModelToTrainDto(getTrainService().getTrainById(id));

        model.addAttribute("form", trainDto);

        return "trains/edit";
    }

    @PostMapping("/update/{id}")
    public String updateTrain(@PathVariable("id") long id, @Valid TrainDto trainDto, BindingResult result) {
        if (result.hasErrors()) {
            trainDto.setId(id);
            return "trains/edit";
        }

        getTrainService().updateTrain(getTrainMapper().trainDtoToTrainModel(trainDto));

        return "redirect:/trains/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrain(@PathVariable("id") long id) {
        getTrainService().deleteTrainById(id);
        return "redirect:/trains/all";
    }
}
