package com.lesson.Springboot.repository;

import com.lesson.Springboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student ,Long> {

    @Query("SELECT s FROM Student s WHERE s.name = :name and s.lastName = :lastName")
    List<Student> findAll(@Param("name")String name,
                          @Param("lastName") String lastName);

    List<Student> findAllByNameAndLastName(String name,String lastName);


    @Query(value = "Select * From student s where s.name =:name " , nativeQuery = true)
    List<Student> findAll(@Param("name") String name);






}
