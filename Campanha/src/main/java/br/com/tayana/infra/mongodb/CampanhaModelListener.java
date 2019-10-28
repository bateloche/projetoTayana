package br.com.tayana.infra.mongodb;


	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
	import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
	import org.springframework.stereotype.Component;

import br.com.tayana.domain.Campanha;


	@Component
	public class CampanhaModelListener extends AbstractMongoEventListener<Campanha> {

	    private SequenceGeneratorService sequenceGenerator;

	    @Autowired
	    public CampanhaModelListener(SequenceGeneratorService sequenceGenerator) {
	        this.sequenceGenerator = sequenceGenerator;
	    }

	    @Override
	    public void onBeforeConvert(BeforeConvertEvent<Campanha> event) {
	        if (event.getSource().getId() < 1) {
	            event.getSource().setId(sequenceGenerator.generateSequence(Campanha.SEQUENCE_NAME));
	        }
	    }


	}