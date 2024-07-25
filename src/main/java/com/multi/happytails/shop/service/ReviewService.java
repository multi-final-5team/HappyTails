package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dto.ReviewDTO;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.service
 * fileName       : ReviewService.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 리뷰 Service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
public interface ReviewService {
    /**
     * methodName : insertReview
     * author : Shin HyeonCheol
     * description : 리뷰 작성 Service
     *
     * @param reviewDTO the review dto
     */
    void insertReview(ReviewDTO reviewDTO);

    /**
     * methodName : updateReview
     * author : Shin HyeonCheol
     * description : 리뷰 수정 Service
     *
     * @param reviewDTO the review dto
     */
    void updateReview(ReviewDTO reviewDTO);

    /**
     * methodName : deleteReview
     * author : Shin HyeonCheol
     * description : 리뷰 삭제 Service
     *
     * @param reviewDTO the review dto
     */
    void deleteReview(ReviewDTO reviewDTO);

    /**
     * methodName : reviewList
     * author : Shin HyeonCheol
     * description : 리뷰 목록 조회 Service
     *
     * @param reviewDTO the review dto
     * @return the list
     */
    List<ReviewDTO> reviewList(ReviewDTO reviewDTO);

    /**
     * Gets review no.
     *
     * @return the review no
     */
    int getReviewNo();
}
