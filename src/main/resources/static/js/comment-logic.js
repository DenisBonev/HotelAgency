let commentBtn = document.getElementById('button-addon2');
let commentInput = document.getElementById('commentInput');
let commentSection = document.getElementById('commentSection');
let commentCount = document.getElementById('commentCount');
commentBtn.addEventListener('click', postComment);

window.addEventListener('load', loadAllComments);

function postComment(e) {
    fetch('http://localhost:8080/hotels/' + commentBtn.value + '/add-comment',
        {
            method: 'POST',
            body: commentInput.value
        }).then(r => {
        commentInput.value = '';
        loadAllComments();
    });
}

function loadAllComments() {
    const allComments = [];
    fetch('http://localhost:8080/hotels/' + commentBtn.value + '/comments').then(r => r.json())
        .then(data => {
            for (let d of data) {
                allComments.push(d);
            }
        }).then(p => {
        commentSection.innerHTML = '';
        commentCount.innerText = 'Comments(' + allComments.length + ')';
        for (let c of allComments) {
            const div1 = document.createElement('div');
            div1.className = 'card p-3';
            const div2 = document.createElement('div');
            div2.className = 'd-flex justify-content-between align-items-center';
            const div3 = document.createElement('div');
            div3.className = 'user d-flex flex-row align-items-center gap-1';
            const image = document.createElement('img');
            image.src = c.userPic;
            image.className = 'user-img rounded-circle mr-2';
            image.width = 30;
            const span = document.createElement('span');
            span.className='gap-2 align-items-center d-flex flex-row';
            const smallUsername = document.createElement('small');
            smallUsername.className = 'font-weight-bold text-primary'
            smallUsername.innerText = c.userNames;
            const smallContent = document.createElement('small');
            smallContent.className = 'font-weight-bold';
            smallContent.innerText = c.content;
            const smallPostedOn = document.createElement('small');
            smallPostedOn.innerText = c.postedOn;
            div1.appendChild(div2);
            div2.appendChild(div3);
            div3.appendChild(image);
            div3.appendChild(span);
            span.appendChild(smallUsername);
            span.appendChild(smallContent);
            div2.appendChild(smallPostedOn);
            commentSection.appendChild(div1);
        }
    });

    /* <div class="card p-3 mt-2">
                    <div class="d-flex justify-content-between align-items-center">
                        <div class="user d-flex flex-row align-items-center">
                            <img src="https://i.imgur.com/C4egmYM.jpg" width="30"
                                    class="user-img rounded-circle mr-2">
                            <span>
                                <small class="font-weight-bold text-primary">olan_sams</small>
                                <small class="font-weight-bold">Loving your work and profile! </small>
                            </span>
                        </div>
                        <small>3 days ago</small>
                    </div>
                </div>*/
}