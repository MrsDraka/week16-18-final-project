package art.connect.controller.model;

import java.util.ArrayList;
import java.util.List;

import art.connect.entity.ArtPiece;
import art.connect.entity.Artist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtistData {

	private Long artistId;
	private String artistFullName;
	private String artistName;
	private String artistBio;
	private List<ArtPieceData> artPieces;

	public ArtistData(Artist artist, boolean includeArtPieces, boolean includeExpos) {
		artistId = artist.getArtistId();
		artistFullName = artist.getFullName();
		artistName = artist.getArtistName();
		artistBio = artist.getBio();
		if (includeArtPieces) {
			artPieces = new ArrayList<>();
			for (ArtPiece artPiece : artist.getArtPieces()) {
				artPieces.add(new ArtPieceData(artPiece, includeArtPieces, includeExpos));
			}

		}

	}
}