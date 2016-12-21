package studio.lineage2.cms.other;

import org.springframework.security.core.AuthenticationException;

import java.util.List;

import static java.util.Collections.singletonList;

public class RecaptchaAuthenticationException extends AuthenticationException
{
	public RecaptchaAuthenticationException(List<ErrorCode> errorCodes)
	{
		super("reCAPTCHA authentication error: " + errorCodes);
	}

	public RecaptchaAuthenticationException(ErrorCode errorCode)
	{
		this(singletonList(errorCode));
	}
}