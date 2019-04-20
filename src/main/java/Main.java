public class Main {
    public static void main(String [] args)
    {

        System.out.println("hello");
        String url = "http://legacy.aonprd.com/bestiary/monsterIndex.html";

        URLCrawler crawler = new URLCrawler(url);
        crawler.getMonstersLinks();
    }
}
