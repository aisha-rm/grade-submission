package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.exception.EntityNotFoundException;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student getStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return unwrapStudent(student, id);
    } 

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student); //save method is inherited from CrudRepository in the StudentRepo, returns a student object
    }

    @Override
    public void deleteStudent(Long id) {  
        studentRepository.deleteById(id);      
    }

    @Override
    public List<Student> getStudents() {
        return (List<Student>)studentRepository.findAll(); //returns generic iterable, wh is type casted to list
    }

    @Override
    public Set<Course> getEnrolledCourses(Long studentId) {
        Student student = getStudent(studentId);
        return student.getCourses();
    }

    static Student unwrapStudent(Optional<Student> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Student.class);
    }

}