package com.indigo.sample.mmorpg.model.player;

import com.indigo.game.common.persistence.AbstractEntity;
import com.indigo.game.common.persistence.annotation.*;

/**
 * @author lixun
 * created at 30/8/18 下午5:05
 */
@Table(name = "player", index = {@Index(name = "idx_player_account_id", columns = {"accountId"}),
								@Index(name = "uidx_player_name", columns = "name")},
		cache = {@Cache(columns = {"accountId"})},
		comment = "角色表")
public class PlayerEntity extends AbstractEntity {

	@Pk(auto = false)
	@Column(name = "id")
	private long id;

	@Column(name = "account_id", length = 64, readOnly = true)
	private String accountId;

	@Column(name = "name", length = 32)
	private String name;

	@Column(name = "gender")
	private int gender;

	@Column(name = "clazz")
	private int clazz;

	@Column(name = "level")
	private int level;

	@Column(name = "map_id")
	private int mapId;

	@Column(name = "x")
	private float x;

	@Column(name = "y")
	private float y;

	@Column(name = "exp")
	private long exp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getClazz() {
		return clazz;
	}

	public void setClazz(int clazz) {
		this.clazz = clazz;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
}
