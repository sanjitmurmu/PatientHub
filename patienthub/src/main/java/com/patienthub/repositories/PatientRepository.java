package com.patienthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patienthub.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, String>{

}
