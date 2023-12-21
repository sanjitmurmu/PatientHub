package com.patienthub.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patienthub.entities.Patient;
import com.patienthub.service.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {
	
	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
	
	@Autowired
	private PatientService patientService;
	
	//add patient
	@PostMapping
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
		logger.info("Endpoint to create new patient is called..!!");
		Patient newPatient = patientService.savePatient(patient);
		return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);
		
	}
	
	//fetch patient information using id
	@GetMapping("/{patientId}")
	public ResponseEntity<Patient> getPatient(@PathVariable String patientId){
		logger.info("Endpoint to get a single patient data is called..!!");
		Patient patient = patientService.getPatient(patientId);
		return ResponseEntity.ok(patient);
	}	
	
	//fetch all patient information
	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatient(){
		logger.info("Endpoint to get all patient data is called..!!");
		List<Patient> patientList = patientService.getAllPatient();
		return ResponseEntity.ok(patientList);
	}
	
	//delete patient
	@DeleteMapping("/{patientId}")
	public ResponseEntity<Patient> deletePatient(@PathVariable String patientId){
		logger.info("Endpoint to delete existing patient is called..!!");
		Patient patient = patientService.deletePatient(patientId);
		return ResponseEntity.ok(patient);
	}
	
	//update patient information
	@PutMapping
	public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient){
		logger.info("Endpoint to update existing patient is called..!!");
		Patient updatedPatient = patientService.updatePatient(patient);
		return ResponseEntity.ok(updatedPatient);
	}
	
	
}
