package br.com.tayana.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import br.com.tayana.domain.Campanha;

@Component
public class CampanhaLogicBusiness {

	public List<Campanha> alterarDataVigencia(List<Campanha> campanhasOrdenadas, Campanha novaCampanha) {
		
		NavigableMap<LocalDate, Campanha> map = new TreeMap<LocalDate, Campanha>();
		
		map.put(novaCampanha.getDataFim(), novaCampanha);
		
		for(Campanha campanhaPreExistente : campanhasOrdenadas) {
			LocalDate maiorData = map.lastKey();
			if(dataMenorOuIgual(maiorData, campanhaPreExistente.getDataFim())) {
				campanhaPreExistente.setDataFim(maiorData.plusDays(1));
				campanhaPreExistente.setDataModificacao(LocalDateTime.now());
			}
			map.put(campanhaPreExistente.getDataFim(), campanhaPreExistente);
		}
		
		return campanhasOrdenadas;
	}
	
	private boolean dataMenorOuIgual(LocalDate ladoEsquerdo, LocalDate ladoDireito) {
		return !ladoEsquerdo.isAfter(ladoDireito);
	}
}
