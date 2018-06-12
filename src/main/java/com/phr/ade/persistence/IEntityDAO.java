package com.phr.ade.persistence;

import java.util.List;

import org.slim3.datastore.ModelMeta;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.AbstractEntity;

public interface IEntityDAO
{
    Key addModel(AbstractEntity model);

    List<Key> addModels(Iterable<AbstractEntity> models);

    void deleteModel(Key key);

    void deleteModel(List<Key> keys);

    void deleteAll(ModelMeta modelMeta);

    Entity getModel(Key key);

    List<Entity> getModels(List<Key> keys);
}
