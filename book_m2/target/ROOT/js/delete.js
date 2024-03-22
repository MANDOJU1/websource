// * Error: Integer.parseInt("") → NumberFormatException 발생 - 클라이언트 단에서 막기
// submit 발생 시
// submit 기능 중지
document.querySelector("form").addEventListener("submit", (e) => {
  e.preventDefault();

  const code = document.querySelector("#code");

  // code, title, writer, price 비어 있는지 확인
  // code : 4자리인지 확인
  if (!code.value || code.value.length != 4 || isNaN(code.value)) {
    alert("code 값이 비어 있거나 숫자 4자리가 아닙니다.");
    code.focus();
    return;
  }
  // 값이 존재한다면 submit
  e.target.submit();
});
