package edu.usp.planex;

import edu.usp.planex.cambios.CambioConfidence;
import edu.usp.planex.cambios.CambioCotacao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanexApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testSearchQuote() {
		System.out.println((new CambioConfidence()).calcularCotacao());
	}

}
