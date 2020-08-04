package com.alex.telegram.bot.service;

import java.util.Arrays;
import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alex.telegram.bot.config.ReadProperties;
import com.alex.telegram.bot.dto.OfertaDTO;

@Service
public class IntegrationService {
	
	public List<OfertaDTO> execute(Double latitude, Double longitude){
		String url = ReadProperties.getProperty("app.alex.oferta.oferta-endpoint");
		List<OfertaDTO> list = null;
		
		HttpEntity<String> request = new HttpEntity<>(this.generateJsonContentTypeHeader());
		String urlferta = url.trim() + "localizacao?latitude=" + latitude.toString() + "&longitude=" + longitude.toString();
		ResponseEntity< OfertaDTO[]> response = new RestTemplate().exchange(urlferta, HttpMethod.GET, request, OfertaDTO[].class);
		if(response.getBody() != null) {			
			list = Arrays.asList(response.getBody());
		}
		return list;
	}
	
	private HttpHeaders generateJsonContentTypeHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
