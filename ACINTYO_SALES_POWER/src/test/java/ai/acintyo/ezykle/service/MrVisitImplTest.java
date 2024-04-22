package ai.acintyo.ezykle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ai.acintyo.sales.binding.MrVisitForm;
import ai.acintyo.sales.collection.MrVisit;
import ai.acintyo.sales.service.MrVisitServiceImpl;

public class MrVisitImplTest {

	private static MrVisitServiceImpl service=null;
	
	@BeforeAll
	public static void setUpOnce()
	{
		service=new MrVisitServiceImpl();
	}
	
	@Test
	public void registerMrVisitTest()
	{
		MrVisit mrVisit = new MrVisit();
		MrVisitForm mr=new MrVisitForm();
		MrVisit actual = service.registerMrVisit(mr);
		assertEquals(mrVisit, actual);
	}
}
