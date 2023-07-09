package art.connect.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import art.connect.controller.model.ArtPieceData;
import art.connect.controller.model.ArtistData;
import art.connect.controller.model.ExpoData;
import art.connect.dao.ArtPieceDao;
import art.connect.dao.ArtistDao;
import art.connect.dao.ExpoDao;
import art.connect.entity.ArtPiece;
import art.connect.entity.Artist;
import art.connect.entity.Expo;

@Service
public class ArtConnectService {

	@Autowired
	private ExpoDao expoDao;

	@Autowired
	private ArtistDao artistDao;

	@Autowired
	private ArtPieceDao artPieceDao;

	@Transactional(readOnly = true)
	public List<ExpoData> retrieveAllExpos(Optional<java.util.Date> startDate, Optional<java.util.Date> endDate,
			Optional<String> location) {
		List<Expo> expos;

		if (startDate.isPresent() && endDate.isPresent()) {
			java.sql.Date sqlStartDate = new java.sql.Date(startDate.get().getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(endDate.get().getTime());
			expos = expoDao.findByExpoStartDateGreaterThanEqualAndExpoEndDateLessThanEqual(sqlStartDate, sqlEndDate);
		} else if (location.isPresent()) {
			expos = expoDao.findByExpoLocationContainingIgnoreCase(location.get());
		} else {
			expos = expoDao.findAll();
		}

		List<ExpoData> response = new LinkedList<>();
		for (Expo expo : expos) {
			response.add(new ExpoData(expo, false, false));
		}
		return response;
	}

	@Transactional
	public ArtistData createArtist(ArtistData artistData) {
		Artist artist = new Artist();

		artist.setArtistName(artistData.getArtistName());
		artist.setFullName(artistData.getArtistFullName());
		artist.setBio(artistData.getArtistBio());
		Artist createdArtist = artistDao.save(artist);
		return new ArtistData(createdArtist, false, false);
	}

	@Transactional
	public ArtistData updateArtist(ArtistData artistData) {

		Long artistId = artistData.getArtistId();
		Artist artist = findArtist(artistId);
		setArtistFields(artist, artistData);
		return new ArtistData(artistDao.save(artist), true, false);

	}

	@Transactional
	public ArtistData retrieveArtistById(Long artistId) {
		Artist artist = findArtist(artistId);
		List<ArtPiece> artPieces = artPieceDao.findAllArtPiecesByArtistArtistId(artistId);
		Set<ArtPiece> artPieceSet = new HashSet<>(artPieces);

		artist.setArtPieces(artPieceSet);
		return new ArtistData(artist, true, false);
	}

	private void setArtistFields(Artist artist, ArtistData artistData) {

		artist.setFullName(artistData.getArtistFullName());
		artist.setArtistName(artistData.getArtistName());
		artist.setBio(artistData.getArtistBio());

	}

	@Transactional(readOnly = false)
	public ArtPieceData createArtPiece(Long artistId, ArtPieceData artPieceData) {
		ArtPiece artPiece = new ArtPiece();

		Artist artist = artistDao.findById(artistId)
				.orElseThrow(() -> new RuntimeException("Artist not found with ID=" + artistId));

		artPiece.setArtist(artist);
		artPiece.setArtTitle(artPieceData.getArtPieceTitle());
		artPiece.setArtYear(artPieceData.getArtPieceYear());
		artPiece.setArtMedium(artPieceData.getArtPieceMedium());

		ArtPiece createdArtPiece = artPieceDao.save(artPiece);
		return new ArtPieceData(createdArtPiece, false, false);
	}

	@Transactional(readOnly = true)
	public ArtPieceData retrieveArtPieceById(Long artPieceId) {
		ArtPiece artPiece = findArtPiece(artPieceId);
		return new ArtPieceData(artPiece, true, true);
	}

	@Transactional(readOnly = false)
	public void deleteArtPieceById(Long artPieceId) {
		ArtPiece artPiece = findArtPiece(artPieceId);
		artPieceDao.delete(artPiece);

	}

	@Transactional(readOnly = false)
	public ArtPieceData updateArtPiece(ArtPieceData artPieceData) {

		Long artPieceId = artPieceData.getArtPieceId();

		ArtPiece artPiece = findArtPiece(artPieceId);

		setArtPieceFields(artPiece, artPieceData);

		return new ArtPieceData(artPieceDao.save(artPiece), false, true);

	}

	private void setArtPieceFields(ArtPiece artPiece, ArtPieceData artPieceData) {
		artPiece.setArtTitle(artPieceData.getArtPieceTitle());
		artPiece.setArtYear(artPieceData.getArtPieceYear());
		artPiece.setArtMedium(artPieceData.getArtPieceMedium());
	}

	public ArtPiece findArtPiece(Long artPieceId) {

		return artPieceDao.findById(artPieceId)
				.orElseThrow(() -> new NoSuchElementException("Art piece with ID=" + artPieceId + " does not exist."));
	}

	@Transactional(readOnly = true)
	public List<ArtistData> retrieveAllArtists() {
		List<Artist> artists = artistDao.findAll();
		List<ArtistData> response = new LinkedList<>();

		for (Artist artist : artists) {
			response.add(new ArtistData(artist, false, false));
		}
		return response;
	}

	@Transactional(readOnly = false)
	public ExpoData createExpo(ExpoData expoData) {
		Expo expo = new Expo();

		expo.setExpoLocation(expoData.getExpoLocation());
		expo.setExpoName(expoData.getExpoName());
		expo.setExpoStartDate(expoData.getExpoStartDate());
		expo.setExpoEndDate(expoData.getExpoEndDate());

		Expo createdExpo = expoDao.save(expo);
		return new ExpoData(createdExpo, false, false);
	}

	@Transactional
	public ExpoData retrieveExpoById(Long expoId) {
		Expo expo = findExpo(expoId);
		return new ExpoData(expo, true, false);
	}
	
	@Transactional(readOnly = false)
	public ExpoData addArtPieceToExpo(Long expoId, Long artPieceId) {
	    Expo expo = findExpo(expoId);
	    ArtPiece artPiece = findArtPiece(artPieceId);
	    Set<ArtPiece> expoPieces = expo.getArtPieces();
	    
	    for(Expo existingExpo : artPiece.getExpos()) {
	        if(!(expo.getExpoEndDate().before(existingExpo.getExpoStartDate()) || expo.getExpoStartDate().after(existingExpo.getExpoEndDate()))) {
	            throw new UnsupportedOperationException("The art piece is already in another expo during these dates.");
	        }
	    }

	    expoPieces.add(artPiece);
	    expo.setArtPieces(expoPieces);
	    return new ExpoData(expoDao.save(expo), true, false);
	}
	
	@Transactional
	public void removeArtPieceFromExpo(Long expoId, Long artPieceId) {
		Expo expo = findExpo(expoId);
	    ArtPiece artPiece = findArtPiece(artPieceId);
	    if (!expo.getArtPieces().contains(artPiece)) {
	        throw new IllegalArgumentException("ArtPiece with ID " + artPieceId + " is not associated with Expo ID " + expoId);
	    }
	    
	    expo.getArtPieces().remove(artPiece);
	    artPiece.getExpos().remove(expo);
	    
	    artPieceDao.save(artPiece);
	    expoDao.save(expo);
	}

	@Transactional(readOnly = false)
	public ExpoData updateExpo(ExpoData expoData) {
		Long expoId = expoData.getExpoId();

		Expo expo = findExpo(expoId);

		setExpoFields(expo, expoData);

		return new ExpoData(expoDao.save(expo), false, false);
	}

	@Transactional(readOnly = false)
	public void deleteExpoById(Long expoId) {
		Expo expo = findExpo(expoId);
		expoDao.delete(expo);

	}

	private void setExpoFields(Expo expo, ExpoData expoData) {
		expo.setExpoName(expoData.getExpoName());
		expo.setExpoLocation(expoData.getExpoLocation());
		expo.setExpoStartDate(expoData.getExpoStartDate());
		expo.setExpoEndDate(expoData.getExpoEndDate());

	}

	public Expo findExpo(Long expoId) {
		return expoDao.findById(expoId)
				.orElseThrow(() -> new NoSuchElementException("Expo with ID=" + expoId + " was not found."));
	}

	public Artist findArtist(Long artistId) {
		return artistDao.findById(artistId)
				.orElseThrow(() -> new NoSuchElementException("Artist with ID=" + artistId + " was not found."));
	}

	public void deleteArtistById(Long artistId) {
		Artist artist = findArtist(artistId);
		artistDao.delete(artist);
	}

	@Transactional(readOnly = true)
	public List<ArtPieceData> retrieveAllArtPieces() {
		List<ArtPiece> artPieces = artPieceDao.findAll();
		List<ArtPieceData> response = new LinkedList<>();

		for (ArtPiece artPiece : artPieces) {
			response.add(new ArtPieceData(artPiece, false, false));
		}
		return response;
	}

}
