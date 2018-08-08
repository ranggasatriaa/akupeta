package com.astagenta.rangga.akupeta;

public class Tempat {

  package com.astagenta.rangga.akupeta;

import android.content.Intent;

  public class Tempat {
    private int id;
    private String nama;
    private String deskripsi;
    private String telphone;
    private Double latitude;
    private Double longitude;
    private Integer kategoriId;
    private Integer status;
    private String kategoriName;
    private String kategoriIcon;

    public Tempat(int id, String nama, String deskripsi, String telphone, Double latitude, Double longitude, Integer kategoriId, Integer status, String kategoriName, String kategoriIcon) {
      this.id = id;
      this.nama = nama;
      this.deskripsi = deskripsi;
      this.telphone = telphone;
      this.latitude = latitude;
      this.longitude = longitude;
      this.kategoriId = kategoriId;
      this.status = status;
      this.kategoriName = kategoriName;
      this.kategoriIcon = kategoriIcon;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getNama() {
      return nama;
    }

    public void setNama(String nama) {
      this.nama = nama;
    }

    public String getDeskripsi() {
      return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
      this.deskripsi = deskripsi;
    }

    public String getTelphone() {
      return telphone;
    }

    public void setTelphone(String telphone) {
      this.telphone = telphone;
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

    public Integer getKategoriId() {
      return kategoriId;
    }

    public void setKategoriId(Integer kategoriId) {
      this.kategoriId = kategoriId;
    }

    public Integer getStatus() {
      return status;
    }

    public void setStatus(Integer status) {
      this.status = status;
    }

    public String getKategoriName() {
      return kategoriName;
    }

    public void setKategoriName(String kategoriName) {
      this.kategoriName = kategoriName;
    }

    public String getKategoriIcon() {
      return kategoriIcon;
    }

    public void setKategoriIcon(String kategoriIcon) {
      this.kategoriIcon = kategoriIcon;
    }
  }

}
