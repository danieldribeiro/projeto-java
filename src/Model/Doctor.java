package Model;

public class Doctor {
    Integer id;
    String name;
    Integer crm;
    String speciality;

    public Doctor() {
    }

    public Doctor(Integer id, String name, Integer crm, String speciality) {
        this.id = id;
        this.name = name;
        this.crm = crm;
        this.speciality = speciality;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCrm() {
        return crm;
    }

    public void setCrm(Integer crm) {
        this.crm = crm;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
