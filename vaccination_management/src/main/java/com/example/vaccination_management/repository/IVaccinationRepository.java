package com.example.vaccination_management.repository;

import com.example.vaccination_management.dto.IVaccinationDTO;
import com.example.vaccination_management.entity.Vaccination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVaccinationRepository extends JpaRepository<Vaccination, Integer> {
    @Query(
            value = "SELECT vac.id, vac.description, vac.date , vac.end_time as endTime, vac.start_time as startTime, vac.times,\n" +
                    "loc.name as locationName , vaccine.name as vaccineName , typ.name as vaccineTypeName\n" +
                    "FROM vaccination_manager.vaccination vac\n" +
                    "JOIN location loc  ON loc.id = vac.location_id\n" +
                    "JOIN vaccine ON  vaccine.id = vac.vaccine_id \n" +
                    "JOIN vaccine_type  typ ON vac.vaccination_type_id = typ.id\n" +
                    "WHERE vac.delete_flag = 0 AND (loc.name LIKE ?1 OR vaccine.name LIKE ?1 OR typ.name LIKE ?1 OR vac.description LIKE ?1 )\n" +
                    "ORDER BY vac.date DESC ",
            countQuery = "SELECT COUNT(*) FROM vaccination_manager.vaccination vac " +
                    "JOIN location loc  ON loc.id = vac.location_id \n" +
                    "JOIN vaccine ON  vaccine.id = vac.vaccine_id \n" +
                    "JOIN vaccine_type  typ ON vac.vaccination_type_id = typ.id " +
                    "WHERE  vac.delete_flag = 0 AND (loc.name LIKE ?1 OR vaccine.name LIKE ?1 OR typ.name LIKE ?1 OR vac.description LIKE ?1) ",
            nativeQuery = true
    )
    Page<IVaccinationDTO> getVaccinations(String strSearch, Pageable pageable);

}
