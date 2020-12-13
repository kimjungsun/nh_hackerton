import javax.management.Descriptor;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
public class test {
    public static <JSONObject> void main(String[] args) throws Exception {

        String finacno, acno, othacno,bncd1,bncd2;
        int ran, totcnt1=0,totcnt2=0  ;


        finacno = "00820100006400000000000004852";
        acno = "3020000002695";
        othacno = "3510000002698";
        bncd1 = "011";
        bncd2 = "012";

        ran = (int)(Math.random()*100000000);
        Transfer.Transfer(ran,finacno);
        TransferIn.TransferIn(ran,othacno);
        ran = (int)(Math.random()*100000000);
        totcnt1 = THistory.THistory(ran,bncd1);
        ran = (int)(Math.random()*100000000);
        totcnt2 = THistory.THistory(ran,bncd2);
        double totcnt = (double)totcnt1/(totcnt1+totcnt2);
        System.out.println(totcnt2);

        String out = "농협 이체횟수/전체 이체횟수 : " + totcnt;
        System.out.println(out);




    }

    }

