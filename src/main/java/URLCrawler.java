import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class URLCrawler {
    String url;

    public URLCrawler(String url) {
        /* attributes */
        this.url = url;
    }

    public ArrayList getMonstersLinks() {
        ArrayList links = new ArrayList();
        Document doc = null;

        try {
            doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements newsHeadlines = doc.select("#monster-index-wrapper ul li");

        for (Element headline : newsHeadlines) {
            String headlineChar = headline.toString();
            String test = Character.toString(headlineChar.charAt(4));
            if (test.equals("<")) {
                Element tag = Jsoup.parse(headlineChar, "", Parser.xmlParser());
                //String urlFetched = Helper.removeUrlLastPart(url) + "/" + tag.select("a").attr("href");
                String urlFetched = "/" + tag.select("a").attr("href");
                links.add(urlFetched);
            }
        }
        System.out.println(links);
        return links;
    }

}
