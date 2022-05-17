package ua.salatosik;

import java.util.Properties;
import java.util.Timer;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class App {

    private static Properties properties = new Properties();
    private static String botToken, politicalChatId, generalNewsLink;
    private static Integer updatePeriod;
    private static JDABuilder jdaBuilder;
    public static JDA jda;

    private static Timer timer = new Timer("TSN Parser");

    public static String getGeneralNewsLink() { return generalNewsLink; }
    public static String getPoliticalChatId() { return politicalChatId; }
    public static void main(String[] args) throws Exception {
        
        // load and read file configuration
        properties.load(App.class.getClassLoader().getResourceAsStream("config/bot-config.properties"));
        botToken = properties.getProperty("bot.token");
        politicalChatId = properties.getProperty("bot.political.chat.id");
        generalNewsLink = properties.getProperty("site.general.link");
        updatePeriod = Integer.valueOf(properties.getProperty("site.update.period"));

        // creating jda bot
        jdaBuilder = JDABuilder.createDefault(botToken);
        jda = jdaBuilder.build();

        // setting a timer task for parse new news
        timer.scheduleAtFixedRate(new NewsTask(generalNewsLink), 1000, updatePeriod.intValue());

        jda.awaitReady();
    }
}
