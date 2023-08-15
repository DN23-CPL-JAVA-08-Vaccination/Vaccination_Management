package com.example.vaccination_management.controller;

import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.entity.VaccinationStatus;
import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.impl.PatientService;
import com.example.vaccination_management.service.impl.VaccinationHistoryService;
import com.example.vaccination_management.service.impl.VaccinationStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping(value = "/vaccination-history", method = RequestMethod.GET)
@Controller
public class VaccinationHistoryController {

    @Autowired
    private IPatientRepository iPatientRepository;

    @Autowired
    private VaccinationHistoryService vaccinationHistoryService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private VaccinationStatusService vaccinationStatusService;

    /**
     * LoanHTP
     * Displays the vaccination history of a patient along with filtering options by vaccination status.
     */
    @GetMapping("/patient/{patient_id}")
    public String listVaccinationHistoryByPatient(Model model,
                                                  @PathVariable("patient_id") int patient_id,
                                                  @RequestParam(value = "vaccination_status_id", required = false) Integer vaccination_status_id,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                  HttpServletRequest request) {
        Patient patient = iPatientRepository.findPatientById(patient_id);

        // Lấy danh sách vaccinationStatus từ vaccinationStatusService
        List<VaccinationStatus> vaccinationStatusList = vaccinationStatusService.getAllVaccinationStatus();
        model.addAttribute("vaccinationStatusList", vaccinationStatusList);

        // Lấy danh sách vaccination history theo vaccination_status và patient
        Page<VaccinationHistory> vaccinationHistories;
        if (vaccination_status_id != null) {
            vaccinationHistories = vaccinationHistoryService.listVaccinationHistoryByPatientAndStatusPaged(patient_id, vaccination_status_id, PageRequest.of(page, size));
        } else {
            vaccinationHistories = vaccinationHistoryService.listVaccinationHistoryByPatientPaged(patient_id, PageRequest.of(page, size));
        }
        model.addAttribute("vaccinationHistories", vaccinationHistories);

        // Tính toán số trang và trang hiện tại
        int totalPages = vaccinationHistories.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("vaccinationHistories", vaccinationHistories.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("patient", patient);

        // Lấy URL gốc từ request và truyền vào model để giữ nguyên khi chuyển hướng trở lại
        String originalUrl = request.getRequestURL().toString();
        model.addAttribute("originalUrl", originalUrl);

        return "/user/patient/profile-patient";
    }

    /**
     * LoanHTP
     * Displays detailed information about a specific vaccination history entry.
     */
    @GetMapping("/list/{vaccinationHistory_id}")
    public String showVaccinationHistory(Model model,
                                         @PathVariable("vaccinationHistory_id") int vaccinationHistory_id,
                                         @RequestParam(value = "vaccination_status_id", required = false) Integer vaccination_status_id,
                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                         HttpServletRequest request) {
        VaccinationHistory vaccinationHistory = vaccinationHistoryService.findVaccinationHistoryById(vaccinationHistory_id);

        model.addAttribute("vaccinationHistory", vaccinationHistory);
        Patient patient = vaccinationHistory.getPatient();
        model.addAttribute("patient", patient);

        // Lấy URL gốc từ request và truyền vào model để giữ nguyên khi chuyển hướng trở lại
        String originalUrl = request.getHeader("Referer");
        model.addAttribute("originalUrl", originalUrl);

        //lấy vaccinatinStatusID từ VaccinationHistory
        int vaccinationStatusId = vaccinationHistory.getVaccinationStatus().getId();
        if(vaccinationStatusId == 2){
            return "/user/patient/view-vaccination-history";
        }else {
            return "/user/patient/view-vaccination-history2";
        }
    }

}
