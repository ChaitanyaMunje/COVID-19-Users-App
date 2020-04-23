package com.gtappdevelopers.transport_tracker_driver;

public class User {

    public String name;
    public double originallongitude;
    public double originallatitude;
    public double changelat;
    public double changelon;

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    public double status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOriginallongitude() {
        return originallongitude;
    }

    public void setOriginallongitude(double originallongitude) {
        this.originallongitude = originallongitude;
    }

    public double getOriginallatitude() {
        return originallatitude;
    }

    public void setOriginallatitude(double originallatitude) {
        this.originallatitude = originallatitude;
    }

    public double getChangelat() {
        return changelat;
    }

    public void setChangelat(double changelat) {
        this.changelat = changelat;
    }

    public double getChangelon() {
        return changelon;
    }

    public void setChangelon(double changelon) {
        this.changelon = changelon;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAltphone() {
        return altphone;
    }

    public void setAltphone(String altphone) {
        this.altphone = altphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getQuartdate() {
        return quartdate;
    }

    public void setQuartdate(String quartdate) {
        this.quartdate = quartdate;
    }

    public float speed;



    public String email;
    public String phone;

    public User(String name, double originallongitude, double originallatitude, double changelat, double changelon, float speed, String email, String phone, String altphone, String address, String country, String quartdate,double status) {
        this.name = name;
        this.originallongitude = originallongitude;
        this.originallatitude = originallatitude;
        this.changelat = changelat;
        this.changelon = changelon;
        this.speed = speed;
        this.email = email;
        this.phone = phone;
        this.altphone = altphone;
        this.address = address;
        this.country = country;
        this.quartdate = quartdate;
        this.status=status;
    }

    public String altphone;
    public String address;
    public String country;
    public String quartdate;


    public User(){

    }



}
