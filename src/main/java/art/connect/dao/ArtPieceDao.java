package art.connect.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import art.connect.entity.ArtPiece;

public interface ArtPieceDao extends JpaRepository<ArtPiece, Long> {

	List<ArtPiece> findAllArtPiecesByArtistArtistId(Long artistId);

}