moves.forEach((move) => {
  move.addEventListener("click", (e) => {
    // 제목 클릭 시 a 태그 기능 중기
    e.preventDefault();
    // href 가져오기
    const href = e.target.getAttribute("href");

    const element = `<imput type="hidden" name="bno" value="${href}">`;
    actionForm.insertAdjacentElement("beforeend", element);

    actionForm.action = "/qCount.do";

    console.log(actionForm);
  });

  console.log(e.target.href);

  // actionForm 안에 bno 태그 추가
  // actionForm.action = "/qCount.do"
});
