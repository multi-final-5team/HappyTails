package com.multi.happytails.shop.service;

import com.multi.happytails.shop.model.dao.ReviewDAO;
import com.multi.happytails.shop.model.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.multi.happytails.shop.service
 * fileName       : ReviewServiceImpl.java
 * author         : ShinHyeoncheol
 * date           : 2024-07-24
 * description    : 리뷰 ServiceImpl
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        ShinHyeoncheol       최초 생성
 */
@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewDAO reviewDAO;

    @Override
    public void insertReview(ReviewDTO reviewDTO) {
        reviewDAO.insertReview(reviewDTO);
    }

    @Override
    public int updateReview(ReviewDTO reviewDTO) {
        return reviewDAO.updateReview(reviewDTO);
    }

    @Override
    public void deleteReview(ReviewDTO reviewDTO) {
        reviewDAO.deleteReview(reviewDTO);
    }

    @Override
    public List<ReviewDTO> reviewList(ReviewDTO reviewDTO) {
        return reviewDAO.reviewList(reviewDTO);
    }

    @Override
    public int getReviewNo() {
        return reviewDAO.getReviewNo();
    }

    @Override
    public Boolean hasUserReviewed(String id, int goodsNo) {
        return reviewDAO.hasUserReviewed(id, goodsNo);
    }

    @Override
    public ReviewDTO selectReview(String id, int goodsNo) {
        return reviewDAO.selectReview(id, goodsNo);
    }

    @Override
    public ReviewDTO selectReview2(String id, int goodsNo) {
        return reviewDAO.selectReview2(id, goodsNo);
    }


}
