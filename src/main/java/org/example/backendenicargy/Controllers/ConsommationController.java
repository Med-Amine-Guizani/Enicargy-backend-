package org.example.backendenicargy.Controllers;

import org.example.backendenicargy.Services.ConsommationScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/consommation")
public class ConsommationController {

    @Autowired
    private ConsommationScrapingService scrapingService;

    @GetMapping("/scrapped-data")
    public Map<String, List<Integer>> getScrappedData() {

        return scrapingService.getConsommationDataFromHtml();
    }
}
