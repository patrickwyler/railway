package ch.teko.railway.controllers;

import ch.teko.railway.dtos.StationDto;
import ch.teko.railway.mappers.StationMapper;
import ch.teko.railway.services.StationService;
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
 * Controller for station functions
 */
@Controller
@RequestMapping("/stations")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationController {

	StationService stationService;
	StationMapper stationMapper;

	@Autowired
	public StationController(StationService stationService, StationMapper stationMapper) {
		this.stationService = stationService;
		this.stationMapper = stationMapper;
	}

	@GetMapping("/all")
	public String showAll(final Model model) {
		final List<StationDto> stations = getStationMapper().stationModelsToStationDtos(getStationService().getAllStations());
		model.addAttribute("stations", stations);

		return "stations/all";
	}

	@GetMapping("/create")
	public String createStationForm(final Model model) {
		final boolean isRootStation = getStationService().existsRootStation();
		final StationDto stationDto = StationDto.builder().rootStation(!isRootStation).build();

		model.addAttribute("isNotRootStation", isRootStation);
		model.addAttribute("form", stationDto);
		return "stations/create";
	}

	@PostMapping("/create")
	public String createStationSubmit(@ModelAttribute final StationDto stationDto) {
		getStationService().createStation(getStationMapper().stationDtoToStationModel(stationDto));
		return "redirect:/stations/all";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		final StationDto stationDto = getStationMapper().stationModelToStationDto(getStationService().getStationById(id));

		model.addAttribute("form", stationDto);

		return "stations/edit";
	}

	@PostMapping("/update/{id}")
	public String updateStation(@PathVariable("id") long id, @Valid StationDto stationDto, BindingResult result) {
		if (result.hasErrors()) {
			stationDto.setId(id);
			return "stations/edit";
		}

		getStationService().updateStation(getStationMapper().stationDtoToStationModel(stationDto));

		return "redirect:/stations/all";
	}

	@GetMapping("/delete/{id}")
	public String deleteStation(@PathVariable("id") long id) {
		getStationService().deleteStationById(id);
		return "redirect:/stations/all";
	}

}
