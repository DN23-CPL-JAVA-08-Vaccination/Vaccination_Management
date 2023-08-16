package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.*;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.entity.VaccinationStatus;
import com.example.vaccination_management.security.AccountDetailService;
import com.example.vaccination_management.service.*;
import com.example.vaccination_management.utils.Validation;
import com.example.vaccination_management.dto.VaccinationHistoryDTO;
import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.service.IPatientService;
import com.example.vaccination_management.service.IVaccinationHistoryService;
import com.example.vaccination_management.service.IVaccinationService;
import com.example.vaccination_management.service.IVaccineTypeService;
import com.example.vaccination_management.validator.VaccinationValidator;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.vaccination_management.validation.VaccinationEventValidator;

import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.vaccination_management.dto.IVaccinationDTO;
import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;


import static com.example.vaccination_management.utils.DateUtils.calculateAge;

import java.sql.Timestamp;


@RequestMapping("/")
@Controller
public class VaccinationController {


    @Autowired
    private IVaccineService vaccineService;

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
    IVaccinationService iVaccinationService;

    @Autowired
    IInventoryService iInventoryService;

    @Autowired
    IEmailService iEmailService;

    @Autowired
    private AccountDetailService accountDetailService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private VaccinationEventValidator vaccinationEventValidator;

    @Autowired
    private IVaccineTypeService vaccineTypeService;

    @Autowired
    private IVaccinationHistoryService iVaccinationHistory;

    @Autowired
    private VaccinationValidator vaccinationValidator;

    @Autowired
    IEmailService iEmail;

