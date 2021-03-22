package com.baeldung.web.upload.client;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConfigService {

	public Config getJson(String config) {

		Config configJson = new Config();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			configJson = objectMapper.readValue(config, Config.class);
		} catch (IOException err) {
			System.out.printf("Error", err.toString());
		}
						
		return configJson;

	}

}