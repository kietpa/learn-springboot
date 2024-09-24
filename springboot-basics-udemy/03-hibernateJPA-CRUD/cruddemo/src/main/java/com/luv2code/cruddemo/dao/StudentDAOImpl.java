package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO{

    private EntityManager entityManager;

    @Autowired
    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Student theStudent) {
        entityManager.persist(theStudent);
    }

    @Override
    public Student findById(Integer id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public List<Student> findAll() {
        // create query (from entity not DB)
        TypedQuery<Student> query = entityManager.createQuery("FROM Student", Student.class);

        // return results
        return query.getResultList();
    }

    @Override
    public List<Student> findByLastName(String lastName) {
        // named parameters in JPA use colon
        TypedQuery<Student> query = entityManager.createQuery(
                "FROM Student where lastName = :theData", Student.class);

        // set param
        query.setParameter("theData", lastName);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Student theStudent) {
        entityManager.merge(theStudent);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Student s = entityManager.find(Student.class, id);
        entityManager.remove(s);
    }

    @Override
    @Transactional
    public int deleteAll() {
        int numDeleted = entityManager.createQuery("DELETE FROM Student ").executeUpdate();
        return numDeleted;
    }
}
