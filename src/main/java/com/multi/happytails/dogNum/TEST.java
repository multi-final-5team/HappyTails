package com.multi.happytails.dogNum;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class TEST{
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/animalInfoSrvc/animalInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=WO6eYkS2IohC2ttnnnk4oJVRUweEG%2F9Pvi9HokXptwYM1PokLcexnxHVoThQZk%2FAl2mAfd1WltIinmxbGpmodQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dog_reg_no","UTF-8") + "=" + URLEncoder.encode("410000001513331", "UTF-8")); /*동물등록번호 또는 RFID코드 필수*/
        //urlBuilder.append("&" + URLEncoder.encode("rfid_cd","UTF-8") + "=" + URLEncoder.encode("410000001513331", "UTF-8")); /*동물등록번호 또는 RFID코드 필수*/
        urlBuilder.append("&" + URLEncoder.encode("owner_nm","UTF-8") + "=" + URLEncoder.encode("홍길동", "UTF-8")); /*소유자 성명 또는 생년월일 필수*/
        //urlBuilder.append("&" + URLEncoder.encode("owner_birth","UTF-8") + "=" + URLEncoder.encode("001106", "UTF-8")); /*소유자 성명 또는 생년월일 필수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml(기본값) 또는 json*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
}
