package br.com.tayana.command.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tayana.command.CampanhaLogicCommand;
import br.com.tayana.domain.Campanha;
import br.com.tayana.enums.TimesEnum;
import br.com.tayana.model.CampanhaRequest;
import br.com.tayana.model.CampanhaResponse;
import br.com.tayana.services.CampanhaService;

@Component
public class FindCampanhasModificadasPorDataCmd implements CampanhaLogicCommand {

	@Autowired
	private CampanhaService service;

	private List<CampanhaResponse> response;

	private LocalDate dataModificacao;

	@Override
	public void execute() {
		List<Campanha> listaCampanha = new ArrayList<>();
		Instant queryDate = dataModificacao.atStartOfDay().toInstant(ZoneOffset.of("-03"));

		listaCampanha = service.findByDataFimGreaterThanOrEqual(queryDate);

		if (!listaCampanha.isEmpty()) {
			response = new ArrayList<>();
			for (Campanha campanha : listaCampanha) {
				CampanhaResponse result = new CampanhaResponse();
				result.setId(campanha.getId());
				result.setNome(campanha.getNome());
				result.setIdTime(campanha.getIdTime().getId());
				result.setDataInicio(campanha.getDataInicio());
				result.setDataFim(campanha.getDataFim());
				result.setDataModificacao(campanha.getDataModificacao());
				response.add(result);
			}
			
			setResponse(response);
		}
	}

	public void setDataModificacao(LocalDate dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public List<CampanhaResponse> getResponse() {
		return response;
	}

	public void setResponse(List<CampanhaResponse> response) {
		this.response = response;
	}
}
