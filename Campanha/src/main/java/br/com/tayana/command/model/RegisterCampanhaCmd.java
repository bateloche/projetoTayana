package br.com.tayana.command.model;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tayana.business.CampanhaLogicBusiness;
import br.com.tayana.command.CampanhaLogicCommand;
import br.com.tayana.domain.Campanha;
import br.com.tayana.enums.TimesEnum;
import br.com.tayana.infra.mongodb.SequenceGeneratorService;
import br.com.tayana.model.CampanhaRequest;
import br.com.tayana.model.CampanhaResponse;
import br.com.tayana.services.CampanhaService;

@Component
public class RegisterCampanhaCmd implements CampanhaLogicCommand {

	private CampanhaService service;
	private SequenceGeneratorService sequenceGenerator;
	private CampanhaLogicBusiness business;

	private CampanhaResponse response;
	private CampanhaRequest request;

	public RegisterCampanhaCmd(CampanhaService service, CampanhaLogicBusiness business,
			SequenceGeneratorService sequenceGenerator) {
		this.service = service;
		this.business = business;
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public void execute() {
		Campanha campanha = new Campanha();
		campanha.setId(sequenceGenerator.generateSequence(Campanha.SEQUENCE_NAME));
		campanha.setDataInicio(request.getDataInicio());
		campanha.setDataFim(request.getDataFim());
		campanha.setIdTime(TimesEnum.fromValue(request.getIdTime()));
		campanha.setNome(request.getNome());

		Instant queryDate = request.getDataFim().atStartOfDay().toInstant(ZoneOffset.ofHours(-3));
		List<Campanha> listaCampanha = service.findByDataFimGreaterThanOrEqual(queryDate);
		if (listaCampanha.isEmpty()) {
			campanha = service.insert(campanha);
		} else {
			listaCampanha = business.alterarDataVigencia(listaCampanha, campanha);
			service.updateAll(listaCampanha);
			campanha = service.insert(campanha);
			// fazer recurso para avisar que houve modificações nas campanhas existentes
		}

		response = new CampanhaResponse();
		response.setId(campanha.getId());
		response.setNome(campanha.getNome());
		response.setIdTime(campanha.getIdTime().getId());
		response.setDataInicio(campanha.getDataInicio());
		response.setDataFim(campanha.getDataFim());
	}

	public CampanhaResponse getResponse() {
		return response;
	}

	public void setResponse(CampanhaResponse response) {
		this.response = response;
	}

	public CampanhaRequest getRequest() {
		return request;
	}

	public void setRequest(CampanhaRequest request) {
		this.request = request;
	}

}
