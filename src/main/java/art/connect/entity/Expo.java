package art.connect.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Expo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long expoId;

	@EqualsAndHashCode.Exclude
	private String expoName;

	@EqualsAndHashCode.Exclude
	private String expoLocation;

	@EqualsAndHashCode.Exclude
	private Date expoStartDate;

	@EqualsAndHashCode.Exclude
	private Date expoEndDate;

	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "art_piece_expo", joinColumns = @JoinColumn(name = "expo_id"), inverseJoinColumns = @JoinColumn(name = "art_piece_id"))
	@JsonBackReference
	Set<ArtPiece> artPieces = new HashSet<>();
}
