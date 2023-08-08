package com.example.vaccination_management.controller;

import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
public class VaccinationController {

//    @Autowired
//
//    private final VaccinationService vaccinationService;


    @Autowired
    private IVaccineService iVaccineService;
    @Autowired
    private ILocationService iLocationService;
    @Autowired
    private IVaccinationTypeService iVaccinationTypeService;

    @Autowired
    private IVaccinationService iVaccinationService;

    @Autowired
    private IPatientService iPatientService;

    @Autowired
    private IEmailService iEmailService;
    /**
     * VuongVV
     * add information of Vaccination, admin after login
     */
    @GetMapping("/AddVaccination")
    public String getAllVaccination(Model model) {
        List<Vaccine> vaccineList = iVaccineService.findAll();
        model.addAttribute("vaccineList", vaccineList);

        List<Location> locationList = iLocationService.finAll();
        model.addAttribute("locationList", locationList);

        List<VaccinationType> vaccineTypeList = iVaccinationTypeService.finAll();
        model.addAttribute("vaccineTypeList", vaccineTypeList);
        //create
        model.addAttribute("vaccination", new Vaccination());
        model.addAttribute("location", new Location());
        model.addAttribute("vaccinationType", new VaccinationType());
        model.addAttribute("vaccine", new Vaccine());

        return "addVaccination";
    }
    /**
     * VuongVV
     * update information of Vaccination, admin after login
     */
    @PostMapping("/addVaccination")
    public String savePersonWithAddress(Vaccination vaccination, Location location, VaccinationType vaccinationType, Vaccine vaccine) {
        iVaccinationService.saveVaccinationService(vaccination, location, vaccinationType, vaccine);
        return "redirect:/AddVaccination";
    }
    /**
     * VuongVV
     * get information of Vaccination, admin after login
     */
    @GetMapping("/listVaccination")
    public String GetAllVaccination(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "7") int size,Model model) {

        List<Vaccination> listVaccination = iVaccinationService.getVaccinationByPage(page,size);
        model.addAttribute("listVaccination", listVaccination);

        // Tính tổng số lượng vaccines
        long totalVaccination = iVaccinationService.getTotalVaccination();

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalVaccination / size);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("currentPage", page); // Thêm dong nay đe truyen vào trang
        return "listVaccination";
    }
    /**
     * VuongVV
     * delete Notification by id of Vaccination, admin after login
     */
    @GetMapping("/deleteNotification/{id}")
    public String deleteAccount(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        boolean deleted = iVaccinationService.deleteNotificationVaccination(id);

        if (deleted) {
            redirectAttributes.addFlashAttribute("message", "<span style='background-color: #18d26e'> Đã xoá thành công!</span> ");
        } else {
            redirectAttributes.addFlashAttribute("message", "<span style='background-color: red'> Xoá thất bại </span>");
        }
        return "redirect:/listVaccination"; // Chuyển hướng về trang danh sách tài khoản sau khi xóa
    }
    /**
     * VuongVV
     * send Email vaccination notification by location of Vaccination , admin after login
     */
    @GetMapping("/endMailAddress/{id}")
    public String sendEmailAdress(@PathVariable("id") int id,RedirectAttributes model){
     Vaccination vaccination=iVaccinationService.finById(id);
        if (vaccination != null) {
            List<String> matchingPatientNames = iVaccinationService.getPatientsWithMatchingLocationName(vaccination);
            if (!matchingPatientNames.isEmpty()) {
                String subject = "Thông báo tiêm chủng " +vaccination.getVaccine().getName();
                String content = "Kính gửi! <br>Thông báo thời gian tiêm chủng "+vaccination.getVaccine().getName()+" bắt đầu đăng ký từ "+vaccination.getStartTime()+" và kết thúc vào "+vaccination.getEndTime()+" và thời gian tiêm vào "+vaccination.getTimes()+" giờ ngày "+vaccination.getDate()+" đăng kí vào lòng truy cập vào đường link <a href='dangkitiemchung/"+vaccination.getId()+"'>Đăng ký tiêm chủng</a> <br> trân trọng cảm ơn.";
               for (String account : matchingPatientNames) {
               iEmailService.sendEmail(account, subject, content);
                }
                model.addFlashAttribute("messageEmail", " <span style='background-color: #18d26e'> Email được gửi thành công đến tất cả các tài khoản thuộc "+ vaccination.getLocation().getName()+"! </span>");
            } else {

                model.addFlashAttribute("messageEmail", "Không tìm thấy tài khoản!");
            }
            return "redirect:/listVaccination";
         //   model.addAttribute("matchingPatientNames",matchingPatientNames);
        }
       return "errorEmail";
    }
}