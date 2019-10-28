package br.com.tayana.command.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.tayana.command.CampanhaLogicCommand;
import br.com.tayana.services.CampanhaService;

@Component
public class DeleteCampanhaCmd implements CampanhaLogicCommand{
	
	@Autowired
	private CampanhaService service;
	
	private String message;
	
	private Integer id;


	@Override
	public void execute() {
		this.message = service.delete(getId());
	}
	
	public String getMessage() {
		return this.message;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

}
