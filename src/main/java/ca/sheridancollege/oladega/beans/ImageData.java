package ca.sheridancollege.oladega.beans;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ImageData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long Id;
	private String name;
	private String type;
	@Lob //binary format in the db
	private byte[]  imageData;
	
}
