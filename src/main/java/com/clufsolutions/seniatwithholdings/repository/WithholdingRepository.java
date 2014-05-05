package com.clufsolutions.seniatwithholdings.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.clufsolutions.seniatwithholdings.domain.Withholding;

@RepositoryRestResource
public interface WithholdingRepository extends
		PagingAndSortingRepository<Withholding, Long> {

}
