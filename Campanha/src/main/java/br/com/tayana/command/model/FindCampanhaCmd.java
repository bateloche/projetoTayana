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
public class FindCampanhaCmd implements CampanhaLogicCommand{
	
	@Autowired
	private CampanhaService service;
	
	private CampanhaResponse response;
	
	private CampanhaRequest request;
	
	private List<CampanhaResponse> responses;
	
	private Integer idTime;

	@Override
	public void execute() {
		List<Campanha> listaCampanha = new ArrayList<>();
		LocalDate date = LocalDate.now();
		Instant queryDate = date.atStartOfDay().toInstant(ZoneOffset.of("-03"));
		if(getIdTime() == null)
			listaCampanha = service.findByDataFimGreaterThanOrEqual(queryDate);
		else {
			TimesEnum time = TimesEnum.fromValue(request.getIdTime());
			listaCampanha = service.findAtivasByTime(time, queryDate);
		}
		if(listaCampanha.isEmpty()) {
			//
		} else {
			responses = new ArrayList<>();
			for(Campanha campanha : listaCampanha) {
				response = new CampanhaResponse();
				response.setId(campanha.getId());
				response.setNome(campanha.getNome());
				response.setIdTime(campanha.getIdTime().getId());
				response.setDataInicio(campanha.getDataInicio());
				response.setDataFim(campanha.getDataFim());
				responses.add(response);
			}	
		}
	}
	
	public void setRequest(CampanhaRequest request) {
		this.request = request;
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

	public List<CampanhaResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<CampanhaResponse> responses) {
		this.responses = responses;
	}

	public Integer getIdTime() {
		return idTime;
	}

	public void setIdTime(Integer idTime) {
		this.idTime = idTime;
	}	
	
}
