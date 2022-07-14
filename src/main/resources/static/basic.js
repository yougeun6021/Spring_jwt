

$(document).ready(function () {
    let temp_html = ``;
    if ($.cookie('token')) {
        console.log($.cookie('token'))
        $.ajaxSetup({
            headers: {
                'Authorization': $.cookie('token')
            }
        })
        temp_html =`<form id="my_form" method="post" action="/user/logout">
        <p><a id="logout-text" href="/" onclick="document.getElementById('my_form').submit(); document.cookie = 'token' + '=; expires=Thu, 01 Jan 1999 00:00:10 GMT;'; ">로그아웃</a></p>
    </form>`
    }else{
        temp_html = '<p><a href="/user/loginView">로그인하러가기</a></p>'
    }
    $('#login_logout').append(temp_html);
    // HTML 문서를 로드할 때마다 실행합니다.
    getPosts();
})

function getComments(postid){
    $.ajax({
        type: 'GET',
        url: `/api/comments/${postid}`,
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let comment = response[i];
                let id = comment["id"];
                let content = comment["content"];
                let modifiedAt = comment["modifiedAt"];
                addCommentsHTML(postid,id,content,modifiedAt);

            }
        }
    })

}

function deleteComment(id){
    $.ajax({
        type: "DELETE",
        url: `/api/comments/${id}`,
        success: function (response) {
            alert("댓글이 삭제되었습니다")
            window.location.reload()

        }
    })
}

function addCommentsHTML(postid,id,content,modifiedAt){
    console.log(postid)
    let tempHtml = `<article class="message">
  <div class="message-header">
    <p>작성날짜: ${modifiedAt}</p>
  </div>
  <div class="message-body" id="${id}-comments-content">
  <div id="${id}-comments-body">
  ${content}
</div> 
   <div id="${id}-comment-editarea" class="edit">
           <textarea id="${id}-comments-textarea" class="te-edit" name="" id="" cols="30" rows="5"></textarea>
  </div>
    <img id="${id}-comments-edit" class="icon-start-edit" src="/images/edit.png" alt="" onclick="editComment('${id}','${content}')">
    <img id="${id}-comments-delete" class="icon-delete" src="/images/delete.png" alt="" onclick="deleteComment('${id}')">
    <img id="${id}-comments-submit" class="icon-end-edit" src="/images/done.png" alt="" onclick="submitComment('${id}')">
   
  </div>
  <div class="comment-img">
</div>
</article>`

    $(`#${postid}-card`).append(tempHtml);

}

