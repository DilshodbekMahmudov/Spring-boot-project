package com.lesson.Springboot.web.rest;

import com.lesson.Springboot.entity.Student;
import com.lesson.Springboot.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentResource {

    private final StudentService studentService;

    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public ResponseEntity create(@RequestBody Student student) {
        Student result = studentService.save(student);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/students")
    public ResponseEntity update(@RequestBody Student student) {
        Student result = studentService.save(student);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        Optional<Student> result=studentService.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/students")
    public ResponseEntity getAll(@RequestParam String name,
                                 @RequestParam String lastName){
        List<Student> students=studentService.findAll(name,lastName);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/students1")
    public ResponseEntity getAll(){
        List<Student> students = studentService.findAll();
        return ResponseEntity.ok(students);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok("Qator o'chirildi");

    }
}
