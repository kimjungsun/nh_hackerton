import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class THistory {
    public static <JSONObject> int THistory(int tuno_val,String bncd) throws Exception {

        try {
            URL url = new URL("https://developers.nonghyup.com/InquireTransactionHistory.nh");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            con.setDoOutput(true);
            String jsonInputString = "{\"Header\":{" +
                    "\"ApiNm\":\"InquireTransactionHistory\"," +
                    "\"Tsymd\":\"20201212\"," +
                    "\"Trtm\":\"002611\"," +
                    "\"Iscd\":\"000640\"," +
                    "\"FintechApsno\":\"001\"," +
                    "\"ApiSvcCd\":\"DrawingTransferA\"," +
                    "\"IsTuno\":" + tuno_val + "," +
                    "\"AccessToken\":\"8c50e4c9e92ef9d12ff935639c286b14b07bb2e207ef074430a932fa19ef0f16\"}," +
                    "\"Bncd\":\"012\"," +
                    "\"Acno\":\"3510000002698\"," +
                    "\"Insymd\":\"20201010\"," +
                    "\"Ineymd\":\"20201212\"," +
                    "\"TrnsDsnc\":\"A\"," +
                    "\"Lnsq\":\"ASC\"," +
                    "\"PageNo\":\"1\"," +
                    "\"Dmcnt\":\"50\"}" ;

            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            byte[] input = jsonInputString.getBytes();
            os.writeBytes(jsonInputString);
            System.out.println(con.getResponseCode());

            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String responseLine = null;
            StringBuffer response = new StringBuffer();
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine);
            }
            if (con.getResponseCode() != 200) {
                System.out.println("Failed: HTTP error code : " + con.getResponseCode());
                throw new RuntimeException("Failed: HTTP error code : " + con.getResponseCode());
            } else {
                System.out.println("발송 성공");
                System.out.println(response);
            }
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine engine = sem.getEngineByName("javascript");
            String json = response.toString();
            String script = "Java.asJSONCompatible(" + json + ")";
            Object result = engine.eval(script);
            Map contents = (Map) result;


            //   con.disconnect();
         //   System.out.println(contents.get("TotCnt").toString());
            return Integer.parseInt(contents.get("TotCnt").toString());

        } catch(IOException e) { return 0;}

    }
}
