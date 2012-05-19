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
import com.alta189.cyborg.api.command.CommandResult;
import com.alta189.cyborg.api.command.CommandSource;
import com.alta189.cyborg.api.command.ReturnType;
import com.alta189.cyborg.api.command.annotation.Command;
import com.alta189.cyborg.api.util.StringUtils;
import org.pircbotx.Channel;

import static com.alta189.cyborg.perms.PermissionManager.hasPerm;

public class AdminCommands {
	@Command(name = "disconnect", desc = "Mutes the bot in a specific channel", aliases = {"dc", "quit", "exit"})
	public CommandResult disconnect(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}
		CommandResult result = new CommandResult();
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.echo")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("You don't have permission!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (context.getArgs() != null && context.getArgs().length >= 1) {
			Cyborg.getInstance().shutdown(StringUtils.toString(context.getArgs(), " "));
		} else {
			Cyborg.getInstance().shutdown();
		}
		return null;
	}

	@Command(name = "joinchannel", desc = "Sends message to target", aliases = {"j", "jc", "join"})
	public CommandResult joinchannel(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}
		CommandResult result = new CommandResult();
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.join")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("You don't have permission!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (context.getArgs() == null || context.getArgs().length < 1) {
			String body = "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "joinchannel <channel> [key]";
			return result.setReturnType(ReturnType.NOTICE).setBody(body).setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (!context.getArgs()[0].startsWith("#")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("Invalid channel").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (context.getArgs().length >= 2) {
			Cyborg.getInstance().joinChannel(context.getArgs()[0], context.getArgs()[1]);
		} else {
			Cyborg.getInstance().joinChannel(context.getArgs()[0]);
		}
		return result.setReturnType(ReturnType.MESSAGE).setBody("Joining channel '" + context.getArgs()[0] + "'").setTarget(context.getLocationType() == CommandContext.LocationType.CHANNEL ? context.getLocation() : source.getUser().getNick());
	}

	@Command(name = "partchannel", desc = "Sends message to target", aliases = {"p", "pc", "part"})
	public CommandResult partchannel(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}
		CommandResult result = new CommandResult();
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.part")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("You don't have permission!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (context.getArgs() == null || context.getArgs().length < 1) {
			String body = "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "partchannel <channel> [reason]";
			return result.setReturnType(ReturnType.NOTICE).setBody(body).setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (!context.getArgs()[0].startsWith("#")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("Invalid channel!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		Channel channel = Cyborg.getInstance().getChannel(context.getArgs()[0]);
		if (channel == null) {
			return result.setReturnType(ReturnType.NOTICE).setBody("I am not in that channel").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (context.getArgs().length >= 2) {
			System.out.println(StringUtils.toString(context.getArgs(), 1, " "));
			Cyborg.getInstance().partChannel(channel, StringUtils.toString(context.getArgs(), 1, " "));
		} else {
			Cyborg.getInstance().partChannel(channel);
		}
		return result.setReturnType(ReturnType.MESSAGE).setBody("Parting channel '" + context.getArgs()[0] + "'").setTarget(context.getLocationType() == CommandContext.LocationType.CHANNEL ? context.getLocation() : source.getUser().getNick());
	}

	@Command(name = "echo", desc = "Sends message to target", aliases = {"e", "say"})
	public CommandResult echo(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}
		CommandResult result = new CommandResult();
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.echo")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("You don't have permission!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (context.getArgs() == null || context.getArgs().length < 2) {
			String body = "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "echo <target> <message>";
			return result.setReturnType(ReturnType.NOTICE).setBody(body).setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		String message = StringUtils.toString(context.getArgs(), 1, " ");
		return result.setReturnType(ReturnType.MESSAGE).setBody(message).setTarget(context.getArgs()[0]).setForced(true);
	}

	@Command(name = "notice", desc = "Sends notice to target", aliases = {"n"})
	public CommandResult notice(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}
		CommandResult result = new CommandResult();
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.notice")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("You do not have permission!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (context.getArgs() == null || context.getArgs().length < 2) {
			String body = "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "notice <target> <notice>";
			return result.setReturnType(ReturnType.NOTICE).setBody(body).setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		String message = StringUtils.toString(context.getArgs(), 1, " ");
		return result.setReturnType(ReturnType.NOTICE).setBody(message).setTarget(context.getArgs()[0]).setForced(true);
	}

	@Command(name = "action", desc = "Sends action to target", aliases = {"a", "act"})
	public CommandResult action(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}
		CommandResult result = new CommandResult();
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.action")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("You don't have permission!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (context.getArgs() == null || context.getArgs().length < 2) {
			String body = "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "action <target> <action>";
			return result.setReturnType(ReturnType.NOTICE).setBody(body).setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		String message = StringUtils.toString(context.getArgs(), 1, " ");
		return result.setReturnType(ReturnType.ACTION).setBody(message).setTarget(context.getArgs()[0]).setForced(true);
	}

	@Command(name = "mute", desc = "Mutes the bot in a specific channel")
	public CommandResult muteCommand(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}
		CommandResult result = new CommandResult();
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.mute")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("You don't have permission!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (context.getArgs() == null || context.getArgs().length < 1) {
			String body = "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "mute <channel>";
			return result.setReturnType(ReturnType.NOTICE).setBody(body).setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (!context.getArgs()[0].startsWith("#")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("Not a valid channel!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (Config.isChannelMuted(context.getArgs()[0])) {
			return result.setReturnType(ReturnType.NOTICE).setBody("Already muted!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		Config.addMutedChannel(context.getArgs()[0]);
		return null;
	}

	@Command(name = "unmute", desc = "Unutes the bot in a specific channel")
	public CommandResult unmuteCommand(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}
		CommandResult result = new CommandResult();
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.mute")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("You don't have permission!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (context.getArgs() == null || context.getArgs().length < 1) {
			String body = "Correct usage is " + (source.getSource() == CommandSource.Source.USER ? "." : "") + "mute <channel>";
			return result.setReturnType(ReturnType.NOTICE).setBody(body).setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (!context.getArgs()[0].startsWith("#")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("Not a valid channel!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		if (!Config.isChannelMuted(context.getArgs()[0])) {
			return result.setReturnType(ReturnType.NOTICE).setBody("Channel is not muted").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		Config.removeMutedChannel(context.getArgs()[0]);
		return null;
	}

	@Command(name = "listmute", desc = "Unutes the bot in a specific channel")
	public CommandResult listmute(CommandSource source, CommandContext context) {
		if (source.getSource() == CommandSource.Source.USER && (context.getPrefix() == null || !context.getPrefix().equals("."))) {
			return null;
		}
		CommandResult result = new CommandResult();
		if (source.getSource() == CommandSource.Source.USER && !hasPerm(source.getUser(), "admin.mutelist")) {
			return result.setReturnType(ReturnType.NOTICE).setBody("You don't have permission!").setTarget(source.getSource() != CommandSource.Source.USER ? null : source.getUser().getNick());
		}
		StringBuilder builder = new StringBuilder();
		builder.append("Muted channels: ");
		int i = 0;
		for (String chan : Config.getMutedChannels()) {
			i++;
			builder.append(chan);
			if (i != Config.getMutedChannels().size()) {
				builder.append(", ");
			}
		}
		return result.setReturnType(ReturnType.MESSAGE).setBody(builder.toString()).setTarget(context.getLocationType() == CommandContext.LocationType.CHANNEL ? context.getLocation() : source.getUser().getNick());
	}
}
