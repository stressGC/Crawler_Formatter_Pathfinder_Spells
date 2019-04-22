/**
 * @author Georges Cosson
 */

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * class made to crawl an entity
 */
public class EntityCrawler {

    /* attributes */
    private JSONHelper jsonHelper = new JSONHelper();
    private ArrayList<String> urls;

    /* constructor */
    public EntityCrawler(ArrayList<String> urls) {
        this.urls = urls;
    }

    /**
     * launches entity crawler
     * @param {ArrayList} contains all urls to crawl
     */
    public JSONArray crawl() {
        JSONArray crawlingResults = new JSONArray();

        for(String url : this.urls) {
            JSONObject entity = this.crawlOne(url);
            crawlingResults.put(entity);
        }

        return crawlingResults;
    }

    /**
     * crawls one URL
     * @param url
     * @return a JSON with entity informations
     */
    private JSONObject crawlOne(String url) {
        try {
            Document html = Jsoup.connect(url).get();

            // lets retrieve the entity's informations
            String name = fetchName(html); // name
            ArrayList<String> spells = fetchSpells(html, name); // spells

            return this.jsonHelper.entityAsJSON(name, spells);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * extracts the entity name from the html
     * @param html
     * @return entity name
     */
    private String fetchName(Document html) {
        String regularQuery = ".stat-block-title > b";
        String emptyQuery = ".stat-block-title";

        Elements entity = html.select(regularQuery);
        if (entity.equals("")) {
            entity = html.select(emptyQuery);
        }

        /* remove noisy text */
        entity.select(".stat-block-cr").remove();
        return entity.text();
    }

    /**
     * extracts the spells from the html
     * @param html
     * @param monsterName
     * @return spells
     */
    public ArrayList<String> fetchSpells(Document html, String monsterName) {
        String query = ".stat-block-title, .stat-block-breaker:contains(Offense) ~ .stat-block-2 a";
        Elements a = html.select(query);

        ArrayList<String> spells = new ArrayList<>();
        boolean hasResult = false;
        int uu = 0;

        // clean array
        for (Element elem : a) {
            elem.select(".stat-block-cr").remove();
        }

        // return spells for current monster
            for (Element element : a) {
                if (!element.classNames().isEmpty()) {
                    if (hasResult) {
                        return spells;
                    }
                    if (element.text().equals(monsterName)) {
                        hasResult = true;
                    }
                }
                if (element.tag().toString().equals("a") && hasResult) {
                    spells.add(element.text());
                }
            }

        return spells;
    }
}
