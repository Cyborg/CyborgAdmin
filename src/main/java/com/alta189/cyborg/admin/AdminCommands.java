/*
 * Copyright (C) 2012 CyborgDev <cyborg@alta189.com>
 *
 * This file is part of CyborgAdmin
 *
 * CyborgAdmin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CyborgAdmin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alta189.cyborg.admin;

import com.alta189.cyborg.Cyborg;
import com.alta189.cyborg.api.command.CommandContext;
import com.alta189.cyborg.api.command.CommandSource;
import com.alta189.cyborg.api.command.annotation.Command;
import com.alta189.cyborg.api.util.StringUtils;

import static com.alta189.cyborg.perms.PermissionManager.hasPerm;

import org.pircbotx.Channel;

public class AdminCommands {

	@Command(name = "disconnect", desc = "Mutes the bot in a specific channel", aliases = {"dc", "quit", "exit"})
	public String disconnect(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals(".")))
			return null;
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.echo"))
			return "You don't have permission!";
		if (context.getArgs() != null && context.getArgs().length >= 1) {
			Cyborg.getInstance().shutdown(StringUtils.toString(context.getArgs(), " "));
		} else {
			Cyborg.getInstance().shutdown();
		}
		return null;
	}

	@Command(name = "joinchannel", desc = "Sends message to target", aliases = {"j", "jc", "join"})
	public String joinchannel(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals(".")))
			return null;
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.join"))
			return "You don't have permission!";
		if (context.getArgs() == null || context.getArgs().length < 1)
			return "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "joinchannel <channel> [key]";
		if (!context.getArgs()[0].startsWith("#"))
			return "Invalid channel!";
		if (context.getArgs().length >= 2) {
			Cyborg.getInstance().joinChannel(context.getArgs()[0], context.getArgs()[1]);
		} else {
			Cyborg.getInstance().joinChannel(context.getArgs()[0]);
		}
		return "Joining channel '" + context.getArgs()[0] + "'";
	}

	@Command(name = "partchannel", desc = "Sends message to target", aliases = {"p", "pc", "part"})
	public String partchannel(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals(".")))
			return null;
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.part"))
			return "You don't have permission!";
		if (context.getArgs() == null || context.getArgs().length < 1)
			return "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "partchannel <channel> [reason]";
		if (!context.getArgs()[0].startsWith("#"))
			return "Invalid channel!";
		Channel channel = Cyborg.getInstance().getChannel(context.getArgs()[0]);
		if (channel == null)
			return "I am not in that channel!";
		if (context.getArgs().length >= 2) {
			System.out.println(StringUtils.toString(context.getArgs(), 1, " "));
			Cyborg.getInstance().partChannel(channel, StringUtils.toString(context.getArgs(), 1, " "));
		} else {
			Cyborg.getInstance().partChannel(channel);
		}
		return "Parting channel '" + context.getArgs()[0] + "'";
	}

	@Command(name = "echo", desc = "Sends message to target", aliases = {"e", "say"})
	public String echo(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals(".")))
			return null;
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.echo"))
			return "You don't have permission!";
		if (context.getArgs() == null || context.getArgs().length < 2)
			return "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "echo <target> <message>";
		String message = StringUtils.toString(context.getArgs(), 1, " ");
		Cyborg.getInstance().sendMessage(context.getArgs()[0], message);
		return null;
	}

	@Command(name = "notice", desc = "Sends notice to target", aliases = {"n"})
	public String notice(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals(".")))
			return null;
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.notice"))
			return "You don't have permission!";
		if (context.getArgs() == null || context.getArgs().length < 2)
			return "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "notice <target> <message>";
		String message = StringUtils.toString(context.getArgs(), 1, " ");
		Cyborg.getInstance().sendNotice(context.getArgs()[0], message);
		return null;
	}

	@Command(name = "action", desc = "Sends action to target", aliases = {"a"})
	public String action(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals(".")))
			return null;
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.action"))
			return "You don't have permission!";
		if (context.getArgs() == null || context.getArgs().length < 2)
			return "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "action <target> <message>";
		String message = StringUtils.toString(context.getArgs(), 1, " ");
		Cyborg.getInstance().sendAction(context.getArgs()[0], message);
		return null;
	}

	@Command(name = "mute", desc = "Mutes the bot in a specific channel")
	public String muteCommand(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals(".")))
			return null;
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.mute"))
			return "You don't have permission!";
		if (context.getArgs() == null || context.getArgs().length < 1)
			return "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "mute <channel>";
		if (!context.getArgs()[0].startsWith("#"))
			return "Not a valid channel";
		if (Config.isChannelMuted(context.getArgs()[0]))
			return "Already muted!";
		Config.addMutedChannel(context.getArgs()[0]);
		return null;
	}

	@Command(name = "unmute", desc = "Unutes the bot in a specific channel")
	public String unmuteCommand(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals(".")))
			return null;
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.mute"))
			return "You don't have permission!";
		if (context.getArgs() == null || context.getArgs().length < 1)
			return "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "mute <channel>";
		if (!context.getArgs()[0].startsWith("#"))
			return "Not a valid channel";
		if (!Config.isChannelMuted(context.getArgs()[0]))
			return "Channel is not muted!";
		Config.removeMutedChannel(context.getArgs()[0]);
		return null;
	}

	@Command(name = "listmute", desc = "Unutes the bot in a specific channel")
	public String listmute(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals(".")))
			return null;
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.mutelist"))
			return "You don't have permission!";
		StringBuilder builder = new StringBuilder();
		builder.append("Muted channels: ");
		int i = 0;
		for (String chan : Config.getMutedChannels()) {
			i ++;
			builder.append(chan);
			if (i != Config.getMutedChannels().size())
				builder.append(", ");
		}
		return builder.toString();
	}
}
