import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FishingTemp {

	HttpURLConnection httpConn = null;
	static String targetURL = "http://koreawqi.go.kr/wQSCHomeLayout_D.wq?action_type=T";

	public void printInfo() {
		// TODO Auto-generated method stub
		String urlParameters = ""; // 파라메타값

		try {
			URL url = new URL(targetURL);
			httpConn = (HttpURLConnection) url.openConnection();

			// 헤더 선언
			httpConn.setRequestMethod("GET");
			httpConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			// httpConn.setRequestProperty("Cookie", "cookievalue="+ cookie);

			httpConn.setUseCaches(false);
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpConn.getOutputStream(), "utf-8"));
			pw.write(urlParameters);
			pw.flush();
			pw.close();

			// Get Response
			InputStream is = httpConn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;

			String content = "";
			while ((line = rd.readLine()) != null) {
				
				content += line.replace(System.getProperty("line.separator"), "");
				//System.out.println(line);
				// response1.append('\r');
			}
			content = content.replaceAll("\\s+","");
			//System.out.println(content);
			
			// <tdclass="start">평창강</td><td>1.2</td><!--수온-->
			// <tdclass="start">(?<POINT>.+)</td><td>(?<TEMPO>.+)</td><!--수온-->
			
			Pattern pp = Pattern.compile("<tdclass=\"start\">(?<POINT>.+?)</td><td>(?<TEMPO>.+?)</td><!--수온-->");
			Matcher mm = pp.matcher(content);
			
			while (mm.find()) {
				System.out.println("측정소:"+mm.group("POINT") + "\t" + "수온:" + mm.group("TEMPO"));
				//type type = (type) mm.find().nextElement();
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
