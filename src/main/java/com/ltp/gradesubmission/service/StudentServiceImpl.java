package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.exception.StudentNotFoundException;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student getStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()){
            return student.get(); //unwrapping the optional
        }else{
            throw new StudentNotFoundException(id);
        } 
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

    static Student unwrapStudent(Optional<Student> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new StudentNotFoundException(id);
    }

}