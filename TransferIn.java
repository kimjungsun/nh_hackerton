import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class TransferIn {
    /*  농협계좌 입금 */
    public static <JSONObject> void TransferIn(int tuno_val,String Acno) throws Exception {

        try {
            URL url = new URL("https://developers.nonghyup.com/ReceivedTransferAccountNumber.nh");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            con.setDoOutput(true);
            String jsonInputString = "{\"Header\":{" +
                    "\"ApiNm\":\"ReceivedTransferAccountNumber\"," +
                    "\"Tsymd\":\"20201212\"," +
                    "\"Trtm\":\"002611\"," +
                    "\"Iscd\":\"000640\"," +
                    "\"FintechApsno\":\"002\"," +
                    "\"ApiSvcCd\":\"DrawingTransferA\"," +
                    "\"IsTuno\":"+ tuno_val + "," +
                    "\"AccessToken\":\"8c50e4c9e92ef9d12ff935639c286b14b07bb2e207ef074430a932fa19ef0f16\"}," +
                    "\"Bncd\":\"012\","  +   // 011 012
                    "\"Acno\":" + "\"" + Acno +"\"" + "," +
                    "\"Tram\":\"10000\","  +
                    "\"DractOtlt\":\"\"," +
                    "\"MractOtlt\":\"\"}" ;

            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            byte[] input = jsonInputString.getBytes();
            os.writeBytes(jsonInputString);
            System.out.println(con.getResponseCode());
            System.out.println(jsonInputString);

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


        } catch(IOException e) { }

    }
}
