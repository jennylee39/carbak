<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/community_detail.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        $('document').ready(function () {

        });

        function like() {
            var img = document.getElementById("like-img");
            img.src = "/img/community/heart2.png"
        }

        function createReplyBox(replyNo) {
            var isVisible =  $("#re-reply-input" + replyNo).is(":visible");

            //상태:show/hide
            //모든 대댓글 폼 숨기기
            $(".re-reply-input").hide();  // 대댓글 폼 클래스 :숨김

            if (isVisible == false) { //숨김 상태이면
                $("#re-reply-input" + replyNo).show();
            }

        }
        function myFunction(flag,idValue) {
            if(flag=='review'){
                document.getElementById("myDropdown").classList.toggle("show");
            }
            else{
                document.getElementById("myDropdown"+idValue).classList.toggle("show");
            }
        }

        window.onclick = function (event) {
            if (!event.target.matches('.dropbtn')) {
                var dropdowns = document.getElementsByClassName("dropdown-content");
                ////////////
                //var dropdowns = document.getElementById()
                var i;
                for (i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (openDropdown.classList.contains('show')) {
                        openDropdown.classList.remove('show');
                    }
                }
            }
        }

        //리플 수정 클릭하면 입력폼으로 변환
        function changeReplyForm(replyNo){
            //댓글 class="reply-one" id="reply-one리플번호"
            //댓글 수정폼 class="reply-one-modify" id="reply-one-modify리플번호"
            //대댓글 class="reply-child" id="reply-one리플번호"
            //대댓글 수정폼 class="reply-one-modify" id="reply-one-modify리플번호"
     ;
            // var isVisible =  $("#reply-one-modify"+replyNo).is(":visible");
            //
            // //상태:show/hide
            //
            // $(".reply-one").show();
            // $(".reply-child").show();
            //
            // $(".reply-one-modify").hide();
            //
            //
            // if (isVisible == false) { //숨김 상태이면
            //     $("#reply-one-modify" + replyNo).show();
            //     $("#reply-one"+replyNo).hide();
            // }
            var cnt=1;
            var defaultReply = document.getElementById("reply-one${reply.replyNo}");
            var modifyReply = document.getElementById("reply-one-modify${reply.replyNo}");

            if(cnt%2==1){

            }
        }

    </script>

<body>
<!-- header -->
<hr>
<br>
<div class="container">
    <div class="top">
        <h1>커뮤니티 리뷰 상세보기</h1>
    </div>
    <!-- 상세 내용 -->
    <div class="second">
        <div class="profile">
            <div class="thumbnail-wrapper">
                <div class="thumbnail">
                    <div class="centered">
                    </div>
                </div>
            </div>
            <div class="writer">
                    <span class="writer-id">
                        ${review.id}
                    </span>
            </div>
            <div class="title">
                [${review.sido}][${review.gugun}] ${review.title}
            </div>

            <div class="content-icon">
                <span>${review.likeCount}+</span>
                <button class="like-img"><img id="like-img" src="/img/community/heart.png"
                                              onclick="like()"></button>
            </div>
            <div class="dropdown">
                <button class="dropbtn"><img class="dropbtn" src="/img/community/menu.png"
                                             onclick="myFunction('review',null)"></button>
                <div class="dropdown-content" id="myDropdown">
                    <a href="/review/modify?reviewNo=${review.reviewNo}">수정하기</a>
                    <a href="/review/delete?reviewNo=${review.reviewNo}">삭제하기</a>
                </div>
            </div>
        </div>
    </div>
    <!-- 게시글 내용 -->
    <div class="main">
        <div class="content">
            ${review.content}
        </div>
    </div>
    <!-- 댓글 -->
    <div class="reply">
        <div class="reply-input">
            <form method="POST" action="/reply/writeReply">
                <input type="hidden" name="reviewNo" value="${review.reviewNo}">
                <input type="hidden" name="id">
                <input type="text" placeholder="댓글 입력" name="content">
                <button type="submit">등록</button>
            </form>
        </div>
        <div class="reply-list">
            <c:forEach items="${replyList}" var="reply">
                <c:choose>
                    <c:when test="${reply.lv eq 0}">

