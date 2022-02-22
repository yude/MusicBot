/*
 * Copyright 2017 John Grosh <john.a.grosh@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.jmusicbot.commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.settings.RepeatMode;
import com.jagrosh.jmusicbot.settings.Settings;
import com.jagrosh.jmusicbot.utils.FormatUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

/**
 *
 * @author John Grosh <john.a.grosh@gmail.com>
 */
public class SettingsCmd extends Command 
{
    private final static String EMOJI = "\uD83C\uDFA7"; // 🎧
    
    public SettingsCmd(Bot bot)
    {
        this.name = "settings";
        this.help = "ボットの設定を表示します。";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.guildOnly = true;
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        Settings s = event.getClient().getSettingsFor(event.getGuild());
        MessageBuilder builder = new MessageBuilder()
                .append(EMOJI + " **")
                .append(FormatUtil.filter(event.getSelfUser().getName()))
                .append("** 設定項目:");
        TextChannel tchan = s.getTextChannel(event.getGuild());
        VoiceChannel vchan = s.getVoiceChannel(event.getGuild());
        Role role = s.getRole(event.getGuild());
        EmbedBuilder ebuilder = new EmbedBuilder()
                .setColor(event.getSelfMember().getColor())
                .setDescription("テキストチャンネル: " + (tchan == null ? "制限なし" : "**#" + tchan.getName() + "**")
                        + "\nテキストチャンネル: " + (vchan == null ? "制限なし" : vchan.getAsMention())
                        + "\nDJ ロール: " + (role == null ? "なし" : "**" + role.getName() + "**")
                        + "\nカスタム prefix: " + (s.getPrefix() == null ? "なし" : "`" + s.getPrefix() + "`")
                        + "\nリピート: " + (s.getRepeatMode() == RepeatMode.OFF
                                                ? s.getRepeatMode().getUserFriendlyName()
                                                : "**"+s.getRepeatMode().getUserFriendlyName()+"**")
                        + "\n既定のプレイリスト: " + (s.getDefaultPlaylist() == null ? "なし" : "**" + s.getDefaultPlaylist() + "**")
                        )
                .setFooter(event.getJDA().getGuilds().size() + " サーバー | "
                        + event.getJDA().getGuilds().stream().filter(g -> g.getSelfMember().getVoiceState().inVoiceChannel()).count()
                        + " ボイスチャンネル", null);
        event.getChannel().sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
    }
    
}
