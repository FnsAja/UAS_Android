package com.example.internship.config;

public interface Config {
    public static final String IP = "192.168.1.5";

    public static final String getDataAdm = "http://" + IP +"/intern/tampil_proj.php";
    public static final String getDataNonAdm = "http://" + IP +"/intern/tampil_projnonadm.php";
    public static final String getDataDetail = "http://" + IP +"/intern/detail_proj.php";
    public static final String getDataDetailNonAdm = "http://" + IP +"/intern/detail_projnonadm.php";
    public static final String loginPhp = "http://" + IP +"/intern/login.php";
    public static final String registerPhp = "http://" + IP +"/intern/register.php";
}
