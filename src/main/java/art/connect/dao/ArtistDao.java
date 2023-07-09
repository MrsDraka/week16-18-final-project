package art.connect.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import art.connect.entity.Artist;

public interface ArtistDao extends JpaRepository<Artist, Long> {

}