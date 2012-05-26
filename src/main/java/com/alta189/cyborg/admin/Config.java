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
import java.util.List;

public class Config {
	private static YAMLProcessor config;
	private static final List<String> mutedChans = new ArrayList<String>();

	public static void setConfig(YAMLProcessor config) {
		Config.config = config;
	}

	public static YAMLProcessor getConfig() {
		return config;
	}

	public static List<String> getMutedChannels() {
		return mutedChans;
	}

	public static void addMutedChannel(String channel) {
		mutedChans.add(channel.toLowerCase());
	}

	public static void removeMutedChannel(String channel) {
		mutedChans.remove(channel.toLowerCase());
	}

	public static boolean isChannelMuted(String channel) {
		return getMutedChannels().contains(channel.toLowerCase());
	}
}
