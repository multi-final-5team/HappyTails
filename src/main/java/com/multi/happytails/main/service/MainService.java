package com.multi.happytails.main.service;

import com.multi.happytails.main.dto.RssNewsDTO;

import java.util.List;

/**
 * packageName    : com.multi.happytails.main.service
 * fileName       : MainService
 * author         : ShinHyeoncheol
 * date           : 2024-08-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-09        ShinHyeoncheol       최초 생성
 */
public interface MainService {

    List<RssNewsDTO> GetRssNewsData(String rssUrl);
}
