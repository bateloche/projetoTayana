package br.com.tayana;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tayana.domain.Campanha;
import br.com.tayana.enums.TimesEnum;
import br.com.tayana.exceptions.ObjectNotFoundException;
import br.com.tayana.repositories.CampanhaRepository;
import br.com.tayana.services.CampanhaService;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CampanhaServiceTests {

	@Mock
	private CampanhaRepository repository;
	
	@InjectMocks
    private CampanhaService campanhaService = new CampanhaService();
	
	@DisplayName("Service deve chamar repository.insert passando objeto esperado.")
	@Test
	void InsertDeveChamarRepositorio() {
		Campanha campanha = getCampanhaDefault();
		campanhaService.insert(campanha);
		
		verify(repository, times(1)).save(campanha);
		verifyNoMoreInteractions(repository);
	}
	
	@DisplayName("Find deve retornar erro caso repositório não retorne resultados.")
	@Test
	void FindDeveRetornarErroCasoRepositorioNaoRetorneResultados() {
		when(repository.findById(1l)).then(null);
		
		assertThrows(ObjectNotFoundException.class, () -> {
			campanhaService.find(1);
		});
	}
	
	@DisplayName("Find deve retornar objeto preenchido caso repositório devolva um resultado.")
	@Test
	void FindDeveRetornarObjetoPreenchido() {
		Campanha esperado = getCampanhaDefault();
		when(repository.findById(1l)).thenReturn(Optional.of(esperado));
		
		Campanha retorno = campanhaService.find(1l);
		assertEquals(esperado, retorno);
	}
	
	private Campanha getCampanhaDefault() {
		Campanha campanha = new Campanha();
		campanha.setId(1);
		campanha.setDataInicio(LocalDate.now());
		campanha.setDataFim(LocalDate.now().plusMonths(2));
		campanha.setIdTime(TimesEnum.ATLETICOMG);
		
		return campanha;
	}
}
