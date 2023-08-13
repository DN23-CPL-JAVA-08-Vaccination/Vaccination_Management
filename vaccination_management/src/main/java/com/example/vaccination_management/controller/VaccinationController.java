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

import org.thymeleaf.spring5.SpringTemplateEngine;

import org.thymeleaf.context.Context;

import com.example.vaccination_management.dto.IVaccinationDTO;
import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.dto.VaccinationHistoryDTO;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.entity.VaccinationStatus;
import com.example.vaccination_management.utils.Validation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.vaccination_management.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.sql.Timestamp;

@RequestMapping("/")
@Controller
public class VaccinationController {


    @Autowired
    private IVaccineService iVaccineService;

    @Autowired
    private ILocationService iLocationService;

    @Autowired
    private IVaccinationTypeService iVaccinationTypeService;

    @Autowired
    private IVaccinationHistoryService iVaccinationHistoryService;

    @Autowired
    private IVaccineStatusService iVaccineStatusService;

    @Autowired
    private Validation validation;

    @Autowired
    private IVaccinationService iVaccinationService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IEmailService iEmailService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private VaccinationEventValidator vaccinationEventValidator;

    @Autowired
    private IVaccinationService vaccinationService;

    @Autowired
    private IVaccinationHistoryRepository iVaccinationHistoryRepository;

    @Autowired
    private IVaccineService vaccineService;

    @Autowired
    private IVaccineTypeService vaccineTypeService;