function submitComment(id){
    let content = $(`#${id}-comments-textarea`).val().trim();

    // 2. 작성한 메모가 올바른지 isValidcontent 함수를 통해 확인합니다.
    // 3. 전달할 data JSON으로 만듭니다.
    let data = {'content': content};

    // 4. PUT /api/memos/{id} 에 data를 전달합니다.
    $.ajax({
        type: "PUT",
        url: `/api/comments/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
           alert("댓글이 수정되었습니다.")
            window.location.reload()
        }
    });
}

function editComment(id,content){
    $(`#${id}-comments-textarea`).val(content);
    showCommentEdits(id)
}
function showCommentEdits(id){
    $(`#${id}-comments-textarea`).show();
    $(`#${id}-comments-submit`).show();
    $(`#${id}-comments-delete`).show();

    $(`#${id}-comments-body`).hide();
    $(`#${id}-comments-edit`).hide();
}

function writeComment(postid){
    let content = $(`#${postid}-comment`).val();


    // 2. 작성한 메모가 올바른지 isValidcontent 함수를 통해 확인합니다.

    // 4. 전달할 data JSON으로 만듭니다.
    let data = {'content': content};

    // 5. POST /api/memos 에 data를 전달합니다.
    $.ajax({
        type: "POST",
        url: `/api/comments/${postid}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('댓글이 성공적으로 작성되었습니다.');
            window.location.reload();
        }
    });
}


function getPosts() {
    $('#cards-box').empty();
    $.ajax({
        type: 'GET',
        url: '/api/posts',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let post = response[i];
                let id = post["id"];
                let writer = post['writer'];
                let title = post['title'];
                let modifiedAt = post['modifiedAt'];
                addPostsHTML(id, writer, title, modifiedAt);
                getComments(id)
            }
        }
    })
}

// 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
// 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
function editPost(id) {
    $.ajax({
        type: 'GET',
        url: `/api/posts/${id}`,
        success: function (response) {
            let writer = response["writer"];
            let title = response["title"]
            let content = response["content"];
            let modifiedAt = response["modifiedAt"];
            showEdits(id);
            $(`#${id}-title`).val(title);
            $(`#${id}-writer`).val(writer);
            $(`#${id}-date`).val(modifiedAt);
            $(`#${id}-textarea`).val(content);
        }
    })

}

function showEdits(id) {
    $(`#${id}-editarea`).show();
    $(`#${id}-submit`).show();
    $(`#${id}-delete`).show();

    $(`#${id}-content`).hide();
    $(`#${id}-edit`).hide();
}


function hideEdits(id) {
    $(`#${id}-editarea`).hide();
    $(`#${id}-submit`).hide();
    $(`#${id}-delete`).hide();

    $(`#${id}-content`).show();
    $(`#${id}-edit`).show();
}


function addPostsHTML(id, writer, title, modifiedAt) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml = `<div class="post">
            <div class="card" id="${id}-card">
        <!-- date/username 영역 -->
        <div class="metadata">
            <div class="date" id="${id}-date">
                작성날짜: ${modifiedAt}
            </div>
            <div id="${id}-writer" class="writer">
                작성자: ${writer}
            </div>
        </div>
        <!-- content 조회/수정 영역-->
        <div class="content">
        <p id ="${id}-title">제목: ${title}</p>
            <div id="${id}-content" class="text">
            </div>
            <div id="${id}-editarea" class="edit">
                <textarea id="${id}-textarea" class="te-edit" name="" id="" cols="30" rows="5"></textarea>
            </div>
            <input id="${id}-password" placeholder="비밀번호를 입력해주세요">
        </div>
        <!-- 버튼 영역-->
        <div class="footer">
            <img id="${id}-edit" class="icon-start-edit" src="/images/edit.png" alt="" onclick="editPost('${id}')">
            <img id="${id}-delete" class="icon-delete" src="/images/delete.png" alt="" onclick="deleteOne('${id}')">
            <img id="${id}-submit" class="icon-end-edit" src="/images/done.png" alt="" onclick="submitEdit('${id}')">
        </div>

        <div class="comment">
        <input class="input" type="text" placeholder="댓글을 입력해주세요" style="width: 538px" id="${id}-comment">
        <img src="images/send.png" class="icon-comment-send" id="${id}-send-comment" alt="" onclick="writeComment(${id})" >
        </div>
    </div>
    </div>`;

    // 2. #cards-box 에 HTML을 붙인다.
    $('#cards-box').append(tempHtml);
}

function isValidcontent(content) {
    if (content == '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (content.trim().length > 140) {
        alert('공백 포함 140자 이하로 입력해주세요');
        return false;
    }
    return true;
}


// 메모를 생성합니다.
function writePost() {
    // 1. 작성한 메모를 불러옵니다.
    let content = $('#content').val();

    let title = $('#title').val();

    let password = $('#password').val();

    // 2. 작성한 메모가 올바른지 isValidcontent 함수를 통해 확인합니다.
    if (isValidcontent(content) == false) {
        return;
    }

    // 4. 전달할 data JSON으로 만듭니다.
    let data = {'title': title, 'password': password, 'content': content};

    // 5. POST /api/memos 에 data를 전달합니다.
    $.ajax({
        type: "POST",
        url: "/api/posts",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('글이 성공적으로 작성되었습니다.');
            window.location.reload();
        }
    });
}

// 메모를 수정합니다.
// 메모를 수정합니다.
function submitEdit(id) {
    let content = $(`#${id}-textarea`).val().trim();
    let title = $(`#${id}-title`).val();
    let password = $(`#${id}-password`).val();


    // 2. 작성한 메모가 올바른지 isValidcontent 함수를 통해 확인합니다.
    if (isValidcontent(content) == false) {
        return;
    }

    // 3. 전달할 data JSON으로 만듭니다.
    let data = {'title': title, 'content': content, 'password': password};

    // 4. PUT /api/memos/{id} 에 data를 전달합니다.
    $.ajax({
        type: "PUT",
        url: `/api/posts/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            if (response == -1) {
                alert("비밀번호가 맞지 않아 게시글이 수정되지 않았습니다")
            } else {
                alert("게시글이 수정 되었습니다")
            }
            window.location.reload()
        }
    });
}

// 메모를 삭제합니다.
function deleteOne(id) {
    let password = $(`#${id}-password`).val();
    console.log(password)
    let data = {"password": password};
    console.log(data)

    $.ajax({
        type: "DELETE",
        url: `/api/posts/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            if (response == -1) {
                alert("비밀번호가 맞지 않아 게시글이 삭제되지 않았습니다")
            } else {
                alert("게시글이 삭제되었습니다")
            }
            window.location.reload()

        }
    })
}
