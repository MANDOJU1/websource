<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import = "java.io.BufferedOutputStream"%>
<%@ page import = "java.io.FileInputStream"%>

<%
    // 클라이언트가 요청한 fileName 가져오기
    // a8b30c0f-118c-4327-81ab-9d93e1a24346_create.jsp
    String fileName = request.getParameter("fileName");

    String fDownloadPath = "c:\\upload";
    // c:\\upload\\a8b30c0f-118c-4327-81ab-9d93e1a24346_create.jsp
    String filePath = fDownloadPath + "\\" + fileName;

    // 하드디스크에 있는 파일 읽기
    // input / output
    // 1. input 
    //  1) 키보드 :system.in
    //  2) 파일 
    //     텍스트 : Reader(추천), InputStream
    //     이미지, 동영상 : FileInputStream
    FileInputStream in = new FileInputStream(filePath);

    // 브라우저가 응답할 때 읽어온 파일 보내기
    response.setContentType("application/octet-stream");
    // iso-8859-1 : 브라우저 기본타입
    fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");

    int start = fileName.lastIndexOf("_");
    String oriName = fileName.substring(start + 1);

    response.setHeader("Content-Disposition", "attachment;filename=" + oriName);

    out.clear();
    out = pageContext.pushBody();

    // BufferedOutputStream(보조스트림) : 속도가 빨라짐
    BufferedOutputStream buf = new BufferedOutputStream(response.getOutputStream());

    int numRead;
    byte b[] = new byte[4096];
    while ((numRead = in.read(b, 0, b.length)) != -1) {
        buf.write(b, 0, numRead);

    }
    buf.flush();
    buf.close();
    in.close();
    
%>