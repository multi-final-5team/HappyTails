package com.multi.happytails.main.service;

import org.springframework.stereotype.Service;

/**
 * packageName    : com.multi.happytails.main.service
 * fileName       : MainServiceImpl
 * author         : ShinHyeoncheol
 * date           : 2024-08-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-09        ShinHyeoncheol       최초 생성
 */
@Service
public class MainServiceImpl implements MainService{

//    @Override
//    public List<RssNewsDTO> GetRssNewsData(String rssUrl) {
//
//        RestTemplate restTemplate = new RestTemplate();
//        String jsonResponse;
//        try {
//            URI uri = new URI(rssUrl);
//            jsonResponse = restTemplate.getForObject(uri, String.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>(); // 에러 발생 시 빈 리스트 반환
//        }
//
//        List<RssNewsDTO> rssItems = new ArrayList<>();
//        try {
//            JSONObject jsonObject = new JSONObject(jsonResponse);
//            if (!"ok".equals(jsonObject.optString("status"))) {
//                System.err.println("RSS API 응답 오류: " + jsonObject.optString("message"));
//                return rssItems;
//            }
//            JSONArray items = jsonObject.getJSONArray("items");
//
//            for (int i = 0; i < items.length(); i++) {
//                JSONObject item = items.getJSONObject(i);
//                RssNewsDTO rssNewsDTO = new RssNewsDTO();
//                rssNewsDTO.setTitle(item.optString("title"));
//                rssNewsDTO.setLink(item.optString("link"));
//                rssNewsDTO.setPubDate(item.optString("pubDate"));
//                rssNewsDTO.setDescription(item.optString("description"));
//                rssNewsDTO.setThumbnail(item.optString("thumbnail", ""));
//                rssItems.add(rssNewsDTO);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(rssItems);
//        return rssItems;
//    }
}
