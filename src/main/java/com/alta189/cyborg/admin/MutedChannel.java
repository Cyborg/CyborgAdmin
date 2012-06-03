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

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;

@Table("mutedchannels")
public class MutedChannel {

	@Id
	private int id;

	@Field
	private String name;

	@Field
	private boolean muted;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public MutedChannel setName(String name) {
		this.name = name;
		return this;
	}

	public boolean isMuted() {
		return muted;
	}

	public MutedChannel setMuted(boolean muted) {
		this.muted = muted;
		return this;
	}
}
