package com.example.vaccination_management.repository;

import com.example.vaccination_management.dto.EmployeeListDTO;
import com.example.vaccination_management.dto.InfoEmployeeAccountDTO;
import com.example.vaccination_management.dto.InforEmployeeDTO;
import com.example.vaccination_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Transactional
@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

    /**
     * ThangLV
     * get List Information of Employee
     */
    String SELECT_EMPLOYEE_LIST = "select e.id, e.name, e.address, e.birthday, e.gender, e.id_card as idCard, e.phone,e.image, p.name as position, a.email, a.username from employee e \n" +
            "            join position p on p.id = e.position_id\n" +
            "            join account a on a.id = e.account_id\n" +
            "            where  e.delete_flag = 0 and e.name like ?";

    @Query(value = SELECT_EMPLOYEE_LIST, countQuery = SELECT_EMPLOYEE_LIST, nativeQuery = true)
    Page<EmployeeListDTO> findEmployeeList(String name, Pageable pageable);

    /**
     * ThangLV
     * get count total of List Employee By Name
     */
    String GET_COUNT_EMPLOYEE = "select count(e.id) as count from employee e \n" +
            "            join position p on p.id = e.position_id\n" +
            "            join account a on a.id = e.account_id\n" +
            "            where  e.delete_flag = 0 and e.name like ?";

    @Query(value = GET_COUNT_EMPLOYEE, countQuery = GET_COUNT_EMPLOYEE, nativeQuery = true)
    long getTotalEmployee(String name);

    /**
     * ThangLV
     * get Information of Employee use Show
     */
    String SELECT_EMPLOYEE_BY_ID = "select e.id, e.name, e.address, e.birthday, e.gender, e.id_card as idCard, e.phone,e.image,p.id as positionId, p.name as positionName, a.email, a.username from employee e     \n" +
            " join position p on p.id = e.position_id    \n" +
            " join account a on a.id = e.account_id    \n" +
            " where  e.delete_flag = 0 and e.id = ?;";

    @Query(value = SELECT_EMPLOYEE_BY_ID, countQuery = SELECT_EMPLOYEE_BY_ID, nativeQuery = true)
    InforEmployeeDTO getInforById(int index);

    /**
     * ThangLV
     * get Information of Employee use Show
     */
    String SELECT_EMPLOYEE_BY_USERNAME = "select e.id, e.name, e.address, e.birthday, e.gender, e.id_card as idCard, e.phone,e.image,p.id as positionId, p.name as positionName, a.email, a.username from employee e     \n" +
            " join position p on p.id = e.position_id    \n" +
            " join account a on a.id = e.account_id    \n" +
            " where  e.delete_flag = 0 and a.username = ?;";
    @Query(value = SELECT_EMPLOYEE_BY_USERNAME, countQuery = SELECT_EMPLOYEE_BY_USERNAME, nativeQuery = true)
    InfoEmployeeAccountDTO getInforByUsername(String username);


    /**
     * ThangLV
     * get Information of Employee
     */
    @Modifying
    @Query(value = "INSERT INTO employee (address, birthday , delete_flag, gender, id_card, name, phone, image, account_id , position_id ) \n" +
            "VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8,?9, ?10)", nativeQuery = true)
    void createNewEmployee(String address, String birthday, boolean enableFlag, boolean gender, String idCard, String name, String phone, String image, Integer accountId, Integer positionId);

    /**
     * ThangLV
     * get Information of Employee
     */
    @Modifying
    @Query(value = "UPDATE employee SET address = ?1, birthday = ?2, gender = ?3, id_card = ?4, image = ?5, name = ?6, phone = ?7, position_id  = ?8 WHERE (id  = ?9)", nativeQuery = true)
    void updateEmployee(String address, String birthday, boolean gender, String idCard, String image, String name, String phone, Integer positionId, Integer Id);

    /**
     * ThangLV
     * get count(phone) in employee table, check duplicate
     */
    @Query(value = "select count(e.phone) as countPhone from employee e where e.phone = ?", nativeQuery = true)
    Integer findByPhone(String phoneNumber);

    /**
     * ThangLV
     * get count(id card) in employee table, check duplicate
     */
    @Query(value = "select count(e.id_card) as countIdCard from employee e where e.id_card = ?", nativeQuery = true)
    Integer findByIdCard(String idCard);

    /**
     * ThangLV
     * get count(email) in employee table join account, check duplicate
     */
    @Query(value = "select count(a.email) as countEmail from employee e \n" +
            "join account a on a.id = e.account_id\n" +
            "where a.email like ?;", nativeQuery = true)
    Integer findByEmail(String email);

    /**
     * ThangLV
     * get Information of Employee
     */
    @Modifying
    @Query(value = " UPDATE employee SET delete_flag = 1 WHERE (id = ?);", nativeQuery = true)
    void deleteEmployee(Integer id);


}


