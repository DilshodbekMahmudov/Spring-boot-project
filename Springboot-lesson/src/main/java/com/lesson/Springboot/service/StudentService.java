package com.lesson.Springboot.service;

import com.lesson.Springboot.entity.Student;
import com.lesson.Springboot.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService{

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student){
        return studentRepository.save(student);
    }
    public Optional<Student> findById(Long id){
        Optional<Student> student=studentRepository.findById(id);
        return student;
    }

    public List<Student> findAll(String name,String lastName){
        List<Student> students=studentRepository.findAllByNameAndLastName(name,lastName);
        return students;

    }

    public List<Student> findAll(){
        List<Student> students = studentRepository.findAll();
        return students;
     }

    public void delete(Long id){
        studentRepository.deleteById(id);
    }

}
