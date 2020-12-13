import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class depositor {
    public static <JSONObject> String depositor() throws Exception {

        try {
            URL url = new URL("https://developers.nonghyup.com/InquireDepositorAccountNumber.nh");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            con.setDoOutput(true);
            String jsonInputString = "{\"Header\":{" +
                    "\"ApiNm\":\"InquireDepositorAccountNumber\"," +
                    "\"Tsymd\":\"20201212\"," +
                    "\"Trtm\":\"002611\"," +
                    "\"Iscd\":\"000640\"," +
                    "\"FintechApsno\":\"001\"," +
                    "\"ApiSvcCd\":\"DrawingTransferA\"," +
                    "\"IsTuno\":\"000311112112333\"," +
                    "\"AccessToken\":\"8c50e4c9e92ef9d12ff935639c286b14b07bb2e207ef074430a932fa19ef0f16\"}," +
                    "\"Bncd\":\"011\"," +
                    "\"Acno\":\"3020000002695\"}";

            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            byte[] input = jsonInputString.getBytes();
            os.writeBytes(jsonInputString);
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
            }
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine engine = sem.getEngineByName("javascript");
            String json = response.toString();
            String script = "Java.asJSONCompatible(" + json + ")";
            Object result = engine.eval(script);
            Map contents = (Map) result;


            //   con.disconnect();

            return contents.get("Dpnm").toString();

        } catch(IOException e) { return null;}

    }
}
