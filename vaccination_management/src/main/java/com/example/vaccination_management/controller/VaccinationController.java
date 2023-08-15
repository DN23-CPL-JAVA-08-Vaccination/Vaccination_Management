package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.*;
import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.repository.IVaccinationHistoryRepository;
import com.example.vaccination_management.security.AccountDetailService;
import com.example.vaccination_management.service.*;
import com.example.vaccination_management.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RequestMapping("/")
@Controller
public class VaccinationController {

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
    AccountDetailService accountDetailService;
    @Autowired
    private IEmployeeService employeeService;


    @Autowired
    private IVaccinationService vaccinationService;

    @Autowired
    private IVaccinationHistoryRepository iVaccinationHistoryRepository;

    @Autowired
    private IVaccineService vaccineService;

    @Autowired
    private IVaccineTypeService vaccineTypeService;

    @Autowired
    private IPatientService patientService;


    /**
     * QuangVT
     * get information of vaccination schedule
     */
    @GetMapping("/doctor/vaccination")

    public String schedule(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "", required = false) String strSearch) {

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
        if (employeeId == null){
            String username = accountDetailService.getCurrentUserName();
            InfoEmployeeAccountDTO employeeDTO = employeeService.getInforByUsername(username);
            model.addAttribute("employeeDTO", employeeDTO);
            vaccinationHistoryDTO.setEmployeeName(employeeDTO.getName());
            vaccinationHistoryDTO.setEmployeePhone(Integer.valueOf(employeeDTO.getPhone()));
            model.addAttribute("employee",employeeDTO);

        } else {
            InforEmployeeDTO employeeDTO = employeeService.getInforById(iVacc.getEmployeeId());
            vaccinationHistoryDTO.setEmployeeName(employeeDTO.getName());
            vaccinationHistoryDTO.setEmployeePhone(Integer.valueOf(employeeDTO.getPhone()));
            model.addAttribute("employee",employeeDTO);
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
    public String update(@Validated @ModelAttribute("vaccinationObject") VaccinationHistoryDTO vaccinationHistoryDTO,
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

        if (vaccinationHistory.getVaccinationStatus().getId() == 2){
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
    public String history(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
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
    public String getVaccinationEvent(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "", required = false) String strSearch) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IVaccinationDTO> vaccinations = iVaccinationService.getAllVaccination('%' + strSearch + '%', pageable);
        model.addAttribute("vaccinationList", vaccinations);
        return "doctors/vaccinationevent";
    }

    @GetMapping("/doctor/event/view")
    public String getEventDetails(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "", required = false) String strSearch) {

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
        List<Vaccination> vaccinationList = vaccinationService.getVaccinationByPage(page, size);

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