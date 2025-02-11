package dz.kyrios.core.controller;

import dz.kyrios.core.dto.herosection.HeroSectionResponse;
import dz.kyrios.core.dto.statsection.StatSectionResponse;
import dz.kyrios.core.dto.subjectssection.SubjectsSectionResponse;
import dz.kyrios.core.service.HeroSectionService;
import dz.kyrios.core.service.StatSectionService;
import dz.kyrios.core.service.SubjectsSectionService;
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

    public HomeController(HeroSectionService heroSectionService,
                          StatSectionService statSectionService,
                          SubjectsSectionService subjectsSectionService) {
        this.heroSectionService = heroSectionService;
        this.statSectionService = statSectionService;
        this.subjectsSectionService = subjectsSectionService;
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
}
