package com.jagrosh.jmusicbot.commands.dj;


import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.audio.AudioHandler;
import com.jagrosh.jmusicbot.audio.QueuedTrack;
import com.jagrosh.jmusicbot.commands.DJCommand;
import com.jagrosh.jmusicbot.queue.FairQueue;

/**
 * Command that provides users the ability to move a track in the playlist.
 */
public class MoveTrackCmd extends DJCommand
{

    public MoveTrackCmd(Bot bot)
    {
        super(bot);
        this.name = "movetrack";
        this.help = "再生待ちにある特定の項目の順番を変更します。";
        this.arguments = "<現在の場所> <変更先の場所>";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event)
    {
        int from;
        int to;

        String[] parts = event.getArgs().split("\\s+", 2);
        if(parts.length < 2)
        {
            event.replyError("2つの正しいインデックスを指定してください。");
            return;
        }

        try
        {
            // Validate the args
            from = Integer.parseInt(parts[0]);
            to = Integer.parseInt(parts[1]);
        }
        catch (NumberFormatException e)
        {
            event.replyError("2つの正しいインデックスを指定してください。");
            return;
        }

        if (from == to)
        {
            event.replyError("項目を同じ場所に動かすことはできません。");
            return;
        }

        // Validate that from and to are available
        AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();
        FairQueue<QueuedTrack> queue = handler.getQueue();
        if (isUnavailablePosition(queue, from))
        {
            String reply = String.format("`%d` は再生待ちの順番として間違っています。", from);
            event.replyError(reply);
            return;
        }
        if (isUnavailablePosition(queue, to))
        {
            String reply = String.format("`%d` は再生待ちの順番として間違っています。", to);
            event.replyError(reply);
            return;
        }

        // Move the track
        QueuedTrack track = queue.moveItem(from - 1, to - 1);
        String trackTitle = track.getTrack().getInfo().title;
        String reply = String.format("**%s** を `%d` 番目から `%d` 番目に移動しました。", trackTitle, from, to);
        event.replySuccess(reply);
    }

    private static boolean isUnavailablePosition(FairQueue<QueuedTrack> queue, int position)
    {
        return (position < 1 || position > queue.size());
    }
}