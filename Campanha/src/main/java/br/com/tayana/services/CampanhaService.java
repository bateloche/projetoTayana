package br.com.tayana.services;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.tayana.domain.Campanha;
import br.com.tayana.enums.TimesEnum;
import br.com.tayana.exceptions.ObjectNotFoundException;
import br.com.tayana.repositories.CampanhaRepository;

@Service
public class CampanhaService {
	
	@Autowired
	private CampanhaRepository repository;
	
	public Campanha find(long id) {
		Optional<Campanha> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Campanha não encontrada! Id: "+id+ " "
				+ ", tipo: " +Campanha.class.getName()));
	}
	
	public List<Campanha> findByDataFimGreaterThanOrEqual(Instant dataFim){
		Sort sort = Sort.by(Direction.ASC, "dataFim");
		List<Campanha> obj = repository.findByDataFimGreaterThanOrEqual(dataFim, sort);
		return obj;
	}
	
	public Campanha insert(Campanha obj) {
		return repository.save(obj);
	}
	
	public Campanha update(Campanha objNew) {
		Campanha obj = new Campanha();
		 obj = find(objNew.getId());
		 obj.setNome(objNew.getNome());
		 obj.setDataInicio(objNew.getDataInicio());
		 obj.setDataFim(objNew.getDataFim());
		 return insert(obj);
	}
	
	public List<Campanha> updateAll(List<Campanha> listObjNew){
		List<Campanha> campanhas = new ArrayList<>();
		for(Campanha objNew: listObjNew) {
			Campanha obj = new Campanha();
			 obj = find(objNew.getId());
			 obj.setNome(objNew.getNome());
			 obj.setDataInicio(objNew.getDataInicio());
			 obj.setDataFim(objNew.getDataFim());
			 campanhas.add(obj);
		}
		return repository.saveAll(campanhas);
	}
	
	public String delete(long id){
		repository.deleteById(id);
		try {
			find(id);
		} catch(ObjectNotFoundException e) {
			return "Campanha" + id + "excluída";
		}
		return "Campanha" + id + "não excluída";
	}
	
	public List<Campanha> findAtivasByTime(TimesEnum time, Instant data){
		return repository.findAtivasByTime(time, data);
	}

}
