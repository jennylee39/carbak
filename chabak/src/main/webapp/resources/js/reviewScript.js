function checkReviewValidate(){

    oEditors.getById["smartEditor"].exec("UPDATE_CONTENTS_FIELD", []);

    //this.contents = $('#smartEditor').val();

    if($("#title").val()==''){
        alert('제목을 입력하세요.');
        return false;
    }

    if($("#sido option:selected").val()=="시/도 선택" || $("#sido option:selected").val()==null){
        alert('시,도를 입력하세요.');
        return false;
    }
    if($("#gugun option:selected").val()=="구/군 선택" || $("#gugun option:selected").val()==null){
        alert('구,군을 입력하세요.');
        return false;
    }

    if($("#smartEditor").length==0)
        return false;

    return true;



}

//필드 글자 및 바이트제한
function checkLengthValidate(obj, maxByte) {

    var strValue = obj.value;
    var strLen = strValue.length;
    var totalByte = 0;
    var len = 0;
    var oneChar = "";
    var str2 = "";

    for (var i = 0; i < strLen; i++) {
        oneChar = strValue.charAt(i);
        if (escape(oneChar).length > 4) {
            totalByte += 2;
        } else {
            totalByte++;
        }

        // 입력한 문자 길이보다 넘치면 잘라내기 위해 저장
        if (totalByte <= maxByte) {
            len = i + 1;
        }
    }

    // 넘어가는 글자는 자른다.
    if (totalByte > maxByte) {
        alert(maxByte + "Byte를 초과 입력 할 수 없습니다.");
        str2 = strValue.substr(0, len);
        obj.value = str2;
        checkLengthValidate(obj, 20);
    }

}

//페이징 시 url String 설정(예: "/review"+getUrlString(curPage)    )
function getUrlString(searchText,sortType){

        var urlString="?";

        urlString += "sortType="+sortType;
        if(searchText!='' || searchText!=null || searchText!=""){

            urlString += "&searchText="+searchText;
        }

        console.log("getUrlString():"+urlString);
        return urlString;


}

// 리뷰 리스트를 ajax로 출력
function ajaxReviewList(sessionId,isSearchButton,curPage) {

    var searchText = $("#search_text").val();
    //검색 버튼 누른 경우
    if(isSearchButton==true){
        $("#search_text_saved").val(searchText);
    }
    else{
        //검색 버튼 안 누른 경우(검색 초기화상태에서 select onchange)
        searchText = $("#search_text_saved").val();
        $("#search_text").val(searchText);
    }
    console.log("ajaxReviewList curPage:"+curPage);
    $.ajax({
        url : "/review/listAjax",
        type : "post",
        dataType:'json',
        data :{"sortType": $("#sortType option:selected").val(),//서버로 전송하는 데이터(정렬방식)
               "searchText": searchText,
               "curPage": curPage       }, //검색창의 텍스트값
        success : function(data) {
            
            //받은 sessionId 값이 문자열이 아니므로(따옴표로 감싸이지 않았음) 따옴표 추가
            sessionId = "'"+sessionId+"'";
            
            var reviewListDiv = $("#reviewListDiv"); //리뷰가 추가되는 영역
            reviewListDiv.empty();  //리뷰 추가 영역 초기화

            var list = data["reviewList"];
            $.each(list, function() {
                var newReview = $("#dummy-review").clone(true);

                //            원형 복사시 수정할 부분: #dummy-review(id),  .writer-id(value),    .review-img img(src,onclick) .content-title(value)

                var reviewNo = this["reviewNo"];
                var reviewId = "review"+reviewNo;

                newReview.attr("id",reviewId);


                var writer = newReview.find(".writer-id");  //작성자
                writer.html(this["id"]);  //작성자 설정

                var reviewImg =  newReview.find(".review-img img")  //리뷰 이미지
                reviewImg.attr("src",this["titleImageSrc"]);  //리뷰 타이틀이미지 src

                var onclickLink =  "location.href='/review/detail?reviewNo="+ reviewNo+"'"; //리뷰 타이틀 이미지 링크
                reviewImg.attr("onclick",onclickLink);

                var title = newReview.find(".content-title");   //리뷰 타이틀
                title.text('['+this["sido"]+']'+'['+this["gugun"]+']'+this["title"]);                      //리뷰 타이틀 설정 [sido][gugun][title]

                //좋아요 토글될 이미지 선택
                var toggleImage = newReview.find(".toggle-like-img");
                //좋아요 토글될 이미지 id 설정
                toggleImage.attr("id","like-img"+reviewNo);

                //onclick 속성 추가(함수 실행)
                toggleImage.attr("onclick","ajaxReviewLikeToggle('"+reviewNo+"',this,"+sessionId+")");

                console.log(sessionId);

                if(sessionId=="" || sessionId==null || this["likeYn"]==0){

                    toggleImage.attr("src","/img/community/heart.png");
               }
                else{
                    toggleImage.attr("src","/img/community/heart2.png");
                }

                newReview.show();
                reviewListDiv.append(newReview);


            });
            var pagenation = data["pagination"];

            var curPageVar = pagenation["curPage"];
            var startPageVar = pagenation["startPage"];
            var endPageVar = pagenation["endPage"];
            var pageCntVar = pagenation["pageCnt"];
            var curRangeVar = pagenation["curRange"];
            var rangeCntVar = pagenation["rangeCnt"];
            var nextPageVar = pagenation["nextPage"];

            console.log("총 페이지 수 : "+pageCntVar+ "/ 현재 페이지 : "+ curPageVar + "/ 현재 블럭 : "+ curRangeVar + "/ 총 블럭 수 : "+ rangeCntVar);

            var pagingDiv = $("#pagingDiv");
            pagingDiv.empty();
            for(var i=startPageVar;i<= endPageVar;i++){
                if(i==curPageVar){
                    var span1 = $("#i_eq_curPage").clone(true);
                    var innerATag = span1.find("a");
                    innerATag.attr("onclick","fn_paging('"+ i +"')");
                    innerATag.text(i);

                    span1.attr("style","display:inline-block");
                    pagingDiv.append(span1);
                }
                else{
                    var aTag1 = $("#i_ne_curPage").clone(true);
                    aTag1.attr("onclick","fn_paging('"+ i +"')");
                    aTag1.text(i);

                    aTag1.attr("style","display:inline-block");
                    pagingDiv.append(aTag1);
                }
            }
            if(curPageVar!=pageCntVar && pageCntVar > 0){
                var aTag2 = $("#curPage_ne_pageCnt").clone(true);
                aTag2.attr("onclick","fn_paging('"+ nextPageVar +"')");

                aTag2.attr("style","display:inline-block");
                pagingDiv.append(aTag2);
            }
            if(curRangeVar != rangeCntVar && rangeCntVar > 0){
                var aTag3 = $("#curRange_ne_rangeCnt").clone(true);
                aTag3.attr("onclick","fn_paging('"+ pageCntVar +"')");

                aTag3.attr("style","display:inline-block");
                pagingDiv.append(aTag3);
            }


        },
        error:function(error){
            alert('error');
        }
    });  // ajax 끝

}

function cancelFunction(link) {
    var option = confirm("작성을 종료하고 나가시겠습니까?");
    if(option==true){
        location.href=link;

    }
}

