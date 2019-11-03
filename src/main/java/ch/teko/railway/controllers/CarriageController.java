package ch.teko.railway.controllers;

import ch.teko.railway.dtos.CarriageDto;
import ch.teko.railway.mappers.CarriageMapper;
import ch.teko.railway.services.CarriageService;
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
 * Controller for carriage functions
 */
@Controller
@RequestMapping("/carriages")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarriageController {

    CarriageService carriageService;
    CarriageMapper carriageMapper;

    @Autowired
    public CarriageController(CarriageService carriageService, CarriageMapper carriageMapper) {
        this.carriageService = carriageService;
        this.carriageMapper = carriageMapper;
    }

    @GetMapping("/all")
    public String showAll(final Model model) {
        final List<CarriageDto> carriages = getCarriageMapper().carriageModelsToCarriageDtos(getCarriageService().getAllCarriages());
        model.addAttribute("carriages", carriages);
        return "carriages/all";
    }

    @GetMapping("/create")
    public String createCarriageForm(final Model model) {
        final CarriageDto carriageDto = CarriageDto.builder().build();

        model.addAttribute("form", carriageDto);
        model.addAttribute("carriages", getCarriageMapper().carriageModelsToCarriageDtos(getCarriageService().getAllCarriages()));

        return "carriages/create";
    }

    @PostMapping("/create")
    public String createCarriageSubmit(@ModelAttribute final CarriageDto carriageDto) {
        getCarriageService().createCarriage(getCarriageMapper().carriageDtoToCarriageModel(carriageDto));
        return "redirect:/carriages/all";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        final CarriageDto carriageDto = getCarriageMapper().carriageModelToCarriageDto(getCarriageService().getCarriageById(id));

        model.addAttribute("form", carriageDto);

        return "carriages/edit";
    }

    @PostMapping("/update/{id}")
    public String updateCarriage(@PathVariable("id") long id, @Valid CarriageDto carriageDto, BindingResult result) {
        if (result.hasErrors()) {
            carriageDto.setId(id);
            return "carriages/edit";
        }

        getCarriageService().updateCarriage(getCarriageMapper().carriageDtoToCarriageModel(carriageDto));

        return "redirect:/carriages/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteCarriage(@PathVariable("id") long id) {
        getCarriageService().deleteCarriageById(id);
        return "redirect:/carriages/all";
    }
}
