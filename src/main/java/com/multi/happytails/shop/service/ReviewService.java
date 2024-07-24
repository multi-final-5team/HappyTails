package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dto.ReviewDTO;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.service
 * fileName       : ReviewService.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
public interface ReviewService {
    void insertReview(ReviewDTO reviewDTO);

    void updateReview(ReviewDTO reviewDTO);

    void deleteReview(ReviewDTO reviewDTO);

    List<ReviewDTO> reviewList(ReviewDTO reviewDTO);

    int getReviewNo();
}
