package com.chabak.repositories;

import com.chabak.vo.Review;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository("reviewDao")
public class ReviewDao {
    @Autowired
    SqlSession sqlSession;

    /* reviewNo 값 가져오기 */
    public int selectReviewNo(){
        int reviewNo =sqlSession.selectOne("review.selectReviewNo");
        return reviewNo;
    }
    public int insertReview(Review review){
        int insertedCount = sqlSession.insert("review.insertReview",review);
        return insertedCount;
    }

    public int maxReviewCount(Map map){
        int maxCount = sqlSession.selectOne("review.maxReviewCount",map);
        return maxCount;
    }

    public List<Review> selectReviewTop5(String id){
        List<Review> reviewList = null;
        reviewList = sqlSession.selectList("review.selectReviewTop5",id);
        return reviewList;
    }
    public List<Review> selectReviewList(Map map){
        List<Review> reviewList = null;
        reviewList = sqlSession.selectList("review.selectReviewList",map);
        return reviewList;
    }

    public List<Review> selectReviewListMyPage(Map map){
        List<Review> reviewList = null;
        reviewList = sqlSession.selectList("review.selectReviewListMyPage",map);
        return reviewList;
    }

    public Review selectReviewDetail(int reviewNo){

        Review review = sqlSession.selectOne("review.selectReviewDetail",reviewNo);
        return review;
    }

    // similarUsersReview
    public List<Review> selectSimilarUsersReview(Map map) {
        List<Review> reviewList = sqlSession.selectList("review.selectSimilarUsersReview", map);
        return reviewList;
    }

    public int countRecommendReview(Map map) {
        int count = sqlSession.selectOne("review.countRecommendReview", map);
        return count;
    }
    // recommendReview
    public List<Review> selectRecommendReview(Map map) {
        List<Review> reviewList = sqlSession.selectList("review.selectRecommendReview", map);
        return reviewList;
    }

    public int updateReview(Review review) {
        int updateCount = sqlSession.update("review.updateReview",review);
        return updateCount;
    }

    public int updateReadCount(int reviewNo){
        int updateCount = sqlSession.update("review.updateReadCount",reviewNo);
        return updateCount;
    }

    public int increaseLikeCount(int reviewNo){
        int updateLikeCount = sqlSession.update("review.increaseLikeCount",reviewNo);
        return updateLikeCount;
    }

    public int decreaseLikeCount(int reviewNo){
        int updateLikeCount = sqlSession.update("review.decreaseLikeCount",reviewNo);
        return updateLikeCount;
    }

    public int deleteReview(int reviewNo){
        int deleteCount = sqlSession.delete("review.deleteReview",reviewNo);
        return deleteCount;
    }

    public int increaseReplyCount(int reviewNo){
        int updateLikeCount = sqlSession.update("review.increaseReplyCount",reviewNo);
        return updateLikeCount;
    }

    public int decreaseReplyCount(int reviewNo){
        int updateLikeCount = sqlSession.update("review.decreaseReplyCount",reviewNo);
        return updateLikeCount;
    }

}
