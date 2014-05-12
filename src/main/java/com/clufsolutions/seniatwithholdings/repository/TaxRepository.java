package com.clufsolutions.seniatwithholdings.repository;

import org.springframework.data.repository.CrudRepository;

import com.clufsolutions.seniatwithholdings.domain.Company;

public interface TaxRepository extends CrudRepository<Company, Long> {

}
