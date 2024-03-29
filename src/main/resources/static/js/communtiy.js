
/*
*提交回复
 */

function post() {
    var questionId=$("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}

function comment2target(targetId,type,content) {

    if(!content){
        alert("不能回复空内容~~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data:JSON.stringify( {
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            if(response.code==200){
                window.location.reload();
            } else{
                if(response.code==2003){
                    var isAccepted=confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=893bc32ca7064735351a&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true);

                    }
                }else{
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}


function comment(e) {
    var commentId=e.getAttribute("data-id");
    var content=$("#input-"+commentId).val();
    comment2target(commentId,2,content);

}

/*
* 展开二级评论
* */
function collapseComments(e) {
    var id=e.getAttribute("data-id");
    var comments= $("#comment-"+id);
    //获取一下二级评论的展开状态
   var collapse=e.getAttribute("data-collapse");
    if(collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else{
        var subCommentContainer=$("#comment-"+id);
        if (subCommentContainer.children().length !=1){
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else{
            $.getJSON( "/comment/" + id, function( data ) {
                $.each( data.data.reverse(), function(index,comment) {
                    var mediaLeftElement=$("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));
                    var mediaBodyElement=$("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                            "html": comment.content
                    })).append($("<div/>", {
                            "class":"menu",
                    }).append($("<span/>", {
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));



                    var mediaElement=$("<div/>",{
                      "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);


                    var commentElement= $("<div/>",{
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {

    var flag=true;
    //从后台得到标签值
    var value=e.getAttribute("data-tag");

    //得到输入框中标签值
    var previous=$("#tag").val();

    //将输入框中的字符按,分割得到标签数组
    var psplits=previous.split(",");
    //循环数组与后台得到的值进行比较

    //判断是否添加重复标签
    for(var i=0;i<psplits.length;i++){
        if(psplits[i]==value){
            flag=false;
        }
    }
    //如果没有重复元素的话,在添加
    if(flag){
        //如果前面有值的话,加上从台得到的值
        if(previous){
            $("#tag").val(previous+','+value);
        }else{
            //前面无值,不用加
            $("#tag").val(value);
        }
    }
}