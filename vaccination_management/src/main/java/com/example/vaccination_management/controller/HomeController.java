package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.service.IVaccinationHistoryService;
import com.example.vaccination_management.service.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;


@RequestMapping("/")
@Controller
public class HomeController {
    @Autowired
    IVaccinationHistoryService iVaccinationHistoryService;
    @Autowired
    IVaccineService iVaccineService;
    private String getDefaultYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

//    /**
//     * QuangVT
//     * get information for dashboard
//     */
//    @GetMapping("/")
//    public String home(Model model, @RequestParam(defaultValue = "") String year) {
//        LocalDate today = LocalDate.now();
//        model.addAttribute("today", today);
//
//        IVaccinationHistoryDTO ivacci = iVaccinationHistoryService.countVaccination();
//        model.addAttribute("countVacc", ivacci);
//        long count = iVaccineService.count();
//        if (year.isEmpty()) {
//            year = getDefaultYear();
//        }
//        List<Integer> listChart = iVaccinationHistoryService.getDataForChart(year);
//
//        model.addAttribute("chartList", listChart);
//        model.addAttribute("countVaccine", count);
//
//        return "doctors/homedoctor";
//    }
//
//    /**
//     * QuangVT
//     * get information of datachart by year
//     */
//    @GetMapping("/chart-data")
//    @ResponseBody
//    public List<Integer> getChartData(@RequestParam(defaultValue = "") String year) {
//        return iVaccinationHistoryService.getDataForChart(year);
//    }
}
