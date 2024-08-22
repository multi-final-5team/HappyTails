package com.multi.happytails.shop.model.dao;

import com.multi.happytails.shop.model.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.model.dao
 * fileName       : ReviewDAO.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 리뷰 DAO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Mapper
public interface ReviewDAO {
    /**
     * methodName : insertReview
     * author : Shin HyeonCheol
     * description : 리뷰 작성 DAO
     *
     * @param reviewDTO the review dto
     */
    void insertReview(ReviewDTO reviewDTO);

    /**
     * methodName : updateReview
     * author : Shin HyeonCheol
     * description : 리뷰 수정 DAO
     *
     * @param reviewDTO the review dto
     */
    int updateReview(ReviewDTO reviewDTO);

    /**
     * methodName : deleteReview
     * author : Shin HyeonCheol
     * description : 리뷰 삭제 DAO
     *
     * @param reviewDTO the review dto
     */
    void deleteReview(ReviewDTO reviewDTO);

    /**
     * methodName : reviewList
     * author : Shin HyeonCheol
     * description : 리뷰 목록 호출 DAO
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

    Boolean hasUserReviewed(@Param("id") String id, @Param("goodsNo") int goodsNo);

    ReviewDTO selectReview(@Param("id") String id, @Param("goodsNo") int goodsNo);
    ReviewDTO selectReview2(@Param("id") String id, @Param("goodsNo") int goodsNo);
}
