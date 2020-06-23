package com.chabak.controllers;

import com.chabak.repositories.ReplyDao;
import com.chabak.repositories.ReviewDao;
import com.chabak.vo.Reply;
import com.chabak.vo.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    ReplyDao replyDao;

    @RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.GET)
    public ModelAndView reviewList(){

        //파라미터를 저장할 맵 생성+값(정렬타입)
        Map map = new HashMap<String,String>();
        map.put("sortType","regDate");
        //리뷰 리스트 select
        List<Review> reviewList = reviewDao.selectReviewList(map);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("community/community");
        mv.addObject("reviewList",reviewList);

        System.out.println("(/review/list)reviewList:"+reviewList);
        return mv;
    } //리뷰 리스트 출력

    //검색 버튼 클릭시
@RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.POST)
public ModelAndView searchReviewList(@RequestParam String search_text){

    //파라미터를 저장할 맵 생성+값(정렬타입,검색텍스트)
    Map map = new HashMap<String,String>();
    map.put("sortType","regDate");
    map.put("search_text",search_text);

    //리뷰 리스트 select
    List<Review> reviewList = reviewDao.selectReviewList(map);

    ModelAndView mv = new ModelAndView();
    mv.setViewName("community/community");
    mv.addObject("reviewList",reviewList);

    System.out.println("(/review/list)reviewList:"+reviewList);
    return mv;
} //리뷰 리스트 출력

    @SneakyThrows
    @ResponseBody
    @RequestMapping("/listAjax")
    public String listAjax(HttpServletRequest request,ModelAndView mv){
        //TODO:아직 작성중
        String sortType = request.getParameter("sortType");
        String search_text = request.getParameter("search_text");

        //파라미터를 저장할 맵 생성+값(정렬타입,검색텍스트)
        Map map = new HashMap<String,String>();
        map.put("sortType",sortType);
        map.put("search_text",search_text);

        List<Review> reviewList = reviewDao.selectReviewList(map);

        //Jacson 라이브러리로 자바->json 변환
       ObjectMapper mapper = new ObjectMapper();
       String jsonString = mapper.writeValueAsString(reviewList);

        System.out.println("in listAjax(reviewList):"+reviewList);
        System.out.println("jsonString:"+jsonString);

        return jsonString;
    }

    @RequestMapping(value ="/writeForm", method=RequestMethod.GET)
    public String writeReviewForm(){
        return "community/community_write";
    } //리뷰 작성폼 이동

    @RequestMapping(value ="/write", method=RequestMethod.POST)
    public ModelAndView writeReview(@ModelAttribute Review review){


        //TODO:세션에서 id 가져오기
        //임시 코드(나중에 수정)
        review.setId("id1");

        System.out.println("review:"+review);

        //대표 이미지 저장
        Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //img 태그 src 추출 정규표현식
        Matcher matcher = pattern.matcher(review.getContent());

        //리뷰 대표이미지 디폴트 이미지(이미지 등록 안 했을때 보여줄 이미지)
        String titleImage = "/reviewImages/reviewTitleDefault.png";

        while(matcher.find()){
            titleImage = matcher.group(1);
        }

        if(titleImage!=null){
            review.setTitleImageSrc(titleImage);
        }

        //리뷰 저장
        reviewDao.insertReview(review);


        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/review");

        return mv;
    } //리뷰 저장

    //리뷰 수정 페이지로 이동
    @RequestMapping(value ="/modify", method=RequestMethod.GET)
    public ModelAndView modifyForm(@RequestParam int reviewNo){

        //TODO:세션에서 id 가져오기
        //임시 코드(나중에 수정)


        //TODO:로직 확인
        System.out.println("/modify(GET) reviewNo:"+reviewNo);
        Review review = reviewDao.selectReviewDetail(reviewNo);


        ModelAndView mv = new ModelAndView();
        mv.addObject("review",review);
        mv.setViewName("community/community_update");

        return mv;
    }

    @RequestMapping(value ="/modify", method=RequestMethod.POST)
    public ModelAndView modifyReview(@ModelAttribute Review review){
        System.out.println("review/modify(POST)");
        //TODO:세션에서 id 가져오기
        //임시 코드(나중에 수정)
        review.setId("id1");

        //TODO:로직 확인

        System.out.println("review:"+review);

        reviewDao.updateReview(review);


        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/review");

        return mv;
    }

    @RequestMapping(value ="/delete", method=RequestMethod.POST)
    public ModelAndView deleteReview(@RequestParam int reviewNo){

        //TODO:로직 확인
        reviewDao.deleteReview(reviewNo);
        replyDao.deleteReply(reviewNo);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/review");

        return mv;
    }

    @RequestMapping(value ="/detail", method=RequestMethod.GET)
    public ModelAndView detailReview(@RequestParam int reviewNo){
        System.out.println("reviewNo:"+reviewNo);

        //조회수 1 증가
        reviewDao.updateReadCount(reviewNo);
        //리뷰 선택
        Review review = reviewDao.selectReviewDetail(reviewNo);
        System.out.println("review:"+review);

        //해당 리뷰에 달린 댓글들

        List<Reply> replyList = replyDao.selectReplyList(reviewNo);

        System.out.println("replyList:"+replyList);
        ModelAndView mv = new ModelAndView();
        mv.addObject("review",review);
        mv.addObject("replyList",replyList);

        mv.setViewName("community/community_detail");
        return mv;
    }




}
