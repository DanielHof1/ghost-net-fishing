package de.iu.ghostnet.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ghost_net")
public class GhostNet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "size")
	private String size;

	@Column(name = "status")
	private String status;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "is_anonymous")
	private boolean anonymous;

	
	@ManyToOne
	@JoinColumn(name = "reporter_id")
	private Person reporter;

	@ManyToOne
	@JoinColumn(name = "rescuer_id")
	private Person rescuer;
	
	@ManyToOne
	@JoinColumn(name = "lost_reporter_id")
	private Person lostReporter;

	// Konstruktoren
	public GhostNet() {
		this.createdAt = LocalDateTime.now();
		this.status = Status.GEMELDET;
	}

	public GhostNet(Double latitude, Double longitude, String size, String description, Person reporter) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.size = size;
		this.description = description;
		this.reporter = reporter;
		this.createdAt = LocalDateTime.now();
		this.status = Status.GEMELDET;
	}


	// Getter und Setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}
	
	public Person getReporter() {
		return reporter;
	}

	public void setReporter(Person reporter) {
		this.reporter = reporter;
	}

	public Person getRescuer() {
		return rescuer;
	}

	public void setRescuer(Person rescuer) {
		this.rescuer = rescuer;
	}

	public Person getLostReporter() {
		return lostReporter;
	}

	public void setLostReporter(Person lostReporter) {
		this.lostReporter = lostReporter;
	}
	

	
	
}