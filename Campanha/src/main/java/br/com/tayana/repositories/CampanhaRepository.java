package br.com.tayana.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tayana.domain.Campanha;
import br.com.tayana.enums.TimesEnum;

@Repository
public interface CampanhaRepository extends MongoRepository<Campanha, Long> {
	
	@Query("{'dataFim':{$gte:?0}}")
	public List<Campanha> findByDataFimGreaterThanOrEqual(Instant dataFim, Sort sort);
	
	@Query( "{ " + 
			"   idTime: '?0', " + 
			"	dataInicio: { $lte: ?1 }, " + 
			"	dataFim: { $gte: ?1 } " + 
			" }")
	public List<Campanha> findAtivasByTime(TimesEnum time, Instant data);
}
