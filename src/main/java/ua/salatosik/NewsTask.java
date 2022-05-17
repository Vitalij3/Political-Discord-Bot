package ua.salatosik;

import java.util.TimerTask;

import java.awt.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import ua.salatosik.utils.TSNInfo;
import ua.salatosik.utils.TSNParser;

public class NewsTask extends TimerTask {

    private String link;
    private TSNInfo lastTsnInfo = new TSNInfo();

    public NewsTask(String link) { this.link = link; }

    // private method for creating embed messages
    private MessageEmbed embedNewsBuilder(String name, String link, String imageLink, String newsCategory, String time) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED);
        embedBuilder.setThumbnail(imageLink);
        embedBuilder.addField("**" + newsCategory + "** - " + "*" + time + "*", name, true);
        embedBuilder.addField("Link", link, true);
        return embedBuilder.build();
    }

    public void run() {
        TSNInfo tsnInfo = TSNParser.parse(link);
        
        if(tsnInfo == null) return;
        else if(tsnInfo.link.equals(lastTsnInfo.link)) return;
        lastTsnInfo = tsnInfo;

        App.jda.getTextChannelById(App.getPoliticalChatId())
            .sendMessageEmbeds(embedNewsBuilder(tsnInfo.name, tsnInfo.link, tsnInfo.imageLink, tsnInfo.category, tsnInfo.time)).complete();
    }
}
