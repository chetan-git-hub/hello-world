package com.baeldung.web.upload.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.web.upload.client.Config;
import com.baeldung.web.upload.client.ConfigService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/fileserver")
public class FileServerResource {

    @RequestMapping(path = "/singlefileupload/", method = RequestMethod.POST)
    public ResponseEntity<String> processFile(@RequestHeader("Authorization") String cred, @RequestPart("config") String config ,@RequestPart("file") MultipartFile file) throws IOException {

        byte[] bytes = file.getBytes();

        System.out.println("File Name: " + file.getOriginalFilename());
        System.out.println("File Content Type: " + file.getContentType());
        System.out.println("File Content:\n" + new String(bytes));
                		
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		  
		 // headers.setBasicAuth("user", "password");
			
			/*
			 * String plainCreds = cred;//"user:password"; byte[] plainCredsBytes =
			 * plainCreds.getBytes(); //byte[] base64CredsBytes =
			 * Base64.encodeBase64(plainCredsBytes); String base64Creds = new
			 * String(plainCredsBytes);
			 */

			  headers.add("Authorization", cred);
			
		  			
			  MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			  ByteArrayResource contentsAsResource = new ByteArrayResource(bytes){
				  @Override
				  public String getFilename() {
					  return file.getOriginalFilename();
				  }
			  };
			  body.add("file",  contentsAsResource); // body.add("user", user);
			  ConfigService configService = new ConfigService();
			  
			  Config configFile = configService.getJson(config);			  
			  
			  body.add("config", getTestFile(configFile));
			  
			  
			  HttpEntity<MultiValueMap<String, Object>> requestEntity = new
			  HttpEntity<>(body, headers);
			 
			String serverUrl = "http://localhost:8082/upload/";

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
			System.out.println("Response code: " + response.getBody());
		 
        
        return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
    }

	/*
	 * @RequestMapping(path = "/multiplefileupload/", method = RequestMethod.POST)
	 * public ResponseEntity<String> processFile(@RequestParam("files")
	 * List<MultipartFile> files) throws IOException {
	 * 
	 * for (MultipartFile file : files) { byte[] bytes = file.getBytes();
	 * 
	 * System.out.println("File Name: " + file.getOriginalFilename());
	 * System.out.println("File Content Type: " + file.getContentType());
	 * System.out.println("File Content:\n" + new String(bytes)); }
	 * 
	 * return (new ResponseEntity<>("Successful", null, HttpStatus.OK)); }
	 */
    
    public static Resource getTestFile(Config config) throws IOException {
        Path testFile = Files.createTempFile("config", ".config");
        System.out.println("Creating and Uploading Config File: " + testFile);
        Files.write(testFile, config.toString().getBytes());
        
        return new FileSystemResource(testFile.toFile());
    }
    
}