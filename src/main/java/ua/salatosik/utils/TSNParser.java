package ua.salatosik.utils;

import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TSNParser {
    private static Logger logger = Logger.getGlobal();

    public static TSNInfo parse(String categoryLink) {
        TSNInfo info = new TSNInfo();

        try {

            Document doc = Jsoup.connect(categoryLink).get();
            Elements elements = doc.getElementsByClass("l-row l-flex _l-order-aside-tr u-hide--sdmd");
        
            Element newsEmbed = elements.get(0).getElementsByClass("l-col l-col--xs l-gap").get(0);
        
            info.category = newsEmbed.getElementsByClass("c-card__category c-bar--sm__row--sin c-bar c-bar--dense c-bar--bold c-bar--sm c-bar--text").text();
            info.name = newsEmbed.getElementsByClass("c-card__title").text();
            info.link = newsEmbed.getElementsByClass("c-card__link").attr("href");
            info.time = newsEmbed.getElementsByTag("time").text();
            info.imageLink = newsEmbed.getElementsByClass("c-card__media").first().getElementsByClass("c-card__embed").first().getElementsByTag("img").first().attributes().get("data-src");

        } catch(Exception exception) {
            logger.info(exception.getMessage());
            return null;
        }

        return info;
    }
}
