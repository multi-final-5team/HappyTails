package com.multi.happytails.shop.model.dao;

import com.multi.happytails.shop.model.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.model.dao
 * fileName       : ReviewDAO.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Mapper
public interface ReviewDAO {
    void insertReview(ReviewDTO reviewDTO);

    void updateReview(ReviewDTO reviewDTO);

    void deleteReview(ReviewDTO reviewDTO);

    List<ReviewDTO> reviewList(ReviewDTO reviewDTO);

    int getReviewNo();
}
