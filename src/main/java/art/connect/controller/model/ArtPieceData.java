package art.connect.controller.model;

import java.util.ArrayList;
import java.util.List;

import art.connect.entity.ArtPiece;
import art.connect.entity.Artist;
import art.connect.entity.Expo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtPieceData {

	private Long artPieceId;
	private String artPieceTitle;
	private Long artPieceYear;
	private String artPieceMedium;
	private List<ExpoData> artPieceExpos;
	private Artist artist;

	public ArtPieceData(ArtPiece artPiece, boolean includeExpos, boolean includeArtist) {
		artPieceId = artPiece.getArtPieceId();
		artPieceTitle = artPiece.getArtTitle();
		artPieceYear = artPiece.getArtYear();
		artPieceMedium = artPiece.getArtMedium();

		if (includeExpos) {
			artPieceExpos = new ArrayList<>();
			for (Expo expo : artPiece.getExpos()) {
				artPieceExpos.add(new ExpoData(expo, false, includeArtist));
			}

		}

		if (includeArtist) {
			artist = artPiece.getArtist();
			artist.setArtPieces(null);

		}
	}
}
