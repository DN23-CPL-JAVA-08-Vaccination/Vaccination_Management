package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.VaccinationHistoryDTO;
import com.example.vaccination_management.entity.*;
import com.example.vaccination_management.repository.*;
import com.example.vaccination_management.service.IVaccinationHistoryService;
import com.example.vaccination_management.service.impl.PatientService;
import com.example.vaccination_management.service.impl.VaccinationService;
import com.example.vaccination_management.service.impl.VaccineService;
import com.example.vaccination_management.service.impl.VaccineTypeService;
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
    private VaccinationService vaccinationService;

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
    private VaccineTypeService vaccineTypeService;

    @Autowired
    private PatientService patientService;

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

        Patient patient = patientService.findPatientById(2);//set patient_id = 1
        model.addAttribute("patient", patient);

        // Tính tuổi từ ngày sinh của bệnh nhân và thêm vào model
        int age = calculateAge(patient.getBirthday());
        model.addAttribute("age", age);

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


    @PostMapping("/register")
    public String registerVaccination(@ModelAttribute("vaccinationHistoryDTO")
                                      VaccinationHistoryDTO vaccinationHistoryDTO,
                                      Model model,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes){
        Patient patientGet = patientService.findPatientById(2);
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


//    @Transactional
//    @PostMapping("/register")
//    public String registerVaccination(@Validated @ModelAttribute("vaccinationHistoryDTO")
//                                      VaccinationHistoryDTO vaccinationHistoryDTO,
////                                      @RequestParam("patientId") int patientId,
//                                      @RequestParam("selectedDateTime") String selectedDateTime,
//                                      Model model,
//                                      BindingResult bindingResult,
//                                      RedirectAttributes redirectAttributes){
//        //set patient_id = 1
//        Patient patientGet = patientService.findPatientById(1);
//        vaccinationHistoryDTO.setPatient(patientGet);
//
//        LocalDateTime selectedEndTime = LocalDateTime.parse(selectedDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
//        String formattedEndTime = selectedEndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//
//        vaccinationHistoryDTO.setEndTime(formattedEndTime);
//
//        vaccinationValidator.validate(vaccinationHistoryDTO, bindingResult);
//        if(bindingResult.hasErrors()){
//            boolean hasErrors = true;
//            model.addAttribute("errorsCheck", hasErrors);
//            return "/user/vaccination/test";
//        }
//
//        VaccinationHistory vaccinationHistory = new VaccinationHistory();
//        Vaccination vaccination = vaccinationService.findVaccinationById(vaccinationHistoryDTO.getVaccination().getId());
//
////        Patient patient = patientService.findPatientById(1);
////        vaccinationHistoryDTO.setPatient(patientGet);
//
//        vaccinationHistory.setPatient(patientGet);
//        int age = calculateAge(patientGet.getBirthday());
//
//        vaccinationHistory.setVaccination(vaccination);
//        vaccinationHistory.setVaccinationStatus(new VaccinationStatus(1, "Chua tiem"));
//        vaccinationHistory.setGuardianName(vaccinationHistoryDTO.getGuardianName());
//        vaccinationHistory.setGuardianPhone(vaccinationHistoryDTO.getGuardianPhone());
////        vaccinationHistory.setStartTime(vaccination.getStartTime());
//        LocalDateTime now = LocalDateTime.now();
//        vaccinationHistory.setStartTime(String.valueOf(now));
//        vaccinationHistory.setVaccinationTimes(vaccination.getTimes());
//
////        vaccinationHistory.setEndTime(vaccination.getEndTime());
//        vaccinationHistory.setEndTime(formattedEndTime);
//
//        iVaccinationHistoryRepository.save(vaccinationHistory);
//
//        redirectAttributes.addFlashAttribute("submitSuccess", true);
//        return "redirect:/vaccination/list-vaccination";
////        return "redirect:/vaccination/form-vaccination";
//    }

//======================================
//    @GetMapping("/form-vaccination")
//    public String showVaccinationForm( Model model,
//                                      @RequestParam Integer id
//    ) {
//        Vaccination vaccination = vaccinationService.findVaccinationById(id);
//        model.addAttribute("vaccination", vaccination);
//
//        Patient patient = patientService.findPatientById(1);//set patient_id = 1
//        model.addAttribute("patient", patient);
//
//        // Tính tuổi từ ngày sinh của bệnh nhân và thêm vào model
//        Integer age = calculateAge(patient.getBirthday());
//        model.addAttribute("age", age);
//        VaccinationHistory vaccinationHistory = new VaccinationHistory();
//        vaccinationHistory.setVaccination(vaccination);
//        vaccinationHistory.setGuardianName(patient.getGuardianName());
//        vaccinationHistory.setGuardianPhone(patient.getGuardianPhone());
//
//        Date startTime = new Date(); // Lấy thời gian hiện tại
//        String endTime = "2023-8-12 12:12:00";
//        vaccinationHistory.setVaccinationTimes(vaccination.getTimes());
//        vaccinationHistory.setPatient(patient);
//
//        model.addAttribute("vaccinationHistory", vaccinationHistory);
//
//        return "/user/vaccination/vaccination-form-register";
//    }
//
//    /**
//     * LoanHTP
//     * Calculates the age of a patient based on their birthday.
//     */
//    @PostMapping("/form-vaccination")
//    public String registerVaccination(Model model,
//                                      RedirectAttributes redirectAttributes,
////                                      @PathVariable("vaccination_id") int vaccination_id,
//                                      @Validated @ModelAttribute("vaccinationHistory") VaccinationHistory vaccinationHistory,
////                                      @RequestParam("patient_id") int patient_id,
//                                      BindingResult bindingResult) {
//        vaccinationValidator.validate(vaccinationHistory, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "  ";
//        }
////         Lấy thông tin Vaccination từ ID
//        Vaccination vaccination = vaccinationHistory.getVaccination();
//
//        // Lấy thông tin Patient từ ID
//        Patient patient = patientService.findPatientById(vaccinationHistory.getPatient().getId());
//
//        // Thiết lập thông tin Patient cho VaccinationHistory
//        vaccinationHistory.setPatient(patient);
//        vaccinationHistory.setGuardianName(patient.getGuardianName());
//        vaccinationHistory.setGuardianPhone(patient.getGuardianPhone());
//
//        vaccinationHistory.setVaccination(vaccination);
//        vaccinationHistory.setVaccinationStatus(new VaccinationStatus(1, "Chờ xét duyệt"));
//        vaccinationHistory.setStartTime(vaccination.getStartTime());
//        vaccinationHistory.setEndTime(vaccination.getEndTime());
//        vaccinationHistory.setVaccinationTimes(vaccination.getTimes());
//
//        iVaccinationHistoryRepository.save(vaccinationHistory);

//        // Thiết lập thuộc tính "submitSuccess" trong RedirectAttributes
//        redirectAttributes.addFlashAttribute("submitSuccess", true);
//
//        // Chuyển hướng về trang đăng ký tiêm chủng để hiển thị thông báo thành công
//        return "/user/vaccination/vaccination-form-register";
//    }

}