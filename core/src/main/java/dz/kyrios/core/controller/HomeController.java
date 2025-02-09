package dz.kyrios.core.controller;

import dz.kyrios.core.dto.herosection.HeroSectionResponse;
import dz.kyrios.core.service.HeroSectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class HomeController {

    private final HeroSectionService heroSectionService;

    public HomeController(HeroSectionService heroSectionService) {
        this.heroSectionService = heroSectionService;
    }

    @GetMapping("/active/hero/section")
    public ResponseEntity<Object> getOne() {
        HeroSectionResponse response = heroSectionService.getActiveHeroSection();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
