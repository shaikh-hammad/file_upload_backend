package ca.sheridancollege.oladega.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.oladega.beans.ImageData;

public interface ImageRepo extends JpaRepository<ImageData, Long> {
	
	<Optional>ImageData findByName(String fileName);

}
