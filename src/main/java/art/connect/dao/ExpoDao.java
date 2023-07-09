package art.connect.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import art.connect.entity.Expo;

public interface ExpoDao extends JpaRepository<Expo, Long> {

	List<Expo> findByExpoStartDateGreaterThanEqualAndExpoEndDateLessThanEqual(Date startDate, Date endDate);
	List<Expo> findByExpoLocationContainingIgnoreCase(String location);
    List<Expo> findAll();

}
