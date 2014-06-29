package com.clufsolutions.seniatwithholdings.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import java.net.URI;

/**
 * Created by Eduardo on 14-06-2014.
 */
@Configuration
@Import(RepositoryRestMvcConfiguration.class)
public class RestExporterConfig extends RepositoryRestMvcConfiguration {}