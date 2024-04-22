package ai.acintyo.sales.collection;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection="MrVisit")
@Data
public class MrVisit {

	@Id
	private String id;
	private String supplierId;
	private String retailerId;
	private String mrId;
	private String asmId;
	private LocalTime visitTime;
	private String visitLocationLatitude;
	private String visitLocationLongitude;
	private LocalDateTime insertedOn;
	private LocalDateTime updatedOn;
}
