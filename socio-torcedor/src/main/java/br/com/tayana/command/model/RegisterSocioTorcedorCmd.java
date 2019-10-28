package br.com.tayana.command.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.tayana.command.SocioTorcedorLogicCommand;
import br.com.tayana.domain.SocioTorcedor;
import br.com.tayana.model.CampanhaResponse;
import br.com.tayana.model.SocioTorcedorRequest;
import br.com.tayana.model.SocioTorcedorResponse;
import br.com.tayana.service.SocioTorcedorService;

@Component
public class RegisterSocioTorcedorCmd implements SocioTorcedorLogicCommand {

	private SocioTorcedorRequest request;
	
	private SocioTorcedorResponse response;

	private SocioTorcedor socioTorcedorCadastrado;

	@Autowired
	private SocioTorcedorService service;

	@Override
	public void execute() {

		setSocioTorcedorCadastrado(service.findByEmail(getRequest().getEmail()));

		if (getSocioTorcedorCadastrado() == null) {
			setSocioTorcedorCadastrado(new SocioTorcedor());
			getSocioTorcedorCadastrado().setNome(getRequest().getNome());
			getSocioTorcedorCadastrado().setEmail(getRequest().getEmail());
			getSocioTorcedorCadastrado().setTime(getRequest().getTime());
		}

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<CampanhaResponse>> responseEntity = restTemplate.exchange(
				"http://localhost:8080/campanha/time/" + getSocioTorcedorCadastrado().getTime().getId(), HttpMethod.GET,
				null, new ParameterizedTypeReference<List<CampanhaResponse>>() {
				});
		List<CampanhaResponse> campanhas = responseEntity.getBody();

		if (getSocioTorcedorCadastrado().getCampanhas() == null) {
			getSocioTorcedorCadastrado().setCampanhas(new ArrayList<Integer>());
		}
		
		for (CampanhaResponse campanha : campanhas) {
			if (!getSocioTorcedorCadastrado().getCampanhas().contains(campanha.getId())) {
				getSocioTorcedorCadastrado().getCampanhas().add(campanha.getIdTime());
			}
		}

		setSocioTorcedorCadastrado(service.insert(getSocioTorcedorCadastrado()));
		
		response = new SocioTorcedorResponse();
		response.setId(getSocioTorcedorCadastrado().getId());
		response.setEmail(getSocioTorcedorCadastrado().getEmail());
		response.setNome(getSocioTorcedorCadastrado().getNome());
		response.setTime(getSocioTorcedorCadastrado().getTime());
		response.setCampanhas(getSocioTorcedorCadastrado().getCampanhas());
	}

	public SocioTorcedorRequest getRequest() {
		return request;
	}

	public void setRequest(SocioTorcedorRequest request) {
		this.request = request;
	}

	public SocioTorcedorResponse getResponse() {
		return response;
	}

	public void setResponse(SocioTorcedorResponse response) {
		this.response = response;
	}

	public SocioTorcedor getSocioTorcedorCadastrado() {
		return socioTorcedorCadastrado;
	}

	public void setSocioTorcedorCadastrado(SocioTorcedor socioTorcedorCadastrado) {
		this.socioTorcedorCadastrado = socioTorcedorCadastrado;
	}

}
