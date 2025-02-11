package dz.kyrios.core.controller;

import dz.kyrios.core.dto.fieldssection.FieldsSectionResponse;
import dz.kyrios.core.dto.herosection.HeroSectionResponse;
import dz.kyrios.core.dto.planssection.PlansSectionResponse;
import dz.kyrios.core.dto.statsection.StatSectionResponse;
import dz.kyrios.core.dto.subjectssection.SubjectsSectionResponse;
import dz.kyrios.core.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class HomeController {

    private final HeroSectionService heroSectionService;
    private final StatSectionService statSectionService;
    private final SubjectsSectionService subjectsSectionService;
    private final FieldsSectionService fieldsSectionService;
    private final PlansSectionService plansSectionService;

    public HomeController(HeroSectionService heroSectionService,
                          StatSectionService statSectionService,
                          SubjectsSectionService subjectsSectionService,
                          FieldsSectionService fieldsSectionService,
                          PlansSectionService plansSectionService) {
        this.heroSectionService = heroSectionService;
        this.statSectionService = statSectionService;
        this.subjectsSectionService = subjectsSectionService;
        this.fieldsSectionService = fieldsSectionService;
        this.plansSectionService = plansSectionService;
    }

    @GetMapping("/active/hero/section")
    public ResponseEntity<Object> getActiveHeroSection() {
        HeroSectionResponse response = heroSectionService.getActiveHeroSection();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/active/stat/section")
    public ResponseEntity<Object> getActiveStatSection() {
        StatSectionResponse response = statSectionService.getActiveStatSection();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/active/subjects/section")
    public ResponseEntity<Object> getActiveSubjectsSection() {
        SubjectsSectionResponse response = subjectsSectionService.getActiveSubjectsSection();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/active/fields/section")
    public ResponseEntity<Object> getActiveFieldsSection() {
        FieldsSectionResponse response = fieldsSectionService.getActiveFieldsSection();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/active/plans/section")
    public ResponseEntity<Object> getActivePlansSection() {
        PlansSectionResponse response = plansSectionService.getActivePlansSection();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
