package project.order_rae.controller;

import project.order_rae.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Map<String, Object> data = dashboardService.getDashboardData();
        model.addAllAttributes(data);
        return "dashboard";
    }
}