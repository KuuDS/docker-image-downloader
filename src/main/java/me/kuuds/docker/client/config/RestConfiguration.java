package me.kuuds.docker.client.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * RestTemplate Configuration.
 *
 * @author KuuDS
 */
@Configuration
public class RestConfiguration {

  /**
   * Bean Definition RestTemplate.
   *
   * @param restTemplateBuilder builder from auto configuraiton.
   * @return {@link RestTemplate}
   */
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder.build();
  }
}
