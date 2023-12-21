package com.patienthub.service;

import java.util.List;

import com.patienthub.entities.Patient;

public interface PatientService {
	
	Patient savePatient(Patient patient);
	
	List<Patient> getAllPatient();
	
	Patient getPatient(String id);
	
	Patient deletePatient(String id);
	
	Patient updatePatient(Patient patient);

}
