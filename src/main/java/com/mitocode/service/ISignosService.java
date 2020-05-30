package com.mitocode.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mitocode.model.Signos;

public interface ISignosService extends IGenericService<Signos, Integer>{
	
	Page<Signos> listarPageable(Pageable pageable);
	
}
