document.querySelector("#list").addEventListener("click", () => {
  location.href = "/qList.do";
});

// 수정을 클릭하면
document.querySelector("#modify").addEventListener("click", () => {
  location.href = "/qModify.do?bno=" + bno;
});

document.querySelector("#delete").addEventListener("click", () => {
  location.href = "/view/qna_board_pwdCheck.jsp?bno=" + bno;
});

document.querySelector("#reply").addEventListener("click", () => {
  location.href = "/qReplyView.do?bno=" + bno;
});
