package com.example.vaccination_management.controller;

import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.service.*;
import com.example.vaccination_management.validation.VaccinationEventValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import org.thymeleaf.spring5.SpringTemplateEngine;

import org.thymeleaf.context.Context;

@Controller
@RequestMapping("/admin")
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

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private VaccinationEventValidator vaccinationEventValidator;

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

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedNow = now.format(formatter);
        model.addAttribute("formattedNow", formattedNow);
        List<VaccinationType> vaccineTypeList = iVaccinationTypeService.finAll();
        model.addAttribute("vaccineTypeList", vaccineTypeList);
        //create
        model.addAttribute("vaccination", new Vaccination());
        model.addAttribute("location", new Location());
        model.addAttribute("vaccinationType", new VaccinationType());
        model.addAttribute("vaccine", new Vaccine());
        return "admin/vaccination/addVaccination";
    }

    /**
     * VuongVV
     * update information of Vaccination, admin after login
     */
    @PostMapping("/AddVaccination")
    public String savePersonWithAddress(@Validated @ModelAttribute("vaccination") Vaccination vaccination,
                                        RedirectAttributes redirectAttributes, Model model, BindingResult result) {

        vaccinationEventValidator.validate(vaccination, result);
        if (result.hasErrors()) {
        //    redirectAttributes.addFlashAttribute("error", "lỗi khi update");// sửa lại mess cho hợp lí
            List<Vaccine> vaccineList = iVaccineService.findAll();
            model.addAttribute("vaccineList", vaccineList);
            List<Location> locationList = iLocationService.finAll();
            model.addAttribute("locationList", locationList);
            List<VaccinationType> vaccineTypeList = iVaccinationTypeService.finAll();
            model.addAttribute("vaccineTypeList", vaccineTypeList);
            return "admin/vaccination/addVaccination";
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedNow = now.format(formatter);
        model.addAttribute("formattedNow", formattedNow);
        redirectAttributes.addFlashAttribute("submitSuccess", true);
        iVaccinationService.saveVaccinationService(vaccination, vaccination.getLocation(), vaccination.getVaccinationType(), vaccination.getVaccine());

        return "redirect:/admin/AddVaccination";

    }

    /**
     * VuongVV
     * get information of Vaccination, admin after login
     */
    @GetMapping("/ListVaccination")
    public String GetAllVaccination(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size, Model model) {


        int startRow = page * size + 1;
        model.addAttribute("startRow", startRow);
//             List<Vaccination> listVaccination = iVaccinationService.getDeletedVaccinations(page,size);
//             model.addAttribute("listVaccination", listVaccination);
        // Tính tổng số lượng vaccines
        List<Vaccination> deletedVaccinations = iVaccinationService.getVaccinationByPage(page, size);

        model.addAttribute("listVaccination", deletedVaccinations);

        List<Vaccination> deletedVaccinationTrue = iVaccinationService.getDeletedVaccinations();

        int deletedVaccinationsCount = deletedVaccinationTrue.size();
        long totalVaccination = iVaccinationService.getTotalVaccination() - deletedVaccinationsCount;

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalVaccination / size);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("currentPage", page);
        return "admin/vaccination/listVaccination";
    }

    /**
     * VuongVV
     * deleteFlag Notification by id of Vaccination, admin after login
     */
    @GetMapping("/ListDeleteVaccination")
    public String GetDeleteVaccination(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size, Model model) {
        List<Vaccination> deletedVaccinations = iVaccinationService.getDeletedVaccinations();
        model.addAttribute("deletedVaccinations", deletedVaccinations);
        int startRow = page * size + 1;
        model.addAttribute("startRow", startRow);
        List<Vaccination> listVaccination = iVaccinationService.getVaccinationByPage(page, size);
        model.addAttribute("listVaccination", listVaccination);

        // Tính tổng số lượng vaccines
        long totalVaccination = iVaccinationService.getTotalVaccination();

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalVaccination / size);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("currentPage", page); // Thêm dong nay đe truyen vào trang
        return "admin/vaccination/listDeVaccination";
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
        return "redirect:/admin/ListDeleteVaccination"; // Chuyển hướng về trang danh sách tài khoản sau khi xóa
    }

    /**
     * VuongVV
     * send Email vaccination notification by location of Vaccination , admin after login
     */
    @GetMapping("/endMailAddress/{id}")
    public String sendEmailAdress(@PathVariable("id") int id, RedirectAttributes model) {
        Vaccination vaccination = iVaccinationService.finById(id);
        if (vaccination != null) {
            List<String> matchingPatientNames = iVaccinationService.getPatientsWithMatchingLocationName(vaccination);
            if (!matchingPatientNames.isEmpty()) {
                String subject = vaccination.getDescription();
                // String content = "Kính gửi! <br>Thông báo tiêm chủng "+vaccination.getVaccine().getName()+" lần thứ "+vaccination.getTimes()+" độ tuổi "+vaccination.getVaccine().getAge()+" bắt đầu đăng ký từ "+vaccination.getStartTime()+" và kết thúc vào "+vaccination.getEndTime()+" và thời gian tiêm vào khoản "+vaccination.getDuration()+" tại "+vaccination.getLocation().getLocationDetail()+" đăng kí vào lòng truy cập vào đường link <a href='dangkitiemchung/"+vaccination.getId()+"'>Đăng ký tiêm chủng</a> <br> trân trọng cảm ơn.";
                String content = buildEmailContent(vaccination);
                for (String account : matchingPatientNames) {
                    iEmailService.sendEmail(account, subject, content);
                }
                model.addFlashAttribute("messageEm", vaccination.getLocation().getName());

                model.addFlashAttribute("submitSuccess", true);

//                model.addFlashAttribute("messageEmail", " <span style='background-color: #18d26e'> Email được gửi thành công đến tất cả các tài khoản thuộc "+ vaccination.getLocation().getName()+"! </span>");
            } else {

                model.addFlashAttribute("messageEmail", "Không tìm thấy tài khoản!");
            }
            return "redirect:/admin/ListVaccination";
            //   model.addAttribute("matchingPatientNames",matchingPatientNames);
        }
        return "admin/vaccination/error_email";
    }

    private String buildEmailContent(Vaccination vaccination) {
        Context context = new Context();
        context.setVariable("description", vaccination.getDescription());
        context.setVariable("vaccinationType", vaccination.getVaccinationType().getName());
        context.setVariable("vaccineName", vaccination.getVaccine().getName());
        context.setVariable("times", vaccination.getTimes());
        context.setVariable("age", vaccination.getVaccine().getAge());

        context.setVariable("startTime", vaccination.getStartTime());
        context.setVariable("endTime", vaccination.getEndTime());
        context.setVariable("duration", vaccination.getDuration());
        context.setVariable("locationDetail", vaccination.getLocation().getLocationDetail());
        context.setVariable("registrationLink", "dangkitiemchung/" + vaccination.getId());

        return templateEngine.process("admin/vaccination/notification_email", context);
    }

    @GetMapping("/softDeleteVaccination/{id}")
    public String softDeleteVaccination(@PathVariable int id) {
        iVaccinationService.softDeleteVaccination(id);
        return "redirect:/admin/ListVaccination"; // Thay đổi đường dẫn tương ứng
    }
}