// submit 클릭 시
// 값이 비어 있지않도록 처리 (age는 숫자인지 확인)
const form = document.querySelector("form");
form.addEventListener("submit", (e) => {
  e.preventDefault(); // submit 중지

  const userId = document.getElementById("id");
  const userName = document.getElementById("name");
  const userAge = document.getElementById("age");

  if (!userId.value) {
    alert("아이디를 입력해 주세요");
    userId.focus();
    return;
  } else if (!userName.value) {
    alert("이름을 입력해주세요");
    userName.focus();
    return;
  } else if (!userAge.value || isNaN(userAge.value)) {
    alert("나이를 입력해주세요");
    userAge.focus();
    return;
  }
  // 검증 완료 후 폼 submit 실행
  form.submit();
});
