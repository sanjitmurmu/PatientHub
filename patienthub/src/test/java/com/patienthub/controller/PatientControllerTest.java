package com.patienthub.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.patienthub.entities.Patient;
import com.patienthub.service.PatientService;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientService patientService;

	Patient patientOne;
	Patient patientTwo;
	List<Patient> patientList = new ArrayList<>();

	@BeforeEach
	void setup() {
		patientOne = Patient.builder().id("1").about("asthama").address("bangalore").name("ram").age(28).gender('M')
				.build();

		patientTwo = Patient.builder().id("2").about("Leg injury").address("bangalore").name("shyam").age(30)
				.gender('M').build();

		patientList.add(patientOne);
		patientList.add(patientTwo);

	}

	@AfterEach
	void TearDown() {

	}

	@Test
	void testCreatePatient() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(patientOne);
		
		when(patientService.savePatient(patientOne)).thenReturn(patientOne);
		this.mockMvc.perform(post("/patients")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestJson))
					.andDo(print()).andExpect(status().isCreated());

		
	}

	@Test
	void testGetPatient() throws Exception {
		
		when(patientService.getPatient("1")).thenReturn(patientOne);
		this.mockMvc.perform(get("/patients/1")).andDo(print()).andExpect(status().isOk());
		
	}

	@Test
	void testGetAllPatient() throws Exception {
		
		when(patientService.getAllPatient()).thenReturn(patientList);
		this.mockMvc.perform(get("/patients")).andDo(print()).andExpect(status().isOk());
		
	}

	@Test
	void testDeletePatient() throws Exception {
		
		when(patientService.deletePatient("1")).thenReturn(patientOne);
		this.mockMvc.perform(delete("/patients/1")).andDo(print()).andExpect(status().isOk());
		
	}

	@Test
	void testUpdatePatient() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(patientOne);
		
		when(patientService.updatePatient(patientOne)).thenReturn(patientOne);
		this.mockMvc.perform(put("/patients")
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestJson))
					.andDo(print()).andExpect(status().isOk());

	}

}
