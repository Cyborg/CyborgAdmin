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

import com.alta189.cyborg.api.event.EventHandler;
import com.alta189.cyborg.api.event.Listener;
import com.alta189.cyborg.api.event.Order;
import com.alta189.cyborg.api.event.bot.SendActionEvent;
import com.alta189.cyborg.api.event.bot.SendMessageEvent;
import com.alta189.cyborg.api.event.bot.SendNoticeEvent;

public class ChannelMuteListener implements Listener {

	@EventHandler(order = Order.EARLIEST)
	public void onSendAction(SendActionEvent event) {
		if (event.getTarget().startsWith("#")) {
			if (Config.isChannelMuted(event.getTarget())) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(order = Order.EARLIEST)
	public void onSendMessage(SendMessageEvent event) {
		if (event.getTarget().startsWith("#")) {
			if (Config.isChannelMuted(event.getTarget())) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(order = Order.EARLIEST)
	public void onSendNotice(SendNoticeEvent event) {
		if (event.getTarget().startsWith("#")) {
			if (Config.isChannelMuted(event.getTarget().toLowerCase())) {
				event.setCancelled(true);
			}
		}
	}

}
