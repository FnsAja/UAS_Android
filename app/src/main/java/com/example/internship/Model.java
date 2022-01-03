package com.example.internship;

public class Model {
    Integer id;
    String namaProject, jumlahMember, namaIntern, jobDesc;

    public String getJumlahMember() {
        return jumlahMember;
    }

    public void setJumlahMember(String jumlahMember) {
        this.jumlahMember = jumlahMember;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaProject() {
        return namaProject;
    }

    public void setNamaProject(String namaProject) {
        this.namaProject = namaProject;
    }

    public String getNamaIntern() {
        return namaIntern;
    }

    public void setNamaIntern(String namaIntern) {
        this.namaIntern = namaIntern;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }
}
