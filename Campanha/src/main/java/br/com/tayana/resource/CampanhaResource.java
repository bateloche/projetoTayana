package br.com.tayana.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.tayana.command.CampanhaCommandFactory;
import br.com.tayana.command.model.DeleteCampanhaCmd;
import br.com.tayana.command.model.FindCampanhaCmd;
import br.com.tayana.command.model.RegisterCampanhaCmd;
import br.com.tayana.command.model.UpdateCampanhaCmd;
import br.com.tayana.model.CampanhaRequest;
import br.com.tayana.model.CampanhaResponse;

@RestController
@RequestMapping(value="/campanhas")
public class CampanhaResource {
	
	@Autowired
	CampanhaCommandFactory commandFactory;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<CampanhaResponse> findCampanhas() {
		FindCampanhaCmd cmd = commandFactory.createInstance(FindCampanhaCmd.class);
		cmd.execute();	
		return cmd.getResponses();
	}
	
	@GetMapping("/time/{id}")
	public List<CampanhaResponse> findCampanhasAtivasPorTime(Integer id) {
		FindCampanhaCmd cmd = commandFactory.createInstance(FindCampanhaCmd.class);
		cmd.setIdTime(id);
		cmd.execute();	
		return cmd.getResponses();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody CampanhaRequest request) {
		RegisterCampanhaCmd cmd = commandFactory.createInstance(RegisterCampanhaCmd.class);
		cmd.setRequest(request);
		cmd.execute();	
		return ResponseEntity.ok().body(cmd.getResponse());
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public CampanhaResponse update(@RequestBody CampanhaRequest request) {
		UpdateCampanhaCmd cmd = commandFactory.createInstance(UpdateCampanhaCmd.class);
		cmd.setRequest(request);
		cmd.execute();	
		return cmd.getResponse();
	}
	
	@RequestMapping(value= "/{id}" , method = RequestMethod.DELETE)
	public String delete(@PathVariable Integer id) {
		DeleteCampanhaCmd cmd = commandFactory.createInstance(DeleteCampanhaCmd.class);
		cmd.setId(id);
		cmd.execute();	
		return cmd.getMessage();
	}
	
}