    /**
     * VuongVV
     * add information of Vaccination, admin after login
     */
    @GetMapping("/admin/AddVaccination")
    public String getAllVaccination(Model model) {
        List<Vaccine> vaccineList = vaccineService.findAll();
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
        return "Admin/vaccination/addVaccination";
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
            List<Vaccine> vaccineList = vaccineService.findAll();
            model.addAttribute("vaccineList", vaccineList);
            List<Location> locationList = iLocationService.findAll();
            model.addAttribute("locationList", locationList);
            List<VaccinationType> vaccineTypeList = iVaccinationTypeService.finAll();
            model.addAttribute("vaccineTypeList", vaccineTypeList);
            return "Admin/vaccination/addVaccination";
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
        return "Admin/vaccination/listVaccination";
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
        return "Admin/vaccination/listDeVaccination";
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
    public String sendEmailAdress(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Vaccination vaccination = iVaccinationService.finById(id);
        Boolean isEmail = iEmail.SendEmailTBByLocation(vaccination);
        if (isEmail) {
            redirectAttributes.addFlashAttribute("submitSuccess", true);
            redirectAttributes.addFlashAttribute("messageEm", "Email đã gửi thành công khu vực " + vaccination.getLocation().getName());
        } else {
            redirectAttributes.addFlashAttribute("messageEm", "Gửi thất bại không tìm thấy email");
        }


        return "redirect:/admin/ListVaccination";
    }

    @GetMapping("/admin/softDeleteVaccination/{id}")
    public String softDeleteVaccination(@PathVariable int id, RedirectAttributes redirectAttributes) {
        iVaccinationService.softDeleteVaccination(id);
        redirectAttributes.addFlashAttribute("submitSuccess", true);
        redirectAttributes.addFlashAttribute("messageEm", "Bỏ vào thùng rác thành công ?");

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
        vaccinationHistoryDTO.setVaccineId(iVacc.getVaccineId());
        vaccinationHistoryDTO.setRegisTime(iVacc.getRegisTimeFormatted());
        vaccinationHistoryDTO.setVaccinationTimes(iVacc.getVaccinationTimes());

        Integer employeeId = iVacc.getEmployeeId();
        if (employeeId == null) {
            String username = accountDetailService.getCurrentUserName();
            InfoEmployeeAccountDTO employeeDTO = employeeService.getInforByUsername(username);
            model.addAttribute("employeeDTO", employeeDTO);
            vaccinationHistoryDTO.setEmployeeName(employeeDTO.getName());
            vaccinationHistoryDTO.setEmployeePhone(Integer.valueOf(employeeDTO.getPhone()));
            model.addAttribute("employee", employeeDTO);

        } else {
            InforEmployeeDTO employeeDTO = employeeService.getInforById(iVacc.getEmployeeId());
            vaccinationHistoryDTO.setEmployeeName(employeeDTO.getName());
            vaccinationHistoryDTO.setEmployeePhone(Integer.valueOf(employeeDTO.getPhone()));
            model.addAttribute("employee", employeeDTO);
        }


        vaccinationHistoryDTO.setGuardianName(iVacc.getGuardianName());
        vaccinationHistoryDTO.setGuardianPhone(iVacc.getGuardianPhone());
        vaccinationHistoryDTO.setPreStatus(iVacc.getPreStatus());
        vaccinationHistoryDTO.setStatus(iVacc.getStatus());
        vaccinationHistoryDTO.setLastTime(iVacc.getLastTimeFormatted());
        vaccinationHistoryDTO.setDosage(iVacc.getDosage());
        vaccinationHistoryDTO.setDuration(iVacc.getDuration());
        vaccinationHistoryDTO.setAgePatient(iVacc.getAgePatient());
        vaccinationHistoryDTO.setEmailPatient(iVacc.getEmailPatient());
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
    @Transactional
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
        String username = accountDetailService.getCurrentUserName();
        InfoEmployeeAccountDTO employeeDTO = employeeService.getInforByUsername(username);
        Employee employee = employeeService.getEmployeeById(employeeDTO.getId());
        vaccinationHistory.setEmployee(employee);
        vaccinationHistory.setGuardianName(vaccinationHistoryDTO.getGuardianName());
        vaccinationHistory.setGuardianPhone(vaccinationHistoryDTO.getGuardianPhone());
        vaccinationHistory.setPreStatus(vaccinationHistoryDTO.getPreStatus());
        vaccinationHistory.setVaccinationStatus(vaccinationStatus);
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        vaccinationHistory.setEndTime(String.valueOf(timestamp));
        iVaccinationHistoryService.save(vaccinationHistory);

        Integer vaccine_id = vaccinationHistoryDTO.getVaccineId();
        iInventoryService.updateInventoryQuantity(vaccine_id);

        if (vaccinationHistory.getVaccinationStatus().getId() == 2) {
            IVaccinationHistoryDTO iVaccinationHistoryDTO = iVaccinationHistoryService.getVaccinationHistoryByID(vaccinationHistoryDTO.getId());
            Boolean isEmail = iEmailService.SendEmailCompleted(iVaccinationHistoryDTO);
            if (isEmail) {
                model.addAttribute("msg", true);
            } else {
                model.addAttribute("msg", false);
            }
        }

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
        long totalVaccination = iVaccinationService.getTotalVaccinations();

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalVaccination / size);

        // Lấy danh sách vaccination theo trang
        List<Vaccination> vaccinationList = iVaccinationService.getVaccinationByPage(page, size);


        List<VaccineType> vaccineTypes = vaccineTypeService.showVaccineType();
        model.addAttribute("vaccineTypes", vaccineTypes);

        // Truyền các thông tin vào model
        model.addAttribute("vaccines", vaccines);
        model.addAttribute("vaccinationList", vaccinationList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        // Trả về view tương tự như trong listVaccinationByVaccine
        return "/user/vaccination/vaccination-vaccine-all";
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
        model.addAttribute("vaccine", vaccine);

        //tính tổng số lượng vaccination cho loại vaccine
        long totalVaccination = iVaccinationService.getTotalVaccinationsByVaccine(vaccine);

        //tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalVaccination / size);
        model.addAttribute("totalPages", totalPages);

        //lấy danh sách vaccination theo trang và vaccine
        List<Vaccination> vaccinationList = iVaccinationService.getVaccinationsByPageAndVaccine(page, size, vaccine);
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
        Vaccination vaccination = iVaccinationService.findVaccinationById(vaccination_id);
        model.addAttribute("vaccinationL", vaccination);


        String username = accountDetailService.getCurrentUserName();

        Patient patient = patientService.findPatientByUsername(username);
//        Patient patient = patientService.findPatientById(2);//set patient_id = 1

        model.addAttribute("patient", patient);

        // Tính tuổi từ ngày sinh của bệnh nhân và thêm vào model
        int age = calculateAge(patient.getBirthday());
        model.addAttribute("age", age);
        Vaccine vaccine = vaccination.getVaccine(); // Lấy đối tượng vaccine từ đối tượng Vaccination

        validateAgeForVaccine(vaccine, age, model);

        // Kiểm tra nếu có lỗi
        if (model.containsAttribute("ageError")) {
            // Điều hướng đến trang đăng ký tiêm chủng với thông báo lỗi
            return "redirect:/vaccination/list-vaccination/" + vaccine.getId();
        }

        CreateVaccinationHistoryDTO vaccinationHistoryDTO = new CreateVaccinationHistoryDTO();
        vaccinationHistoryDTO.setVaccinationId(vaccination_id);
        vaccinationHistoryDTO.setVaccination(vaccination);
        vaccinationHistoryDTO.setGuardianName(patient.getGuardianName());
        vaccinationHistoryDTO.setGuardianPhone(patient.getGuardianPhone());

        //lay datetime hien tai
        LocalDateTime now = LocalDateTime.now();
        vaccinationHistoryDTO.setStartTime(String.valueOf(now));
        vaccinationHistoryDTO.setEndTime(vaccination.getStartTime());

//        vaccinationHistoryDTO.setEndTime(vaccination.getEndTime());
        vaccinationHistoryDTO.setVaccinationTimes(vaccination.getTimes());
        vaccinationHistoryDTO.setPatient(patient);
        boolean hasErrors = false;

        model.addAttribute("vaccinationHistoryDTO", vaccinationHistoryDTO);
        model.addAttribute("errorsCheck", hasErrors);
        return "/user/vaccination/vaccination-form-register";
    }

    /**
     * LoanHTP
     * Displays the vaccination registration form for a specific vaccination.
     */
    @PostMapping("/register")
    public String registerVaccination(@ModelAttribute("vaccinationHistoryDTO")
                                              CreateVaccinationHistoryDTO vaccinationHistoryDTO,
                                      Model model,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
//        Patient patientGet = patientService.findPatientById(2);
        String username = accountDetailService.getCurrentUserName();
        Patient patientGet = patientService.findPatientByUsername(username);
        vaccinationHistoryDTO.setPatient(patientGet);
        LocalDateTime endTime = LocalDateTime.parse(vaccinationHistoryDTO.getEndTime());
        LocalDateTime startTime = LocalDateTime.now();
        int idStatus = 1;
        Vaccination vaccination = iVaccinationService.findVaccinationById(vaccinationHistoryDTO.getVaccinationId());
        vaccinationHistoryDTO.setVaccination(vaccination);
        int age = calculateAge(patientGet.getBirthday());
        model.addAttribute("age", age);
        vaccinationValidator.validate(vaccinationHistoryDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/user/vaccination/vaccination-form-register";
        }
        iVaccinationHistory.insertVaccinationHTR(false, vaccinationHistoryDTO.getDosage(), endTime, vaccinationHistoryDTO.getGuardianName(), vaccinationHistoryDTO.getGuardianPhone(), startTime, vaccinationHistoryDTO.getVaccinationTimes(), patientGet.getId(), vaccinationHistoryDTO.getVaccinationId(), idStatus);

        // Thiết lập thuộc tính "submitSuccess" trong RedirectAttributes
        redirectAttributes.addFlashAttribute("submitSuccess", true);
        return "redirect:/vaccination/form-vaccination/" + vaccinationHistoryDTO.getVaccinationId();
//        return "/user/vaccination/vaccination-form-register";
    }

    public void validateAgeForVaccine(Vaccine vaccine, int patientAge, Model model) {
        String[] ageRange = vaccine.getAge().split("-");
        int minAge = Integer.parseInt(ageRange[0]);
        int maxAge = Integer.parseInt(ageRange[1]);

        if (patientAge < minAge || patientAge > maxAge) {
            String errorMessage = "Bệnh nhân không nằm trong khoảng tuổi của vaccine.";
            model.addAttribute("ageError", errorMessage);
        }
    }
}