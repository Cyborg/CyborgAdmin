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

import com.alta189.cyborg.api.util.yaml.YAMLProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.alta189.cyborg.admin.CyborgAdmin.getDatabase;

public class Config {
	private static YAMLProcessor config;
	private static Map<String, MutedChannel> mutedChans = new HashMap<String, MutedChannel>();

	public static void setConfig(YAMLProcessor config) {
		Config.config = config;
	}

	public static YAMLProcessor getConfig() {
		return config;
	}

	public static Collection<MutedChannel> getMutedChannels() {
		return mutedChans.values();
	}

	public static void addMutedChannel(String channel) {
		MutedChannel mutedChannel = mutedChans.get(channel.toLowerCase());
		if (mutedChannel == null) {
			mutedChannel = new MutedChannel().setName(channel).setMuted(true);
			mutedChans.put(channel.toLowerCase(), mutedChannel);
		} else {
			mutedChannel.setMuted(true);
		}
	}

	public static void removeMutedChannel(String channel) {
		MutedChannel mutedChannel = mutedChans.get(channel.toLowerCase());
		if (mutedChannel == null) {
			mutedChannel = new MutedChannel().setName(channel).setMuted(false);
			mutedChans.put(channel.toLowerCase(), mutedChannel);
		} else {
			mutedChannel.setMuted(false);
		}
	}

	public static boolean isChannelMuted(String channel) {
		MutedChannel mutedChannel = mutedChans.get(channel.toLowerCase());
		return mutedChannel != null && mutedChannel.isMuted();
	}

	public static void load() {
		List<MutedChannel> channels = getDatabase().select(MutedChannel.class).execute().find();
		for (MutedChannel chan : channels) {
			mutedChans.put(chan.getName().toLowerCase(), chan);
		}
	}

	public static void save() {
		for (MutedChannel chan : mutedChans.values()) {
			getDatabase().save(MutedChannel.class, chan);
		}
	}
}
