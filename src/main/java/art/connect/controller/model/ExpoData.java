package art.connect.controller.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import art.connect.entity.ArtPiece;
import art.connect.entity.Expo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpoData {

	private Long expoId;
	private String expoName;
	private String expoLocation;
	private Date expoStartDate;
	private Date expoEndDate;
	private List<ArtPieceData> expoArtPieces;

	public ExpoData(Expo expo, boolean includePieces, boolean includeArtist) {
		expoId = expo.getExpoId();
		expoName = expo.getExpoName();
		expoLocation = expo.getExpoLocation();
		expoStartDate = expo.getExpoStartDate();
		expoEndDate = expo.getExpoEndDate();

		if (includePieces) {
			expoArtPieces = new ArrayList<>();
			for (ArtPiece artPiece : expo.getArtPieces()) {
				expoArtPieces.add(new ArtPieceData(artPiece, false, includeArtist));
			}

		}

	}
}
