package studio.lineage2.cms.other;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) public class ValidationResult
{
	public boolean success;

	@JsonCreator
	public ValidationResult(@JsonProperty("success") boolean success)
	{
		this.success = success;
	}

	public boolean isSuccess()
	{
		return success;
	}
}