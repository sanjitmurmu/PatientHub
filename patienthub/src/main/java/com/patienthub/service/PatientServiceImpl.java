package com.patienthub.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.patienthub.entities.Patient;
import com.patienthub.exception.ResourceNotFoundException;
import com.patienthub.repositories.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService{
	
	private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);
	
	@Autowired
	private PatientRepository patientRepository;
	
	public PatientServiceImpl() {

	}

	public PatientServiceImpl(PatientRepository patientRepository2) {
		this.patientRepository = patientRepository2;
	}

	@Override
	public Patient savePatient(Patient patient) {
		logger.info("Saving new patient in database !!");
		return patientRepository.save(patient);
	}

	@Override
	public List<Patient> getAllPatient() {
		logger.info("Retrieving all patient records from database !!");
		return patientRepository.findAll();
	}

	@Override
	@Cacheable(cacheNames = "patients", key = "#id")
	public Patient getPatient(String id) {
		logger.info("Fetching patient based on the id from database !!");
		Patient patient = patientRepository.findById(id).orElseThrow(()-> 
		new ResourceNotFoundException("Patient with given id is not found on server !! : "+id));
		return patient;
	}

	@Override
	@CacheEvict(cacheNames = "patients", key = "#id")
	public Patient deletePatient(String id) {
		logger.info("deleting patient record from databse !!");
		Patient deletePatient = patientRepository.findById(id).orElseThrow(()-> 
		new ResourceNotFoundException("Patient with given id is not found on server !! : "+id));
		patientRepository.deleteById(id);
		return deletePatient;
	}

	@Override
	@CachePut(cacheNames = "patients", key = "#patient.id")
	public Patient updatePatient(Patient patient) {
		logger.info("Updating the patient information to database !!");
		Patient updatePatient = patientRepository.findById(patient.getId()).orElseThrow(()-> 
		new ResourceNotFoundException("Patient to update is not found on server !!"));
		updatePatient.setAbout(patient.getAbout());
		updatePatient.setAge(patient.getAge());
		patientRepository.save(updatePatient);
		return updatePatient;
	}

}
