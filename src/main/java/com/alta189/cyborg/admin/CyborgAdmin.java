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

import com.alta189.cyborg.api.command.annotation.EmptyConstructorInjector;
import com.alta189.cyborg.api.plugin.CommonPlugin;
import com.alta189.cyborg.api.util.yaml.YAMLFormat;
import com.alta189.cyborg.api.util.yaml.YAMLProcessor;
import com.alta189.simplesave.Database;
import com.alta189.simplesave.DatabaseFactory;
import com.alta189.simplesave.exceptions.ConnectionException;
import com.alta189.simplesave.exceptions.TableRegistrationException;
import com.alta189.simplesave.mysql.MySQLConfiguration;
import com.alta189.simplesave.mysql.MySQLConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class CyborgAdmin extends CommonPlugin {
	private static CyborgAdmin instance;
	private Database db;

	@Override
	public void onEnable() {
		instance = this;
		getLogger().log(Level.INFO, "Enabling...");

		Config.setConfig(setupConfig(new File(getDataFolder(), "config.yml")));
		try {
			Config.getConfig().load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		MySQLConfiguration dbConfig = new MySQLConfiguration();
		dbConfig.setHost(Config.getConfig().getString("database.mysql.host", "127.0.0.1"));
		dbConfig.setPort(Config.getConfig().getInt("database.mysql.port", MySQLConstants.DefaultPort));
		dbConfig.setDatabase(Config.getConfig().getString("database.mysql.database"));
		dbConfig.setUser(Config.getConfig().getString("database.mysql.user", MySQLConstants.DefaultUser));
		dbConfig.setPassword(Config.getConfig().getString("database.mysql.password", MySQLConstants.DefaultPass));

		db = DatabaseFactory.createNewDatabase(dbConfig);

		try {
			getDatabase().registerTable(MutedChannel.class);
		} catch (TableRegistrationException e) {
			e.printStackTrace();
		}

		try {
			getDatabase().connect();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}

		Config.load();

		getCyborg().getEventManager().registerEvents(new ChannelMuteListener(), this);
		getCyborg().getCommandManager().registerCommands(this, AdminCommands.class, new EmptyConstructorInjector());

		getLogger().log(Level.INFO, "Successfully enabled!");
	}

	@Override
	public void onDisable() {
		getLogger().log(Level.INFO, "Disabling...");
		Config.getConfig().save();
		Config.save();
		try {
			getDatabase().close();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		getLogger().log(Level.INFO, "Successfully disabled!");
		instance = null;
	}

	public static Database getDatabase() {
		return instance.db;
	}

	private YAMLProcessor setupConfig(File file) {
		if (!file.exists()) {
			try {
				InputStream input = getClass().getResource("config.yml").openStream();
				if (input != null) {
					FileOutputStream output = null;
					try {
						if (file.getParentFile() != null) {
							file.getParentFile().mkdirs();
						}
						output = new FileOutputStream(file);
						byte[] buf = new byte[8192];
						int length;

						while ((length = input.read(buf)) > 0) {
							output.write(buf, 0, length);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							input.close();
						} catch (Exception ignored) {
						}
						try {
							if (output != null) {
								output.close();
							}
						} catch (Exception e) {
						}
					}
				}
			} catch (Exception e) {
			}
		}

		return new YAMLProcessor(file, false, YAMLFormat.EXTENDED);
	}
}
