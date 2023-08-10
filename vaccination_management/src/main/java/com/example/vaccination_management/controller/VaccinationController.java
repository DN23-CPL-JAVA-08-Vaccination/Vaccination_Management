package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.IVaccinationDTO;
import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.dto.VaccinationHistoryDTO;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.entity.VaccinationStatus;
import com.example.vaccination_management.service.IVaccinationHistoryService;
import com.example.vaccination_management.service.IVaccinationService;
import com.example.vaccination_management.service.IVaccineStatusService;
import com.example.vaccination_management.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequestMapping("/")
@Controller
public class VaccinationController {
    @Autowired
    IVaccinationHistoryService iVaccinationHistoryService;
    @Autowired
    IVaccineStatusService iVaccineStatusService;
    @Autowired
    Validation validation;
    @Autowired
    IVaccinationService iVaccinationService;

    /**
     * QuangVT
     * get information of vaccination schedule
     */
    @GetMapping("/doctor/vaccination")
    public String schedule(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "", required = false) String strSearch) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<IVaccinationHistoryDTO> vaccinations = iVaccinationHistoryService.getVaccinationSchedule( '%' + strSearch + '%',pageable);
        model.addAttribute("vaccinationList", vaccinations);
        return "doctors/scheduleVaccination";
    }
    /**
     * QuangVT
     * get information of vaccination history by ID,
     */
    @GetMapping("/doctor/vaccination/view")
    public String schedule(Model model, @RequestParam int id) {
        IVaccinationHistoryDTO iVacc = iVaccinationHistoryService.getVaccinationHistoryByID(id);
        VaccinationHistoryDTO vaccinationHistoryDTO = new VaccinationHistoryDTO();
        vaccinationHistoryDTO.setId(iVacc.getId());
        vaccinationHistoryDTO.setPatientName(iVacc.getPatientName());
        vaccinationHistoryDTO.setPatientBirth(iVacc.getBirthFormat());
        vaccinationHistoryDTO.setVaccinationDesc(iVacc.getVaccinationDesc());
        vaccinationHistoryDTO.setVaccineName(iVacc.getVaccineName());
        vaccinationHistoryDTO.setRegisTime(iVacc.getRegisTimeFormatted());
        vaccinationHistoryDTO.setVaccinationTimes(iVacc.getVaccinationTimes());
        vaccinationHistoryDTO.setEmployeeName(iVacc.getEmployeeName());
        vaccinationHistoryDTO.setEmployeePhone(iVacc.getEmployeePhone());
        vaccinationHistoryDTO.setGuardianName(iVacc.getGuardianName());
        vaccinationHistoryDTO.setGuardianPhone(iVacc.getGuardianPhone());
        vaccinationHistoryDTO.setPreStatus(iVacc.getPreStatus());
        vaccinationHistoryDTO.setStatus(iVacc.getStatus());
        vaccinationHistoryDTO.setLastTime(iVacc.getRegisTimeFormatted());
        vaccinationHistoryDTO.setDosage(iVacc.getDosage());
        vaccinationHistoryDTO.setDuration(iVacc.getDuration());
        vaccinationHistoryDTO.setAgePatient(iVacc.getAgePatient());
        boolean hasErrors = false;
        model.addAttribute("errorsCheck", hasErrors);
        model.addAttribute("vaccinationdetail", iVacc);
        model.addAttribute("vaccinationObject", vaccinationHistoryDTO);
        return "doctors/detailhistory";
    }

    /**
     * QuangVT
     * update information of accination history by ID,
     */
    @PostMapping("/doctor/vaccination/view")
    public String update(@Validated @ModelAttribute("vaccinationObject") VaccinationHistoryDTO vaccinationHistoryDTO ,
                         BindingResult bindingResult, Model model , RedirectAttributes redirectAttributes) {
        VaccinationStatus vaccinationStatus = iVaccineStatusService.getById(vaccinationHistoryDTO.getStatus());
        VaccinationHistory vaccinationHistory = iVaccinationHistoryService.getById(vaccinationHistoryDTO.getId());
        validation.validate(vaccinationHistoryDTO, bindingResult);
        if (bindingResult.hasErrors()){
            boolean hasErrors = true;
            model.addAttribute("errorsCheck", hasErrors);
            return "doctors/detailhistory";

        }
        vaccinationHistory.setGuardianName(vaccinationHistoryDTO.getGuardianName());
        vaccinationHistory.setGuardianPhone(vaccinationHistoryDTO.getGuardianPhone());
        vaccinationHistory.setPreStatus(vaccinationHistoryDTO.getPreStatus());
        vaccinationHistory.setVaccinationStatus(vaccinationStatus);
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        vaccinationHistory.setEndTime(String.valueOf(timestamp));
        iVaccinationHistoryService.save(vaccinationHistory);
        redirectAttributes.addFlashAttribute("submitCheck", true);
        return "redirect:/doctor/history";
    }
    /**
     * QuangVT
     * get information of accination history completed
     */
    @GetMapping("/doctor/history")
    public String history(Model model,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                          RedirectAttributes redirectAttributes,@RequestParam(defaultValue = "", required = false) String strSearch ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastTime").descending());
        Page<IVaccinationHistoryDTO>  history = iVaccinationHistoryService.getHistoryVaccination('%' + strSearch + '%',pageable);

        String successMessage = (String) redirectAttributes.getFlashAttributes().get("submitCheck");

        // Nếu có thông báo, thêm nó vào model để hiển thị trên trang danh sách
        if (successMessage != null) {
            model.addAttribute("submitCheck", successMessage);
        }
        model.addAttribute("historyList", history);
        return "doctors/historylist";
    }
    @GetMapping("/doctor/event")
    public String getVaccinationEvent(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "", required = false) String strSearch) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IVaccinationDTO> vaccinations = iVaccinationService.getAllVaccination( '%' + strSearch + '%',pageable);
        model.addAttribute("vaccinationList", vaccinations);
        return "doctors/vaccinationevent";
    }

    @GetMapping("/doctor/event/view")
    public String getEventDetails(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "", required = false) String strSearch) {

        return "doctors/detailevent";
    }
}
