package art.connect.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import art.connect.controller.model.ArtPieceData;
import art.connect.controller.model.ArtistData;
import art.connect.controller.model.ExpoData;
import art.connect.service.ArtConnectService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/art_connect")
public class ArtConnectController {

	@Autowired
	private ArtConnectService artConnectService;

	@GetMapping("/expos")
	public List<ExpoData> retrieveAllExpos(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.util.Date endDate,
			@RequestParam(required = false) String location) {
		log.info("Retrieving all expos.");
		return artConnectService.retrieveAllExpos(Optional.ofNullable(startDate), Optional.ofNullable(endDate),
				Optional.ofNullable(location));
	}

	@GetMapping("/expos/{expoId}")
	public ExpoData retrieveExpoById(@PathVariable Long expoId) {
		log.info("Retrieving expo with ID={}", expoId);
		return artConnectService.retrieveExpoById(expoId);
	}

	@PostMapping("/expos")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ExpoData createExpo(@RequestBody ExpoData expoData) {
		log.info("Creating new expo.");
		if (expoData.getExpoStartDate().after(expoData.getExpoEndDate())) {
			throw new IllegalArgumentException("Expo start date cannot be after end date");
		}
		return artConnectService.createExpo(expoData);
	}

	@PutMapping("/expos/{expoId}")
	public ExpoData updateExpo(@PathVariable Long expoId, @RequestBody ExpoData expoData) {
		log.info("Updating expo {}", expoData);
		if (expoData.getExpoStartDate().after(expoData.getExpoEndDate())) {
			throw new IllegalArgumentException("Expo start date cannot be after end date");
		}
		expoData.setExpoId(expoId);
		return artConnectService.updateExpo(expoData);
	}

	@PostMapping("expos/addPiece/{expoId}")
	public ExpoData addArtPieceToExpo(@PathVariable Long expoId, @RequestBody Long artPieceId) {
		log.info("Adding art piece with ID={} to expo with ID={}", artPieceId, expoId);
		return artConnectService.addArtPieceToExpo(expoId, artPieceId);
	}
	
	@DeleteMapping("/expos/{expoId}/artPieces/{artPieceId}")
	public Map<String, String> removeArtPieceFromExpo(@PathVariable Long expoId, @PathVariable Long artPieceId) {
	    log.info("Removing art piece with ID={} from expo with ID={}", artPieceId, expoId);
	    artConnectService.removeArtPieceFromExpo(expoId, artPieceId);
	    return Map.of("Note", "Removed art piece with ID=" + artPieceId + " from the expo with ID=" +expoId);
	}

	@DeleteMapping("/expos/{expoId}")
	public Map<String, String> deleteExpoById(@PathVariable Long expoId) {
		log.info("Deleting expo with ID={}", expoId);
		artConnectService.deleteExpoById(expoId);
		return Map.of("Note", "Deleted expo with ID=" + expoId);
	}

	@GetMapping("/artists")
	public List<ArtistData> retrieveAllArtists() {
		log.info("Retrieving all artists.");
		return artConnectService.retrieveAllArtists();
	}

	@PostMapping("/artists")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ArtistData createArtist(@RequestBody ArtistData artistData) {
		log.info("Creating new artist.");
		return artConnectService.createArtist(artistData);
	}

	@PutMapping("/artists/{artistId}")
	public ArtistData updateArtist(@PathVariable Long artistId, @RequestBody ArtistData artistData) {
		artistData.setArtistId(artistId);
		log.info("Updating artist {}", artistData);
		return artConnectService.updateArtist(artistData);
	}

	@GetMapping("/artists/{artistId}")
	public ArtistData retrieveArtist(@PathVariable Long artistId) {
		log.info("Retrieving artist with ID={}", artistId);
		return artConnectService.retrieveArtistById(artistId);
	}

	@DeleteMapping("artists/{artistId}")
	public Map<String, String> deleteArtist(@PathVariable Long artistId) {
		log.info("Deleting artist with ID={}", artistId);
		artConnectService.deleteArtistById(artistId);
		return Map.of("Note", "Deleted artist with ID=" + artistId);
	}

	@PostMapping("/artist/{artistId}/art_piece")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ArtPieceData createArtPiece(@PathVariable Long artistId, @RequestBody ArtPieceData artPieceData) {
		log.info("Creating new art piece {} for artist with ID={}", artPieceData, artistId);
		return artConnectService.createArtPiece(artistId, artPieceData);
	}

	@GetMapping("/art_pieces")
	public List<ArtPieceData> retrieveAllArtPieces() {
		log.info("Retrieving all art pieces.");
		return artConnectService.retrieveAllArtPieces();
	}

	@GetMapping("/art_pieces/{artPieceId}")
	public ArtPieceData retrieveArtPieceById(@PathVariable Long artPieceId) {
		log.info("Retrieving art piece with ID={}");
		return artConnectService.retrieveArtPieceById(artPieceId);
	}

	@DeleteMapping("/art_piece/{artPieceId}")
	public Map<String, String> deleteArtPiece(@PathVariable Long artPieceId) {
		log.info("Deleting art piece with ID={}", artPieceId);
		artConnectService.deleteArtPieceById(artPieceId);
		return Map.of("Note", "Deleted art piece with ID=" + artPieceId);
	}

	@PutMapping("/art_piece/{artPieceId}")
	public ArtPieceData updateArtPiece(@PathVariable Long artPieceId, @RequestBody ArtPieceData artPieceData) {

		log.info("Updating art piece {}", artPieceData);

		artPieceData.setArtPieceId(artPieceId);

		return artConnectService.updateArtPiece(artPieceData);

	}

}
