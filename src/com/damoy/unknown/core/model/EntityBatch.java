package com.damoy.unknown.core.model;

import java.util.Arrays;
import java.util.List;

public class EntityBatch {

	private Model model;
	private List<Entity> entities;
	
	public EntityBatch() {}

	public EntityBatch(Model model, List<Entity> entities) {
		this.model = model;
		this.entities = entities;
		this.entities.forEach(e -> e.setModel(model));
	}
	
	public EntityBatch(Model model, Entity... entities) {
		this.model = model;
		this.entities = Arrays.asList(entities);
	}
	
	public EntityBatch rotate(float dx, float dy, float dz) {
		entities.forEach(e -> e.getRotation().add(dx, dy, dz));
		return this;
	}
	
	public EntityBatch terminate() {
		model.getVao().delete();
		return this;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
	
}