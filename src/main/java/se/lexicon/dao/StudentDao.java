package se.lexicon.dao;

import se.lexicon.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    // basic actions like CRUD
    Student save(Student student);
    List<Student> findAll();

    Optional<Student> findById(int id);
    void delete(Student student);
}
