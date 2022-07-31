package com.joebrooks.mapshotimageapi.storage;

import org.springframework.data.repository.CrudRepository;

public interface StorageRepository extends CrudRepository<Storage, String> {
    Storage findByUuid(String uuid);
}
