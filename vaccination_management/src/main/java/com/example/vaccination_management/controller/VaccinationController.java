package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.VaccinationHistoryDTO;
import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.repository.*;
import com.example.vaccination_management.service.IPatientService;
import com.example.vaccination_management.service.IVaccinationHistoryService;
import com.example.vaccination_management.service.IVaccinationService;
import com.example.vaccination_management.service.IVaccineTypeService;
import com.example.vaccination_management.service.impl.VaccineService;
import com.example.vaccination_management.validator.VaccinationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.vaccination_management.utils.DateUtils.calculateAge;


@RequestMapping(value = "/vaccination", method = RequestMethod.GET)
@Controller
public class VaccinationController {

    @Autowired
    private IVaccinationService vaccinationService;

    @Autowired
    private IVaccineRepository iVaccineRepository;

    @Autowired
    private IVaccinationHistoryRepository iVaccinationHistoryRepository;

    @Autowired
    private IVaccinationRepository iVaccinationRepository;

    @Autowired
    private VaccineService vaccineService;

    @Autowired
    private IVaccineTypeRepository iVaccineTypeRepository;

    @Autowired
    private IVaccineTypeService vaccineTypeService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IVaccinationHistoryService iVaccinationHistory;

    @Autowired
    private VaccinationValidator vaccinationValidator;


    /**
     * LoanHTP
     * Retrieves a paginated list of all vaccinations.
     */
    @GetMapping("/list-vaccination")
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
    @GetMapping("list-vaccination/{vaccine_id}")
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
    @GetMapping("/form-vaccination/{vaccination_id}")
    public String showVaccinationForm(Model model,
                                      @PathVariable ("vaccination_id") int vaccination_id){
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