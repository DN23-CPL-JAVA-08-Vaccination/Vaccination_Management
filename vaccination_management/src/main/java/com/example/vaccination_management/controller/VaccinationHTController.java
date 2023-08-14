package com.example.vaccination_management.controller;

import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.service.IEmailService;
import com.example.vaccination_management.service.IVaccinationHTService;
import com.example.vaccination_management.service.IVaccinationStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class VaccinationHTController {
    @Autowired
    private IVaccinationStatusService iVaccinationStatusService;
    @Autowired
    private IVaccinationHTService iVaccinationHTService;
//    @Autowired
//    private IVaccinationHistoryRepository iVaccinationHistoryRepository;
//    @Autowired
//    private IEmailService iEmailService;
    @Autowired
    public VaccinationHTController(IVaccinationStatusService iVaccinationStatusService,IVaccinationHTService iVaccinationHTService){
       this.iVaccinationHTService=iVaccinationHTService;
       this.iVaccinationStatusService=iVaccinationStatusService;
      }
     /**
     * VuongVV
     * get all information of VaccinationHistory, admin after login
     */
//    @GetMapping("Status")
//    public String finAll(@RequestParam(value = "page", defaultValue = "0") int page,
//                         @RequestParam(value = "size", defaultValue = "10") int size,Model model){
////        List<VaccinationStatus> VaccinationService =iVaccinationStatusService.finAll();
////        model.addAttribute("VaccinationServiceList",VaccinationService);
//        List<VaccinationHistory> listVaccinationHT=iVaccinationHTService.getVaccinationHTByPage(page,size);
//        model.addAttribute("listVaccinationHT",listVaccinationHT);
//
//        long totalVaccines = iVaccinationHTService.getTotalVaccinationHT();
//        // Tính tổng số trang
//        int totalPages = (int) Math.ceil((double) totalVaccines / size);
//        model.addAttribute("totalPages", totalPages);
//        model.addAttribute("currentPage", page); // Thêm dong nay đe truyen vào trang
//        return "statusVaccine";
//    }
     /**
     * VuongVV
     * get imformation status of VaccinationHistory, admin after login
     */
//    @GetMapping("Update_Status/{id}")
//    public String getVaccinationHistoryById(@PathVariable("id") int id, Model model) {
//         VaccinationHistory VaccinationHistory = iVaccinationHTService.finById(id);
//         List<VaccinationStatus> VaccinationStatusService =iVaccinationStatusService.finAll();
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//        String formattedNow = now.format(formatter);
//        model.addAttribute("formattedNow", formattedNow);
//
//         if (VaccinationStatusService.size() >= 3) {
//            List<VaccinationStatus> subList = VaccinationStatusService.subList(0, 3);
//            model.addAttribute("vaccinationStatusList", subList);
//         } else{
//            model.addAttribute("vaccinationStatusList",VaccinationStatusService);
//         }
//         if (VaccinationHistory == null) {
//            return "statusVaccine";
//         }
//         model.addAttribute("vaccinationHistory", VaccinationHistory);
//        return "statusVaccineId";
//    }
     /**
     * VuongVV
     * update status of VaccinationHistory, admin after login
     */
    //Update Status
//    @PostMapping("Update_Status_VaccinationHistory")
//    public String updateVaccinationHistory(Model model, RedirectAttributes redirectAttributes,
//            @ModelAttribute("vaccinationHistory") VaccinationHistory vaccinationHistory ) {
//        iVaccinationHTService.updateStatusVaccinationHistory(vaccinationHistory.getVaccinationStatus().getId(),vaccinationHistory.getId());
//
//
//        model.addAttribute("message", "Cập nhật thành công");
//       if(vaccinationHistory.getVaccinationStatus().getId()==2){
//           sendEmailAdressNot(vaccinationHistory.getId(), model);
//       } else if (vaccinationHistory.getVaccinationStatus().getId()==3) {
//           sendEmailAdress(vaccinationHistory.getId(), model);
//       }
//
////        sendEmailAdress(vaccinationHistory.getId(), model);
//        redirectAttributes.addFlashAttribute("submitSuccess", true);
//        return "redirect:/Status"; // Redirect to the product list page after successful update
//    }
    // send email when update stustus /sendMailHTAddress/{id}
     /**
     /**
     * VuongVV
     * send email when update status Agree to be vaccinated of VaccinationHistory, admin after login
     */
//    public void sendEmailAdress(@PathVariable("id") int id,Model model){
//        VaccinationHistory vaccinationHistory=iVaccinationHTService.finById(id);
//        String subject = "Thông báo yêu cầu tiêm chủng tiêm chủng " +vaccinationHistory.getVaccination().getVaccine().getName();
//               String content = "Kính gửi! <br>Thông báo trạng thái tiêm chủng "+vaccinationHistory.getVaccination().getVaccine().getName()+" của bạn  <span style='color: red'> "+vaccinationHistory.getVaccinationStatus().getName()+"</span>";
//               iEmailService.sendEmail(vaccinationHistory.getPatient().getAccount().getEmail(), subject, content);
//               model.addAttribute("message", "Email sent successfully to all accounts!");
//    }


}
