package com.multi.happytails.main.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * packageName    : com.multi.happytails.main.service
 * fileName       : ClovaService
 * author         : ehdtka
 * date           : 2024-08-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-14        ehdtka       최초 생성
 */
@Service
public class ClovaService {
    @Value("${clova.api.secret-key}")
    private String secretKey;

    @Value("${clova.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ClovaService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public JsonNode processMessage(String message) throws Exception {
        String requestBody = getReqMessage(message);
        String signature = makeSignature(requestBody, secretKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-NCP-CHATBOT_SIGNATURE", signature);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject(apiUrl, entity, String.class);
        return parseResponse(response);
    }

    private JsonNode parseResponse(String jsonResponse) throws Exception {
        JsonNode root = objectMapper.readTree(jsonResponse);
        return root.path("bubbles").get(0).path("data");
    }

    private String makeSignature(String message, String secretKey) throws Exception {
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec signingKey = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(rawHmac);
    }

    private String getReqMessage(String voiceMessage) throws Exception {
        long timestamp = new Date().getTime();

        ObjectNode requestBody = objectMapper.createObjectNode()
                .put("version", "v2")
                .put("userId", "U47b00b58c90f8e47428af8b7bddc1231heo2")
                .put("timestamp", timestamp)
                .put("event", "send");

        ArrayNode bubblesArray = requestBody.putArray("bubbles");
        ObjectNode bubbleObject = bubblesArray.addObject();
        bubbleObject.put("type", "text");

        ObjectNode dataObject = bubbleObject.putObject("data");
        dataObject.put("description", voiceMessage);

        return objectMapper.writeValueAsString(requestBody);
    }
}
