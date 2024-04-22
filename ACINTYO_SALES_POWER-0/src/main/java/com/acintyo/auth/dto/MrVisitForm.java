package com.acintyo.auth.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MrVisitForm {

	@NotBlank(message = "{sales.mrvisit.supplierId}")
	private String supplierId;

	@NotBlank(message = "{sales.mrvisit.retailerId}")
	private String retailerId;

	@NotBlank(message = "{sales.mrvisit.mrId}")
	private String mrId;

	@NotBlank(message = "{sales.mrvisit.asmId}")
	private String asmId;

	@NotNull(message = "{sales.mrvisit.visitTime}")
	private LocalDateTime visitTime;

	@NotBlank(message = "{sales.mrvisit.visitLocationLatitude}")
	private String visitLocationLatitude;

	@NotBlank(message = "{sales.mrvisit.visitLocationLongitude}")
	private String visitLocationLongitude;
}
