package com.learning.net_retrofit_java;

import com.google.gson.annotations.SerializedName;

public class Data {

  @SerializedName("gender")
  private String gender;

  @SerializedName("name")
  private String name;

  @SerializedName("id")
  private String id;

  @SerializedName("email")
  private String email;

  @SerializedName("status")
  private String status;

  Data() {}

  Data(String id, String name, String gender, String email, String status) {
    this.id = id;
    this.name = name;
    this.gender = gender;
    this.email = email;
    this.status = status;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getGender() {
    return gender;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "Data{"
        + "gender = '"
        + gender
        + '\''
        + ",name = '"
        + name
        + '\''
        + ",id = '"
        + id
        + '\''
        + ",email = '"
        + email
        + '\''
        + ",status = '"
        + status
        + '\''
        + "}";
  }
}
