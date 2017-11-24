<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.net.MalformedURLException"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.URLConnection"%>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.regex.Pattern" %>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	String url = request.getParameter("upfile");
	String filePath = "system/ueditor1_2_1/upload";
	String[] arr = url.split("ue_separate_ue");
	String[] outSrc = new String[arr.length];
	for(int i=0;i<arr.length;i++){
		
		//保存文件路径
		String savePath = request.getRealPath("/") + filePath;//保存路径
		//格式验证
		Pattern reg=Pattern.compile("[.](jpg|png|jpeg|gif)$");
		Matcher matcher=reg.matcher(arr[i]);
		if(!matcher.find()) {
			return;
		}
		String saveName = System.currentTimeMillis() + arr[i].substring(arr[i].lastIndexOf("."));
		//大小验证
		URL urla = new URL(arr[i]);
		URLConnection conn = urla.openConnection();
		
		File savetoFile = new File(savePath +"/"+ saveName);
		outSrc[i]=filePath +"/"+ saveName;
		try {
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(savetoFile);
			int b;
			while ((b = is.read()) != -1) {
				os.write(b);
			}
			os.close();
			is.close();
			// 这里处理 inputStream
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("页面无法访问");
		}
	}
	String outstr = "";
	for(int i=0;i<outSrc.length;i++){
		outstr+=outSrc[i]+"ue_separate_ue";
	}
	outstr = outstr.substring(0,outstr.lastIndexOf("ue_separate_ue"));
	System.out.print(outstr);
	String tmpURL = filePath+"/"+url.substring(url.lastIndexOf("/")+1,url.length());
	String rtnStr = outstr.replace("system/ueditor1_2_1", "");
	System.out.print(rtnStr);
	response.getWriter().print("{'url':'" + rtnStr + "','tip':'远程图片抓取成功！','srcUrl':'" + url + "'}" );
	
%>
