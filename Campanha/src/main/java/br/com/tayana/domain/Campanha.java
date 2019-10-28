package br.com.tayana.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.tayana.enums.TimesEnum;

@Document(collection = "campanha")
public class Campanha implements Serializable {

	private static final long serialVersionUID = 5647679975891208095L;

	@Transient
	public static final String SEQUENCE_NAME = "seq_campanha";
	
	@Id
	private long id;
	private TimesEnum idTime;
	private String nome;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private LocalDateTime dataModificacao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TimesEnum getIdTime() {
		return idTime;
	}

	public void setIdTime(TimesEnum time) {
		this.idTime = time;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public LocalDateTime getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(LocalDateTime dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
}
