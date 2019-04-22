import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static String RESULT_PATH = "entitys.json";

    public static void main(String [] args)
    {
        String url = "http://legacy.aonprd.com/bestiary/monsterIndex.html";

        URLCrawler urlCrawler = new URLCrawler(url);
        ArrayList<String> allURLs = urlCrawler.crawl();

        EntityCrawler eCrawler = new EntityCrawler(allURLs);
        JSONArray result = eCrawler.crawl();
        System.out.println(result.length());

        try {
            FSHelper.writeToFile(RESULT_PATH, result);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
