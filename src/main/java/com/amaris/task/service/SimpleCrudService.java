package com.amaris.task.service;

import java.util.List;
import java.util.Optional;

// Should be move into common lib
public interface SimpleCrudService<ENTITY, ID> {
	public List<ENTITY> getAll();
	public Optional<ENTITY> getById(final ID id);
	public ID save(final ENTITY entity);
	public void update(final ID id, final ENTITY entity);
	public void deleteById(final ID id);
}