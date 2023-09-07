package com.example.vaccination_management.controller;

<<<<<<< HEAD
import com.example.vaccination_management.dto.VaccinationHistoryDTO;
import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.repository.*;
import com.example.vaccination_management.service.IPatientService;
import com.example.vaccination_management.service.IVaccinationHistoryService;
import com.example.vaccination_management.service.IVaccinationService;
import com.example.vaccination_management.service.IVaccineTypeService;
import com.example.vaccination_management.service.impl.VaccineService;
import com.example.vaccination_management.validator.VaccinationValidator;
=======
import com.example.vaccination_management.dto.IVaccinationDTO;
import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.dto.VaccinationHistoryDTO;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.entity.VaccinationStatus;
import com.example.vaccination_management.service.*;
import com.example.vaccination_management.utils.Validation;
>>>>>>> origin/fix_bug_thanglv_huylvn
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
<<<<<<< HEAD
=======
import org.springframework.validation.annotation.Validated;
>>>>>>> origin/fix_bug_thanglv_huylvn
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

<<<<<<< HEAD
import static com.example.vaccination_management.utils.DateUtils.calculateAge;


@RequestMapping(value = "/vaccination", method = RequestMethod.GET)
=======

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequestMapping("/")
>>>>>>> origin/fix_bug_thanglv_huylvn
@Controller
public class VaccinationController {

    @Autowired
<<<<<<< HEAD
    private IVaccinationService vaccinationService;
=======
    private IVaccinationHistoryService iVaccinationHistoryService;
>>>>>>> origin/fix_bug_thanglv_huylvn

    @Autowired
    private IVaccineStatusService iVaccineStatusService;

    @Autowired
    private Validation validation;

    @Autowired
    private IVaccinationService iVaccinationService;

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

<<<<<<< HEAD
    @Autowired
    private IVaccineTypeService vaccineTypeService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IVaccinationHistoryService iVaccinationHistory;

    @Autowired
    private VaccinationValidator vaccinationValidator;
=======

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
>>>>>>> origin/fix_bug_thanglv_huylvn


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
<<<<<<< HEAD
                                      @PathVariable ("vaccination_id") int vaccination_id){
=======
                                      @PathVariable("vaccination_id") int vaccination_id) {
>>>>>>> origin/fix_bug_thanglv_huylvn
        Vaccination vaccination = vaccinationService.findVaccinationById(vaccination_id);
        model.addAttribute("vaccinationL", vaccination);

//        Patient patient = patientService.findPatientById(2);//set patient_id = 1
//        String username = accountDetailService.getCurrentUserName();

        Patient patient = patientService.findPatientByUsername("johndoe");
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

        VaccinationHistoryDTO vaccinationHistoryDTO = new VaccinationHistoryDTO();
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
<<<<<<< HEAD
    @PostMapping("/register")
    public String registerVaccination(@ModelAttribute("vaccinationHistoryDTO")
                                      VaccinationHistoryDTO vaccinationHistoryDTO,
                                      Model model,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes){
//        Patient patientGet = patientService.findPatientById(2);
        Patient patientGet = patientService.findPatientByUsername("johndoe");
        vaccinationHistoryDTO.setPatient(patientGet);
        LocalDateTime endTime= LocalDateTime.parse(vaccinationHistoryDTO.getEndTime());
        LocalDateTime startTime = LocalDateTime.now();
        int idStatus= 1;
        Vaccination vaccination = vaccinationService.findVaccinationById(vaccinationHistoryDTO.getVaccinationId());
        vaccinationHistoryDTO.setVaccination(vaccination);
        int age = calculateAge(patientGet.getBirthday());
        model.addAttribute("age", age);
        vaccinationValidator.validate(vaccinationHistoryDTO,bindingResult);
        if(bindingResult.hasErrors()){
            return "/user/vaccination/vaccination-form-register";
=======
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
>>>>>>> origin/fix_bug_thanglv_huylvn
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