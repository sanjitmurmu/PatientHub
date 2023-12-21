package com.patienthub.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.patienthub.entities.Patient;
import com.patienthub.repositories.PatientRepository;

class PatientServiceImplTest {
	
	@Mock
	private PatientRepository patientRepository;
	
	private PatientService patientService;
	
	AutoCloseable autoCloseable;
	
	Patient patient;
	
	@BeforeEach
	void setup() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		patientService = new PatientServiceImpl(patientRepository);
		patient = Patient.builder()
						.id("1")
						.about("asthama")
						.address("bangalore")
						.name("ram")
						.age(30)
						.gender('M')
						.build();
		
	}
	
	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}
	
	@Test
	void testGetAllPatient() {
		mock(Patient.class);
		mock(PatientRepository.class);
		
		when(patientRepository.findAll()).thenReturn(new ArrayList<Patient>(Collections.singleton(patient)));
		assertThat(patientService.getAllPatient().get(0).getName()).isEqualTo(patient.getName());
	}
	
	@Test
	void testSavePatient() {
		mock(Patient.class);
		mock(PatientRepository.class);
		
		when(patientRepository.save(patient)).thenReturn(patient);
		assertThat(patientService.savePatient(patient)).isEqualTo(patient);
	}
	
	@Test
	void testUpdatePatient() {
		mock(Patient.class);
		mock(PatientRepository.class);
		
		when(patientRepository.findById(patient.getId())).thenReturn(Optional.ofNullable(patient));
		assertThat(patientService.updatePatient(patient)).isEqualTo(patient);
	}
	
	@Test
	void testGetPatient() {
		mock(Patient.class);
		mock(PatientRepository.class);
		
		when(patientRepository.findById("1")).thenReturn(Optional.ofNullable(patient));
		assertThat(patientService.getPatient("1").getName()).isEqualTo(patient.getName());
	}
	
	@Test
	void testDeletePatient() {
		mock(Patient.class);
		mock(PatientRepository.class, Mockito.CALLS_REAL_METHODS);
		
		when(patientRepository.findById("1")).thenReturn(Optional.ofNullable(patient));
		doAnswer(Answers.CALLS_REAL_METHODS).when(patientRepository).deleteById("1");
		
		assertThat(patientService.deletePatient("1")).isEqualTo(patient);
	}
	

	


}
