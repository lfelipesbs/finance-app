package com.example.finance.controller;

import com.example.finance.model.DashboardSummary;
import com.example.finance.model.CategoryTotal;
import com.example.finance.repository.DashboardRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")

public class DashboardController {
    private final DashboardRepository repo;

    public DashboardController(DashboardRepository repo) {
        this.repo = repo; 
    }

    @GetMapping("/summary") 
    public DashboardSummary summary() { 
        return repo.getSummary();
    }

    @GetMapping("/categories") 
    public List<CategoryTotal> categories() { 
        return repo.getCategoryBreakdown(); 
    }
}
