package studio.lineage2.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import studio.lineage2.cms.other.ErrorCode;
import studio.lineage2.cms.other.RecaptchaAuthenticationException;
import studio.lineage2.cms.other.ValidationResult;

@Component public class ReCaptchaService
{
	private final RestTemplate rest;
	@Value("${recaptcha.secretKey}") private String secretKey;
	@Value("${recaptcha.verificationUrl}") private String verificationUrl;

	@Autowired
	public ReCaptchaService(RestTemplate restTemplate)
	{
		this.rest = restTemplate;
	}

	public ValidationResult check(String captchaResponse, String clientIp)
	{
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("secret", secretKey);
		body.add("response", captchaResponse);
		body.add("remoteip", clientIp);
		try
		{
			return rest.postForObject(verificationUrl, body, ValidationResult.class);
		}
		catch(RestClientException exception)
		{
			throw new RecaptchaAuthenticationException(ErrorCode.VALIDATION_HTTP_ERROR);
		}
	}
}