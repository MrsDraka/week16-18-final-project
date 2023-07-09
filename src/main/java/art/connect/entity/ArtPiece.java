package art.connect.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class ArtPiece {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long artPieceId;

	@EqualsAndHashCode.Exclude
	public String artTitle;

	@EqualsAndHashCode.Exclude
	public Long artYear;

	@EqualsAndHashCode.Exclude
	public String artMedium;

	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "artist_id", nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Artist artist;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "artPieces", cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties("artPieces")
	private Set<Expo> expos = new HashSet<>();

}
