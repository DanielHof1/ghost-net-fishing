package de.iu.ghostnet.beans;

import de.iu.ghostnet.entities.GhostNet;
import de.iu.ghostnet.entities.Person;
import de.iu.ghostnet.entities.Status;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;
import java.util.List;

@Named
@Stateless
public class GhostNetBean {

	@PersistenceContext
	private EntityManager em;

	private List<GhostNet> ghostNets;
	private Long selectedGhostNetId;

	// Neue Geisternetz-Daten
	private Double latitude;
	private Double longitude;
	private String size;
	private String description;
	private String reporterFirstName;
	private String reporterLastName;
	private String reporterPhone;
	private boolean anonymous;

	// Retter-Daten
	private String rescuerFirstName;
	private String rescuerLastName;
	private String rescuerPhone;

	// Verschollen Daten
	private Long selectedLostNetId;
	private String lostReporterFirstName;
	private String lostReporterLastName;
	private String lostReporterPhone;

	// Alle Geisternetze laden
	public void loadGhostNets() {
		TypedQuery<GhostNet> query = em.createQuery("SELECT g FROM GhostNet g ORDER BY g.createdAt DESC",
				GhostNet.class);
		ghostNets = query.getResultList();
	}

	// Neues Geisternetz melden
	public String reportGhostNet() {

		try {
			Person reporter = null;

			if (!anonymous && reporterFirstName != null && !reporterFirstName.isEmpty() && reporterLastName != null
					&& !reporterLastName.isEmpty()) {
				reporter = new Person(reporterFirstName, reporterLastName, reporterPhone);
				em.persist(reporter);
			}

			GhostNet ghostNet = new GhostNet(latitude, longitude, size, description, reporter);
			ghostNet.setStatus(Status.GEMELDET);
			ghostNet.setAnonymous(anonymous);
			em.persist(ghostNet);

			clearFormData();
			loadGhostNets();
			return "index";
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// Für Bergung eintragen
	public String registerForRescue() {
		try {
			GhostNet ghostNet = em.find(GhostNet.class, selectedGhostNetId);

			Person rescuer = new Person(rescuerFirstName, rescuerLastName, rescuerPhone);
			em.persist(rescuer);

			ghostNet.setRescuer(rescuer);
			ghostNet.setStatus(Status.BERGUNG_BEVORSTEHEND);
			em.merge(ghostNet);

			clearRescuerData();
			loadGhostNets();
			return "ghostnetlist";
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Speichert die ID des ausgewählten Geisternetzes und leitet zur Bergungsseite weiter
	public String prepareRescue(Long ghostNetId) {
		this.selectedGhostNetId = ghostNetId;
		return "rescue";
	}

	// Als geborgen markieren
	public void markAsRescued(Long ghostNetId) {
		try {
			GhostNet ghostNet = em.find(GhostNet.class, ghostNetId);
			ghostNet.setStatus(Status.GEBORGEN);
			em.merge(ghostNet);
			loadGhostNets();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Geisternetz als verschollen melden
	public String markAsLost() {
		try {
			GhostNet ghostNet = em.find(GhostNet.class, selectedLostNetId);

			Person lostReporter = new Person(lostReporterFirstName, lostReporterLastName, lostReporterPhone);
			em.persist(lostReporter);

			ghostNet.setLostReporter(lostReporter);
			ghostNet.setStatus(Status.VERSCHOLLEN);
			em.merge(ghostNet);

			clearLostData();
			loadGhostNets();
			return "ghostnetlist";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Speichert die ID des ausgewählten Geisternetzes und leitet zur Seite für die Verschollen-Meldung weiter
	public String prepareLost(Long ghostNetId) {
		this.selectedLostNetId = ghostNetId;
		return "lostnet";
	}

	// Daten zurücksetzen
	private void clearFormData() {
		latitude = null;
		longitude = null;
		size = null;
		description = null;
		reporterFirstName = null;
		reporterLastName = null;
		reporterPhone = null;
		anonymous = false;
	}

	private void clearRescuerData() {
		rescuerFirstName = null;
		rescuerLastName = null;
		rescuerPhone = null;
	}

	private void clearLostData() {
		selectedLostNetId = null;
		lostReporterFirstName = null;
		lostReporterLastName = null;
		lostReporterPhone = null;
	}

	// Getter und Setter
	public List<GhostNet> getGhostNets() {
		loadGhostNets();
		return ghostNets;
	}

	
	public void setSelectedGhostNetId(Long selectedGhostNetId) {
		this.selectedGhostNetId = selectedGhostNetId;
	}

	public Long getSelectedGhostNetId() {
		return selectedGhostNetId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReporterFirstName() {
		return reporterFirstName;
	}

	public void setReporterFirstName(String reporterFirstName) {
		this.reporterFirstName = reporterFirstName;
	}

	public String getReporterLastName() {
		return reporterLastName;
	}

	public void setReporterLastName(String reporterLastName) {
		this.reporterLastName = reporterLastName;
	}

	public String getReporterPhone() {
		return reporterPhone;
	}

	public void setReporterPhone(String reporterPhone) {
		this.reporterPhone = reporterPhone;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public String getRescuerFirstName() {
		return rescuerFirstName;
	}

	public void setRescuerFirstName(String rescuerFirstName) {
		this.rescuerFirstName = rescuerFirstName;
	}

	public String getRescuerLastName() {
		return rescuerLastName;
	}

	public void setRescuerLastName(String rescuerLastName) {
		this.rescuerLastName = rescuerLastName;
	}

	public String getRescuerPhone() {
		return rescuerPhone;
	}

	public void setRescuerPhone(String rescuerPhone) {
		this.rescuerPhone = rescuerPhone;
	}

	public Long getSelectedLostNetId() {
		return selectedLostNetId;
	}

	public void setSelectedLostNetId(Long selectedLostNetId) {
		this.selectedLostNetId = selectedLostNetId;
	}

	public String getLostReporterFirstName() {
		return lostReporterFirstName;
	}

	public void setLostReporterFirstName(String lostReporterFirstName) {
		this.lostReporterFirstName = lostReporterFirstName;
	}

	public String getLostReporterLastName() {
		return lostReporterLastName;
	}

	public void setLostReporterLastName(String lostReporterLastName) {
		this.lostReporterLastName = lostReporterLastName;
	}

	public String getLostReporterPhone() {
		return lostReporterPhone;
	}

	public void setLostReporterPhone(String lostReporterPhone) {
		this.lostReporterPhone = lostReporterPhone;
	}
}
