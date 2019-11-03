package ch.teko.railway.controllers;

import ch.teko.railway.mappers.ResourcePlanMapper;
import ch.teko.railway.mappers.TimeTableMapper;
import ch.teko.railway.mappers.WorkScheduleMapper;
import ch.teko.railway.services.PlanService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for our plans like timetable, resourceplan and workschedule
 */
@Controller
@Getter
@Setter
@RequestMapping("/plans")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanController {

    PlanService planService;
    TimeTableMapper timeTableMapper;
    ResourcePlanMapper resourcePlanMapper;
    WorkScheduleMapper workScheduleMapper;

    @Autowired
    public PlanController(PlanService planService, TimeTableMapper timeTableMapper, ResourcePlanMapper resourcePlanMapper, WorkScheduleMapper workScheduleMapper) {
        this.planService = planService;
        this.timeTableMapper = timeTableMapper;
        this.resourcePlanMapper = resourcePlanMapper;
        this.workScheduleMapper = workScheduleMapper;
    }

    @PostMapping("/reset")
    public String reset() {
        this.getPlanService().reset();
        return "redirect:/plans/timetable";
    }

    @GetMapping("/timetable")
    public String showTimeTable(final Model model) {
        model.addAttribute("timeTable", getTimeTableMapper().timeTableModelToTimeTableDto(getPlanService().getTimeTable()));
        return "/plans/timetable";
    }

    @GetMapping("/resourceplan")
    public String showResourceplan(final Model model) {
        model.addAttribute("resourcePlan", getResourcePlanMapper().resourcePlanModelToResourcePlanDto(getPlanService().getResourcesPlan()));
        return "/plans/resourceplan";
    }

    @GetMapping("/workschedule")
    public String showWorkschedule(final Model model) {
        model.addAttribute("workSchedule", getWorkScheduleMapper().workScheduleModelToWorkScheduleDto(getPlanService().getWorkSchedule()));
        return "/plans/workschedule";
    }

}
