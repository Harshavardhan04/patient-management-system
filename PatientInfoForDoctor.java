package trial.swing;

public class PatientInfoForDoctor implements Comparable<PatientInfoForDoctor> {

	private String pName;
	private String pDate;
	private String pTime;

	public PatientInfoForDoctor(String pName, String pDate, String pTime) {
		this.pName = pName;
		this.pDate = pDate;
		this.pTime = pTime;
	}

	public PatientInfoForDoctor() {
		// TODO Auto-generated constructor stub
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpDate() {
		return pDate;
	}

	public void setpDate(String pDate) {
		this.pDate = pDate;
	}

	public String getpTime() {
		return pTime;
	}

	public void setpTime(String pTime) {
		this.pTime = pTime;
	}

	@Override
	public int compareTo(PatientInfoForDoctor o) {
		return this.pName.compareTo(o.getpName());
	}

}