<%--                 댓글 시작       --%>

                        <div class="reply-one" id="reply-one${reply.replyNo}">
                            <div class="thumbnail-wrapper">
                                <div class="reply-thumbnail">
                                    <div class="centered">
                                    </div>
                                </div>
                            </div>
                            <div class="writer">
                            <span class="writer-id">
                                 id:${reply.id}
                            </span>
                            </div>
                            <div class="reply-date">
                                    ${reply.regDate}
                            </div>
 <%--          댓글 드롭다운 시작--%>

                            <div class="dropdown">
                                <button class="dropbtn"><img class="dropbtn" src="/img/community/menu.png"
                                                             onclick="myFunction('reply',${reply.replyNo})"></button>
                                <div class="dropdown-content" id="myDropdown${reply.replyNo}">
                                    <a onclick="changeReplyForm(${reply.replyNo})">수정하기</a>
                                    <a href="/reply/delete?replyNo=${reply.replyNo}">삭제하기</a>
                                    <a onclick="createReplyBox(${reply.replyNo})">댓글달기</a>
                                </div>
                            </div>
<%--                          댓글 드롭다운 끝  --%>

<%--                            <div class="button">--%>
<%--                                <button class="update" >수정</button>--%>
<%--                                <button class="delete" onclick="">삭제</button>--%>
<%--                                <button class="replyButton" onclick="createReplyBox(${reply.replyNo})">댓글달기</button>--%>
<%--                            </div>--%>

                            <div class="button" style="display: none">
                                <button class="cancel">취소</button>
                                <button class="save" onclick="">저장</button>

                            </div>

                            <div class="reply-content">
                                    ${reply.content}
                            </div>


  <%--          수정 버튼에 따라 변경됨 끝--%>

                        </div>
<%--          댓글 끝--%>

<%--       대댓글 폼 시작--%>
                        <div class="re-reply-input" id="re-reply-input${reply.replyNo}" style="display:none">
                            <form method="POST" action="/reply/writeReReply">
                                <input type="hidden" name="reviewNo" value="${reply.reviewNo}">
                                <input type="hidden" name="replyNo" value="${reply.replyNo}">
                                <input type="hidden" name="groupNo" value="${reply.groupNo}">
                                <input type="hidden" name="groupOrder" value="${reply.groupOrder}">
                                <input type="hidden" name="lv" value="${reply.lv}">
                                <input type="hidden" name="id">
                                <input type="text" placeholder="댓글 입력" name="content">
                                <button type="submit">등록</button>
                            </form>
                        </div>

                    </c:when>
                    <c:otherwise>

<%--       대댓글 폼 끝--%>
 <!--대댓글 시작-->
                        <div class="reply-child" id="reply-one${reply.replyNo}">
                            <div class="re-reply">
                                <img src="/img/community/re_reply3.png">
                                <div class="thumbnail-wrapper">
                                    <div class="reply-thumbnail">
                                        <div class="centered">
                                        </div>
                                    </div>
                                </div>
                                <div class="writer">
                    <span class="writer-id">
                            ${reply.id}
                    </span>
                                </div>
                                <div class="reply-date">
                                        ${reply.regDate}
                                </div>

<%--                                대댓글 드롭다운 시작--%>
                                <div class="dropdown">
                                    <button class="dropbtn"><img class="dropbtn" src="/img/community/menu.png"
                                                                 onclick="myFunction('reply',${reply.replyNo})"></button>
                                    <div class="dropdown-content" id="myDropdown${reply.replyNo}">
                                        <a onclick="changeReplyForm(${reply.replyNo})">수정하기</a>
                                        <a href="/reply/delete?replyNo=${reply.replyNo}">삭제하기</a>
                                        <a onclick="createReplyBox(${reply.replyNo})">댓글달기</a>
                                    </div>
                                </div>
<%--             대댓글 드롭다운 끝          --%>


                                <div class="re-reply-content">
                                        ${reply.content}
                                </div>


                            </div>
                        </div>



                        <%--                        대댓글 폼--%>
                        <div class=re-reply-input" id="re-reply-input${reply.replyNo}" style="display:none">

                            <form method="POST" action="/reply/writeReReply">
                                <input type="hidden" name="reviewNo" value="${reply.reviewNo}">
                                <input type="hidden" name="replyNo" value="${reply.replyNo}">
                                <input type="hidden" name="groupNo" value="${reply.groupNo}">
                                <input type="hidden" name="groupOrder" value="${reply.groupOrder}">
                                <input type="hidden" name="lv" value="${reply.lv}">
                                <input type="hidden" name="id">
                                <input type="text" placeholder="댓글 입력" name="content">
                                <button type="submit">등록</button>
                            </form>
                        </div>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
        </div>


    </div>
</div>
</div>

</div>
</div>
</body>

</html>