package br.com.tayana.command.model;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.tayana.command.CampanhaLogicCommand;
import br.com.tayana.domain.Campanha;
import br.com.tayana.model.CampanhaRequest;
import br.com.tayana.model.CampanhaResponse;
import br.com.tayana.services.CampanhaService;

public class UpdateCampanhaCmd implements CampanhaLogicCommand{
	
	@Autowired
	private CampanhaService service;
	
	private CampanhaRequest request;
	
	private CampanhaResponse response;

	@Override
	public void execute() {
		Campanha campanha = new Campanha();
		campanha.setDataInicio(request.getDataInicio());
		campanha.setDataFim(request.getDataFim());
		campanha.setId(request.getId());
		campanha.setIdTime(campanha.getIdTime()); 
		campanha.setNome(request.getNome());
		
		campanha = service.update(campanha);
		
		response = new CampanhaResponse();
		response.setId(campanha.getId());
		response.setNome(campanha.getNome());
		response.setIdTime(campanha.getIdTime().getId());
		response.setDataInicio(campanha.getDataInicio());
		response.setDataFim(campanha.getDataFim());
	}
	
	public CampanhaRequest getRequest() {
		return request;
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
}
