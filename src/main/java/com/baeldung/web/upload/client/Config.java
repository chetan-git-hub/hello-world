package com.baeldung.web.upload.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)

public class Config {
	private String applicationName;
	private String serviceName;
	private String description;
	private String dataProtocol;
	private String transportProtocol;
	private String environment;
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDataProtocol() {
		return dataProtocol;
	}
	public void setDataProtocol(String dataProtocol) {
		this.dataProtocol = dataProtocol;
	}
	public String getTransportProtocol() {
		return transportProtocol;
	}
	public void setTransportProtocol(String transportProtocol) {
		this.transportProtocol = transportProtocol;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	@Override
	public String toString() {
		return "Config [applicationName=" + applicationName + ", serviceName=" + serviceName + ", description="
				+ description + ", dataProtocol=" + dataProtocol + ", transportProtocol=" + transportProtocol
				+ ", environment=" + environment + "]";
	}
	
}