    /**
     * VuongVV
     * add information of Vaccination, admin after login
     */
    @GetMapping("/admin/AddVaccination")
    public String getAllVaccination(Model model) {
        List<Vaccine> vaccineList = iVaccineService.findAll();
        model.addAttribute("vaccineList", vaccineList);

        List<Location> locationList = iLocationService.findAll();
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
    @PostMapping("/admin/AddVaccination")
    public String savePersonWithAddress(@Validated @ModelAttribute("vaccination") Vaccination vaccination,
                                        RedirectAttributes redirectAttributes, Model model, BindingResult result) {

        vaccinationEventValidator.validate(vaccination, result);
        if (result.hasErrors()) {
            //    redirectAttributes.addFlashAttribute("error", "lỗi khi update");// sửa lại mess cho hợp lí
            List<Vaccine> vaccineList = iVaccineService.findAll();
            model.addAttribute("vaccineList", vaccineList);
            List<Location> locationList = iLocationService.findAll();
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
    @GetMapping("/admin/ListVaccination")
    public String GetAllVaccination(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size, Model model) {


        int startRow = page * size + 1;
        model.addAttribute("startRow", startRow);
//             List<Vaccination> listVaccination = iVaccinationService.getDeletedVaccinations(page,size);
//             model.addAttribute("listVaccination", listVaccination);
        // Tính tổng số lượng vaccines
        List<Vaccination> deletedVaccinations = iVaccinationService.getVaccinationByPageV(page, size);

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
    @GetMapping("/admin/ListDeleteVaccination")
    public String GetDeleteVaccination(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size, Model model) {
        List<Vaccination> deletedVaccinations = iVaccinationService.getDeletedVaccinations();
        model.addAttribute("deletedVaccinations", deletedVaccinations);
        int startRow = page * size + 1;
        model.addAttribute("startRow", startRow);
        List<Vaccination> listVaccination = iVaccinationService.getVaccinationByPageV(page, size);
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
    @GetMapping("/admin/deleteNotification/{id}")
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
    @GetMapping("/admin/endMailAddress/{id}")
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

    @GetMapping("/admin/softDeleteVaccination/{id}")
    public String softDeleteVaccination(@PathVariable int id) {
        iVaccinationService.softDeleteVaccination(id);
        return "redirect:/admin/ListVaccination"; // Thay đổi đường dẫn tương ứng
    }

    /**
     * QuangVT
     * get information of vaccination schedule
     */
    @GetMapping("/doctor/vaccination")

    public String schedule(Model model, @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "", required = false) String strSearch) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<IVaccinationHistoryDTO> vaccinations = iVaccinationHistoryService.getVaccinationSchedule('%' + strSearch + '%', pageable);
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
    public String update(@Validated @ModelAttribute("vaccinationObject") VaccinationHistoryDTO
                                 vaccinationHistoryDTO,
                         BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        VaccinationStatus vaccinationStatus = iVaccineStatusService.getById(vaccinationHistoryDTO.getStatus());
        VaccinationHistory vaccinationHistory = iVaccinationHistoryService.getById(vaccinationHistoryDTO.getId());
        validation.validate(vaccinationHistoryDTO, bindingResult);
        if (bindingResult.hasErrors()) {
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
    public String history(Model model, @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size,
                          RedirectAttributes redirectAttributes, @RequestParam(defaultValue = "", required = false) String strSearch) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastTime").descending());
        Page<IVaccinationHistoryDTO> history = iVaccinationHistoryService.getHistoryVaccination('%' + strSearch + '%', pageable);

        String successMessage = (String) redirectAttributes.getFlashAttributes().get("submitCheck");

        // Nếu có thông báo, thêm nó vào model để hiển thị trên trang danh sách
        if (successMessage != null) {
            model.addAttribute("submitCheck", successMessage);
        }
        model.addAttribute("historyList", history);
        return "doctors/historylist";
    }

    @GetMapping("/doctor/event")
    public String getVaccinationEvent(Model model, @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "", required = false) String strSearch) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IVaccinationDTO> vaccinations = iVaccinationService.getAllVaccination('%' + strSearch + '%', pageable);
        model.addAttribute("vaccinationList", vaccinations);
        return "doctors/vaccinationevent";
    }

    @GetMapping("/doctor/event/view")
    public String getEventDetails(Model model, @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "", required = false) String strSearch) {

        return "doctors/detailevent";
    }


    /**
     * LoanHTP
     * Retrieves a paginated list of all vaccinations.
     */
    @GetMapping("/vaccination/list-vaccination")
    public String listVaccination(Model model,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "4") int size) {
        // Tương tự như trong listVaccinationByVaccine, tính toán và truyền thông tin vào model
        // Lấy danh sách vaccine và tính tổng số lượng vaccination
        List<Vaccine> vaccines = vaccineService.showVaccines();
        long totalVaccination = vaccinationService.getTotalVaccinations();

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalVaccination / size);

        // Lấy danh sách vaccination theo trang
        List<Vaccination> vaccinationList = vaccinationService.getVaccinationByPageV(page, size);

        List<VaccineType> vaccineTypes = vaccineTypeService.showVaccineType();
        model.addAttribute("vaccineTypes", vaccineTypes);

        // Truyền các thông tin vào model
        model.addAttribute("vaccines", vaccines);
        model.addAttribute("vaccinationList", vaccinationList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        // Trả về view tương tự như trong listVaccinationByVaccine
        return "/user/vaccination/vaccination-vaccine-list";
    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccinations based on the provided vaccine ID.
     */
    @GetMapping("/vaccination/list-vaccination/{vaccine_id}")
    public String listVaccinationByVaccine(@PathVariable("vaccine_id") int vaccine_id,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "4") int size,
                                           Model model) {
        Vaccine vaccine = vaccineService.findVaccineById(vaccine_id);
        model.addAttribute("vaccine_id", vaccine_id);

        //tính tổng số lượng vaccination cho loại vaccine
        long totalVaccination = vaccinationService.getTotalVaccinationsByVaccine(vaccine);

        //tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalVaccination / size);
        model.addAttribute("totalPages", totalPages);

        //lấy danh sách vaccination theo trang và vaccine
        List<Vaccination> vaccinationList = vaccinationService.getVaccinationsByPageAndVaccine(page, size, vaccine);
        model.addAttribute("vaccinationList", vaccinationList);

        List<VaccineType> vaccineTypes = vaccineTypeService.showVaccineType();
        model.addAttribute("vaccineTypes", vaccineTypes);
        model.addAttribute("vaccine_type_id", vaccine.getVaccineType().getId());


        model.addAttribute("currentPage", page);
        return "/user/vaccination/vaccination-vaccine-list";
    }

    /**
     * LoanHTP
     * Displays the vaccination registration form for a specific vaccination.
     */
    @GetMapping("/vaccination/form-vaccination/{vaccination_id}")
    public String showVaccinationForm(Model model,
                                      @PathVariable("vaccination_id") int vaccination_id) {
        Vaccination vaccination = vaccinationService.findVaccinationById(vaccination_id);
        model.addAttribute("vaccination", vaccination);
        model.addAttribute("vaccinationHistory", new VaccinationHistory());

        Patient patient = patientService.findPatientById(1);//set patient_id = 1
        model.addAttribute("patient", patient);

        // Tính tuổi từ ngày sinh của bệnh nhân và thêm vào model
        int age = calculateAge(patient.getBirthday());
        model.addAttribute("age", age);

        return "/user/vaccination/vaccination-form-register";
    }

    /**
     * LoanHTP
     * Calculates the age of a patient based on their birthday.
     */
    @Transactional //đảm bảo tính toàn vẹn dữ liệu
    @PostMapping("/vaccination/register/{vaccination_id}")
    public String registerVaccination(Model model,
                                      RedirectAttributes redirectAttributes,
                                      @PathVariable("vaccination_id") int vaccination_id,
                                      @ModelAttribute("vaccinationHistory") VaccinationHistory vaccinationHistory,
                                      @RequestParam("patient_id") int patient_id) {

        // Lấy thông tin Vaccination từ ID
        Vaccination vaccination = vaccinationService.findVaccinationById(vaccination_id);

        // Lấy thông tin Patient từ ID
        // Patient patient = patientService.findPatientById(patient_id);
        Patient patient = patientService.findPatientById(1);

        //Thiết lập thông tin Patient cho VaccinationHistory
        vaccinationHistory.setPatient(patient);

        // Tính tuổi của bệnh nhân
        int age = calculateAge(patient.getBirthday());
        if (age < 16) {
            // Nếu tuổi nhỏ hơn 16, yêu cầu nhập guardianName và guardianPhone
            if (vaccinationHistory.getGuardianName() == null || vaccinationHistory.getGuardianName().isEmpty()) {
                model.addAttribute("errorMessage", "Vui lòng nhập Họ và tên người giám hộ.");
                return "/user/vaccination/vaccination-form-register";
            }

            if (vaccinationHistory.getGuardianPhone() == null || vaccinationHistory.getGuardianPhone().isEmpty()) {
                model.addAttribute("errorMessage", "Vui lòng nhập Số điện thoại người giám hộ.");
                return "/user/vaccination/vaccination-form-register";
            }
        } else {
            // Nếu tuổi lớn hơn hoặc bằng 16, thiết lập guardianName và guardianPhone từ thông tin Patient
            vaccinationHistory.setGuardianName(patient.getGuardianName());
            vaccinationHistory.setGuardianPhone(patient.getGuardianPhone());
        }

        // Thiết lập thông tin Vaccination cho VaccinationHistory
        vaccinationHistory.setVaccination(vaccination);
        vaccinationHistory.setVaccinationStatus(new VaccinationStatus(1, "Chờ xét duyệt"));
        vaccinationHistory.setStartTime(vaccination.getStartTime());
        vaccinationHistory.setEndTime(vaccination.getEndTime());
        vaccinationHistory.setVaccinationTimes(vaccination.getTimes());

//        model.addAttribute("patient", patient);
        iVaccinationHistoryRepository.save(vaccinationHistory);

//        Thiết lập thuộc tính "submitSuccess" trong RedirectAttributes
        redirectAttributes.addFlashAttribute("submitSuccess", true);

//        Chuyển hướng về trang đăng ký tiêm chủng để hiển thị thông báo thành công
        return "redirect:/vaccination/form-vaccination/" + vaccination_id;
//        return "/user/sign-up-vaccination-success";
    }

    /**
     * LoanHTP
     * Retrieves a patient's information based on the provided patient ID.
     */
    private int calculateAge(String birthday) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birthDate = format.parse(birthday);
            Date currentDate = new Date();

            long diff = currentDate.getTime() - birthDate.getTime();
            long ageInMilliseconds = Math.abs(diff);

            // Lấy số năm trong tuổi (365.25 ngày một năm để xem xét năm nhuận)
            double years = (double) ageInMilliseconds / (365.25 * 24 * 60 * 60 * 1000);
            int age = (int) years;

            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            // Nếu có lỗi trong quá trình chuyển đổi, trả về giá trị mặc định hoặc xử lý lỗi phù hợp
            return -1;
        }
    }
}