package com.acintyo.auth.mrvisit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.acintyo.auth.dto.MrVisitForm;
import com.acintyo.auth.exception.MrVisitsNotFoundException;
import com.acintyo.auth.model.MrVisit;
import com.acintyo.auth.repository.IMrVisitRepo;
import com.acintyo.auth.service.MrVisitServiceImpl;

@ExtendWith(MockitoExtension.class) // Integrates Mockito into the JUnit 5 testing framework, enabling mocks and
									// injections
public class MrVisitServiceImplTest {

	@Mock
	private IMrVisitRepo repoMock;

	@InjectMocks
	private MrVisitServiceImpl serviceImpl;

	// Need to create object and set values to the MrVisitForm

	@Mock
	private static MrVisitForm visitForm;

	private static Pageable pageable;

	@BeforeAll
	public static void setUpPageable() {
		pageable = PageRequest.of(0, 10, Direction.DESC, "visitTime");
	}

	@BeforeAll
	public static void setUpMrVisitForm() {
		visitForm = new MrVisitForm();
		visitForm.setSupplierId("s123");
		visitForm.setRetailerId("r123");
		visitForm.setMrId("mr123");
		visitForm.setAsmId("as1234");
		visitForm.setVisitLocationLatitude("23° 26′ N");
		visitForm.setVisitLocationLongitude("23° 26′ S");
		visitForm.setVisitTime(LocalDateTime.now());
	}

	@Test
	public void testMrVisitRegistration() {
		MrVisit mrV = new MrVisit();
		mrV.setAsmId(visitForm.getAsmId());
		mrV.setMrId(visitForm.getMrId());
		mrV.setRetailerId(visitForm.getRetailerId());
		mrV.setSupplierId(visitForm.getSupplierId());
		mrV.setVisitTime(visitForm.getVisitTime());

		when(repoMock.save(any(MrVisit.class))).thenReturn(mrV);// if we send MrVisit type then we will get Ongoing
																// stubbing(value) here implementation Class is created
																// it will invoked when we call service class method

		MrVisit result = serviceImpl.registerMrVisit(visitForm);

		assertEquals(mrV.getAsmId(), result.getAsmId());

		verify(repoMock).save(any(MrVisit.class));

	}

	@Test
	public void fetchAllMrVisitTest() {
		MrVisit mrVisit = new MrVisit();
		PageImpl<MrVisit> pages = new PageImpl<>(List.of(mrVisit));// expected

		when(repoMock.findAll(pageable)).thenReturn(pages);

	Page<MrVisit> result = serviceImpl.fetchAllMrVisit(pageable);

		assertFalse(result.isEmpty(), "Result should not be empty");

		assertEquals(mrVisit, result.getContent().get(0), "The result was not expected");

		verify(repoMock).findAll(pageable);
	}

	@Test
	public void throwExceptionTestWhenPageNotContainData() {
		// set Mock here
		when(repoMock.findAll(pageable)).thenReturn(Page.empty());

		MrVisitsNotFoundException dnfe = assertThrows(MrVisitsNotFoundException.class, () -> {
			serviceImpl.fetchAllMrVisit(pageable);
		});
		assertEquals("MrVisit data not available", dnfe.getMessage(), "Expected message was not matched");

	}

	@Test
	public void testFetchAllMrVisitWithDifferentPageParamValues() {
		// creating diff pageable object,passing
		Pageable pageable = PageRequest.of(0,+ 5);
		MrVisit mrVisit = new MrVisit();
		Page<MrVisit> page = new PageImpl<>(Arrays.asList(mrVisit, mrVisit));

		Mockito.when(repoMock.findAll(pageable)).thenReturn(page);

		Page<MrVisit> result = serviceImpl.fetchAllMrVisit(pageable);

		assertEquals(2, result.getContent().size(), "Expected number of objects were not found");

		Mockito.verify(repoMock).findAll(pageable);
	}
	
	
	@Test
	public void testFetchMrVisitById()
	{
		
		MrVisit  mrVisit=new MrVisit();
		String id="6620bbd53980717a05544b99";
		Mockito.when(repoMock.findById(id)).thenReturn(Optional.of(mrVisit));
		
		MrVisit visit = serviceImpl.fetchMrVisitById(id);
	    assertEquals(mrVisit,visit,"The return visit object should match to the MrVisit");
	    assertNotNull(visit,"The return object should not be null");
	}
	
	@Test
	public void testFetchMrVisitByIdWithInvalidId()
	{
		String id="980717a05544b99";
	    Mockito.when(repoMock.findById(id)).thenReturn(Optional.empty()); 
	    assertThrows(IllegalArgumentException.class,()->{serviceImpl.fetchMrVisitById(id);},"IllegalArgumentException should be thrown when No MrVisit object found");
	}
	
	  @Test
	    public void testUpdateMrVisitByIdFound() {
	        // Arrange
	        String id = "6620bbd53980717a05544b99";//any string
	        MrVisit existingVisit = new MrVisit();
	        existingVisit.setId(id);
	        when(repoMock.findById(id)).thenReturn(Optional.of(existingVisit));
	        when(visitForm.getSupplierId()).thenReturn("supplierId");
	        when(visitForm.getRetailerId()).thenReturn("retailerId");
	        when(visitForm.getMrId()).thenReturn("mrId");
	        when(visitForm.getAsmId()).thenReturn("asmId");
	        when(visitForm.getVisitTime()).thenReturn(LocalDateTime.now());
	        when(repoMock.save(any(MrVisit.class))).thenReturn(existingVisit);

	        MrVisit updatedVisit = serviceImpl.updateMrVisitById(id, visitForm);

	        assertNotNull(updatedVisit, "The returned MrVisit should not be null");
	        verify(repoMock).save(any(MrVisit.class));
	        assertEquals("supplierId", updatedVisit.getSupplierId());
	        assertEquals("retailerId", updatedVisit.getRetailerId());
	        assertEquals("mrId", updatedVisit.getMrId());
	        assertEquals("asmId", updatedVisit.getAsmId());
	       }
	

}
