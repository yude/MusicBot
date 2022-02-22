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
package com.jagrosh.jmusicbot.commands.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.JDAUtilitiesInfo;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.commands.OwnerCommand;
import com.jagrosh.jmusicbot.utils.OtherUtil;
import com.sedmelluq.discord.lavaplayer.tools.PlayerLibrary;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class DebugCmd extends OwnerCommand 
{
    private final static String[] PROPERTIES = {"java.version", "java.vm.name", "java.vm.specification.version", 
        "java.runtime.name", "java.runtime.version", "java.specification.version",  "os.arch", "os.name"};
    
    private final Bot bot;
    
    public DebugCmd(Bot bot)
    {
        this.bot = bot;
        this.name = "debug";
        this.help = "shows debug info";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("```\nシステムの設定:");
        for(String key: PROPERTIES)
            sb.append("\n  ").append(key).append(" = ").append(System.getProperty(key));
        sb.append("\n\nボットの情報:")
                .append("\n  バージョン = ").append(OtherUtil.getCurrentVersion())
                .append("\n  管理者 = ").append(bot.getConfig().getOwnerId())
                .append("\n  prefix = ").append(bot.getConfig().getPrefix())
                .append("\n  代わりの prefix = ").append(bot.getConfig().getAltPrefix())
                .append("\n  最大の項目の長さ = ").append(bot.getConfig().getMaxSeconds())
                .append("\n  サムネイル = ").append(bot.getConfig().useNPImages())
                .append("\n  ステータスに状態を表示 = ").append(bot.getConfig().getSongInStatus())
                .append("\n  チャンネルに留まる = ").append(bot.getConfig().getStay())
                .append("\n  デバッグ = ").append(bot.getConfig().useEval())
                .append("\n  アップデートの通知 = ").append(bot.getConfig().useUpdateAlerts());
        sb.append("\n\n依存ライブラリの情報:")
                .append("\n  JDA バージョン = ").append(JDAInfo.VERSION)
                .append("\n  JDA-Utilities バージョン = ").append(JDAUtilitiesInfo.VERSION)
                .append("\n  Lavaplayer バージョン = ").append(PlayerLibrary.VERSION);
        long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        long used = total - (Runtime.getRuntime().freeMemory() / 1024 / 1024);
        sb.append("\n\nランタイムの情報:")
                .append("\n  RAM 総量 = ").append(total)
                .append("\n  使用済み RAM = ").append(used);
        sb.append("\n\nDiscord の情報:")
                .append("\n  ID = ").append(event.getJDA().getSelfUser().getId())
                .append("\n  サーバー数 = ").append(event.getJDA().getGuildCache().size())
                .append("\n  ユーザー数 = ").append(event.getJDA().getUserCache().size());
        sb.append("\n```");
        
        if(event.isFromType(ChannelType.PRIVATE) 
                || event.getSelfMember().hasPermission(event.getTextChannel(), Permission.MESSAGE_ATTACH_FILES))
            event.getChannel().sendFile(sb.toString().getBytes(), "debug_information.txt").queue();
        else
            event.reply("デバッグ情報: " + sb.toString());
    }
}
